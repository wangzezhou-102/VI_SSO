var CruiseDevice = {
    id: 'device_table',
    tableParams: {pagination: false, search: true},
    table: null,
    planid: -1,
    layerIndex: -1,
};
/**
 * 自定义表格列
 */
CruiseDevice.customIndexCell = function (value, rowData, index) {
    return index;
};
CruiseDevice.customCountCell = function (value, rowData, index) {
    return rowData.devicepresetcount + '/' + rowData.presetcount;
};
CruiseDevice.customStatusCell = function (value, rowData, index) {
    // var initStatus = "";
    // if(value){
    // initStatus = "checked";
    // }
    // return '<div class="switch switch-small">' +
    //     '<input type="checkbox" '+initStatus+' name="device-status" />' +
    //     '</div>';
    if(value){
        return '<button type="button" class="btn btn-primary btn-xs" style="margin-bottom: 0;" data-state="'+value+'" onclick="CruiseDevice.DeviceStateChange(event)">已启用</button>';
    }else{
        return '<button type="button" class="btn btn-danger btn-xs" style="margin-bottom: 0;" data-state="'+value+'" onclick="CruiseDevice.DeviceStateChange(event)">已禁用</button>';
    }
};
CruiseDevice.customOperCell = function (value, rowData, index) {
    return '<button type="button" class="btn btn-primary btn-xs device-configure" style="margin-bottom: 0;">配置</button>';
};
/**
 * 表格自定义回调
 */
CruiseDevice.tableParams.onPostBody = function () {
    $('input[name="device-status"]').bootstrapSwitch({
        onText: "启用",
        offText: "禁用",
        onColor: "success",
        offColor: "danger",
        size: "mini",
        onSwitchChange: CruiseDevice.singleStateChange,
    });
};
/**
 * 表格事件
 */
window.deviceEvents = {
    /**
     * 配置设备预置位
     */
    'click .device-configure': function (e, value, row, index) {
        CruiseDevice.layerIndex = layer.open({
            type: 1,
            title: '配置预置位',
            area: ['800px', '500px'],
            fix: false,
            maxmin: true,
            content: $('#configuePreset').html(),
            success: function (layero, index) {
                CruisePreset.deviceid = row.deviceid;
                CruisePreset.initPresets($(layero).height());
            },
            end: function () {
                if(CruisePreset.deviceid) {
                    CruisePreset.deviceid = -1;
                }
                if(CruisePreset.presetCounts) {
                    $('#'+CruiseDevice.id).bootstrapTable('updateCell', {index: index, field: 'presetcount',value: CruisePreset.presetCounts});
                    CruisePreset.presetCounts = -1;
                }
            }
        });
    },
};
/**
 * 表格字段
 */
CruiseDevice.columns = [
    {field: 'selectItem', checkbox: true},
    {title: 'rowindex', field: 'rowindex', class: 'hidden', formatter:CruiseDevice.customIndexCell},
    {title: 'ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true, class: 'hidden'},
    {title: '逻辑号', field: 'logicid', align: 'center', valign: 'middle', sortable: true},
    {title: '设备名称', field: 'devicename', align: 'center', valign: 'middle', sortable: true},
    {title: '预置位数目/已配置', field: 'presetcount', align: 'center', valign: 'middle', sortable: true, width:"50px", formatter:CruiseDevice.customCountCell},
    {title: '状态', field: 'deviceusing', align: 'center', valign: 'middle', sortable: true, width: '150px', formatter:CruiseDevice.customStatusCell},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '50px',formatter: CruiseDevice.customOperCell, events: deviceEvents}
];

CruiseDevice.DeviceStateChange = function (event) {
    var target = event.target, devicelist = [], tips = "";
    var state = $(target).attr('data-state');
    var $tr = $(target).closest('tr');
    var rowIndex = $tr.find('td:eq(1)').text();
    var deviceid = $tr.find('td:eq(2)').text();
    if(state=='0'){
        tips = "启用";
        state = 1;
        devicelist.push({deviceid: deviceid, deviceusing: state});
    }else{
        tips = "禁用";
        state = 0;
        devicelist.push({deviceid: deviceid, deviceusing: state});
    }
    var ajax = new $ax(baseUrl + "/cruise/enabledevice", function (data) {
        Feng.success(tips + "成功");
        $('#'+CruiseDevice.id).bootstrapTable('updateCell', {index: rowIndex, field: 'deviceusing',value: state});
        $(target).attr('data-state', state);
    }, function (data) {
        Feng.error(tips + "失败");
        // $(target).bootstrapSwitch('state', !state);
    });
    ajax.set({planid: CruiseDevice.planid, opts: JSON.stringify(devicelist)});
    ajax.start();
};
/**
 * 修改单个设备状态
 */
CruiseDevice.singleStateChange = function (event, state) {
    var target = event.target, devicelist = [], tips = "";
    if(state){
        tips = "启用";
    }else{
        tips = "禁用";
    }
    var $tr = $(target).closest('tr');
    var rowIndex = $tr.find('td:eq(1)').text();
    var deviceid = $tr.find('td:eq(2)').text();
    devicelist.push({deviceid: deviceid, deviceusing: +state});
    var ajax = new $ax(baseUrl + "/cruise/enabledevice", function (data) {
        Feng.success(tips + "成功");
        $('#'+CruiseDevice.id).bootstrapTable('updateCell', {index: rowIndex, field: 'deviceusing',value: +state});
    }, function (data) {
        Feng.error(tips + "失败");
        $(target).bootstrapSwitch('state', !state);
    });
    ajax.set({planid: CruiseDevice.planid, opts: JSON.stringify(devicelist)});
    ajax.start();
};
/**
 * 批量修改设备状态
 */
CruiseDevice.multiStateChange = function (event) {
    var buttonname = event.target.name, devices = [], tips = "";
    var selections = $('#'+CruiseDevice.id).bootstrapTable('getSelections');
    if(selections.length<=0){
        Feng.info('请选择设备');
        return;
    }
    if(buttonname=='enable') {
        tips = "启用";
        selections.forEach(function (t) {
            var obj = {deviceid: t.deviceid, deviceusing: t.deviceusing};
            if(!t.deviceusing){
                obj.deviceusing = 1;
            }
            devices.push(obj);
        });
    }else if(buttonname=='disabled') {
        tips = "禁用";
        selections.forEach(function (t) {
            var obj = {deviceid: t.deviceid, deviceusing: t.deviceusing};
            if(t.deviceusing){
                obj.deviceusing = 0;
            }
            devices.push(obj);
        });
    }
    var ajax = new $ax(baseUrl + "/cruise/enabledevice", function (data) {
        Feng.success(tips + "成功");
        CruiseDevice.table.refresh();
    }, function (data) {
        Feng.error(tips + "失败");
    });
    ajax.set({planid: CruiseDevice.planid, opts: JSON.stringify(devices)});
    ajax.start();
};
/**
 * 保存设备配置
 */
CruiseDevice.saveConfigue = function () {
    var presetCruises = $('#'+CruisePreset.id).bootstrapTable('getData');
    var ajax = new $ax(baseUrl + '/cruise/cruiseupdates', function (data) {
        Feng.success("保存成功");
        layer.close(CruiseDevice.layerIndex);
    }, function (data) {
        Feng.error("保存失败");
    });
    ajax.set(JSON.stringify(presetCruises));
    ajax.start();
};
/**
 * 设备全选
 */
CruiseDevice.selectAll = function () {
    $('#'+this.id).bootstrapTable('checkAll');
};
/**
 * 取消全选
 */
CruiseDevice.cancalAll = function () {
    $('#'+this.id).bootstrapTable('uncheckAll');
};
/**
 *  初始化设备表格数据
 */
CruiseDevice.initDevices = function (layerHeight) {
    CruiseDevice.tableParams.height = layerHeight * 0.75;
    var table = new BSTable(CruiseDevice.id, baseUrl + "/cruise/devicelist", CruiseDevice.columns, CruiseDevice.tableParams);
    table.setPaginationType("client");
    table.setQueryParams({planid: CruiseDevice.planid, areacode: 0});
    CruiseDevice.table = table.init();

    CruiseDevice.initArea();
};
/**
 * 初始化区域列表
 */
CruiseDevice.initArea = function () {
    var ajax = new $ax(baseUrl + '/cruise/arealist', function (data) {
        var html = "", $area = $('#area');
        $area.empty();
        $area.append('<option value="all">全部</option>');
        $.each(data, function (i, item) {
            html = html + '<option value="'+item.id+'">'+item.name+'</option>';
        });
        $area.append(html);
        $area.change(CruiseDevice.deviceFilter);
    }, function (data) {
        Feng.error("初始化区域失败");
    });
    ajax.start();
};
/**
 * 设备过滤
 */
CruiseDevice.deviceFilter = function () {
    var areacode = $(this).find('option:selected').val();
    if(areacode=='all') {
        areacode = 0;
    }
    $.post(baseUrl + "/cruise/devicelist", {planid: CruiseDevice.planid, areacode: areacode}, function (data) {
        $('#'+CruiseDevice.id).bootstrapTable('removeAll');
        $('#'+CruiseDevice.id).bootstrapTable('append', data);
    });

};