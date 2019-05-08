var SceneTask = {
    taskTable: {
        id: 'task_table',
        params: {method: 'GET', 'search': true, 'showColumns': false, 'showRefresh': false},
        columns: [
            {field: 'selectItem', checkbox: true},
            {title: '设备ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
            {title: '设备名', field: 'devicename', align: 'center', valign: 'middle', sortable: true},
            {title: '检查周期', field: 'statecheckintervalinsecond', align: 'center', valign: 'middle', sortable: true},
            {title: '不在预置位报警时间', field: 'vdinvalidviewtime', align: 'center', valign: 'middle', sortable: true},
            {title: '报警间隔', field: 'vdalarmcooldown', align: 'center', valign: 'middle', sortable: true},
            {title: '断流报警时间', field: 'vdstreamfailtime', align: 'center', valign: 'middle', sortable: true}
        ]
    },
    deviceTable: {
        id: 'device_table',
        params: {method: 'GET', 'search': true, 'showColumns': false, 'showRefresh': false},
        columns: [
            {field: 'selectItem', checkbox: true},
            {title: '设备ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
            {title: '设备名称', field: 'devicename', align: 'center', valign: 'middle', sortable: true}
        ]
    }
};
var pattern = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
/**
 * 打开弹出层
 */
SceneTask.openLayer = function (title, content, callback, size) {
    var maxWidth = size ? size.width : document.body.clientWidth * 0.8;
    var maxHeight = size ? size.height : document.body.clientHeight * 0.8;
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: [maxWidth+'px', maxHeight+'px'],
        fix: false,
        maxmin: true,
        content: content,
        success: function (layero, index) {
            if(callback){
                callback();
            }
        },
        end: function () {

        }
    });
};

SceneTask.initTaskTable = function () {
    var table = new BSTable(this.taskTable.id, '/ali_scene_check_config/list', this.taskTable.columns, this.taskTable.params);
    table.setPaginationType("client");
    this.taskTable.instance = table.init();
};

SceneTask.addTask = function (event) {
    $(event.target).blur();
    var _this = this;
    var callback = function () {
        var table = new BSTable(_this.deviceTable.id, '/ali_scene_check_config/device_not_in_config_list', _this.deviceTable.columns, _this.deviceTable.params);
        table.setPaginationType("client");
        _this.deviceTable.instance = table.init();
    };
    this.openLayer("添加任务", $("#addTask").html(), callback);
};

//批量添加
SceneTask.saveTask = function () {
    var _this = this;
    var devices = $('#'+_this.deviceTable.id).bootstrapTable('getSelections');
    if(!devices.length){
        Feng.info('请选择设备');
        return;
    }
    var ids = new Array();
    devices.forEach(function (t) {
       ids.push(t.deviceid);
    });

    var statecheckintervalinsecond = $("#check-statecheckintervalinsecond").val();
    var vdinvalidviewtime = $("#check-vdinvalidviewtime").val();
    var vdalarmcooldown = $("#check-vdalarmcooldown").val();
    var vdstreamfailtime = $("#check-vdstreamfailtime").val();



    if(!statecheckintervalinsecond || statecheckintervalinsecond<=0 || statecheckintervalinsecond>=2147483647 || pattern.test(statecheckintervalinsecond)){
        Feng.info('请输入大于0的整数');
        return;
    }


    if(!vdinvalidviewtime || vdinvalidviewtime<=0 || vdinvalidviewtime>=2147483647 || pattern.test(vdinvalidviewtime)){
        Feng.info('请输入大于0的整数');
        return;
    }
    if(!vdalarmcooldown || vdalarmcooldown<=0 || vdalarmcooldown>=2147483647 || pattern.test(vdalarmcooldown)){
        Feng.info('请输入大于0的整数');
        return;
    }
    if(!vdstreamfailtime || vdstreamfailtime<=0 || vdstreamfailtime>=2147483647 || pattern.test(vdstreamfailtime)){
        Feng.info('请输入大于0的整数');
        return;
    }

    var ajax = new $ax("/ali_scene_check_config/batch_add", function (json) {
        if(json.code==200){
            $('#'+_this.deviceTable.id).bootstrapTable('destroy');
            layer.close(_this.layerIndex);
            Feng.success("添加成功");
            $('#'+_this.taskTable.id).bootstrapTable('refresh');
        }else{
            Feng.error(json.message);
        }
    }, function () {
        Feng.error("添加任务异常");
    });
    ajax.set({
        devices: ids.join(','),
        statecheckintervalinsecond: statecheckintervalinsecond,
        vdinvalidviewtime: vdinvalidviewtime,
        vdalarmcooldown: vdalarmcooldown,
        vdstreamfailtime: vdstreamfailtime
    });
    ajax.start();
};

SceneTask.configTask = function (event) {
    $(event.target).blur();
    var tasks = $('#'+this.taskTable.id).bootstrapTable('getSelections');
    if(!tasks.length){
        Feng.info("请选择任务");
        return;
    }
    var callback = function () {
        if(tasks.length==1){
            $("#config-statecheckintervalinsecond").val(tasks[0].statecheckintervalinsecond);
            $("#config-vdinvalidviewtime").val(tasks[0].vdinvalidviewtime);
            $("#config-vdalarmcooldown").val(tasks[0].vdalarmcooldown);
            $("#config-vdstreamfailtime").val(tasks[0].vdstreamfailtime);
        }
    };
    this.openLayer("配置任务", $("#configTask").html(), callback);
};
//批量配置
SceneTask.saveConfig = function () {
    var _this = this, statecheckintervalinsecond = $("#config-statecheckintervalinsecond").val();
    var _this = this, vdinvalidviewtime = $("#config-vdinvalidviewtime").val();
    var _this = this, vdalarmcooldown = $("#config-vdalarmcooldown").val();
    var _this = this, vdstreamfailtime = $("#config-vdstreamfailtime").val();

    if(!statecheckintervalinsecond || statecheckintervalinsecond<=0 || statecheckintervalinsecond>=2147483647 || pattern.test(statecheckintervalinsecond)){
        Feng.info('请输入大于0的整数')
        return;
    }

    if(!vdinvalidviewtime || vdinvalidviewtime<=0 || vdinvalidviewtime>=2147483647 || pattern.test(vdinvalidviewtime)){
        Feng.info('请输入大于0的整数');
        return;
    }

    if(!vdalarmcooldown || vdalarmcooldown<=0 || vdalarmcooldown>=2147483647 || pattern.test(vdalarmcooldown)){
        Feng.info('请输入大于0的整数');
        return;
    }

    if(!vdstreamfailtime || vdstreamfailtime<=0 || vdstreamfailtime>=2147483647 || pattern.test(vdstreamfailtime)){
        Feng.info('请输入大于0的整数');
        return;
    }
    var selected = $('#'+_this.taskTable.id).bootstrapTable('getSelections');
    var ids = new Array();
    selected.forEach(function (t) {
        ids.push(t.deviceid);
    });
    var ajax = new $ax("/ali_scene_check_config/batch_config", function (json) {
        if(json.code==200){
            layer.close(_this.layerIndex);
            Feng.success("配置成功");
            $('#'+_this.taskTable.id).bootstrapTable('refresh');
        }else{
            Feng.error(json.message);
        }
    }, function () {
        Feng.error("配置任务异常");
    });
    ajax.set({
        ids: ids.join(','),
        statecheckintervalinsecond: statecheckintervalinsecond,
        vdinvalidviewtime: vdinvalidviewtime,
        vdalarmcooldown: vdalarmcooldown,
        vdstreamfailtime: vdstreamfailtime
    });
    ajax.start();
};

//批量删除
SceneTask.removeTask = function (event) {
    $(event.target).blur();
    var _this = this;
    var selected = $('#'+_this.taskTable.id).bootstrapTable('getSelections');

    if(!selected.length){
        Feng.info("请选择任务");
        return;
    }
    Feng.confirm('是否删除选中的任务？', function () {
        var ids = new Array();
        selected.forEach(function (t) {

            ids.push(t.deviceid);
        });

        var ajax = new $ax("/ali_scene_check_config/batch_delete", function (json) {
            if(json.code==200){
                $('#'+_this.taskTable.id).bootstrapTable('refresh');
                Feng.success("删除成功");
            }else{
                Feng.error(json.message);
            }
        }, function () {
            Feng.error("删除任务异常");
        });
        ajax.set('ids', ids.join(','));
        ajax.start();
    });
};

$(function () {
    SceneTask.initTaskTable();
});