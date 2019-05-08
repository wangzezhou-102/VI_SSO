var AreaMager = {
    layerIndex: -1,
    ztreeObj: null,
    oper: false,      //操作类型（true表示编辑操作，false表示添加操作）
};
/**
 * 添加自定义按钮
 */
AreaMager.addHoverDom = function(treeId, treeNode){
    var sObj = $("#" + treeNode.tId + "_info");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    $('.edit').css('display', 'none');
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if(btn) {
        btn.bind("click", AreaMager.openLayer);
    }
};
/**
 * 移除自定义按钮
 */
AreaMager.removeHoverDom = function(treeId,treeNode){
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
/**
 * 删除区域
 */
AreaMager.beforeDel = function (treeId, treeNode) {
    var oper = function () {
        var ajax = new $ax(baseUrl + "/areacode/areacodedel", function (data) {
            Feng.success('删除成功');
            var deviceArea = deviceManager.zTreeInstance.getNodeByParam("id", treeNode.id, null);
            AreaMager.ztreeObj.removeNode(treeNode);
            deviceManager.zTreeInstance.removeNode(deviceArea);
        }, function (data) {
            Feng.error("删除失败");
        });
        ajax.set('areacode', treeNode.id);
        ajax.start();
    };
    Feng.confirm("是否删除区域：" + treeNode.name, oper);
    return false;
};
/**
 * 修改区域
 */
AreaMager.zTreeOnRename = function (event, treeId, treeNode, isCancel) {
    var nodeData = {
        id: treeNode.id,
        name: treeNode.name
    };
    var ajax = new $ax(baseUrl + "/deletearea", function (data) {

    }, function (data) {

    });
    ajax.set(nodeData);
    ajax.start();
};
/**
 * 初始化区域弹出框
 */
AreaMager.init = function () {
    layer.open({
        type: 1,
        title: '区域管理',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#area_Mager').html(),
        success: function (index, layero) {
            var ztree = new $ZTree("area_tree", baseUrl + "/areacode/areacodelist");
            var settings = {
                view : {
                    dblClickExpand : true,
                    selectedMulti : false,
                    // addHoverDom:AreaMager.addHoverDom,
                    // removeHoverDom:AreaMager.removeHoverDom,
                },
                data : {simpleData : {enable : true}},
                callback : {
                    beforeRemove: AreaMager.beforeDel,
                    // onRename: AreaMager.zTreeOnRename,
                    // onClick : this.onClick,
                    // onDblClick:this.ondblclick
                },
                edit : {
                    enable: true,
                    showRemoveBtn: true,
                    showRenameBtn: false
                }
            };
            ztree.setSettings(settings);
            AreaMager.ztreeObj = ztree.init();
            var treeNodes = AreaMager.ztreeObj.getNodes();
            treeNodes.forEach(function (item) {
                $("#" + item.tId + "_span").after('<span id="'+item.tId+'_info" class="id_info">('+item.id+')</span>');
            });
        },
        end: function () {
            AreaMager.ztreeObj.destroy();
        }
    });
};
/**
 * 添加区域弹出框
 */
AreaMager.openLayer = function (e) {
    var target = e.target;
    if(target.name=='areaEdit'){
        var nodes = AreaMager.ztreeObj.getSelectedNodes();
        if(nodes.length){
            this.oper = true;
        }else{
            Feng.info('请选择区域');
            return;
        }
    }
    AreaMager.layerIndex = layer.open({
        type: 1,
        title: '添加区域',
        area: ['500px', '250px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#add_area').html(),
        success: function (index, layero) {
            if(nodes && nodes.length){
                $('#areacode').val(nodes[0].id);
                $('#areaname').val(nodes[0].name);
            }
        },
        end: function () {
            AreaMager.oper = false;
        }
    });
};
/**
 * 保存区域
 */
AreaMager.saveArea = function () {
    var areacode = $('#areacode').val();
    var areaname = $('#areaname').val();
    if(!areacode){
        Feng.error('请输入区域码');
        return;
    }else if(!/^\d{6}$/.test(areacode) || !/^[1-9]\d/.test(areacode)){
        Feng.error('区域码不合法,请输入6位整数区域码');
        return;
    }
    if(!areaname){
        Feng.error('请输入区域名');
        return;
    }
    var newArea = {"areacode":parseInt(areacode), "areaname":areaname}, url = "";
    if(this.oper){
        var editItem = AreaMager.ztreeObj.getSelectedNodes()[0];
        url = baseUrl + '/areacode/area_code_update';
        newArea.old_areacode = editItem.id;
    }else{
        url = baseUrl + '/areacode/areacodeadd';
    }
    var ajax = new $ax(url, function (data) {
        Feng.success('保存成功');
        if(AreaMager.oper){
            editItem.id = newArea.areacode;
            editItem.name = newArea.areaname;
            AreaMager.ztreeObj.updateNode(editItem);
            $('#'+editItem.tId+'_info').text('('+newArea.areacode+')');
            var deviceArea = deviceManager.zTreeInstance.getNodeByParam("id", newArea.old_areacode, null);
            deviceArea.id = newArea.areacode;
            deviceArea.name = newArea.areaname;
            deviceManager.zTreeInstance.updateNode(deviceArea);
        }else{
            var newnode = {
                id: newArea.areacode,
                name: newArea.areaname,
                pId: "0"
            };
            var nodes = AreaMager.ztreeObj.addNodes(null, newnode);
            $("#" + nodes[0].tId + "_span").after('<span id="'+nodes[0].tId+'_info" class="id_info">('+nodes[0].id+')</span>');
            deviceManager.zTreeInstance.addNodes(null, newnode);
        }
        layer.close(AreaMager.layerIndex);
    }, function (data) {
        Feng.error("保存失败");
    });
    ajax.set(newArea);
    ajax.start();
};

