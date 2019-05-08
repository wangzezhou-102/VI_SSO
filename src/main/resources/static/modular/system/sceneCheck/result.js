var SceneResult = {
    table: {
        id: 'result_table',
        params: {method: 'GET', 'search': false, 'showColumns': false, 'showRefresh': false, sortOrder: 'asc'}
    }
};

SceneResult.customResultCell = function (value, rowData, index) {
    var result = "";
    if(value==0){
        result = '<span style="color: green;">未偏移</span>';
    }else if(value==1){
        result = '<span style="color: #f8ac59;">已偏移</span>';
    }
    return result;
};

SceneResult.customImageCell = function (value, rowData, index) {
    return '<img src="'+value+'" alt="检测截图" class="viewer" style="width: 150px;height: 90px;border-radius: 4px;">';
};

SceneResult.table.columns = [
    {title: '设备ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
    {title: '设备名', field: 'devicename', align: 'center', valign: 'middle', sortable: true},
    {title: '检测结果', field: 'isoffset', align: 'center', valign: 'middle', sortable: true, formatter: SceneResult.customResultCell},
    {title: '检测截图', field: 'image', align: 'center', valign: 'middle', formatter: SceneResult.customImageCell},
    {title: '检测时间', field: 'checktime', align: 'center', valign: 'middle', sortable: true}
];

SceneResult.initResultTable = function () {
    var table = new BSTable(this.table.id, '/scene_check_result/list', this.table.columns, this.table.params);
    table.setPaginationType("server");
    this.table.instance = table.init();
};

SceneResult.search = function () {
    var formArray = $(document.forms['result-filter']).serializeArray(), param = new Object();
    formArray.forEach(function (t) {
        param[t.name] = t.value;
    });
    this.table.instance.refresh({query: param});
};

SceneResult.reset = function () {
    this.table.instance.refresh({query: {beginTime: '', endTime: '', keyword: '', result: -1}});
};

$(function () {
    SceneResult.initResultTable();
    $('#'+SceneResult.table.id).on('post-body.bs.table', function (e, arg1) {
        $('.viewer').bootstrapViewer();
    });
});