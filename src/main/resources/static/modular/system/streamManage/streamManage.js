var StreamMager = {
    id: "device_table",
    tableParams: {'uniqueId': "deviceid", 'search': true, 'showColumns': false},
    table: null,
    enabled: 1,  //1表示只显示已启用的设备
    accounts: [],
    setItem: null,     //配置数据项
    layerIndex: -1,    //弹出框编号
    intervalIndex: -1, //interval编号
    validateFields: {
        hlsid: {
            message: '接入账号不能为空',
            validators: {
                notEmpty: {
                    message: '接入账号不能为空'
                }
            }
        },
        epsid: {
            message: '发布账号不能为空',
            validators: {
                notEmpty: {
                    message: '发布账号不能为空'
                }
            }
        },
        hlscount: {
            message: 'HLS并发数不能为空',
            validators: {
                notEmpty: {
                    message: 'HLS并发数不能为空'
                },
                regexp: {
                    regexp: /^(0|\+?[1-9][0-9]*)$/,
                    message: 'HLS并发数为0和正整数'
                },
                // greaterThan: {
                //     value: 18
                // },
                // lessThan: {
                //     value: 100
                // }
            }
        },
        hlscrypto: {
            message: '加密类型不能为空',
            validators: {
                notEmpty: {
                    message: '加密类型不能为空'
                }
            }
        },
        streamstyle: {
            message: '协议类型不能为空',
            validators: {
                notEmpty: {
                    message: '协议类型不能为空'
                }
            }
        }
    }
};

/**
 * 页面unload之前清除进程资源
 * @param e
 */
window.onbeforeunload = function(e){
    clearInterval(StreamMager.intervalIndex);
};

/**
 * 自定义表格列
 */
StreamMager.customNameCell = function (value, rowData, index) {
    if(rowData.devicealias){
        return rowData.devicealias;
    }else{
        return rowData.devicename;
    }
};
StreamMager.customStreamStyleCell = function (value, rowData, index) {
    var text = "";
    switch (value.toString()){
        case '0': text = "UDP";
            break;
        case '1': text = "TCP主动模式";
            break;
        case '2': text = "TCP被动模式";
            break;
        default: text = "未知协议";
            break;
    }
    return text;
};
StreamMager.customDeviceOnlineCell = function (value, rowData, index) {
    if(value=="ON"){
        return '<span style="color: green;">在线</span>';
    }else if(value=="OFF"){
        return '<span style="color: #9ea6b9;">离线</span>';
    }else{
        return "未知";
    }
};
StreamMager.customStreamStateCell = function (value, rowData, index) {
    var text = "";
    switch (value.toString()){
        case "0": text = '<span style="color: #9ea6b9;">未启动</span>';
            break;
        case "1": text = '<span style="color: green;">已启动</span>';
            break;
        case "2": text = '<span style="color: #23c6c8;">正在启动...</span>';
            break;
        case "3": text = '<span style="color: #23c6c8;">正在停止...</span>';
            break;
        case "4": text = '<span style="color: #f8ac59;">设备服务断开</span>';
            break;
        case "5": text = '<span style="color: #f8ac59;">SRS服务断开</span>';
            break;
        case "6": text = '<span style="color: #ed5565;">VMC服务异常</span>';
            break;
        case "7": text = '<span style="color: #9ea6b9;">UAC下线</span>';
            break;
        default: text = '<span style="color: #9ea6b9;">未知状态</span>';
            break;
    }
    return text;
};
StreamMager.customIsDeleteCell = function (value, rowData, index) {
    if(value==0){
        return '<span style="color: green;">推送中...</span>';
    }else if(value==1){
        return '<span style="color: #9ea6b9;">未推送</span>';
    }else{
        return "未知";
    }
};
StreamMager.customDeviceUseCell = function (value, rowData, index) {
    if(value==1){
        return '<span style="color: green;">启用</span>';
    }else if(value==0){
        return '<span style="color: #9ea6b9;">禁用</span>';
    }else{
        return "未知";
    }
};
StreamMager.customOperCell = function (value, rowData, index) {
    return '<i class="fa fa-cog" title="配置" style="font-size: 1.7rem;color: #388bff;"></i>' +
        '<i class="fa fa-search" title="查看" style="font-size: 1.7rem;color: #388bff;margin-left: 2rem;"></i>';
};

StreamMager.createTopic = function(deviceid){
    var url = "/stream/create_topic/"+deviceid;
    var createFunc = function(){
        console.log('post %s',url);
        new $ax(url, function (data) {
            console.log(JSON.stringify(data));
        }, function (data) {
            Feng.error("请求账号列表异常");
        }).start();
    }

    Feng.confirm('确认创建'+deviceid+'对应的Topic?', createFunc);

};

StreamMager.customTopicStatusCell = function (value, rowData, index) {
    var tipStr = "";
    switch (value){
        case 0:
            tipStr = '<span ><a style="color: #9ea6b9;" href="javascript:void(0);" onclick="StreamMager.createTopic(\''+rowData.deviceid+'\')" title="点击链接创建">未创建</a></span>';
            break;
        case 1:
            tipStr = '<span style="color: #FFCC00;">未写入</span>';
            break;
        case 2:
            tipStr = '<span style="color: green;">写入中</span>';
            break;
        default:
            tipStr = "未知";
    }

    return tipStr;
};


/**
 * 表格事件
 */
window.deviceEvents = {
    /**
     * 配置设备
     */
    'click .fa-cog': function (e, value, row, index) {
        var callback = function () {
            var $hlsSelect = $("#config_form select[name='hlsid']"), $epsSelect = $("#config_form select[name='epsid']");
            var ajax = new $ax("/stream/services_account_list", function (data) {
                var hlsHtml = "", epsHtml = "";
                $.each(data, function (i, item) {
                    if(item.authlevel==51 || item.authlevel==52 || item.authlevel==54||item.authlevel==77){
                        hlsHtml = hlsHtml + '<option data-type="'+item.authlevel+'" value="'+item.userId+'">'+item.username+'</option>';
                    }else if(item.authlevel==53){
                        epsHtml = epsHtml + '<option data-type="'+item.authlevel+'" value="'+item.userId+'">'+item.username+'</option>';
                    }
                });
                hlsHtml = hlsHtml + '<option data-type="0" value="0">解除账号关联</option>';
                epsHtml = '<option selected data-placeholder="placeholder" style="display:none;">Please Choose</option>' + epsHtml + '<option data-type="0" value="0">解除账号关联</option>';
                $hlsSelect.append(hlsHtml);
                $epsSelect.append(epsHtml);
            }, function (data) {
                Feng.error("请求账号列表异常");
            }).start();
            $hlsSelect.val(row.hlsid).change(function () {
                var hlsText = $(this).find("option:selected").text();
                var hlsType = $(this).find("option:selected").attr("data-type");
                if(hlsType==51){
                    $epsSelect.find("option[data-placeholder=\"placeholder\"]").removeAttr("selected").attr("selected", true).text(hlsText);
                    $epsSelect.attr("disabled", true);
                }else{
                    $epsSelect.val("").attr("disabled", false);
                }
            });
            if($hlsSelect.find('option[value="'+row.hlsid+'"]').attr("data-type")==51){
                $epsSelect.attr("disabled", true);
                $epsSelect.find("option[data-placeholder='placeholder']").text($hlsSelect.find("option:selected").text());
            }else{
                $epsSelect.val(row.epsid);
            }
            $("input[name='hlscount']").val(row.hlscount);
            $("select[name='hlscrypto']").val(row.hlscrypto);
            $("select[name='streamstyle']").val(row.streamstyle);
        };
        StreamMager.openLayer("配置（<i class='fa fa-video-camera'>&nbsp;"+row.deviceid+"</i>）", callback);
        StreamMager.setItem = row;
    },
    /**
     * 查看设备
     */
    'click .fa-search':function (e, value, row, index) {
        location.href = "/stream/device_detail?deviceid="+row.deviceid;
    }
};

/**
 * 表格字段
 */
StreamMager.columns = [
    {field: 'selectItem', checkbox: true},
    {title: '设备ID', field: 'deviceid', align: 'center', valign: 'middle', sortable: true},
    {title: '设备名', field: 'name', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customNameCell},
    {title: '逻辑号', field: 'logicid', align: 'center', valign: 'middle', sortable: true},
    {title: '接入账号', field: 'hls_name', align: 'center', valign: 'middle', sortable: true},
    {title: '发布账号', field: 'eps_name', align: 'center', valign: 'middle', sortable: true},
    {title: '协议类型', field: 'streamstyle', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customStreamStyleCell},
    {title: '设备状态', field: 'status', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customDeviceOnlineCell},
    {title: '推送状态', field: 'isdelete', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customIsDeleteCell},
    // {title: '启用状态', field: 'enabled', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customDeviceUseCell},
    {title: '流状态', field: 'streamstate', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customStreamStateCell},
    {title: 'Topic', field: 'topic_status', align: 'center', valign: 'middle', sortable: true, formatter: StreamMager.customTopicStatusCell},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: StreamMager.customOperCell, events: deviceEvents}
];

/**
 * 打开弹出层
 */
StreamMager.openLayer = function (title, callback) {
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: ["680px", "auto"],
        fix: false,
        maxmin: true,
        content: $('#deviceConfig').html(),
        success: function (layero, index) {
            if(callback){
                callback();
            }
        },
        end: function () {

        }
    });
};

/**
 * 获取账号列表
 */
StreamMager.getAccountList = function () {
    var ajax = new $ax("/stream/services_account_list", function (data) {
        var html = "";
        StreamMager.accounts = data;
        $.each(data, function (i, item) {
            html = html + '<option value="'+item.userId+'">'+item.username+'</option>';
        });
        html = '<option value="-1">所有</option>' + html;
        $("select[name='hlsid']").append(html);
        var param = location.search.substring(1);
        if(param.indexOf("account=")>=0){
            window.sessionStorage.clear();
            $("select[name='hlsid']").val(param.substring(param.indexOf("=")+1)).trigger("change");
        }
    }, function (data) {
        Feng.error("请求账号列表异常");
    }).start();
    this.initFilter();
};

/**
 * 初始化过滤条件
 */
StreamMager.initFilter = function () {
    $("#filterForm select").map(function (i, item) {
        var $item = $(item);
        var value = window.sessionStorage.getItem($item.prop("name"));
        if(value){
            $item.val(value).trigger('change');
        }
    });
    var keyword = window.sessionStorage.getItem("keyword");
    if(keyword){
        $("#filterForm input").val(keyword);
    }
};

/**
 * 初始化设备表格
 */
StreamMager.initDeviceTable = function () {
    var param = new Object();
    $("#filterForm").serializeArray().map(function (item) {
        param[item.name] = item.value;
    });
    param.enabled = this.enabled;

    var pageIndex = window.sessionStorage.getItem("pageIndex");
    var pageSize = window.sessionStorage.getItem("pageSize");
    if(!pageIndex){
        pageIndex = 1;
    }
    if(!pageSize){
        pageSize = 10;
    }
    this.tableParams.pageNumber = parseInt(pageIndex);
    this.tableParams.pageSize = parseInt(pageSize);
    var $table = $("#"+this.id);
    var table = new BSTable(this.id, '/stream/device_list', this.columns, this.tableParams);
    table.setPaginationType("client");
    table.setQueryParams(param);
    this.table = table.init();

    var searchKeyword = window.sessionStorage.getItem("searchKeyword");
    if(searchKeyword){
        $table.bootstrapTable("resetSearch", searchKeyword);
    }
};

/**
 * 查找
 */
StreamMager.search = function () {
    var param = new Object();
    $("#filterForm").serializeArray().map(function (item) {
        param[item.name] = item.value;
    });
    param.enabled = this.enabled;
    var opt = {
        url: '/stream/device_list',
        silent: true,
        query: param
    };
    this.table.refresh(opt);
};

/**
 * 重置
 */
StreamMager.reset = function () {
    var param = {
        enabled: StreamMager.enabled,
        hlsid: -1,
        hlsbind: -1,
        isdelete: -1,
        status: -1,
        streamstate: -1
    };
    var opt = {
        url: '/stream/device_list',
        silent: true,
        query: param
    };
    this.table.refresh(opt);
    window.sessionStorage.clear();
};

/**
 * 设备导出
 */
StreamMager.export = function () {
    var param = new Object();
    $("#filterForm").serializeArray().map(function (item) {
        param[item.name] = item.value;
    });
    param.enabled = this.enabled;
    var paramArr = [];
    for(var x in param){
        paramArr.push(x+"="+param[x]);
    }
    window.open("/stream/export?"+paramArr.join("&"), '_self');
};

/**
 * 流启动
 */
StreamMager.start = function (event) {
    $(event.currentTarget).blur();
    var colects = $('#'+this.id).bootstrapTable('getSelections');
    var ids = new Array();
    if(colects.length==0){
        Feng.info("请选择设备");
        return;
    }
    colects.forEach(function (item) {
        ids.push(item.deviceid);
    });
    var ajax = new $ax("/stream/start", function (data) {
        if(data.code==200){
            Feng.success("操作成功");
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("操作异常");
    });
    ajax.set("devices", ids.join(","));
    ajax.setParam('async', true);
    ajax.start();
};

/**
 * 流停止
 */
StreamMager.stop = function (event) {
    $(event.currentTarget).blur();
    var colects = $('#'+this.id).bootstrapTable('getSelections');
    var ids = new Array();
    if(colects.length==0){
        Feng.info("请选择设备");
        return;
    }
    colects.forEach(function (item) {
        ids.push(item.deviceid);
    });
    var ajax = new $ax("/stream/stop", function (data) {
        if(data.code==200){
            Feng.success("操作成功");
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("操作异常");
    });
    ajax.set("devices", ids.join(","));
    ajax.setParam('async', true);
    ajax.start();
};

/**
 * 验证表单
 */
StreamMager.validate = function () {
    var $form = $('#config_form');
    $form.bootstrapValidator({
        message: 'This value is not valid',
        // excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: [':disabled', ':hidden', ':not(:visible)'],//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:  StreamMager.validateFields
    });
    $form.bootstrapValidator('validate');
    return $form.data('bootstrapValidator').isValid();
};

/**
 * 保存配置
 */
StreamMager.saveConfig = function () {
    if(!this.validate()) {
        return;
    }
    var param = {deviceid: this.setItem.deviceid}, paramArr = $("#config_form").serializeArray();
    paramArr.forEach(function (item) {
        param[item.name] = Number(item.value);
    });
    if(!param.hasOwnProperty("epsid")){
        var eps = $("#config_form select[name='epsid']").find("option:selected").val();
        var hlsType = $("#config_form select[name='hlsid']").find("option:selected").attr("data-type");
        if(hlsType=="51"){
            param.epsid = param.hlsid;
        }else{
            if(eps){
                param.epsid = eps;
            }else{
                param.epsid = 0;
            }
        }
    }
    var ajax = new $ax("/stream/save_device_config", function (data) {
        if(data.code==200){
            Feng.success("配置成功");
            layer.close(StreamMager.layerIndex);
            $.each(StreamMager.accounts, function (i, item) {
                if(item.userId==param.hlsid){
                    param.hls_name = item.username;
                }else if(param.hlsid==0){
                    param.hls_name = "";
                }
                if(item.userId==param.epsid){
                    param.eps_name = item.username;
                }else if(param.epsid==0){
                    param.eps_name = "";
                }
            });
            $('#' + StreamMager.id).bootstrapTable('updateRow', {index: StreamMager.setItem.index, row: $.extend(StreamMager.setItem, param)});
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("请求保存设备配置异常");
    });
    ajax.set(param);
    ajax.start();

};

/**
 * 刷新设备流状态
 */
StreamMager.deviceStreamRefresh = function () {
    var param = new Object();
    $("#filterForm").serializeArray().map(function (item) {
        param[item.name] = item.value;
    });
    param.enabled = StreamMager.enabled;
    var $table = $('#' + StreamMager.id), rowData = null;
    var tableData = $table.bootstrapTable("getData");
    var indexMap = {};
    $.each(tableData, function (index, tablerowData) {
        indexMap[tablerowData.deviceid]=index;
    });
    var ajax = new $ax("/stream/get_status", function (data) {
        $.each(data, function (i, item) {
            rowData = $table.bootstrapTable('getRowByUniqueId', item.id);
            var rowIndex = indexMap[item.id]
            if(rowData){
                if(rowData.streamstate!=item.s){
                    $table.bootstrapTable('updateCell', {index: rowIndex, field: "streamstate", value: item.s});
                }
                if(rowData.topic_status!=item.t){
                    $table.bootstrapTable('updateCell', {index: rowIndex, field: "topic_status", value: item.t});
                }
            }
        });
    }, function (data) {
        Feng.error("请求设备状态异常");
    });
    ajax.set(param);
    ajax.setParam('async', true);
    ajax.start();
};

$(function () {
    StreamMager.getAccountList();
    StreamMager.initDeviceTable();

    StreamMager.intervalIndex = setInterval(StreamMager.deviceStreamRefresh, 5000);

    $("#filterForm select").change(function () {
        var $this = $(this);
        window.sessionStorage.setItem($this.prop("name"), $this.val());
    });
    $("#filterForm input").on("change propertychange", function () {
        var $this = $(this);
        window.sessionStorage.setItem($this.prop("name"), $this.val());
    });

    $('#'+StreamMager.id).on('page-change.bs.table', function (element, number, size) {
        window.sessionStorage.setItem("pageSize", size);
        window.sessionStorage.setItem("pageIndex", number);
    });
    $('#'+StreamMager.id).on('search.bs.table', function (element, text) {
        window.sessionStorage.setItem("searchKeyword", text);
    });
});