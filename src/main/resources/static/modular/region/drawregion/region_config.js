var CoilConfigInfo = {
    configData:{}
}
function toJson(childrenData) {
    var arr = [];
    $.each(childrenData, function (i ,item) {
        var obj = {};
        obj.Name = item.argshowname;
        obj.Type = item.argtype;
        switch (item.argtype){
            case 1: obj.Value = $('#'+item.tId).children('input').val();
                break;
            case 2: obj.Value = $('#diyBtn_'+item.id).find('option:selected').val();
                // if(obj.Value=='2'){
                //     obj.Value = $('#diyBtn_'+item.id).next().val();
                // }
                break;
            case 3: var inputs = $('#'+item.tId).children('input');
                obj.Value = $(inputs[0]).val() + "-" + $(inputs[1]).val();
                break;
            case 4: obj.Value = $('#'+item.tId).children('input').val();
                break;
        }
        arr.push(obj);
    });
    return arr;
}
function getAttrJson(dataList) {
    var jsonData = [];
    for(var i = 0;i<dataList.length;i++){
        var obj = {};
        obj.Id = dataList[i].id;
        obj.AttributeName = dataList[i].name;
        obj.IsItem = dataList[i].isitem;
        obj.Enable = dataList[i].Enable;
        if(!obj.Enable) {
            obj.Enable = false;
        }
        if(dataList[i].children) {
            obj.AllSubAttribute = arguments.callee(dataList[i].children);
        }else {
            obj.AllSubAttribute = [];
        }
        jsonData.push(obj);
    }
    return jsonData;
}
function getFuncJson(dataList) {
    var jsonData = [];
    for(var i = 0;i<dataList.length;i++){
        var obj = {};
        obj.Id = dataList[i].id;
        obj.FunctionName = dataList[i].name;

        obj.Enable = dataList[i].Enable;
        if(!obj.Enable) {
            obj.Enable = false;
        }
        if(dataList[i].children) {
            obj.AllFunctionParams = toJson(dataList[i].children);
        }else{
            obj.AllFunctionParams = [];
        }
        jsonData.push(obj);
    }
    return jsonData;
}
function getPathJson(node) {
    var parentNodes = node.getPath();
    for(var i=parentNodes.length-1;i>=0;i--){
        var obj = {
            Id: parentNodes[i].id,
            AttributeName: parentNodes[i].name
        };
        if(!parentNodes[i].isitem) {
            obj.AllSubAttribute = parentNodes[i].AllSubAttribute;
        }
        if(i>=1){
            parentNodes[i-1].AllSubAttribute = obj;
        }else{
            return obj;
        }
    }
}
function checkRegionNum(number) {
    var regions = $('#coil_list').find('a'), result = true;
    if(regions.length>0){
        $.each(regions, function (i, item) {
            var sort = $(item).attr('data-number');
            if(sort==number){
                result = false;
            }
        });
    }
    return result;
}
//获取线圈勾选属性
function getTreeData(treeObj, configInfo) {
    var propNodes = treeObj.getCheckedNodes(true);
    var parentNodes = {}, rootNodes = {};
    $.each(propNodes, function(i, item) {
        var c = {
            "Id": item.id,
            "AttributeName": item.name,
            "AllSubAttribute": []
        };
        var parentNode = item.getParentNode();
        if(parentNodes[parentNode.id]){
            parentNodes[parentNode.id].AllSubAttribute.push(c);
        }else{
            var p = {
                "Id": parentNode.id,
                "AttributeName": parentNode.name,
                "AllSubAttribute": []
            };
            p.AllSubAttribute.push(c);
            parentNodes[parentNode.id] = p;
        }
    });
    for(var id in parentNodes){
        var node = treeObj.getNodeByParam("id", id, null);
        if(node.pId!=null){
            var parentNode = node.getParentNode();
            if(rootNodes[parentNode.id]){
                rootNodes[parentNode.id].AllSubAttribute.push(parentNodes[id]);
            }else{
                var p = {
                    "Id": parentNode.id,
                    "AttributeName": parentNode.name,
                    "AllSubAttribute": []
                };
                p.AllSubAttribute.push(parentNodes[id]);
                rootNodes[parentNode.id] = p;
            }
        }else{
            configInfo.Attributions.push(parentNodes[id]);
        }
    }
    for(var r in rootNodes){
        configInfo.Attributions.push(rootNodes[r]);
    }
    return configInfo;
}
/**
 * 提交线圈配置
 */
CoilConfigInfo.layer_sure = function(){
    if(!$('#coilnumber').val()){
        Feng.error('请输入编号');
        return false;
    }
    if(!$('#coilname').val()){
        Feng.error('请输入名称');
        return false;
    }
    var areaname = $('#coilname').val();
    var sortno = $('#coilnumber').val();
    var proptreeObj = $.fn.zTree.getZTreeObj("attr_tree");
    var directetreeObj = $.fn.zTree.getZTreeObj("directe_tree");
    var functreeObj = $.fn.zTree.getZTreeObj("fun_tree");
    var configInfo = {
        "Attributions":[],
        "Functions":[]
    };

    //configInfo.Attributions
    configInfo = getTreeData(proptreeObj, configInfo);
    configInfo = getTreeData(directetreeObj, configInfo);
    //configInfo.Functions
    var funcNodes = functreeObj.getCheckedNodes(true);
    $.each(funcNodes, function (i, item) {
        var node = {
            "Id": item.id,
            "FunctionName": item.name,
            // "Enable": false,
            AllFunctionParams: ""
        };
        if(item.children){
            node.AllFunctionParams = [];
            item.children.forEach(function (t) {
                var param = {
                    "Name": t.name,
                    "Type": t.argtype
                };
                switch (t.argtype){
                    case 1: param.Value = $('#'+t.tId).children('input').val();
                        break;
                    case 2: param.Value = $('#diyBtn_'+t.id).find('option:selected').val();
                        // if(param.Value=='2'){
                        //     param.Value = $('#'+t.tId).find('input').val();
                        // }
                        break;
                    case 3: var inputs = $('#'+t.tId).children('input');
                        param.Value = $(inputs[0]).val() + "-" + $(inputs[1]).val();
                        break;
                    case 4: param.Value = $('#'+t.tId).children('input').val();
                        break;
                }
                node.AllFunctionParams.push(param);
            });
        }
        configInfo.Functions.push(node);
    });
    var pRatios = "", areapath = [], url = "";
    var jsoninfo = JSON.stringify(configInfo);
    var region = {
        deviceid: coilObj.deviceid,
        presetid: coilObj.presetid,
        sortno: sortno,
        areaname: areaname,
        jsoninfo: jsoninfo,
        jsoninfo2: JSON.stringify({"Attributions":$.extend(getAttrJson(proptreeObj.getNodes()), getAttrJson(directetreeObj.getNodes())), "Functions":getFuncJson(functreeObj.getNodes())})
    };
    if(coilObj.pointsRatio!=""){
        pRatios = coilObj.pointsRatio.trim().split(" ");
        pRatios.forEach(function (item) {
            areapath.push('['+item+']');
        });
        region.areapath = coilObj.pointsOffset;
        region.areapath2 = areapath.join(" ");
        region.linecolor = coilObj.DEFAULT.color;
        region.linethickness = coilObj.DEFAULT.radius;
        url = baseUrl+"/drawregion/regionadd";
        if(!checkRegionNum(sortno)){
            Feng.error('线圈编号已存在');
            return false;
        }
    }else{
        region.regionid = $(coilObj.editTarget).attr('id');
        url = baseUrl+"/drawregion/regionupdate";
    }
    var ajax = new $ax(url, function (data) {
        if(data.code==200){
            Feng.success('保存成功');
            coilObj.prop = [];
            var prop = getProp(configInfo.Attributions);
            if(coilObj.editTarget){
                $(coilObj.editTarget).attr({
                    'title': areaname,
                    'data-number': sortno,
                    'data-config': jsoninfo
                });
                $(coilObj.editTarget).html(areaname);
                var table = $('#collapse-'+region.regionid).find('table');
                $(table[0]).find('td.prop').html(prop.join('<br>'));
                $(table[0]).find('td.func').html(getFunc(configInfo.Functions));
            }else{
                var coilItem = "<div class='panel panel-default'><a id='"+data.message+"' title='"+areaname+"' class='list-group-item' data-number='"+sortno+"' data-points='"+coilObj.pointsRatio+"' data-config='"+jsoninfo+"' data-linecolor='"+region.linecolor+"' data-linethickness='"+region.linethickness+"' data-toggle='collapse' data-parent='#coil_list' href='#collapse-"+data.message+"'>"+
                    areaname + "<i class='fa fa-chevron-right'></i></a>";
                coilItem = coilItem + '<div id="collapse-'+data.message+'" class="panel-collapse collapse" style="border-left: solid 1px #ddd;border-right: solid 1px #ddd;border-bottom: solid 1px #ddd;">' +
                    '<div class="panel-body">' +
                    '<table class="table">' +
                    '<tbody>' +
                    '<tr>' +
                    '<td width="45px">属性</td>' +
                    '<td class="prop">'+prop.join('<br>')+'</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td width="45px">功能</td>' +
                    '<td class="func">' + getFunc(configInfo.Functions) + '</td>' +
                    '</tr>' +
                    '</tbody>' +
                    '</table>' +
                    '</div>' +
                    '</div></div>';
                $('#coil_list').append(coilItem);
                coilObj.curPolygon.attr('regionid', data.message);
            }
        }
    },function (data) {
        Feng.error('线圈保存失败！');
    });
    ajax.set(region);
    ajax.start();
    layer.close(coilObj.layerIndex);
    return true;
};

CoilConfigInfo.layer_apply = function () {
    if(!CoilConfigInfo.layer_sure()){
        return;
    }
    coilObj.coil_apply('apply');
};

CoilConfigInfo.layer_cancel = function () {
    layer.close(coilObj.layerIndex);
    if(coilObj.curPolygon) {
        coilObj.curPolygon.remove();
    }
};
