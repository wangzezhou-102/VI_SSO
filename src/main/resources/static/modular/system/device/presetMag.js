var PresetMager = {
    id: "preset_table",	//表格id
    deviceid: "",//预置位所在设备id
    setItem: null,		//选中的条目
    setItemIndex: undefined,
    editItem: null,
    table: null,
    tableParams: {selectItemName:"presetItem"},    //表格事件对象
    counts: -1,//预置位个数
    positionType: [],  //道路类型列表
    yjpresetList: [],  //银江预置位列表
    layerIndex: -1,
};
/**
 * 表格列初始化
 */
PresetMager.customPositionCol = function (value, rowData, index) {
    if(value){
        return value;
    }else{
        value = rowData.positiontype.split(',');
        var positionName = [];
        for(var i=0;i<value.length;i++){
            for(var j=0;j<PresetMager.positionType.length;j++){
                if(PresetMager.positionType[j].id==value[i]){
                    positionName.push(PresetMager.positionType[j].name);
                    break;
                }
            }
        }
        return positionName.join(',');
    }
};
PresetMager.yjpresetCol = function (value, rowData, index) {
    if(!deviceManager.seItem.yjdeviceid){
        return '设备未匹配';
    }else{
        return value;
    }
};
PresetMager.Columns = [
    {field: 'selectItem', radio: true},
    {title: '预置位编号', field: 'presetid', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '预置位名称', field: 'presetname', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '预置位别名', field: 'presetalias', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '道路类型', field: 'positionname', visible: true, sortable: true, align: 'center', valign: 'middle', formatter:PresetMager.customPositionCol},
    {title: '银江预置位映射编号', field: 'presetidyj', visible: true, sortable: true, align: 'center', valign: 'middle', formatter:PresetMager.yjpresetCol},
    {title: '场景图片数', field: 'viewcount', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '线圈数', field: 'regioncount', sortable: true, visible: true, align: 'center', valign: 'middle'}
];

PresetMager.tableParams.onClickRow = function (row, $element, field) {
    PresetMager.setItemIndex = $element.data('index');
};
/**
 * 获取道路类型
 */
PresetMager.getPositionType = function () {
    $.post(baseUrl + "/device/proplist",function (data) {
        PresetMager.positionType = data;
    });
};
/**
 * 获取银江预置位列表
 */
PresetMager.getYJPreset = function () {
    var ajax = new $ax(baseUrl + "/preset/yjpresetlist", function (data) {
        PresetMager.yjpresetList = data;
    }, function (data) {
        Feng.error("获取银江预置位失败：" + data.message + "!");
    });
    ajax.set("deviceidyj", deviceManager.seItem.yjdeviceid);
    ajax.start();
};
/**
 *  初始化道路类型下拉列表
 */
PresetMager.initPosition = function () {
    var options = "";
    $('#positionType').empty();
    $.each(PresetMager.positionType, function (i ,item) {
        options = options + '<option value="' + item.id + '">' + item.name + '</option>';
    });
    $('#positionType').append(options);
};
/**
 * 初始化银江预置位下拉列表
 */
PresetMager.initYJPreset = function () {
    var options = "";
    $('#presetidyj').empty();
    $.each(PresetMager.yjpresetList, function (i ,item) {
        options = options + '<option value="' + item.presetidyj + '">' + item.presetidyj + ":" + item.presetnameyj + '</option>';
    });
    $('#presetidyj').append(options);
};
/**
 * 初始化预置位
 */
PresetMager.init = function () {
    PresetMager.deviceid = deviceManager.seItem.deviceid;
    var table = new BSTable(PresetMager.id, baseUrl + "/preset/presetlist", PresetMager.Columns, PresetMager.tableParams);
    table.setPaginationType("client");
    table.setQueryParams({deviceid:PresetMager.deviceid});
    PresetMager.table = table.init();
    PresetMager.getPositionType();
    if(deviceManager.seItem.yjdeviceid){
        PresetMager.getYJPreset();
    }
};
/**
 * 检查是否选中
 */
PresetMager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        PresetMager.setItem = selected[0];
        return true;
    }
};

function checkPreset() {
    var presetname = $('#presetname').val();
    var presetid = $('#presetid').val();
    if(!presetname){
        Feng.error("请输入预置位名称");
        return false;
    }
    if(!presetid){
        Feng.error("请输入预置位编号");
        return false;
    }
    if(presetid<1){
        Feng.error("预置位编号从1开始");
        return false;
    }
    var tableDatas = $('#'+PresetMager.id).bootstrapTable('getData');
    var isExist = false;
    tableDatas.forEach(function (t) {
       if(t.presetid==presetid) {
           if(!PresetMager.editItem || presetid!=PresetMager.editItem.presetid){
               isExist = true;
               return;
           }
       }
    });
    if(isExist){
        Feng.error("预置位编号已存在，请重新输入");
        return false;
    }
    return true;
}
/**
 * 添加预置位
 */
PresetMager.addPreset = function () {
    PresetMager.layerIndex = layer.open({
        type: 1,
        title: "添加预置位",
        area: ['600px', '410px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#preset_addlayer').html(),
        success: function (index, layero) {
            var tableDatas = $('#'+PresetMager.id).bootstrapTable('getData');
            var maxNumber = 1;
            tableDatas.forEach(function (t) {
                if(t.presetid>maxNumber){
                    maxNumber = parseInt(t.presetid);
                }
            });
            $('#presetid').val(maxNumber+1);
            PresetMager.initPosition();
            PresetMager.initYJPreset();
        }
    });
};
/**
 * 编辑预置位
 */
PresetMager.editPreset = function () {
    if(this.check()){
        PresetMager.layerIndex = layer.open({
            type: 1,
            title: "编辑预置位",
            area: ['600px', '410px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: $('#preset_addlayer').html(),
            success: function (index, layero) {
                PresetMager.editItem = PresetMager.setItem;
                PresetMager.initPosition();
                PresetMager.initYJPreset();
                $('#presetname').val(PresetMager.setItem.presetname);
                $('#presetid').val(PresetMager.setItem.presetid).attr('disabled', true);
                $('#presetalias').val(PresetMager.setItem.presetalias);
                var value = PresetMager.setItem.positiontype.split(','), positionName = [];
                for(var i=0;i<value.length;i++){
                    for(var j=0;j<PresetMager.positionType.length;j++){
                        if(PresetMager.positionType[j].id==value[i]){
                            $('#positionType').val(value[i]).trigger('change');
                            break;
                        }
                    }
                }
                $('#presetidyj').val(PresetMager.setItem.presetidyj).trigger('change');
            },
            end: function () {
                PresetMager.editItem = null;
            }
        });
    }
};
/**
 * 保存预置位
 */
PresetMager.savePreset = function () {
    if(checkPreset()){
        var presetname = $('#presetname').val();
        var presetid = $('#presetid').val();
        var presetalias = $('#presetalias').val();
        var positiontype = $('#positionType').find('option:selected').val();
        var positionname = $('#positionType').find('option:selected').text();
        var presetidyj = $('#presetidyj').find('option:selected').val();
        if(!presetidyj){
            presetidyj = 0;
        }
        var presetData = {
            deviceid: this.deviceid,
            presetid: parseInt(presetid),
            presetname: presetname,
            presetalias: presetalias,
            positiontype: positiontype,
            presetidyj: parseInt(presetidyj)
        };
        if(PresetMager.editItem){
            $.post(baseUrl + "/preset/presetupdate", presetData,function (result) {
                Feng.success("修改成功");
                layer.close(PresetMager.layerIndex);
                presetData.positionname = positionname;
                var row = $.extend(PresetMager.setItem, presetData);
                $('#'+PresetMager.id).bootstrapTable('updateRow', {index: PresetMager.setItemIndex, row: row});
            });

        }else{
            $.post(baseUrl + "/preset/presetadd", presetData,function (result) {
                Feng.success("保存成功");
                layer.close(PresetMager.layerIndex);
                presetData.positionname = positionname;
                $('#'+PresetMager.id).bootstrapTable('append',presetData);
                PresetMager.counts = $('#'+PresetMager.id).bootstrapTable('getData').length;
            });
        }
    }
};
/**
 * 关闭预置位弹出层
 */
PresetMager.layer_cancel = function () {
    layer.close(PresetMager.layerIndex);
};
/**
 * 删除预置位
 */
PresetMager.deletePreset = function () {
    if (this.check()) {
        var operation = function(){
            var deviceid = PresetMager.deviceid;
            var presetid = PresetMager.setItem.presetid;
            var ajax = new $ax(baseUrl + "/preset/presetdel", function () {
                Feng.success("删除成功!");
                $('#' + PresetMager.id).bootstrapTable('remove', {field:"selectItem",values:[true]});
                PresetMager.counts = $('#'+PresetMager.id).bootstrapTable('getData').length;
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set({"deviceid":deviceid, "presetid": presetid});
            ajax.start();
        };
        Feng.confirm("是否删除预置位：" + PresetMager.setItem.presetname + "?", operation);
    }
};

/**
 * 修改预置位编号
 */
PresetMager.updateNum = function () {
    if(this.check()){
        PresetMager.layerIndex = layer.open({
            type: 1,
            title: "修改预置位编号",
            area: ['600px', 'auto'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: $('#edit_presetNum').html(),
            success: function (index, layero) {
                $('input[name="oldnum"]').val(PresetMager.setItem.presetid);
            }
        });
    }
};

/**
 * 保存预置位编号
 */
PresetMager.saveNum = function () {
    var oldNum = PresetMager.setItem.presetid;
    var newNum = $('input[name="newnum"]').val();
    if(!/^[1-9]\d*$/.test(newNum)){
        Feng.error("编号设置不合法（正整数）");
        return false;
    }
    var ajax = new $ax(baseUrl + "/preset/update_presetid", function (data) {
        if(data.code==200){
            PresetMager.setItem.presetid = newNum;
            $('#'+PresetMager.id).bootstrapTable('updateRow', {index: PresetMager.setItemIndex, row: PresetMager.setItem});
            Feng.success("修改成功");
            layer.close(PresetMager.layerIndex);
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("修改预置位编号请求异常");
    });
    ajax.set({"deviceid": this.deviceid ,"presetid":oldNum, "newpresetid": newNum});
    ajax.start();
};

/**
 * 场景管理
 */
PresetMager.sceneMager = function () {

};
/**
 * 线圈管理
 */
PresetMager.regionMager = function () {

};
/**
 * 预置位管理
 */
PresetMager.presetMag = function () {
    var deviceId = deviceManager.seItem.deviceid;
    var ajax = new $ax(Feng.ctxPath + "/device/unmapping", function () {
        Feng.success("解除成功!");
        deviceManager.table.refresh();
    }, function (data) {
        Feng.error("解除失败!" + data.message + "!");
    });
    var deviceInfo = {
        sourceplateformid:"1",						//源平台id
        sourcedeviceid:"330117010016022471",		//源设备id
        targetplateformid:"2",						//目标平台id
        targetdeviceid:"330117010016022472",		//目标设备id
        devicealias:"设备别名",						//设备别名
        presetposittiontype:"1001001001"			//该设备下预置位道路类型
    };
    ajax.set(deviceInfo);
    ajax.start();
};