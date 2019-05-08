var baseUrl = '';
var Uac = {
    id: "uac_table",           //账号表格id
    tableParams: {'uniqueId': "userId", 'search': true},
    table: null,
    isEdit: false,  //编辑状态
    setItem: null, //编辑账号数据
    setItemIndex: -1, //编辑账号数据行
    layerIndex: -1,    //弹出框编号
    uacType: null,
    validateFields: {
        uacid: {
            message: 'UACID不能为空,且必须是不超过32位数字',
            validators: {
                notEmpty: {
                    message: 'UACID不能为空'
                },
                regexp: {
                    regexp: /^\+?[1-9][0-9]*$/,
                    message: '只能为正整数数字'
                },
                stringLength: {
                    max: 32,
                    message: '长度不能大于32位'
                },
            }
        },
        name: {
            message: '名字不能为空且长度不大于255',
            validators: {
                notEmpty: {
                    message: '名字不能为空'
                },
                stringLength: {
                    max: 255,
                    message: '长度不能大于255'
                },
            }
        }
    }
};

/**
 * 打开弹出层
 */
Uac.openLayer = function (title, callback) {
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: ["500px", "auto"],
        fix: false,
        maxmin: true,
        content: $('#addUac').html(),
        success: function (layero, index) {
            if(callback){
                callback();
            }
        },
        end: function () {
            Uac.isEdit = false;
            Uac.setItem = null;
            Uac.setItemIndex = -1;
        }
    });
};


/**
 * 自定义表格列
 */
Uac.customIndexCell = function (value, rowData, index) {
    return index;
};

Uac.customDeviceCountCell = function (value, rowData, index) {
    if(rowData.authlevel==51 || rowData.authlevel==52 || rowData.authlevel==54){
        return rowData.hlsdeviceCount;
    }else if(rowData.authlevel==53){
        return rowData.epsdeviceCount;
    }else{
        return "-";
    }
};
Uac.customOperCell = function (value, rowData, index) {
    return '<i class="fa fa-edit" title="编辑" style="font-size: 1.6rem;color: #388bff;"></i>' +
        '<i class="fa fa-remove" title="删除" style="font-size: 1.7rem;color: red;margin-left: 2rem;"></i>';
};

/**
 * 表格事件
 */
window.uacEvents = {
    /**
     * 账号编辑
     */
    'click .fa-edit': function (e, value, row, index) {
        var callback = function () {
            var optionElement = "";
            Uac.uacType.forEach(function(element) {
                optionElement += ' <option value="'+element.plateid+'">'+element.platename+'</option>';
            });
            $("#uacTypeSelect").html(optionElement);

            $("input[name='uacid']").val(row.uacid);
            $("input[name='name']").val(row.name);
            $("select[name='uactype']").val(row.uactype);
            $("select[name='uacstate']").val(row.uacstate);
          
        };
        Uac.openLayer('编辑账号', callback);
        Uac.isEdit = true;
        Uac.setItem = row;
        Uac.setItemIndex = index;
    },
    /**
     * 删除账号
     */
    'click .fa-remove':function (e, value, row, index) {
        console.log(JSON.stringify(row));
        var oper = function () {
            var ajax = new $ax(baseUrl + "/subordinate_uac/delete", function (data) {
                if(data.code==200){
                    Feng.success("删除成功");
                    $('#'+Uac.id).bootstrapTable('removeByUniqueId', row.id);
                }else{
                    Feng.info(data.message);
                }
            }, function (data) {
                Feng.error("删除失败");
            });
            ajax.set('id', row.id);
            ajax.start();
        };
        Feng.confirm('是否删除账号:'+row.name+'？', oper);
    }
};
Uac.customUacTypeCell = function name(value, rowData, index) {
    
var uacTypeStr ="未知类型";
Uac.uacType.forEach(function(element) {
    if(element.plateid==rowData.uactype.toString()){
        uacTypeStr =  element.platename;
    }
});

return uacTypeStr;
}

Uac.customUacStateCell = function name(value, rowData, index) {
    var uacState = "未知状态";

    if(rowData.uacstate=='1'){
        uacState = '启用';
    }else{
        uacState = '禁用';
    }

    return uacState;
}


/**
 * 表格字段
 */
Uac.columns = [
    {field: 'selectItem', checkbox: true},
    {field: 'rowindex', class: 'hidden', formatter:Uac.customIndexCell},
    {title: 'UACID', field: 'uacid', align: 'center', valign: 'middle', sortable: true},
    {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
    {title: '平台类型', field: 'uactype', align: 'center', valign: 'middle', sortable: true, formatter: Uac.customUacTypeCell},
    {title: '状态', field: 'uacstate', align: 'center', valign: 'middle', sortable: true,formatter: Uac.customUacStateCell},
    {title: '同步时间', field: 'synctime', align: 'center', valign: 'middle', sortable: true},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '200px', formatter: Uac.customOperCell, events: uacEvents}
];

/**
 * 初始化账号表格
 */
Uac.initAccountTable = function () {
    var table = new BSTable(this.id, baseUrl+'/subordinate_uac/list', this.columns, this.tableParams);
    table.setPaginationType("client");
    this.table = table.init();
};

/**
 * 添加uac
 */
Uac.createUac = function () {
    var callback = function() {
        var optionElement = "";
        Uac.uacType.forEach(function(element) {
            optionElement += ' <option value="'+element.plateid+'">'+element.platename+'</option>';
        });
        $("#uacTypeSelect").html(optionElement);
    }
     this.openLayer("添加下级平台UAC", callback);
};

/**
 * 验证表单
 */
Uac.validate = function () {
    var $form = $('#uac_form');
    $form.bootstrapValidator({
        message: 'This value is not valid',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: [':disabled', ':hidden', ':not(:visible)'],//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:  Uac.validateFields
    });
    $form.bootstrapValidator('validate');
    return $form.data('bootstrapValidator').isValid();
    return true;
};

/**
 * 保存账号
 */
Uac.saveUac = function () {
    var url = "", param = {};
    if(!this.validate()) {
        return;
    }
    var paramArr = $("#uac_form").serializeArray();
    paramArr.forEach(function (item) {
        param[item.name] = item.value;
    });
    if(this.isEdit){
        //编辑状态
        url = baseUrl + "/subordinate_uac/update";
        param.id = this.setItem.id;
    }else{
        //添加账户
        url = baseUrl + "/subordinate_uac/add";
    }
    var ajax = new $ax(url, function (data) {
        if(data.code==200){
            if(Uac.isEdit){
                Feng.success("修改成功");
                param.id = param.id;
                delete param.id;
                $('#' + Uac.id).bootstrapTable('updateRow', {index: Uac.setItemIndex, row: $.extend(Uac.setItem, param)});
            }else{
                Feng.success("添加成功");
                param.id = data.message;
                $('#' + Uac.id).bootstrapTable('append', param);
            }
            layer.close(Uac.layerIndex);
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("保存失败");
    });
    ajax.set(param);
    ajax.start();
};

Uac.subordinateUasSync = function () {
    var subordinateUasChecked = $("#"+Uac.id).bootstrapTable('getAllSelections');
    if(!subordinateUasChecked.length){
        Feng.info('请选择下级网关');
        return;
    }
    var uacids = new Array();
    subordinateUasChecked.forEach(function (t) {
        uacids.push(t.uacid);
    });
    var ajax = new $ax("/subordinate_uac/sync_subordinate_uac", function (json) {
        if(json.code==200){
            Uac.table.refresh();
            Feng.success('同步消息发送成功');
        }else{
            Feng.error(json.message);
        }
    },function () {
        Feng.error("请求同步异常");
    });
    ajax.set('uacids', uacids.join(','));
    ajax.start();
}

$(function () {
    var ajax = new $ax(baseUrl + "/subordinate_uac/plateformlist", function (data) {
        Uac.uacType = data;
    }, function (data) {
        Feng.error("获取平台类型失败");
    });
    ajax.start();
    
    Uac.initAccountTable();
});