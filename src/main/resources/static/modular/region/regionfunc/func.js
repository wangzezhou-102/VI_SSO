var baseURL = '';
var FuncEditer = {
    ztreeObj: null,
    layerIndex: -1,
    settings: null,
    editItem: null,
    oldname: "",
    editProp: null,   //当前编辑功能参数
};
function funcTypeMapping(key) {
    switch (String(key)){
        case '0': return 'string';
            break;
        case '1': return 'int';
            break;
        case '2': return 'float';
            break;
        case '3': return 'datetime';
            break;
        default: return null;
    }
}

/**
 * 重命名前
 */
FuncEditer.zTreeBeforeRename = function (treeId, treeNode) {
    FuncEditer.oldname = treeNode.name;
    return true;
};
/**
 * 功能重命名
 */
FuncEditer.zTreeOnRename = function (event, treeId, treeNode, isCancel)  {
    if(FuncEditer.oldname == treeNode.name) {
        return;
    }
    var data = {
        funcid:treeNode.id,
        newfuncname:treeNode.name
    };
    var ajax = new $ax(baseURL + "/regionfunc/funcupdate", function (data) {
        Feng.success('修改成功');
    }, function (data) {
        Feng.error("修改功能失败");
    });
    ajax.set(data);
    ajax.start();
};
/**
 * 删除功能
 */
FuncEditer.zTreeBeforeRemove = function (treeId, treeNode)  {
    var oper = function () {
        var ajax = new $ax(baseURL + "/regionfunc/funcdel", function (data) {
            FuncEditer.ztreeObj.removeNode(treeNode);
        }, function (data) {
            Feng.error("删除功能失败");
        });
        ajax.set({"funcid": treeNode.id});
        ajax.start();
    };
    Feng.confirm('是否删除功能：'+treeNode.name +'？', oper);
    return false;
};
/**
 * 初始化功能属性列表
 */
FuncEditer.initParam = function (event, treeId, treeNode) {
    $('#param_list').empty();
    var funcid = treeNode.id;
    var ajax = new $ax(baseURL + "/regionfunc/getarg", function (data) {
        var html = "";
        data.forEach(function (item) {
            html = html +  '<a href="##" id="'+item.argid+'" title="'+item.argshowname+'" name="'+item.argshowname+'" data-type="'+item.argtype+'" data-value="'+item.argvalue+'" class="list-group-item">' +
                item.argshowname +':'+item.argvalue+ '<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;">' +
                '</a>';
        });
        $('#param_list')
            .append(html)
            .on('mouseover', '.list-group-item', function () {
                $(this).children('span').removeClass('hidden');
            })
            .on('mouseleave', '.list-group-item', function () {
                $(this).children('span').addClass('hidden');
            });
    }, function (data) {
        Feng.error("获取功能属性列表失败");
    });
    ajax.set('funcid', funcid);
    ajax.start();
};
FuncEditer.settings = {
    view : {
        dblClickExpand : true,
        selectedMulti : false
    },
    data : {simpleData : {enable : true}},
    callback : {
        onClick : FuncEditer.initParam,
        beforeRename: FuncEditer.zTreeBeforeRename,
        onRename: FuncEditer.zTreeOnRename,
        beforeRemove: FuncEditer.zTreeBeforeRemove,
    },
    edit : {
        enable: true,
        showRemoveBtn: true,
        showRenameBtn: true,
        drag : {isCopy : false, isMove : false}
    }
};

/**
 * 打开弹出层
 */
FuncEditer.openLayer = function (id, title, area, init) {
    FuncEditer.layerIndex = layer.open({
        type: 1,
        title: title,
        area: area, //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#'+id).html(),
        success: function (index, layero) {
            if(init){
                init();
            }
        },
        end: function () {
            FuncEditer.editProp = undefined;
        }
    });
};
/**
 * 添加功能
 */
FuncEditer.addFunc = function () {
    this.openLayer('addFunc_layer', '添加功能', ['600px', '250px']);
};
/**
 * 保存功能
 */
function checkFuncId(funcid) {
    if(!/^\d{4}$/.test(funcid) || funcid=='0000'){
        Feng.error('功能ID不合法(0001-9999)');
        return false;
    }
    var nodes = FuncEditer.ztreeObj.getNodes();
    if(nodes.length){
        var result = true;
        nodes.forEach(function (t) {
            if(t.id==funcid) {
                result = false;
                Feng.error('功能ID已存在');
                return;
            }
        });
        return result;
    }
    return true;
}

FuncEditer.saveFunc = function () {
    var functname = $('#functname').val();
    var functid = $('#functid').val();
    if(!functname){
        Feng.error('请输入功能名');
        return;
    }
    if(!functid){
        Feng.error('请输入功能ID');
        return;
    }
    if(!checkFuncId(functid)) return;
    var newFuncNode = {'funcid':String(functid), "funcname":String(functname)};
    var ajax = new $ax(baseURL + "/regionfunc/funcadd", function (data) {
        Feng.success('添加成功');
        layer.close(FuncEditer.layerIndex);
        newFuncNode = {
            name: functname,
            pId: null,
            id: functid,
            isitem: 1,
        };
        var node = FuncEditer.ztreeObj.addNodes(null, newFuncNode);
        $("#" + node[0].tId + "_span").after('<span id="'+node[0].tId+'_info" class="id_info">('+node[0].id+')</span>');
    }, function (data) {
        Feng.error("添加功能失败");
    });
    ajax.set(newFuncNode);
    ajax.start();
};
/**
 * 初始化功能列表
 */
FuncEditer.ininFunc = function () {
    var ztree = new $ZTree("func_tree", baseURL + "/regionfunc/funclist");
    ztree.setSettings(FuncEditer.settings);
    FuncEditer.ztreeObj = ztree.init();
    var treeNodes = FuncEditer.ztreeObj.getNodes();
    treeNodes.forEach(function (item) {
        $("#" + item.tId + "_span").after('<span id="'+item.tId+'_info" class="id_info">('+item.id+')</span>');
    });
};
/**
 * 添加参数
 */
FuncEditer.addParam = function () {
    //判断是否有功能被选中
    var nodes = FuncEditer.ztreeObj.getSelectedNodes();
    if(!nodes.length){
        Feng.info('请选择功能节点');
        return;
    }
    this.openLayer('addParam_layer', '添加参数', ['600px', '350px'], FuncEditer.initPropPlugin);
};

/**
 * 编辑功能参数
 */
FuncEditer.propEdit = function ($item) {
    var paramName = $item.attr('name');
    this.openLayer('addParam_layer', '编辑参数', ['600px', '350px'], FuncEditer.initPropPlugin);
    $('#paramtname').val(paramName);
    $('#paramType').val($item.attr('data-type')).trigger('change');
    switch ($item.attr('data-type')){
        case '1':
            $('#paramvalue').val($item.attr('data-value'));
            break;
        case '3':
            var t = $item.attr('data-value').split('-');
            var times = $('#paramvalue').find('input');
            $(times[0]).val(t[0]);
            $(times[1]).val(t[1]);
            break;
    }
    FuncEditer.editProp = $item;
};
/**
 * 初始化参数控件
 */
FuncEditer.initPropPlugin = function () {
    var $paramType = $('#paramType'), options = "";
    $paramType.empty();
    new $ax(baseURL + "/regionfunc/keywordlist", function (data) {
        $.each(data, function (i, item) {
            options = options + '<option value="'+item.num+'">'+ item.name +'</option>';
        });
    }, function (data) {
        Feng.error("获取参数类型失败");
    }).start();
    $paramType.append(options).val('1').change(function () {
        $('#paramvalue').remove();
        var type = $(this).find('option:selected').val();
        switch (type.toString()){
            case '0': $('#inputType').append('<input class="form-control" type="text" id="paramvalue" name="paramvalue" maxlength="30"/>');
                break;
            case '1': $('#inputType').append('<input class="form-control" type="number" id="paramvalue" name="paramvalue" value="160" min="0" max="10000"/>');
                break;
            case '2': $('#inputType').append('<input class="form-control" type="number" id="paramvalue" name="paramvalue" value="160.000" min="0" max="10000"/>');
                break;
            case '3': $('#inputType').append('<div id="paramvalue">起&nbsp;<input type="text" class="time_picker" style="height: 2.5em;width: 14em;" value=""/>&nbsp;至&nbsp;<input type="text" class="time_picker" style="height: 2.5em;width: 14em;" value=""/></div>');
                $('.time_picker').timepicker({
                    showMeridian: false,
                    minuteStep: 1,
                    showSeconds: true,
                    secondStep: 1
                });
                break;
            case '4': $('#inputType').append('<input class="form-control" type="number" id="paramvalue" name="paramvalue" value="1" min="1" max="10000"/>');
                break;
            default: $('#inputType').append('<input class="form-control" type="number" id="paramvalue" name="paramvalue"/>');
                break;
        }
    });
};
/**
 * 保存参数
 */
FuncEditer.saveParam = function () {
    var nodes = FuncEditer.ztreeObj.getSelectedNodes();
    var funcid = nodes[0].id, r = "";
    var paramtname = $('#paramtname').val();
    // var paramjson = $('#paramjson').val();
    var paramjson = "";
    var paramType = $('#paramType').find('option:selected').val();
    var paramvalue;
    if(!paramtname){
        Feng.error('请输入参数名');
        return;
    }
    // if(!paramjson){
    //     Feng.error('请输入参数JSON名');
    //     return;
    // }
    if(!paramType){
        Feng.error('请选择参数类型');
        return;
    }
    switch (paramType.toString()){
        case '0': paramvalue = $('#paramvalue').val();
            break;
        case '1': paramvalue = $('#paramvalue').val();
            r = /^-?\d+$/;          //验证整数
            if(!r.test(paramvalue)) {
                Feng.info('输入不合法，请输入整数');
                return;
            }
            if(paramvalue<-2147483648 || paramvalue>2147483647){
                Feng.info('数值超出范围(-2147483648 —— 2147483647)');
                return;
            }
            if(paramvalue.indexOf('+')>=0 || paramvalue=='-0'){
                paramvalue = paramvalue.substring(1);
            }
            break;
        case '2':paramvalue = $('#paramvalue').val();
            r = /^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;     //验证浮点数
            if(!r.test(paramvalue)) {
                Feng.info('输入不合法，请输入小数');
                return;
            }
            if(paramvalue<-2147483648 || paramvalue>2147483647){
                Feng.info('数值超出范围(-2147483648 —— 2147483647)');
                return;
            }
            if(paramvalue.indexOf('+')>=0 || paramvalue=='-0'){
                paramvalue = paramvalue.substring(1);
            }
            break;
        case '3': var times = $('#paramvalue').find('.time_picker');
            paramvalue = $(times[0]).val() + '-' + $(times[1]).val();
            break;
        case '4': paramvalue = $('#paramvalue').val();
            r = /^-?\d+$/;
            if(!r.test(paramvalue)) {
                Feng.info('输入不合法，请输入整数');
                return;
            }
            if(paramvalue<-2147483648 || paramvalue>2147483647){
                Feng.info('数值超出范围(-2147483648 —— 2147483647)');
                return;
            }
            if(paramvalue.indexOf('+')>=0 || paramvalue=='-0'){
                paramvalue = paramvalue.substring(1);
            }
            break;
    }
    if(!paramvalue){
        Feng.error('请输入参数值');
        return;
    }
    var newParam = {"funcid":funcid, "argname":paramtname, 'arg_json_name':paramjson, 'argtype':paramType, 'argvalue':paramvalue};
    var url = baseURL;
    if(FuncEditer.editProp){
        //编辑状态
        url = url + '/regionfunc/argupdate';
        newParam.argid = FuncEditer.editProp.attr('id');
    }else{
        //添加参数
        url = url + '/regionfunc/argadd';
    }
    var ajax = new $ax(url, function (data) {
        Feng.success('保存成功');
        if(FuncEditer.editProp){
            FuncEditer.editProp.attr({
                'title': paramtname,
                'name': paramtname,
                'data-type': paramType,
                'data-value': paramvalue
            });
            FuncEditer.editProp.html(paramtname + ':'+ paramvalue+ '<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;">');
        }else{
            var argid = data.message;
            var paramItem = '<a href="##" id="'+argid+'" title="'+newParam.argname+'" name="'+newParam.argname+'" data-type="'+paramType+'" data-value="'+newParam.argvalue+'" class="list-group-item">' +
                newParam.argname + ':'+ newParam.argvalue+ '<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;">' +
                '</a>';

            $('#param_list').append(paramItem);
        }
        layer.close(FuncEditer.layerIndex);
    }, function (data) {
        Feng.error("保存功能参数失败");
    });
    ajax.set(newParam);
    ajax.start();
};

/**
 * 删除功能参数
 */
FuncEditer.propRemove = function ($item) {
    var paramName = $item.text();
    var argid = $item.attr('id');
    var oper = function () {
        var ajax = new $ax(baseURL + "/regionfunc/argdel", function (data) {
            $item.remove();
        },function (data) {
            Feng.error('删除功能参数失败');
        });
        ajax.set('argid',argid);
        ajax.start();
    };
    Feng.confirm("是否删除功能参数：" + paramName + "？", oper);
};

$(function () {
    FuncEditer.ininFunc();

    $('#param_list').on('click', 'span.glyphicon', function (e) {
        var buttonname = $(e.target).attr('name');
        var $item = $(e.target).parent();
        if(buttonname=="edit"){
            FuncEditer.propEdit($item);
        }else if(buttonname=="remove"){
            FuncEditer.propRemove($item);
        }
    });

});