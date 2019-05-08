var Overview = {
    id: 'streamAccount_table',
    tableParams: {'search': true, 'showRefresh': true, 'showColumns': false},
    table: null,
    overviewInterval: -1,
    tableInterval: -1
};

Overview.alarmFit = function (state) {
    var result = '['+state+'] ';
    switch (state.toString()){
        case "0": result = result + "正常";
            break;
        case "1": result = result + "设备网关启动失败(信令交互失败)";
            break;
        case "2": result = result + "设备网关启动失败(启动信令超时)";
            break;
        case "3": result = result + "设备网关启动失败(重复启动)";
            break;
        case "4": result = result + "设备网关启动失败(设备不存在)";
            break;
        case "5": result = result + "设备网关启动失败(vmcsvr的请求报文内容错误)";
            break;
        case "6": result = result + "无数据流";
            break;
        default: result = result + "状态不匹配";
            break;
    }
    return result;
};

/**
 * 自定义表格列
 */
Overview.startCell = function (value, rowData, index) {
    return rowData.stream_start_count + '/' + rowData.stream_stop_count;
};
Overview.onlineCell = function (value, rowData, index) {
    return rowData.online_count + '/' + rowData.offline_count;
};
Overview.bindCell = function (value, rowData, index) {
    var text = "";
    if(rowData.hasOwnProperty("notbind_count")){
        text = rowData.bind_count + '/' + rowData.notbind_count;
    }else{
        text = rowData.bind_count;
    }
    return text;
};
Overview.pushCell = function (value, rowData, index) {
    return rowData.push_count + '/' + rowData.notpush_count;
};
Overview.writeCell = function (value, rowData, index) {
    return rowData.topic_active;
};
Overview.operCell = function (value, rowData, index) {
    return '<a href="/stream?account='+rowData.userid+'">查看</a>';
};
Overview.dvAlarmCell = function (value, rowData, index) {
    return Overview.alarmFit(rowData.dvstate);
};
Overview.checkCell = function (value, rowData, index) {
    return '<a href="/stream/device_detail?deviceid='+rowData.deviceid+'">查看</a>';
};
/**
 * 选择器
 * @type {{username: {url: string, columns: [null,null,null,null,null,null,null]}, name: {url: string, columns: [null,null,null,null,null,null]}}}
 */
Overview.filter = {
    username: {
        url: '/overview/srs_table_data',
        columns: [
            {title: '账号', field: 'username', align: 'center', valign: 'middle', sortable: true},
            {title: '绑定', field: 'bind', align: 'center', valign: 'middle', sortable: true, formatter: Overview.bindCell},
            {title: '推送/未推送', field: 'push', align: 'center', valign: 'middle', sortable: true, formatter: Overview.pushCell},
            {title: '启动/未启动', field: 'start', align: 'center', valign: 'middle', sortable: true, formatter: Overview.startCell},
            {title: '在线/离线', field: 'online', align: 'center', valign: 'middle', sortable: true, formatter: Overview.onlineCell},
            {title: '正在写入', field: 'write', align: 'center', valign: 'middle', sortable: true, formatter: Overview.writeCell},
            {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: Overview.operCell}
        ]
    },
    areaname: {
        url: '/overview/area_table_data',
        columns: [
            {title: '区域', field: 'name', align: 'center', valign: 'middle', sortable: true},
            {title: '绑定/未绑定', field: 'bind', align: 'center', valign: 'middle', sortable: true, formatter: Overview.bindCell},
            {title: '推送/未推送', field: 'push', align: 'center', valign: 'middle', sortable: true, formatter: Overview.pushCell},
            {title: '启动/未启动', field: 'start', align: 'center', valign: 'middle', sortable: true, formatter: Overview.startCell},
            {title: '在线/离线', field: 'online', align: 'center', valign: 'middle', sortable: true, formatter: Overview.onlineCell},
            {title: '正在写入', field: 'write', align: 'center', valign: 'middle', sortable: true, formatter: Overview.writeCell}
        ]
    },
    alarm: {
        url: '/overview/stream_alarm_data',
        columns: [
            {title: 'ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
            {title: '设备名', field: 'devicename', align: 'center', valign: 'middle', sortable: true},
            {title: '报警信息', field: 'dvAlarm', align: 'center', valign: 'middle', sortable: true, formatter: Overview.dvAlarmCell},
            {title: '操作', field: 'check', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: Overview.checkCell}
        ]
    }
};

/**
 * 加载总览数据
 */
Overview.loadOverData = function () {
    var ajax = new $ax("/overview/overview_data", function (data) {
        for(var x in data){
            $("."+x).text(data[x]);
        }
    }, function (data) {
        Feng.error("请求总览数据异常");
    });
    ajax.start();
};
/**
 * 初始化表格
 */
Overview.initTabel = function (url, columns) {
    url = url?url: this.filter.username.url;
    columns = columns?columns: this.filter.username.columns;
    this.table = new BSTable(this.id, url, columns, this.tableParams);
    this.table.setPaginationType("client");
    this.table.init();
};

/**
 *  切换表格数据
 */
Overview.reloadTable = function (name) {
    var filter =  this.filter[name];
    $('#'+this.id).bootstrapTable('destroy');
    Overview.initTabel(filter.url, filter.columns);
};

/**
 *
 */
$("#streamAccount_tableToolbar").on('click', 'button', function () {
    var $this = $(this);
    $this.blur();
    if($this.hasClass('btn-active')) return;
    $('button').removeClass('btn-active');
    $this.addClass('btn-active');
    Overview.reloadTable($this.prop('name'));
});

Overview.overviewInterval = setInterval(Overview.loadOverData, 5000);
Overview.tableInterval = setInterval(function () {
    var _this = Overview;
    var name = $("button.btn-active").prop("name");
    var url = _this.filter[name].url;
    var ajax = new $ax(url, function (data) {
        $('#'+_this.id).bootstrapTable('load', data);
    }, function (data) {
        Feng.error("请求表格数据异常");
    });
    ajax.setParam('async', true);
    ajax.start();
}, 5000);

window.onbeforeunload = function () {
    clearInterval(Overview.overviewInterval);
    clearInterval(Overview.tableInterval);
};

$(function () {
    Overview.loadOverData();
    Overview.initTabel();
});