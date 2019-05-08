var baseUrl = '';
var AccountMager = {
    id: "account_table",           //账号表格id
    tableParams: {'uniqueId': "userId", 'search': true},
    table: null,
    isEdit: false,  //编辑状态
    setItem: null, //编辑账号数据
    setItemIndex: -1, //编辑账号数据行
    layerIndex: -1,    //弹出框编号
    validateFields: {
        authlevel: {
            message: '账号类型不能为空',
            validators: {
                notEmpty: {
                    message: '账号类型不能为空'
                }
            }
        },
        username: {
            message: '账号不能为空',
            validators: {
                notEmpty: {
                    message: '账号不能为空'
                },
                stringLength: {
                    max: 256,
                    message: '账号长度不能超过256位'
                },
            }
        },
        passwd: {
            message: '账号密码不能为空',
            validators: {
                notEmpty: {
                    message: '账号密码不能为空'
                },
                regexp: {
                    regexp: /^[0-9a-zA-Z_]{1,}$/,
                    message: '账号密码只能由字母、数字和下划线组成'
                },
                stringLength: {
                    max: 256,
                    message: '账号密码长度不能超过256位'
                },
            }
        }
    }
};

/**
 * 打开弹出层
 */
AccountMager.openLayer = function (title, callback) {
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: ["500px", "auto"],
        fix: false,
        maxmin: true,
        content: $('#addAccount').html(),
        success: function (layero, index) {
            if(callback){
                callback();
            }
        },
        end: function () {
            AccountMager.isEdit = false;
            AccountMager.setItem = null;
            AccountMager.setItemIndex = -1;
        }
    });
};


/**
 * 自定义表格列
 */
AccountMager.customIndexCell = function (value, rowData, index) {
    return index;
};
AccountMager.customTypeCell = function (value, rowData, index) {
    var text = "";
    switch (value.toString()){
        case "51":  text = "流媒体账号";
            break;
        case "52":  text = "流接入账号";
            break;
        case "53":  text = "流发布账号";
            break;
        case "54":  text = "TPS接入账号";
            break;
        case "55":  text = "国标点播服务账号";
            break;
        case "56":  text = "datahub点播服务账号";
            break;
        case "77":  text = "DB33流接入账号";
            break;
        default: text = "未知类型";
            break;
    }
    return text;
};
AccountMager.customDeviceCountCell = function (value, rowData, index) {
    if(rowData.authlevel==51 || rowData.authlevel==52 || rowData.authlevel==54){
        return rowData.hlsdeviceCount;
    }else if(rowData.authlevel==53){
        return rowData.epsdeviceCount;
    }else{
        return "-";
    }
};
AccountMager.customOperCell = function (value, rowData, index) {
    return '<i class="fa fa-edit" title="编辑" style="font-size: 1.6rem;color: #388bff;"></i>' +
        '<i class="fa fa-remove" title="删除" style="font-size: 1.7rem;color: red;margin-left: 2rem;"></i>';
};

/**
 * 表格事件
 */
window.accountEvents = {
    /**
     * 账号编辑
     */
    'click .fa-edit': function (e, value, row, index) {
        var callback = function () {
            $("select[name='authlevel']").val(row.authlevel).attr("disabled", true);
            $("input[name='username']").val(row.username);
            $("input[name='passwd']").val(row.passwd);
        };
        AccountMager.openLayer('编辑账号', callback);
        AccountMager.isEdit = true;
        AccountMager.setItem = row;
        AccountMager.setItemIndex = index;
    },
    /**
     * 删除账号
     */
    'click .fa-remove':function (e, value, row, index) {
        var oper = function () {
            var ajax = new $ax(baseUrl + "/services_account/del", function (data) {
                if(data.code==200){
                    Feng.success("删除成功");
                    $('#'+AccountMager.id).bootstrapTable('removeByUniqueId', row.userId);
                }else{
                    Feng.info(data.message);
                }
            }, function (data) {
                Feng.error("删除失败");
            });
            ajax.set('userid', row.userId);
            ajax.start();
        };
        Feng.confirm('是否删除账号:'+row.username+'？', oper);
    }
};


/**
 * 表格字段
 */
AccountMager.columns = [
    // {field: 'selectItem', radio: true},
    {field: 'rowindex', class: 'hidden', formatter:AccountMager.customIndexCell},
    {title: '账号', field: 'username', align: 'center', valign: 'middle', sortable: true},
    {title: '密码', field: 'passwd', align: 'center', valign: 'middle', sortable: true},
    {title: '账号类型', field: 'authlevel', align: 'center', valign: 'middle', sortable: true, formatter: AccountMager.customTypeCell},
    {title: '绑定设备数', field: 'deviceCount', align: 'center', valign: 'middle', sortable: true, formatter: AccountMager.customDeviceCountCell},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '200px', formatter: AccountMager.customOperCell, events: accountEvents}
];

/**
 * 初始化账号表格
 */
AccountMager.initAccountTable = function () {
    var table = new BSTable(this.id, baseUrl+'/services_account/list', this.columns, this.tableParams);
    table.setPaginationType("client");
    this.table = table.init();
};

/**
 * 创建账号
 */
AccountMager.createAccount = function () {
    this.openLayer("创建账号");
};

/**
 * 验证表单
 */
AccountMager.validate = function () {
    var $form = $('#account_form');
    $form.bootstrapValidator({
        message: 'This value is not valid',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: [':disabled', ':hidden', ':not(:visible)'],//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:  AccountMager.validateFields
    });
    $form.bootstrapValidator('validate');
    return $form.data('bootstrapValidator').isValid();
};

/**
 * 保存账号
 */
AccountMager.saveAccount = function () {
    var url = "", param = {};
    if(!this.validate()) {
        return;
    }
    var paramArr = $("#account_form").serializeArray();
    paramArr.forEach(function (item) {
        param[item.name] = item.value;
    });
    if(this.isEdit){
        //编辑状态
        url = baseUrl + "/services_account/update";
        delete param.authlevel;
        param.userid = this.setItem.userId;
    }else{
        //添加账户
        url = baseUrl + "/services_account/add";
    }
    var ajax = new $ax(url, function (data) {
        if(data.code==200){
            if(AccountMager.isEdit){
                Feng.success("修改成功");
                param.userId = param.userid;
                delete param.userid;
                $('#' + AccountMager.id).bootstrapTable('updateRow', {index: AccountMager.setItemIndex, row: $.extend(AccountMager.setItem, param)});
            }else{
                Feng.success("添加成功");
                param.userId = data.message;
                $('#' + AccountMager.id).bootstrapTable('append', param);
            }
            layer.close(AccountMager.layerIndex);
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("保存失败");
    });
    ajax.set(param);
    ajax.start();
};

$(function () {
    AccountMager.initAccountTable();
});