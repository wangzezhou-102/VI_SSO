var baseUrl = '';
var deviceManager = {
    id: "device_table",	//表格id
    seItem: null,		//选中的条目
    setItemIndex: undefined,
    table: null,
    tableParams: {selectItemName:"deviceItem", 'showRefresh': true, 'showColumns': false, 'search': false},
    layerIndex: -1,
    zTreeInstance: null,
    areaObj: {},
    YJCount: -1
};
/**
 * 表格列初始化
 */
deviceManager.customCol = function (value, rowData, index) {
    if(rowData.yjdeviceid){
        return rowData.yjpresetcount + '/' + rowData.userdyjpreset;
    }else{
        return '未匹配';
    }
};
deviceManager.plateformCol = function (value, rowData, index) {
    if(isNaN(value)) {
        return value;
    }else{
        return $('#platform').find('option[value="'+value+'"]').text();
    }
};
deviceManager.statusCell = function (value, rowData, index) {
    if(value==1){
        return '<span style="color: green;">已启用</span>';
    }else if(value==0){
        return '<span style="color: red;">已禁用</span>';
    }
};
deviceManager.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '设备名', field: 'devicename', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '设备ID', field: 'deviceid', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '逻辑号', field: 'logicid', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '别名', field: 'devicealias', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '区域', field: 'areaname', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '平台', field: 'plateformname', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '预置位个数/已使用', field: 'yjpresetcount', sortable: true, visible: true, align: 'center', valign: 'middle', formatter:deviceManager.customCol},
        {title: '状态', field: 'enabled', sortable: true, visible: true, align: 'center', valign: 'middle', formatter:deviceManager.statusCell}
        ];
};

deviceManager.tableParams.onClickRow = function (row, $element, field) {
    deviceManager.setItemIndex = $element.data('index');
    if(row.yjdeviceid) {
        $('#btn_match').attr('data-flag', '1').text('解除匹配');
    }else{
        $('#btn_match').attr('data-flag', '0').text('匹配设备');
    }
};

/**
 * 搜索设备
 */
deviceManager.search = function () {
    var keyword = $('#device_name').val();
    if(keyword=="" || keyword==undefined){
        keyword = null;
    }
    var areacode = $('#device_area').attr('areacode');
    if(areacode=="" || areacode==undefined){
        areacode = 0;
    }
    var platform = $('#platform').find('option:selected').val();
    if(platform=="" || platform==undefined){
        platform = 0;
    }
    var ajax = new $ax(baseUrl + "/device/devicelist", function (data) {
        $('#' + deviceManager.id).bootstrapTable('load', data);
    }, function (data) {
        Feng.error("获取设备失败：" + data.message + "!");
    });
    ajax.set({keyword:keyword, areacode:areacode, platetype:platform});
    ajax.start();
};
/**
 * 重置搜索
 */
deviceManager.resetSearch = function () {
    $.post(baseUrl + "/device/devicelist", {keyword:null,areacode:0,platetype:0}, function (data) {
        $('#'+deviceManager.id).bootstrapTable('load', data);
    });
    var treeObj = $.fn.zTree.getZTreeObj("areaMenuTree");
    var nodes = treeObj.getSelectedNodes();
    if (nodes.length>0) {
        treeObj.cancelSelectedNode(nodes[0]);
        $('#device_area').removeAttr('areacode')
    }
};

/**
 * 检查是否选中
 */
deviceManager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    var data = $('#' + this.id).bootstrapTable('getData');
    if (selected.length == 0) {
        Feng.info("请选择设备！");
        return false;
    } else {
        deviceManager.seItem = selected[0];
        $.each(data, function (i, item) {
            if(item.deviceid == deviceManager.seItem.deviceid){
                deviceManager.setItemIndex = i;
            }
        });
        return true;
    }
};
/**
 * 打开弹出层
 */
deviceManager.openLayer = function (title, id, initLayer, callback, width, height) {
    var maxWidth = document.body.clientWidth * 0.7;
    var maxHeight = document.body.clientHeight * 0.7;
    if (this.check()) {
        width = width ? width : maxWidth;
        height = height ? height : maxHeight;
        deviceManager.layerIndex = layer.open({
            type: 1,
            title: title,
            area: [width+'px', height+'px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: $('#'+id).html(),
            success: function (index, layero) {
                if(initLayer){
                    initLayer();
                }
                $('#btn_save').click(function () {
                    if(callback){
                        callback();
                    }
                });
            },
            end: function () {
                if(PresetMager.counts!=-1){
                    deviceManager.seItem.presetcount = PresetMager.counts;
                    $('#'+deviceManager.id).bootstrapTable('updateRow', {index: deviceManager.setItemIndex, row: deviceManager.seItem});
                }
                if($('#form_device').data("bootstrapValidator")) {
                    $('#form_device').data('bootstrapValidator').destroy();
                }
                if(PresetMager.yjpresetList.length || PresetMager.positionType.length){
                    PresetMager.yjpresetList = [];
                    PresetMager.positionType = [];
                }
            }
        });
    }
};
/**
 * 设备详情
 */
deviceManager.detail = function () {
    var deviceId = deviceManager.seItem.deviceid;
    var ajax = new $ax(baseUrl + "/device/detail", function (data) {
        $('#id').text(data.deviceid);
        $('#father_id').text(data.parentid);
        $('#name').text(data.devicename);
        $('#alias').text(data.devicealias);
        $('#logic').text(data.logicid);
        $('#type').text(data.type);
        $('#ip').text(data.ip);
        $('#password').text(data.pwd);
        $('#create_time').text(data.createtime);
        $('#update_time').text(data.modifytime);
        $('#platform_name').text(data.plateformname);
        $('#area').text(data.areaname);
    }, function (data) {
        Feng.error("获取设备详情失败：" + data.message + "!");
    });
    ajax.set("deviceid", deviceId);
    ajax.start();
};
/**
 * 选择区域
 */
deviceManager.setArea = function () {
    var areaname = deviceManager.seItem.areaname;
    var areacode = deviceManager.seItem.areacode;
    $('#devicearea').val(areaname);
    $('#devicearea').attr('areacode', areacode);
    var ztree = new $ZTree("areaMDMenuTree", baseUrl + "/areacode/areacodelist");
    ztree.bindOnClick(deviceManager.areaObj.onClickMenue);
    ztree.init();

};
/**
 * 更新区域
 */
deviceManager.upArea = function () {
    var deviceid = deviceManager.seItem.deviceid;
    var reArae = $('#devicearea').val();
    var areacode = $('#devicearea').attr('areacode');
    if(reArae){
        var ajax = new $ax(baseUrl + "/device/updatearea", function () {
            layer.close(deviceManager.layerIndex);
            Feng.success('修改成功');
            $('#' + deviceManager.id).bootstrapTable('updateCell',{index: deviceManager.setItemIndex, field: "areaname", value: reArae});
        }, function (data) {
            Feng.error("修改设备区域失败：" + data.message + "!");
        });
        ajax.set({"deviceid":deviceid,"areacode":areacode});
        ajax.start();
    }else{
        Feng.error("请选择设备区域");
    }
};
/**
 * 设置别名
 */
deviceManager.setAlias = function () {
    var devicealias = deviceManager.seItem.devicealias;
    $('#device_alias').val(devicealias);
};
/**
 * 更新别名
 */
deviceManager.upAlias = function () {
    var deviceId = deviceManager.seItem.deviceid;
    var reAlias = $('#device_alias').val();
    var ajax = new $ax(baseUrl + "/device/updatedevicealias", function () {
        layer.close(deviceManager.layerIndex);
        Feng.success('修改成功');
        $('#' + deviceManager.id).bootstrapTable('updateCell',{index: deviceManager.setItemIndex, field: "devicealias", value: reAlias});
    }, function (data) {
        Feng.error("修改设备别名失败：" + data.message + "!");
    });
    if(reAlias.trim()){
        ajax.set({"deviceid": deviceId, "devicealias": reAlias.trim()});
        ajax.start();
    }else{
        Feng.error("请输入设备别名");
    }
};
/**
 * 设置逻辑号
 */
deviceManager.setLogic = function () {
    var logicNumber = deviceManager.seItem.logicid;
    $('#logic_number').val(logicNumber);
};
/**
 * 更新逻辑号
 */
deviceManager.upLogic = function () {
    var deviceId = deviceManager.seItem.deviceid;
    var reLogic = $('#logic_number').val();
    var ajax = new $ax(baseUrl + "/device/updatelogicid", function () {
        layer.close(deviceManager.layerIndex);
        Feng.success('修改成功');
        $('#' + deviceManager.id).bootstrapTable('updateCell',{index: deviceManager.setItemIndex, field: "logicid", value: reLogic});
    }, function (data) {
        Feng.error("修改设备逻辑号失败：" + data.message + "!");
    });
    if(!/^\d{1,10}$/.test(reLogic)){
        Feng.error('设备逻辑号不合法');
        return;
    }
    if(reLogic){
        ajax.set({"deviceid": deviceId, "logicid": reLogic});
        ajax.start();
    }else{
        Feng.error("请输入设备逻辑号");
    }

};
/**
 * 匹配设备
 */
deviceManager.initMatchData = function () {
    // $.ajax({
    //     type : "post",
    //     url : baseUrl + '/device/plateformlist',
    //     async : false,
    //     success : function(data){
    //         var options = "";
    //         $('#match_plate').empty();
    //         data.forEach(function (item) {
    //             if(item.plateid!=deviceManager.seItem.plateid){
    //                 options = options + '<option value="' + item.plateid + '">' + item.platename + '</option>';
    //             }
    //         });
    //         $('#match_plate').append(options);
    //         $('#match_plate').change(function () {
    //             var platecode = $(this).find('option:selected').val();
    //             $('#match_table').bootstrapTable("removeAll");
    //             $.post(baseUrl + '/device/devicelist', {keyword:null, areacode:0, platetype:platecode}, function (data) {
    //                 $('#match_table').bootstrapTable('append', data);
    //             });
    //         });
    //     }
    // });
    // var defaultPlate = $('#match_plate').find('option:first-child').val();
    var onClickRow = function (row, $element, field) {
        $('#YJname').val(row.devicename);
    };
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '设备名', field: 'devicename', visible: true, sortable: true, align: 'center', valign: 'middle'},
        {title: '设备ID', field: 'deviceid', visible: true, sortable: true, align: 'center', valign: 'middle'}];
    var table = new BSTable('match_table', baseUrl + "/device/devicelist", columns, {selectItemName:"plateDeviceItem", search:true, onClickRow: onClickRow});
    table.setQueryParams({keyword:null, areacode:0, platetype:'YJ'});
    table.setPaginationType("client");
    deviceManager.subtable = table.init();
    $('#GBname').val(deviceManager.seItem.devicename);
    $.post(baseUrl + "/device/proplist",function (data) {
        var options = "";
        $('#road_type').empty();
        data.forEach(function (item) {
            options = options + '<option value="' + item.id + '">' + item.name + '</option>';
        });
        $('#road_type').append(options);
    });
};
/**
 * 提交匹配
 */
deviceManager.startMatch = function () {
    var srcdeviceId = deviceManager.seItem.deviceid;
    var selected = $('#match_table').bootstrapTable('getSelections');
    if(!selected.length){
        Feng.error('请选择设备');
        return;
    }
    var targetdeviceid = selected[0].deviceid;
    var radio = $('input[name="alias"]:checked');
    var text = radio.next('input').val().trim();
    if(radio.val()=="custom"){
        if(!text){
            Feng.error('请输入自定义别名');
            return;
        }
    }
    var roadType = $('#road_type').find('option:selected').val();
    var ajax = new $ax(baseUrl + "/device/mapping", function (data) {
        if(data.code==200){
            Feng.success('匹配成功');
            layer.close(deviceManager.layerIndex);
            deviceManager.seItem.logicid = selected[0].deviceid;
            deviceManager.seItem.yjdeviceid = targetdeviceid;
            deviceManager.seItem.yjpresetcount = data.message;
            deviceManager.seItem.devicealias = text;
            $('#'+deviceManager.id).bootstrapTable('updateRow', {index: deviceManager.setItemIndex, row: deviceManager.seItem});
            $('#btn_match').attr('data-flag', '1').text('解除匹配');
        }else {
            Feng.info('匹配失败');
        }

    }, function (data) {
        Feng.error("请求异常：" + data.message + "!");
    });
    var parm = {
        sourcedeviceid:srcdeviceId,
        targetdeviceid:targetdeviceid,
        devicealias:text,
        presetposittiontype:roadType
    }
    ajax.set(parm);
    ajax.start();
};
/**
 * 解除匹配
 */
deviceManager.relieve = function () {
    if (this.check()) {
        var operation = function(){
            var param = {
                "sourcedeviceid": deviceManager.seItem.deviceid,
                'targetdeviceid': deviceManager.seItem.yjdeviceid
            };
            var ajax = new $ax(baseUrl + "/device/unmapping", function () {
                Feng.success("解除成功!");
                deviceManager.seItem.yjpresetcount = 0;
                delete deviceManager.seItem.yjdeviceid;
                $('#'+deviceManager.id).bootstrapTable('updateRow', {index: deviceManager.setItemIndex, row: deviceManager.seItem});
                $('#btn_match').attr('data-flag', '0').text('匹配设备');
            }, function (data) {
                Feng.error("解除失败：" + data.message + "!");
            });
            ajax.set(param);
            ajax.start();
        };
        Feng.confirm("是否解除设备：" + deviceManager.seItem.devicename + "匹配?", operation);
    }
};
/**
 * 预置位管理
 */
deviceManager.presetMag = function () {
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

/**
 * 设备启用
 */
deviceManager.deviceEnable = function () {
    if(this.check()){
        var ajax = new $ax("/device/enable_device", function (data) {
            if(data.code==200){
                Feng.success("设备启用成功");
                $('#' + deviceManager.id).bootstrapTable('updateCell', {index: deviceManager.setItemIndex, field: "enabled", value: 1});
            }else{
                Feng.info(data.message);
            }
        }, function (data) {
            Feng.error("请求启用设备异常");
        });
        ajax.set("devices", this.seItem.deviceid);
        ajax.setParam('async', true);
        ajax.start();
    }
};

/**
 * 设备禁用
 */
deviceManager.deviceDisable = function () {
    if(this.check()){
        var ajax = new $ax("/device/disable_device", function (data) {
            if(data.code==200){
                Feng.success("设备禁用成功");
                $('#' + deviceManager.id).bootstrapTable('updateCell', {index: deviceManager.setItemIndex, field: "enabled", value: 0});
            }else{
                Feng.info("设备流正在启动中，禁用失败");
            }
        }, function (data) {
            Feng.error("请求禁用设备异常");
        });
        ajax.set("devices", this.seItem.deviceid);
        ajax.setParam('async', true);
        ajax.start();
    }
};

/**
 * 同步topic
 */
deviceManager.syncTopic = function () {
    var newTopic = "<h3>新建Topic</h3>", overdue = "<h3>过期Topic</h3>", layerIndex;
    var ajax = new $ax("/device/sync_topic", function (data) {
        if(data.code==200){
            newTopic = newTopic + data.newTopic.join("</br>");
            overdue = overdue + data.overdue.join("</br>");
            layerIndex = layer.confirm(newTopic+"</br>"+overdue, {
                title: '提示',
                btn: ['知道了'],
                area: ['auto', '500px'], //宽高
                btnAlign: 'c'
            }, function (index) {
                layer.close(layerIndex);
            }, function (index) {
                layer.close(layerIndex);
            });
        }
    }, function (data) {
        Feng.error("同步topic请求异常");
    });
    ajax.start();
};

/**
 * 删除设备
 */
deviceManager.delete = function () {
    if (this.check()) {
        var operation = function(){
            var deviceId = deviceManager.seItem.deviceid;
            var ajax = new $ax(baseUrl + "/device/devicedel", function (data) {
                if(data.code==200){
                    Feng.success("删除成功");
                    $('#' + deviceManager.id).bootstrapTable('remove', {field:"selectItem",values:[true]});
                    // deviceManager.table.refresh();
                }else {
                    Feng.info(data.message);
                }
            }, function (data) {
                Feng.error("删除设备请求失败！");
            });
            ajax.set("deviceid", deviceId);
            ajax.start();
        };
        Feng.confirm("是否删除设备：" + deviceManager.seItem.devicename + "?", operation);
    }
};

/**
 * 打开区域选择树
 */
deviceManager.areaObj.openMenue = function (event) {
    var e = event || window.event;
    var target = e.target;
    var divContainer = $(target).next();
    if(divContainer.css('display')=='none') {
        divContainer.css({
            // left : areaOffset.left + "px",
            top : $(target).outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
};

/**
 * 关闭区域选择树
 */
deviceManager.areaObj.closeMenue = function() {
    $(".menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
};

deviceManager.areaObj.onClickMenue = function(e, treeId, treeNode) {
    var currentTarget = e.currentTarget;
    var input = $(currentTarget).parent().prev('input')[0];
    $(input).val(treeNode.name);
    $(input).attr({'areacode': treeNode.id});
    if(!treeNode.isParent) {
        deviceManager.areaObj.closeMenue();
    }
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "areaMenu" || $(
            event.target).parents(".menuContent").length > 0)) {
        deviceManager.areaObj.closeMenue();
    }
}
/**
 * 关闭弹出层
 */
deviceManager.layer_cancel = function () {
    layer.close(deviceManager.layerIndex);
};

/**
 * 按钮事件
 */
$('button').click(function (e) {
    switch (e.target.id){
        case "btn-filter": deviceManager.search();
            break;
        case "btn_edit": deviceManager.openLayer('设备编辑', 'add_device', DeviceObj.editDevice, null, 700, 'auto');
            break;
        case "btn_info": deviceManager.openLayer('设备详情', 'device_info', deviceManager.detail);
            break;
        case "btn_area": deviceManager.openLayer('选择区域', 'select_area', deviceManager.setArea, deviceManager.upArea, 600, 260);
            break;
        case "btn_alias": deviceManager.openLayer('设备别名', 'set_alias', deviceManager.setAlias, deviceManager.upAlias, 500, 200);
            break;
        case "btn_logic": deviceManager.openLayer('设备逻辑号', 'set_logicNum', deviceManager.setLogic, deviceManager.upLogic, 500, 200);
            break;
        case "btn_match":
            if($(e.target).attr('data-flag')=='0'){
                deviceManager.openLayer('设备匹配', 'device_match', deviceManager.initMatchData, deviceManager.startMatch);
            }else if($(e.target).attr('data-flag')=='1')
                deviceManager.relieve();
            break;
        case "btn_preset": deviceManager.openLayer('预置位管理', 'device_preset', PresetMager.init);
            break;
        case "btn_deviceEnable": deviceManager.deviceEnable();
            break;
        case "btn_deviceDisable": deviceManager.deviceDisable();
            break;
        case "btn_delete": deviceManager.delete();
            break;
        case "btn_syncTopic": deviceManager.syncTopic();
            break;
        case "btn_areaMag": AreaMager.init();
            break;
    }
});

deviceManager.initSelection = function ($ele, url) {
    $.post(baseUrl + url, function(data){
        var options = "";
        $ele.empty();
        data.forEach(function (item) {
            options = options + '<option value="' + item.plateid + '">' + item.platename + '</option>';
        });
        $ele.append(options);
    });
};

$(function () {
    deviceManager.initSelection($('#platform'), '/device/plateformlist');

    var defaultColunms = deviceManager.initColumn();
    var table = new BSTable(deviceManager.id, baseUrl + "/device/devicelist", defaultColunms, deviceManager.tableParams);
    table.setPaginationType("client");
    table.setQueryParams({keyword:null,areacode:0,platetype:0});
    deviceManager.table = table.init();

    var ztree = new $ZTree("areaMenuTree", baseUrl + "/areacode/areacodelist");
    ztree.bindOnClick(deviceManager.areaObj.onClickMenue);
    deviceManager.zTreeInstance = ztree.init();

});