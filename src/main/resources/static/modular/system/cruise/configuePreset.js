var CruisePreset = {
    id: 'preset_table',
    tableParams: {},
    table: null,
    deviceid: -1,
    setItem: null, //当前编辑预置位对象
    setItemIndex: -1,  //当前编辑预置位index
    presetCounts: -1,  //已配置预置位数目
    layerIndex: -1,
};
/**
 * 自定义表格列
 */
CruisePreset.customIndexCell = function (value, rowData, index) {
    return index;
};
CruisePreset.customStatusCell = function (value, rowData, index) {
    var initStatus = "";
    if(value){
        initStatus = "checked";
    }
    return '<div class="switch switch-small">' +
        '<input type="checkbox" '+initStatus+' name="preset-status" />' +
        '</div>';
};
CruisePreset.customOperCell = function (value, rowData, index) {
    return '<span class="glyphicon glyphicon-arrow-up arrow-up" style="color: #388bff;"></span>' +
        '<span class="glyphicon glyphicon-arrow-down arrow-down" style="color: #388bff;margin-left: 8px;"></span>' +
        '<span class="glyphicon glyphicon-edit arrow-edit" style="color: #388bff;margin-left: 8px;"></span>' +
        '<span class="glyphicon glyphicon-remove arrow-remove" style="color: red;margin-left: 8px;"></span>';
};
/**
 * 表格自定义回调
 */
CruisePreset.tableParams.onPostBody = function () {
    $('input[name="preset-status"]').bootstrapSwitch({
        onText: "启用",
        offText: "禁用",
        onColor: "success",
        offColor: "danger",
        size: "mini",
        onSwitchChange: CruisePreset.singleStateChange,
    });
};
/**
 * 表格事件
 */
function updateSort(param) {
    var state = false;
    $.ajax({
        type: 'post',
        url:  baseUrl + '/cruise/cruiseupdates',
        contentType: 'application/json',
        data: JSON.stringify(param),
        async: false,
        success: function () {
            Feng.info('修改成功');
            state = true;
        }
    });
    return state;
}
window.presetEvents = {
    /**
     * 向上调整巡航路线点
     */
    'click .arrow-up': function (e, value, row, index) {
        if(index<=0){
            Feng.info('已经是第一条');
            return;
        }
        var $PresetTable = $('#'+CruisePreset.id);
        var prevRow = $PresetTable.bootstrapTable('getData')[index-1];
        var prevRowCopy = {};
        for(var p in prevRow){
            prevRowCopy[p] = prevRow[p];
        }
        var prevRowSort = prevRow.sortno;
        prevRowCopy.sortno = row.sortno;
        row.sortno = prevRowSort;
        var param = [];
        param.push({id: row.id, sortno: row.sortno});
        param.push({id: prevRowCopy.id, sortno: prevRowCopy.sortno});
        if(updateSort(param)) {
            $PresetTable.bootstrapTable('updateRow', {index: index-1,row: row});
            $PresetTable.bootstrapTable('updateRow', {index: index,row: prevRowCopy});
        }
    },
    /**
     * 向下调整巡航路线点
     */
    'click .arrow-down': function (e, value, row, index) {
        var $PresetTable = $('#'+CruisePreset.id);
        var data = $PresetTable.bootstrapTable('getData');
        if(index==(data.length-1)){
            Feng.info('已经是最后一条');
            return;
        }
        var nextRow = data[index+1];
        var nextRowCopy = {};
        for(var p in nextRow){
            nextRowCopy[p] = nextRow[p];
        }
        var nextRowSort = nextRow.sortno;
        nextRowCopy.sortno = row.sortno;
        row.sortno = nextRowSort;
        var param = [];
        param.push({id: row.id, sortno: row.sortno});
        param.push({id: nextRowCopy.id, sortno: nextRowCopy.sortno});
        if(updateSort(param)) {
            $PresetTable.bootstrapTable('updateRow', {index: index+1,row: row});
            $PresetTable.bootstrapTable('updateRow', {index: index,row: nextRowCopy});
        }
    },
    /**
     * 编辑巡航路线点
     */
    'click .arrow-edit': function (e, value, row, index) {
        var callback = function () {
            $('#preset').val(row.presetid).trigger("change").attr('disabled', true);
            $('#speed').val(row.speed);
            $('#residence').val(row.residence);
            $('input[value="'+row.using+'"]').prop('checked', true);
            CruisePreset.setItem = row;
            CruisePreset.setItemIndex = index;
        };
        CruisePreset.openLayer('编辑巡航路径', callback);
    },
    /**
     * 删除巡航路线点
     */
    'click .arrow-remove': function (e, value, row, index) {
        var oper = function () {
            var ajax = new $ax(baseUrl + "/cruise/cruisedel", function (data) {
                var ids = [];
                ids.push(row.id);
                $('#'+CruisePreset.id).bootstrapTable('remove', {field: 'id',values: ids});
                CruisePreset.presetCounts = $('#'+CruisePreset.id).bootstrapTable('getData').length;CruisePreset.table = table.init();
            }, function (data) {
                Feng.error("删除失败");
            });
            ajax.set('cruiseid', row.id);
            ajax.start();
        };
        Feng.confirm('是否删除巡航线路？', oper);
    }
};
/**
 * 表格字段
 */
CruisePreset.columns = [
    {title: 'rowindex', field: 'rowindex', class: 'hidden', formatter:CruisePreset.customIndexCell},
    {title: 'id', field: 'id', class: 'hidden'},
    {title: 'ID', field: 'presetid', align: 'center', valign: 'middle', sortable: true, width: '50px'},
    {title: '预置位名称', field: 'presetname', align: 'center', valign: 'middle', sortable: true},
    {title: '速度', field: 'speed', align: 'center', valign: 'middle', sortable: true},
    {title: '时间', field: 'residence', align: 'center', valign: 'middle', sortable: true},
    {title: '状态', field: 'using', align: 'center', valign: 'middle', sortable: true, width: '150px', formatter:CruisePreset.customStatusCell},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '120px',formatter: CruisePreset.customOperCell, events: presetEvents}
];
/**
 * 修改单个设备状态
 */
CruisePreset.singleStateChange = function (event, state) {
    var target = event.target, tips = "";
    if(state){
        tips = "启用";
    }else{
        tips = "禁用";
    }
    var $tr = $(target).closest('tr');
    var rowIndex = $tr.find('td:eq(0)').text();
    var cruiseid = $tr.find('td:eq(1)').text();
    var param = {id: cruiseid, using: +state};
    var ajax = new $ax(baseUrl + "/cruise/cruiseupdate", function (data) {
        Feng.success(tips + "成功");
        $('#'+CruisePreset.id).bootstrapTable('updateCell', {index: rowIndex, field: 'using',value: +state});
    }, function (data) {
        Feng.error(tips + "失败");
        $(target).bootstrapSwitch('state', !state);
    });
    ajax.set(param);
    ajax.start();
};
/**
 *  初始化预置位表格数据
 */
CruisePreset.initPresets = function (layerHeight) {
    CruisePreset.tableParams.height = layerHeight * 0.75;
    var table = new BSTable(CruisePreset.id, baseUrl + "/cruise/cruiselist", CruisePreset.columns, CruisePreset.tableParams);
    table.setPaginationType("client");
    table.setQueryParams({planid: CruiseDevice.planid, deviceid: CruisePreset.deviceid});
    CruisePreset.table = table.init();
    CruisePreset.presetCounts = $('#'+CruisePreset.id).bootstrapTable('getData').length;
};
/**
 * 打开弹出框
 */
CruisePreset.openLayer = function (title, callback) {
    CruisePreset.layerIndex = layer.open({
        type: 1,
        title: title,
        area: ['450px', '350px'],
        fix: false,
        maxmin: true,
        content: $('#addCruisePoint').html(),
        success: function (index, layero) {
            $.post(baseUrl + '/cruise/presetlist',  {deviceid: CruisePreset.deviceid}, function (data) {
                var html = "", $presetSelect = $('#preset');
                $presetSelect.empty();
                $.each(data, function (i, item) {
                    html = html + '<option value="'+item.presetid+'">'+item.presetname+'</option>';
                });
                $presetSelect.append(html);
                if(callback) {
                    callback();
                }
            });

        },
        end: function () {
            if(CruisePreset.setItem) {
                CruisePreset.setItem = null;
                CruisePreset.setItemIndex = -1;
            }
        }
    });
};

/**
 * 添加预置位
 */
CruisePreset.addCruisePoint = function () {
    this.openLayer('添加路线');
};
/**
 * 保存预置位
 */
function getSortno() {
    var sortno = 0;
    var data = $('#'+CruisePreset.id).bootstrapTable('getData');
    $.each(data, function (i, item) {
        if(item.sortno>sortno){
            sortno = item.sortno;
        }
    });
    return parseInt(sortno) + 1;
}
CruisePreset.saveCruisePoint = function () {
    var url = "";
    var preset = $('#preset').find('option:selected').val();
    var presetname = $('#preset').find('option:selected').text();
    var speed = $('#speed').val();
    var residence = $('#residence').val();
    var status = $($('input[name="status"]:checked')[0]).prop('value');
    if(!/^\d+$/.test(speed) || !/^[1-9]\d*$/.test(speed)){
        Feng.info('速度输入不合法，请重新输入正整数');
        return;
    }
    if(!/^\d+$/.test(residence) || !/^[1-9]\d*$/.test(residence)){
        Feng.info('时间输入不合法，请重新输入正整数');
        return;
    }
    var param = {
        planid: CruiseDevice.planid,
        deviceid: CruisePreset.deviceid,
        presetid: parseInt(preset),
        residence: parseInt(residence),
        speed: parseInt(speed),
        using: parseInt(status)
    };
    if(this.setItem){
        url = baseUrl + '/cruise/cruiseupdate';
        param.id = this.setItem.id;
        param.planid = this.setItem.planid;
        param.deviceid = this.setItem.deviceid;
        param.presetid = this.setItem.presetid;
        param.sortno = this.setItem.sortno;

    }else{
        url = baseUrl + '/cruise/cruiseadd';
        param.sortno = getSortno();
    }
    var ajax = new $ax(url, function (data) {
        Feng.success("保存成功");
        layer.close(CruisePreset.layerIndex);
        if(CruisePreset.setItem){
            CruisePreset.setItem = $.extend(CruisePreset.setItem, param);
            $('#'+CruisePreset.id).bootstrapTable('updateRow', {index: CruisePreset.setItemIndex, row: CruisePreset.setItem});
        }else{
            param.id = data.message;
            param.presetname = presetname;
            $('#'+CruisePreset.id).bootstrapTable('append', param);
            CruisePreset.presetCounts = $('#'+CruisePreset.id).bootstrapTable('getData').length;
        }
    }, function (data) {
        Feng.error("保存失败");
    });
    ajax.set(param);
    ajax.start();
};