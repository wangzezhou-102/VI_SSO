var baseURL = '';
var PropEditer = {
    ztreeObj: null,
    layerIndex: -1,
    settings: null,
    parentNode: null,
    oldname: ""
};
/**
 * 添加自定义控件
 */
PropEditer.addHoverDom = function (treeId, treeNode) {
    if(!treeNode.isParent) return;
    var sObj = $("#" + treeNode.tId + "_info");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function (event) {
        var tId = event.target.id.substring(7);
        PropEditer.openLayer(tId);
    });
};
/**
 * 移除自定义控件
 */
PropEditer.removeHoverDom = function (treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
/**
 * 重命名前
 */
PropEditer.zTreeBeforeRename = function (treeId, treeNode) {
    PropEditer.oldname = treeNode.name;
    return true;
};
/**
 * 属性树修改叶子结点名称
 */
PropEditer.zTreeOnRename = function (event, treeId, treeNode, isCancel)  {
    if(treeNode.name==PropEditer.oldname){
        return;
    }
    var nodeData = {
        'propid': treeNode.id,
        'propname': treeNode.name
    };
    var ajax = new $ax(baseURL + "/regionprop/propupdate", function (data) {
        Feng.success('修改成功');
    }, function (data) {
        Feng.error("修改失败");
        treeNode.name = PropEditer.oldname;
        PropEditer.ztreeObj.updateNode(treeNode);
        PropEditer.oldname = "";
    });
    ajax.set(nodeData);
    ajax.start();
};
/**
 * 属性树删除属性事件
 */
PropEditer.zTreeBeforeRemove = function (treeId, treeNode)  {
    var oper = function () {
        var ajax = new $ax(baseURL + "/regionprop/propdel", function (data) {
            Feng.success('删除成功');
            var parentnode = treeNode.getParentNode();
            PropEditer.ztreeObj.removeChildNodes(treeNode);
            PropEditer.ztreeObj.removeNode(treeNode);
            if(parentnode){
                parentnode.isParent = true;
                PropEditer.ztreeObj.updateNode(parentnode);
            }
            $('#updateProp').attr('disabled', true);
        }, function (data) {
            Feng.error("删除失败");
        });
        ajax.set('propid', String(treeNode.id));
        ajax.start();
    };
    Feng.confirm('是否删除属性：'+treeNode.name +'？', oper);
    return false;
};

/**
 * 属性树叶子结点点击事件
 */
PropEditer.zTreeOnClick = function(event, treeId, treeNode) {
    $('#param_list').find('input[type="checkbox"]').attr('checked',false);
    if(treeNode.isParent) {
        $('#checkAll').attr('checked',false);
        $('#updateProp').attr('disabled', true);
        $('.td-edit').remove();
        $('.panel-collapse').collapse('show');
    } else {
        var ajax = new $ax(baseURL + "/regionprop/getpropfunc", function (data) {
            var prop = {}, s = {};
            data.forEach(function (t) {
                $('a#'+t.functionid).find('input[type="checkbox"]').prop('checked',true);
                if(!prop[t.functionid]){
                    prop[t.functionid] = new Array();
                }
                s = {'id': t.argid, 'name': t.argshowname, 'value': t.argvalue, 'type': t.argtype};
                if(t.hasOwnProperty('custom_value')){
                    s.value = t.custom_value;
                }
                prop[t.functionid].push(s);
            });
            $('.td-edit').remove();
            // $('.panel-collapse').collapse('hide');
            for(var x in prop){
                // console.log($('#collapse-'+x));
                // $('#collapse-'+x).collapse('show');
                $.each(prop[x], function (i, item) {
                    $('td#'+item.id).text(item.value);
                    var tr = $('td#'+item.id).closest('tr');
                    if(!tr.find('.glyphicon-edit').length){
                        tr.append('<td class="td-edit">' +
                            '<span class="glyphicon glyphicon-edit pull-right" onclick="PropEditer.changeProp(event)" title="编辑" name="edit">' +
                            '</td>');
                    }
                });
            }
        }, function (data) {
            Feng.error("获取功能失败");
        });
        ajax.set('propid', String(treeNode.id));
        ajax.start();
        $('#updateProp').attr('disabled', false);
    }
};
/**
 * ztree节点打开事件
 */
PropEditer.onExpand = function (event, treeId, treeNode) {
    if(treeNode.children && treeNode.children.length){
        treeNode.children.forEach(function (item) {
            $("#" + item.tId + "_span").after('<span id="'+item.tId+'_info" class="id_info">('+item.id+')</span>');
        });
    }
};
/**
 * ztree节点关闭事件
 */
PropEditer.beforeCollapse = function (treeId, treeNode)  {
    if(treeNode.children && treeNode.children.length){
        treeNode.children.forEach(function (item) {
            $("#" + item.tId + "_info").remove();
        });
    }
};
/**
 * 属性树配置
 */
PropEditer.settings = {
    view : {
        dblClickExpand : true,
        selectedMulti : false,
        addHoverDom: PropEditer.addHoverDom,
        removeHoverDom: PropEditer.removeHoverDom
    },
    data : {simpleData : {enable : true}},
    callback : {
        onClick : PropEditer.zTreeOnClick,
        beforeRename: PropEditer.zTreeBeforeRename,
        onRename: PropEditer.zTreeOnRename,
        beforeRemove: PropEditer.zTreeBeforeRemove,
        onExpand: PropEditer.onExpand,
        beforeCollapse: PropEditer.beforeCollapse,
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
PropEditer.openLayer = function (tId) {
    PropEditer.layerIndex = layer.open({
        type: 1,
        title: "添加属性",
        area: ["500px", "250px"], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#addProp_layer').html(),
        success: function () {
            var parentNode = PropEditer.ztreeObj.getNodesByParam("tId", tId)[0];
            if(parentNode.level==1){
                $('#asChild').attr('disabled', true);
            }else{
                $('#asChild').attr('disabled', false);
            }
        }
    });
};
/**
 * 全选
 */
PropEditer.checkAll = function (obj) {
    $("#param_list input[type='checkbox']").prop('checked', $(obj).prop('checked'));
};
/**
 * 单项勾选
 */
PropEditer.checkToggle = function (e) {
    var target = e.target.parentNode;
    var $checkbox = $(target).children('input');
    var node = PropEditer.ztreeObj.getSelectedNodes()[0];
    if(node && !node.isParent){
        if(!$checkbox.prop('checked')){
            $('#collapse-'+target.id).find('.td-edit').remove();
        }else{
            var trs = $('#collapse-'+target.id).find('table>tbody>tr');
            $.each(trs, function (i, item) {
                $(item).append('<td class="td-edit">' +
                    '<span class="glyphicon glyphicon-edit pull-right" onclick="PropEditer.changeProp(event)" title="编辑" name="edit">' +
                    '</td>');
            });
        }
    }

    //检查是否全选
    var items = $("#param_list input[type='checkbox']");
    $.each(items, function (i, item) {
        if(!$(item).prop('checked')){
            $('#checkAll').prop('checked',$(item).prop('checked'));
            return;
        }
    });
    e.stopImmediatePropagation();
};
/**
 * 保存属性
 */
function standardID(data, type) {
    /**
     * 属性ID规范：总共10位，首位1开头，2-4为根节点编号，若不存在一级子目录，5-7位补0，8-10位为属性编号；若存在一级子目录，5-7位为一级子目录编号，8-10位为属性编号
     */
    var head,tail;
    if(!/^\d{10}$/.test(data.own)) {
        Feng.error("属性ID不合法(10位数字)");
        return false;
    }
    switch (type) {
        case "root":
            head = '1';
            tail = '000000';
            if(data.own.substring(0,1)==head && data.own.substring(4)==tail){
                if(!PropEditer.ztreeObj.getNodesByParam("id", data.own, null).length){
                    return true;
                }
                Feng.info("属性ID已存在");
                return false;
            }
            Feng.info("属性ID不符合规范");
            return false;
            break;
        case "child":
            head = data.father.id.substring(0,4);
            tail = '000';
            if(data.own.substring(0,4)==head && data.own.substring(4,7)!='000' && data.own.substring(7)==tail){
                if(!PropEditer.ztreeObj.getNodesByParam("id", data.own, data.father).length){
                    return true;
                }
                Feng.info("属性ID已存在");
                return false;
            }
            Feng.info("属性ID不符合规范");
            return false;
            break;
        case "prop":
            head = data.father.id.substring(0,7);
            if(data.own.substring(0,7)==head && data.own.substring(7)!='000'){
                if(!PropEditer.ztreeObj.getNodesByParam("id", data.own, data.father).length){
                    return true;
                }
                Feng.info("属性ID已存在");
                return false;
            }
            Feng.info("属性ID不符合规范");
            return false;
            break;
    }
}
PropEditer.saveProp = function (e) {
    var event = e || window.event;
    var target = event.target;
    var propname = $('#propname').val();
    var propid = $('#propid').val();
    var parentNode = PropEditer.ztreeObj.getSelectedNodes()[0];
    if(!propname){
        Feng.error('请输入属性名');
        return;
    }
    if(!propid){
        Feng.error('请输入属性ID');
        return;
    }
    var newNode = {
        "propname":propname,
        "propid":propid,
    };
    var isParent;
    if(target.id=='asRoot'){
        if(!standardID({'father': null, 'own': propid},'root')) return;
        newNode.pid = '0';
        newNode.isitem = 0;
        newNode.level = 0;
        isParent = true;
        parentNode = null;
    }else if(target.id=='asChild'){
        if(!standardID({'father': parentNode, 'own': propid}, 'child')) return;
        newNode.pid = parentNode.id;
        newNode.isitem = 0;
        newNode.level = parentNode.level + 1;
        isParent = true;
    }else if(target.id=='asProp'){
        if(!standardID({'father': parentNode, 'own': propid}, 'prop')) return;
        newNode.pid = parentNode.id;
        newNode.isitem = 1;
        newNode.level = parentNode.level + 1;
        isParent = false;
    }
    var ajax = new $ax(baseURL + "/regionprop/propadd", function (data) {
        layer.close(PropEditer.layerIndex);
        var node = PropEditer.ztreeObj.addNodes(parentNode, {
            'id': newNode.propid,
            'name': newNode.propname,
            'pId': newNode.pid,
            'isitem': newNode.isitem,
            'isParent': isParent
        }, true);
        $("#" + node[0].tId + "_span").after('<span id="'+node[0].tId+'_info" class="id_info">('+node[0].id+')</span>');
        Feng.success('添加成功');
    }, function (data) {
        Feng.error("添加失败");
    });
    ajax.set(newNode);
    ajax.start();
};
/**
 * 初始化功能列表
 */
PropEditer.initFunc = function () {
    var ajax = new $ax(baseURL + "/regionfunc/funclist", function (data) {
        var html = "";
        $('#param_list').empty();
        $.each(data, function (i, item) {
            html = html + '<a href="##" id="'+item.id+'" title="'+item.name+'" class="list-group-item"  data-target="#collapse-'+item.id+'" data-toggle="collapse" data-parent="#accordion">' +
                '<input type="checkbox">' + item.name + '(' + item.id + ')' +
                '</a>';
        });
        $('#param_list').append(html);
    }, function (data) {
        Feng.error("获取功能列表失败");
    });
    ajax.start();
};
/**
 * 初始化功能参数
 */
PropEditer.initParameter = function () {
    var ajax = new $ax(baseURL + "/regionfunc/getallarg", function (data) {
        var parameter = {};
        $.each(data, function (i, item) {
            if(!parameter[item.functionid]){
                parameter[item.functionid] = new Array();
            }
            parameter[item.functionid].push(item);
        });
        for(var s in parameter){
            var html = '<div id="collapse-'+parameter[s][0].functionid+'" class="panel-collapse collapse in" style="border-left: solid 1px #ddd;border-right: solid 1px #ddd;border-bottom: solid 1px #ddd;">' +
                '<div class="panel-body">' +
                '<table class="table">' +
                '<tbody>';
            $.each(parameter[s], function (i, item) {
                html = html + '<tr>' +
                    '<td>'+
                    item.argshowname +
                    '</td>'+
                    '<td class="propvalue text-center" id="'+item.argid+'" data-type="'+item.argtype+'">'+
                    item.argvalue +
                    '</td>' +
                    '</tr>';
            });
            html = html + '</tbody>' +
                '</table>' +
                '</div>' +
                '</div>';
            $('a#'+parameter[s][0].functionid).after(html);
        }
    }, function (data) {
        Feng.error("获取功能列表失败");
    });
    ajax.start();
};
/**
 * 编辑功能参数
 */
PropEditer.changeProp = function (e) {
    var $target = $(e.target);
    var valueTd = $target.closest('tr').find('td.propvalue')[0];
    if($target.hasClass('glyphicon-edit')){
        //编辑
        var value = $(valueTd).text();
        switch ($(valueTd).attr('data-type')){
            case '0':
                $(valueTd).empty().append('<input class="form-control" type="text" value="'+value+'" maxlength="30"/>');
                break;
            case '1':
                $(valueTd).empty().append('<input class="form-control" type="number" style="height: 2em;border-radius: 3px;" value="'+value+'" min="0" max="10000"/>');
                break;
            case '2':
                $(valueTd).empty().append('<input class="form-control" type="number" value="'+value+'" min="0" max="10000"/>');
                break;
            case '3':
                value = value.split('-');
                $(valueTd).empty().append('<div>起&nbsp;<input type="text" class="time_picker" style="height: 2em;max-width: 12em;" value="'+value[0]+'"/>&nbsp;至&nbsp;<input type="text" class="time_picker" style="height: 2em;max-width: 12em;" value="'+value[1]+'"/></div>');
                $('.time_picker').timepicker({
                    showMeridian: false,
                    minuteStep: 1,
                    showSeconds: true,
                    secondStep: 1
                });
                break;
        }
        $target.removeClass('glyphicon-edit').addClass('glyphicon-floppy-saved').attr('title', '保存');
    }else if($target.hasClass('glyphicon-floppy-saved')){
        //保存
        var itemid = $(valueTd).attr('id');
        var propid = PropEditer.ztreeObj.getSelectedNodes()[0].id;
        var argvalue;
        var inputs = $(valueTd).find('input');
        if($(valueTd).attr('data-type')=='3'){
            argvalue = $(inputs[0]).val() + '-' + $(inputs[1]).val();
        }else{
            argvalue = $(inputs[0]).val();
        }
        var ajax = new $ax(baseURL + "/regionprop/update_func_arg_val", function (data) {
            $(valueTd).empty().text(argvalue);
            $target.removeClass('glyphicon-floppy-saved').addClass('glyphicon-edit').attr('title', '编辑');
        }, function (data) {
            Feng.error("更新失败");
        });
        ajax.set({'propid': propid, 'argid': itemid, 'argvalue': argvalue});
        ajax.start();
    }
};
/**
 * 更新属性功能关联
 */
PropEditer.updateFunc = function () {
    var oper = function () {
        var checkedItems = $("#param_list input[type='checkbox']:checked");
        var propfunc = [];
        var propid = PropEditer.ztreeObj.getSelectedNodes()[0].id;
        $.each(checkedItems, function (i, item) {
            var $item = $(item).parent();
            propfunc.push($item.attr('id'));
        });
        var ajax = new $ax(baseURL + "/regionprop/propfunc", function (data) {
            Feng.success('更新成功');
        }, function (data) {
            Feng.error("更新失败");
        });
        ajax.set({'propid': propid, 'funcids': JSON.stringify(propfunc)});
        ajax.start();
    };
    Feng.confirm('是否更新属性关联功能？', oper);
};
/**
 * 初始化属性列表
 */
PropEditer.initPorp = function () {
    var ztree = new $ZTree("prop_tree", baseURL + "");
    $.post(baseURL + "/regionprop/proplist", function (data) {
        data.forEach(function (item) {
            if(!item.isitem){
                item.isParent = true;
            }
        });
        ztree.setSettings(PropEditer.settings);
        PropEditer.ztreeObj = ztree.initLocal(data);
        var treeNodes = PropEditer.ztreeObj.getNodes();
        treeNodes.forEach(function (item) {
            $("#" + item.tId + "_span").after('<span id="'+item.tId+'_info" class="id_info">('+item.id+')</span>');
        });
    });
};

$(function () {
    $("#param_list").on('click', 'input[type="checkbox"]', PropEditer.checkToggle);
    PropEditer.initPorp();
    PropEditer.initFunc();
    PropEditer.initParameter();
});