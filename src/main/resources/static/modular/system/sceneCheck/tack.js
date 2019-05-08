var SceneTask = {
    taskTable: {
        id: 'task_table',
        params: {method: 'GET', 'search': true, 'showColumns': false, 'showRefresh': false},
        columns: [
            {field: 'selectItem', checkbox: true},
            {title: '设备ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
            {title: '设备名', field: 'devicename', align: 'center', valign: 'middle', sortable: true},
            {title: '检测间隔', field: 'interval', align: 'center', valign: 'middle', sortable: true}
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
    var table = new BSTable(this.taskTable.id, '/scene_check_task/list', this.taskTable.columns, this.taskTable.params);
    table.setPaginationType("client");
    this.taskTable.instance = table.init();
};

SceneTask.addTask = function (event) {
    $(event.target).blur();
    var _this = this;
    var callback = function () {
        var table = new BSTable(_this.deviceTable.id, '/scene_check_task/device_not_in_task_list', _this.deviceTable.columns, _this.deviceTable.params);
        table.setPaginationType("client");
        _this.deviceTable.instance = table.init();
    };
    this.openLayer("添加任务", $("#addTask").html(), callback);
};

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
    var interval = $("#check-interval").val();
    if(!interval || interval<=0){
        Feng.info('请输入大于0的间隔时间');
        return;
    }
    var ajax = new $ax("/scene_check_task/batch_add", function (json) {
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
        interval: interval
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
            $("#config-interval").val(tasks[0].interval);
        }
    };
    this.openLayer("配置任务", $("#configTask").html(), callback, {width: 600, height: 200});
};

SceneTask.saveConfig = function () {
    var _this = this, interval = $("#config-interval").val();
    if(!interval || interval<=0){
        Feng.info('请输入大于0的间隔时间');
        return;
    }
    var selected = $('#'+_this.taskTable.id).bootstrapTable('getSelections');
    var ids = new Array();
    selected.forEach(function (t) {
        ids.push(t.id);
    });
    var ajax = new $ax("/scene_check_task/batch_config", function (json) {
        if(json.code==200){
            layer.close(_this.layerIndex);
            Feng.success("配置成功");
            $('#'+_this.taskTable.id).bootstrapTable('refresh');
        }else{
            Feng.error(json.message);
        }
    }, function () {
        Feng.error("删除任务异常");
    });
    ajax.set({
        ids: ids.join(','),
        interval: interval
    });
    ajax.start();
};

SceneTask.restartTask = function (event) {
    var tasks = $('#'+this.taskTable.id).bootstrapTable('getSelections');
    if(!tasks.length){
        Feng.info("请选择任务");
        return;
    }
    $(event.target).button('loading');
    var ids = new Array();
    tasks.forEach(function (t) {
        ids.push(t.id);
    });
    var ajax = new $ax("/scene_check_task/batch_apply", function (json) {
        if(json.code==200){
            $(event.target).button('reset');
            Feng.success("重启成功");
        }else{
            Feng.error(json.message);
        }
    }, function () {
        Feng.error("重启任务异常");
    });
    ajax.set('ids', ids.join(','));
    ajax.start();
};

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
            ids.push(t.id);
        });
        var ajax = new $ax("/scene_check_task/batch_delete", function (json) {
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