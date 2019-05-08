var baseUrl = '';
var AccessMager = {
    id: 'plate_table',
    table: null,
    tableParams: {},
    setItem: null,
};

/**
 * 表格列
 */
AccessMager.columns = [
    {field: 'selectItem', radio: true},
    {title: '平台名称', field: 'platename', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '设备接入数量', field: 'deviceAccessCount', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '设备在线数量', field: 'deviceONlineCount', visible: true, sortable: true, align: 'center', valign: 'middle'},
    {title: '最后同步时间', field: 'modifytime', visible: true, sortable: true, align: 'center', valign: 'middle'},
];

/**
 * 检查表格项选中
 */
AccessMager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        AccessMager.setItem = selected[0];
        return true;
    }
};

/**
 * 平台同步
 */
AccessMager.sentSync = function (type, plateformid) {
    var time = "";
    var ajax = new $ax(baseUrl + "/access/syncdata", function (data) {
        time = data.gbchecklasttime;
    },function (data) {
        Feng.info('同步失败');
    });
    ajax.set({
        'plateformid': plateformid,
        'opt': type
    });
    ajax.start();
    return time;
};

AccessMager.plateSync = function () {
    if(!this.check()){
        return;
    }
    var oper = function () {
        $('.sk-circle').removeClass('hidden');
        var s = 1;
        var plateid = AccessMager.setItem.plateid;
        var firstTime = AccessMager.sentSync('sync', plateid);
        var index = setInterval(function () {
            s++;
            var updateTime = AccessMager.sentSync('check', plateid);
            if(s>300){
                clearInterval(index);
                //关闭进度条
                $('.sk-circle').addClass('hidden');
                alert('同步超时');
            }
            if(updateTime>firstTime){
                clearInterval(index);
                //关闭进度条
                $('.sk-circle').addClass('hidden');
                alert('同步成功');
            }
        },1000);
    };
    Feng.confirm('确定同步平台', oper);
};
/**
 * 同步银江
 */
AccessMager.YJSync = function () {
    var oper = function () {
        $('.sk-circle').removeClass('hidden');
        var s = 1;
        var firstTime = AccessMager.sentSync('sync', 'YJ');
        var index = setInterval(function () {
            s++;
            var updateTime = AccessMager.sentSync('check', 'YJ');
            if(s>300){
                clearInterval(index);
                //关闭进度条
                $('.sk-circle').addClass('hidden');
                alert('同步超时');
            }
            if(updateTime>firstTime){
                clearInterval(index);
                //关闭进度条
                $('.sk-circle').addClass('hidden');
                alert('同步成功');
            }
        },1000);
    };
    Feng.confirm('确定同步银江平台', oper);
};
/**
 * 接入测试
 */
AccessMager.accessTest = function () {
    if(!this.check()){
        return;
    }
    var oper = function () {
        $.post(baseUrl + "", function (data) {
            Feng.success('接入正常');
        });
        Feng.success('接入正常');
    };
    Feng.confirm('确定开始平台接入测试', oper);
};

$(function () {
    var table = new BSTable(AccessMager.id, baseUrl + "/access/plateformlist", AccessMager.columns, AccessMager.tableParams);
    table.setPaginationType("client");
    table.setQueryParams({keyword:null,areacode:0,platetype:0});
    AccessMager.table = table.init();
});