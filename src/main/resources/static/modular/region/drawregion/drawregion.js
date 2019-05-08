var baseUrl = '';
var mousePosition = {
    x: 0,
    y: 0
};
var coilObj = {
    deviceid: "",//设备id
    presetid: "",//预置位id
    devicename: "",//设备名
    presetname: "",//预置位名
    picUrl: "",//场景图url
    image: {
        obj: null,
        size: {
            width: 0,
            height: 0,
            x: 0,
            y: 0
        }
    },
    points: "",//线圈点
    pointsRatio: "",//线圈点比例
    pointsOffset: "", //线圈点绝对偏移
    editTarget: null,//更新线圈(配置线圈)
    lines: [],   //线条对象数组
    circles: [],  //点对象数组
    dragLineObj: null,//鼠标拖拽线
    curPolygon: null,//当前绘制线圈
    clickTimeId: null,
    lastOffset: {
        x: -1,
        y: -1
    },
    layerIndex: null,
    DEFAULT: {
        radius: 3,
        color: '#db913d',
        fill: 'yellow',
        fill_opacity: 0.2
    },
    polygonFocusColor: 'red'
};

var qs = decodeURI(location.search);
if(qs.length){
    qs = qs.substring(1);
    var items = qs.split('&');
    items.forEach(function (t) {
        var item = t.split('=');
        coilObj[item[0]] = item[1];
    });
}

if(SVG.supported){
    var draw = SVG('svg-container').size('100%', '100%').style({'position': 'relative'});
    coilObj.image.obj = draw.image(coilObj.picUrl).loaded(function(loader) {
        var con_width = $('#svg-container').width();
        var con_height = $('#svg-container').height();
        draw.size(loader.width, loader.height);
        if(loader.width<=con_width&&loader.height<=con_height){
            draw.style({ "top": "50%", "left": "50%", "transform": "translate(-50%, -50%)"});
        }
        coilObj.image.size.width = loader.width;
        coilObj.image.size.height = loader.height;
        coilObj.init();
        $('#img-title').text(coilObj.devicename+'/'+coilObj.presetname);
    });

}else{
    alert('SVG not supported');
}

var position = {
    firstPosition:null,
    priviousPositon:null
};

/**
 * svg画图对象
 */
var Svg = {
    line: function (preX, preY, nextX, nextY, width, color) {
        var _width = width ? width : coilObj.DEFAULT.radius;
        var _color = color ? color : coilObj.DEFAULT.color;
        var line = draw.line(preX, preY, nextX, nextY).stroke({width: _width,color:_color}).attr('stroke-dasharray',"5,5");
        return line;
    },
    circle: function (psX, psY, radius, fillColor) {
        var _radius = radius ? radius : coilObj.DEFAULT.radius;
        var _fillColor = fillColor ? fillColor : coilObj.DEFAULT.color;
        var circle = draw.circle(_radius*2).move(psX,psY).stroke({color: 'white'}).attr('fill',_fillColor);
        return circle;
    },
    polygon: function (pointsString, stroke_width, stroke_color, fill, fill_opacity) {
        var _strokeWidth = stroke_width ? stroke_width : coilObj.DEFAULT.radius;
        var _strokeColor = stroke_color ? stroke_color : coilObj.DEFAULT.color;
        var _fill = fill ? fill : coilObj.DEFAULT.fill;
        var _fillOpacity = fill_opacity ? fill_opacity : coilObj.DEFAULT.fill_opacity;
        if(_strokeColor.length>7){
            _strokeColor = _strokeColor.substring(0, 7);
        }
        var polygon = draw.polygon(pointsString).fill(_fill).stroke({width: _strokeWidth,color:_strokeColor}).attr({'fill-opacity':_fillOpacity, 'stroke-dasharray':"5,5"});
        return polygon;
    },
    path: function (pathString, stroke_width, stroke_color, fill, fill_opacity) {
        var _strokeWidth = stroke_width ? stroke_width : coilObj.DEFAULT.radius;
        var _strokeColor = stroke_color ? stroke_color : coilObj.DEFAULT.color;
        var _fill = fill ? fill : coilObj.DEFAULT.fill;
        var _fillOpacity = fill_opacity ? fill_opacity : coilObj.DEFAULT.fill_opacity;
        var path = draw.path(pathString).fill(_fill).stroke({width: _strokeWidth,color:_strokeColor}).attr({'fill-opacity':_fillOpacity,'stroke-dasharray':"5,5"});
        return path;
    },
    change: function (attr, value) {
        if(attr!=undefined&&value!=undefined){
            var svgChildren = draw.children();
            $.each(svgChildren, function (i, item) {
                switch(item.type)
                {
                    case 'line':
                        if(attr=='width'){
                            item.stroke({width: value*0.8});
                            item.attr('stroke-dasharray',value+','+value);
                        }else if(attr=='color'){
                            item.stroke({color: value});
                        }
                        break;
                    case 'circle':
                        if(attr=='width'){
                            item.attr('r',value);
                        }else if(attr=='color'){
                            item.attr('fill',value);
                        }
                        break;
                    case 'polygon':
                        if(attr=='width'){
                            item.attr({'stroke-width': value*0.8,'stroke-dasharray':value+','+value});
                        }else if(attr=='color'){
                            item.attr('stroke',value);
                        }else if(attr=='fill'){
                            item.attr('fill',value);
                        }
                        break;
                    default:
                        break;
                }

            });
        }
    }
};
/**
 * 鼠标拖拽线
 */
coilObj.dragLine = function (event) {
    var e = event || window.event;
    if(mousePosition.x===e.pageX && mousePosition.y===e.pageY) return;
    var offsetX = e.offsetX, offsetY = e.offsetY;
    if(isFirefox()){
        offsetX = e.layerX;
        offsetY = e.layerY;
    }
    if(position.priviousPositon==null||((position.priviousPositon.x===offsetX)&&(position.priviousPositon.y===offsetY))){
        return;
    }
    if(coilObj.dragLineObj!=null){
        coilObj.dragLineObj.remove();
    }
    coilObj.dragLineObj = Svg.line(position.priviousPositon.x, position.priviousPositon.y, offsetX, offsetY);
};
/**
 * 开始画线圈
 */
function checkPoint(offsetX, offsetY) {
    var x = coilObj.lastOffset.x, y = coilObj.lastOffset.y;
    if(x>=0 && y>=0) {
        if(offsetX==x && offsetY==y){
            return false;
        }
    }
    coilObj.lastOffset.x = offsetX;
    coilObj.lastOffset.y = offsetY;
    return true;
}
coilObj.beginDraw = function (event) {
    //this==svg
    var e = event || window.event;
    mousePosition.x = e.pageX;
    mousePosition.y = e.pageY;
    var offsetX = e.offsetX, offsetY = e.offsetY;
    if(e.button!=0) return;
    if(isFirefox()){
        offsetX = e.layerX;
        offsetY = e.layerY;
    }
    if(!checkPoint(offsetX, offsetY)) return;
    var radius = coilObj.DEFAULT.radius;
    var psX = offsetX-radius;
    var psY = offsetY-radius;
    var point = Svg.circle(psX , psY, radius, coilObj.DEFAULT.color);
    coilObj.circles.push(point);
    if(position.firstPosition==null){
        position.firstPosition = {x:offsetX,y:offsetY};
    }else{
        var line = Svg.line(position.priviousPositon.x, position.priviousPositon.y, offsetX, offsetY);
        coilObj.lines.push(line);
    }
    coilObj.points = coilObj.points + offsetX + ',' + offsetY + ' ';
    coilObj.pointsRatio = coilObj.pointsRatio + (offsetX-coilObj.image.obj.x())/coilObj.image.obj.width() + ',' + (offsetY-coilObj.image.obj.y())/coilObj.image.obj.height() + ' ';
    coilObj.pointsOffset = coilObj.pointsOffset + (offsetX-coilObj.image.obj.x())/coilObj.image.obj.width()*coilObj.image.size.width + ',' + (offsetY-coilObj.image.obj.y())/coilObj.image.obj.height()*coilObj.image.size.height + ' ';
    position.priviousPositon = {x:offsetX,y:offsetY};
};
/**
 * 双击结束画线圈
 */
coilObj.endDrawSvg = function (event) {
    // clearTimeout(coilObj.clickTimeId);
    if(this.circles.length<3) return;
    this.curPolygon = Svg.polygon(this.points).attr('pointsRatio', this.pointsRatio).click(this.coilClick).dblclick(this.coilDblClick);
    $.each(this.circles, function (i, item) {
        item.remove();
    });
    $.each(this.lines, function (i ,item) {
        item.remove();
    });
    position.firstPosition = null;
    position.priviousPositon = null;
    coilObj.points = "";
    coilObj.coil_config(event);
    draw.off('mousedown', this.beginDraw).off('mousemove', this.dragLine);
    $('#draw_pen').removeClass('img-tool-active');
};

/**
 * 上一步
 */
coilObj.preStep = function () {
    if(this.circles.length){
        this.circles.pop().remove();
    }
    if(this.lines.length){
        this.lines.pop().remove();
    }
    if(this.dragLineObj!=null){
        this.dragLineObj.remove();
        this.dragLineObj = null;
    }
    this.points = this.points.substring(0, this.points.lastIndexOf(' ', this.points.length - 3));
    if(this.points){
        this.points = this.points + ' ';
    }
    this.pointsRatio = this.pointsRatio.substring(0, this.pointsRatio.lastIndexOf(' ', this.pointsRatio.length - 3));
    if(this.pointsRatio){
        this.pointsRatio = this.pointsRatio + ' ';
    }
    this.pointsOffset = this.pointsOffset.substring(0, this.pointsOffset.lastIndexOf(' ', this.pointsOffset.length - 3));
    if(this.pointsOffset){
        this.pointsOffset = this.pointsOffset + ' ';
    }
    var pos = this.points.substring(this.points.lastIndexOf(' ', this.points.length - 2)).split(',');
    if(pos[0]&&pos[1]){
        position.priviousPositon = {x:parseInt(pos[0]),y:parseInt(pos[1])};
    }else{
        position.firstPosition = null;
        position.priviousPositon = null;
    }
};

/**
 * 适应屏幕
 */

function calculate(image_width, image_height, x, y, pointsRatio) {
    var result = [];
    pointsRatio = pointsRatio.trim().split(" ");
    $.each(pointsRatio, function (i,item) {
        var pos = item.split(",");
        pos[0] = pos[0]*image_width+x;
        pos[1] = pos[1]*image_height+y;
        result.push(pos[0]+","+pos[1]);
    });
    return result.join(" ");
}

coilObj.adaptScreen = function (e) {
    var pos = "";
    var container_width = $('#svg-container').width();
    var container_height = $('#svg-container').height();
    var image_width, image_height;
    var $adaptBtn = $(e.target);
    if($adaptBtn.attr('data-state')=='1'){
        //适应屏幕
        var width_ratio = container_width/this.image.size.width;
        var height_ratio = container_height/this.image.size.height;
        if(width_ratio>height_ratio){
            var ratio1 = this.image.size.width/this.image.size.height;
            image_width = container_height*ratio1;
            image_height = container_height;
        }else{
            var ratio2 = this.image.size.height/this.image.size.width;
            image_width = container_width;
            image_height = container_width*ratio2;
        }
        draw.size(image_width, image_height);
        this.image.obj.size(image_width, image_height);
        if(width_ratio<1 || height_ratio<1){
            draw.style({ "top": "50%", "left": "50%", "transform": "translate(-50%, -50%)"});
        }
        $('#svg-container').css('overflow','hidden');
        $adaptBtn.attr('data-state', '0');
    }else{
        $('#svg-container').css('overflow','auto');
        /**
         * 图片原尺寸
         */
        image_width = this.image.size.width;
        image_height = this.image.size.height;
        draw.size(image_width, image_height);
        this.image.obj.size(image_width, image_height);
        if(image_width>container_width&&image_height>container_height){
            draw.style({ "top": 0, "left": 0, "transform": "translate(0, 0)"});
        }
        $adaptBtn.attr('data-state', '1');
    }
    var polygons = SVG.select('polygon').members;
    if(polygons.length){
        polygons.forEach(function (item) {
            var regionid = item.attr('regionid');
            var pointsRatio = item.attr('pointsRatio');
            var linethickness = item.attr('stroke-width');
            var linecolor = item.attr('stroke');
            item.remove();
            pos = calculate(image_width, image_height, coilObj.image.obj.x(), coilObj.image.obj.y(), pointsRatio);
            Svg.polygon(pos, linethickness, linecolor).attr({'pointsRatio': pointsRatio, 'regionid': regionid}).click(coilObj.coilClick).dblclick(coilObj.coilDblClick);
        });
    }
};
/**
 * 全选
 */
coilObj.checkAll = function (obj) {
    if($(obj).prop('checked')){
        $('#coil_list a').each(function (i,item) {
            if(!$(item).children('input').prop('checked')){
                var regionid = $(item).attr('id');
                var pointsRatio = $(item).attr('data-points');
                var linecolor = $(item).attr('data-linecolor');
                var linethickness = $(item).attr('data-linethickness');
                if(pointsRatio){
                    var points = calculate(coilObj.image.obj.width(), coilObj.image.obj.height(), coilObj.image.obj.x(), coilObj.image.obj.y(), pointsRatio);
                    Svg.polygon(points, linethickness, linecolor).attr({'pointsRatio': pointsRatio, 'regionid' :regionid}).click(coilObj.coilClick).dblclick(coilObj.coilDblClick);
                }
            }
        });
    }else{
        var polygons = SVG.select('polygon').members;
        $.each(polygons, function (i, item) {
            item.remove();
        });
    }
    $("#coil_list input[type='checkbox']").prop('checked', $(obj).prop('checked'));
};
/**
 * 获取线圈配置初始信息
 */
coilObj.getPropAndFunc = function () {
    var propList = [], propList2 = [], funcList = [],funcArgList = [],refPropFuncList = [], valueList = [], customValue = {}, result = null;
    var ajax = new $ax(baseUrl + "/drawregion/propandfunc", function (data) {
            propList = data.propList;
            propList2 = data.propList2;
            funcList = data.funcList;
            funcArgList = data.funcArgList;
            refPropFuncList = data.refPropFuncList;
            valueList = data.valueList;
            //车道类型属性
            propList.forEach(function (item) {
                if(!item.isitem){
                    item.nocheck = true;
                }
            });
            //车道方向属性
            propList2.forEach(function (item) {
                if(!item.isitem){
                    item.nocheck = true;
                }
            });
            //功能列表
            funcList.forEach(function (t) {
                t.open = true;
                t.ownid = 'fun_' + t.id;
                t.parentid = t.pId;
            });
            //功能参数
            funcArgList.forEach(function (item) {
                item.pId = item.functionid;
                item.id = item.argid;
                item.ownid = item.argid;
                item.parentid = 'fun_' + item.functionid;
                item.name = item.argshowname;
                item.nocheck = true;
                funcList.push(item);
            });
            //自定义参数
            $.each(valueList ,function (i ,item) {
                if(!customValue[item.propid]){
                    customValue[item.propid] = new Array();
                }
                customValue[item.propid].push(item);
            });
            result = {
                "propList": propList,
                "propList2": propList2,
                "funcList": funcList,
                "funcArgList": funcArgList,
                "refPropFuncList": refPropFuncList,
                "customValue": customValue,
            };
        },
        function (data) {
            Feng.error('获取线圈列表失败！');
        });
    ajax.start();
    return result;
};
/**
 * 线圈配置
 */
coilObj.coil_config = function (event) {
    var e = event || window.event;
    var propandfunc = coilObj.getPropAndFunc();
    coilObj.configLayer(e.target, propandfunc.propList, propandfunc.propList2, propandfunc.funcList, propandfunc.funcArgList, propandfunc.refPropFuncList, propandfunc.customValue);
};
/**
 * 获取线圈流水编号
 */
coilObj.getRegionNum = function () {
    var regions = $('#coil_list').find('a'), number = 0;
    if(regions.length>0){
        $.each(regions, function (i, item) {
            var sort = parseInt($(item).attr('data-number'));
            if(sort>number){
                number = sort;
            }
        });
    }
    number = parseInt(number) + 1;
    return number;
};
/**
 * 线圈配置弹出层
 */
coilObj.configLayer = function (target, propList, propList2, funcList, funcArgList, refPropFuncList, customValue) {
    coilObj.layerIndex = layer.open({
        type: 1,
        title: '线圈配置',
        area: ['95%', '90%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#coil-config').html(),
        success: function (index, layero) {
            if($(target).attr('data-number')){
                $('#coilname').val($(target).attr('title'));
                $('#coilnumber').val($(target).attr('data-number'));
            }else{
                var number = coilObj.getRegionNum();
                $('#coilname').val(coilObj.devicename+'_'+coilObj.presetname+'_'+number);
                $('#coilnumber').val(number);
            }
            var funcArr = {};
            //属性-功能关联关系归类
            $.each(refPropFuncList, function(i, item) {
                if(!funcArr.hasOwnProperty(item.propid)){
                    funcArr[item.propid] = new Array();
                }
                funcArr[item.propid].push(item.funcid);
            });
            var selectEvent = function (e) {
                var $input = $("#combin_input");
                var selectedItem = $(e.currentTarget).find('option:selected').val();
                if(selectedItem=='2'){
                    $input.css('display','inline-block');
                }else{
                    $input.css('display','none');
                }
            };
            var appendPlu = function (treeId, treeNode) {
                var aObj = $("#" + treeNode.tId + '_a');
                var editStr = "";
                switch (treeNode.argtype){
                    case 1: editStr = '<input type="number" id="'+treeNode.argid+'" value="'+treeNode.argvalue+'"/>&nbsp;';
                        break;
                    case 2: editStr = "<select class='selDemo' name='"+treeNode.argid+"' id='diyBtn_" +treeNode.id+ "'><option value='0'>及时报警</option><option value='1'>规律报警</option><option value='2'>组合报警</option></select>"+
                        "&nbsp;<input type='number' value='10' style='display: none;' id='combin_input'/>&nbsp;次";
                        break;
                    case 3: var dateTime = treeNode.argvalue.split('-');
                        editStr = "起&nbsp;<input type='text' id='"+treeNode.argid+"_start' class='time_picker' value='"+dateTime[0]+"'/>&nbsp;至&nbsp;<input type='text' id='"+treeNode.argid+"_end' class='time_picker' value='"+dateTime[1]+"'/>";
                        break;
                    case 4: editStr = '<input type="number" id="'+treeNode.argid+'" value="'+treeNode.argvalue+'"/>&nbsp;';
                        break;
                    default:break;
                }
                aObj.after(editStr);
                var btn = $("#diyBtn_"+treeNode.id);
                if (btn) btn.bind("change", selectEvent);
                $('.time_picker').timepicker({
                    showMeridian: false,
                    minuteStep: 1,
                    showSeconds: true,
                    secondStep: 1
                });
            };
            var zTreeOnCheck = function (event, treeId, treeNode) {
                var functreeObj = $.fn.zTree.getZTreeObj("fun_tree");
                if(funcArr[treeNode.id]&&funcArr[treeNode.id].length>0){
                    //查找func_tree，进行关联操作treeNode.checked = true/false
                    funcArr[treeNode.id].forEach(function (item) {
                        var nodes = functreeObj.getNodesByParam("id", item, 0);
                        nodes.forEach(function (t) {
                            if(t.id==item) {
                                functreeObj.checkNode(t, treeNode.checked, true);
                                t.Enable = true;
                            }
                        });
                    });
                    if(treeNode.checked){
                        if(customValue[treeNode.id]!=null){
                            $.each(customValue[treeNode.id], function (i, t) {
                                switch (t.argtype){
                                    case 1: $('#'+t.argid).val(t.argvalue);
                                        break;
                                    case 2: $('select[name="'+t.argid+'"]').val(t.argvalue).trigger('change');
                                        break;
                                    case 3:
                                        var argvalue = t.argvalue.split('-');
                                        $('#'+t.argid+'_start').val(argvalue[0]);
                                        $('#'+t.argid+'_end').val(argvalue[1]);
                                        break;
                                    case 4: $('#'+t.argid).val(t.argvalue);
                                        break;
                                }
                            });
                        }

                    }
                }
                treeNode.Enable = true;
            };
            var prop_setting = {
                view : {
                    showLine: false,
                    showIcon: false,
                    dblClickExpand : true,
                    selectedMulti : false
                },
                check:{
                    enable: true,
                    chkStyle: "checkbox",
                    chkboxType: { "Y": "p", "N": "s" }
                },
                data : {simpleData : {enable : true}},
                callback : {
                    onCheck : zTreeOnCheck
                }
            };

            var fun_setting = {
                view : {
                    addDiyDom: appendPlu,
                    showLine: false,
                    showIcon: false
                },
                check:{
                    enable: true,
                    chkStyle: "checkbox"
                },
                data : {simpleData : {
                        enable : true,
                        idKey : 'ownid',
                        pIdKey : 'parentid'
                    }},
                callback : {
                    onCheck : zTreeOnCheck
                }
            };
            var attr_ztree = new $ZTree("attr_tree");
            attr_ztree.setSettings(prop_setting);
            attr_ztree.initLocal(propList);
            var directe_tree = new $ZTree("directe_tree");
            directe_tree.setSettings(prop_setting);
            directe_tree.initLocal(propList2);
            var fun_ztree = new $ZTree("fun_tree");
            fun_ztree.setSettings(fun_setting);
            fun_ztree.initLocal(funcList);

            var configInfo = $(target).attr('data-config');
            if(configInfo){
                configInfo = JSON.parse(configInfo);
                var proptreeObj = $.fn.zTree.getZTreeObj("attr_tree");
                var directeObj = $.fn.zTree.getZTreeObj("directe_tree");
                var functreeObj = $.fn.zTree.getZTreeObj("fun_tree");
                //初始化线圈配置信息到结构树
                coilObj.initConfig(configInfo, proptreeObj, directeObj, functreeObj);
                //展开勾选节点
                var checkedNodes = proptreeObj.getCheckedNodes(true);
                var directeNodes = directeObj.getCheckedNodes(true);
                $.each(checkedNodes, function (i, item) {
                    var path = item.getPath();
                    path.forEach(function (x) {
                        proptreeObj.expandNode(x, true, false, true);
                    });
                });
                $.each(directeNodes, function (i, item) {
                    var path = item.getPath();
                    path.forEach(function (x) {
                        directeObj.expandNode(x, true, false, true);
                    });
                });
            }
            if(coilObj.pointsRatio == ""){
                coilObj.editTarget = target;
            }
        },
        cancel: function(index, layero){
            if(coilObj.curPolygon) {
                coilObj.curPolygon.remove();
            }
        },
        end: function () {
            // if(coilObj.curPolygon){
            //     coilObj.curPolygon.remove();
            //     coilObj.curPolygon = null;
            // }
            $.fn.zTree.destroy();
            if(coilObj.dragLineObj){
                coilObj.dragLineObj.remove();
            }
            coilObj.circles = [];
            coilObj.lines = [];
            coilObj.curPolygon = null;
            coilObj.pointsRatio = "";
            coilObj.pointsOffset = "";
            coilObj.editTarget = null;
        }
    });
};
/**
 * 查看线圈数据
 */
coilObj.coil_Data = function () {
    var checkedList = $("#coil_list a");
    var param = [];
    $.each(checkedList, function (i, item) {
        param.push($(item).attr('id'));
    });
    var ajax = new $ax(baseUrl + "/drawregion/regioninfo", function (data) {
        Feng.infoDetail('线圈数据', data);
    }, function (data) {
        Feng.error("请求线圈数据异常!");
    });
    ajax.set({"ids":param.join(',')});
    ajax.start();
};
/**
 * 线圈应用
 */
coilObj.coil_apply = function (param) {
    var applyCallback = function () {
        var ajax = new $ax(baseUrl + "/drawregion/regionapply", function (data) {
            if(data.code==200){
                Feng.success('线圈应用成功');
            }
        }, function (data) {
            Feng.error("应用失败!");
        });
        ajax.set({"deviceid":coilObj.deviceid});
        ajax.start();
    };
    if(param=='apply'){
        applyCallback();
    }else{
        Feng.confirm('是否应用线圈？',applyCallback);
    }
};
/**
 * 删除线圈
 */
coilObj.coil_remove = function () {
    var regionid = "", region = null;
    var polygons = SVG.select('polygon').members;
    $.each(polygons, function (i, item) {
        if(item.attr('fill')==coilObj.polygonFocusColor){
            region = item;
            regionid = region.attr('regionid');
        }
    });
    var delCallback = function () {
        var ajax = new $ax(baseUrl + "/drawregion/regiondel", function (data) {
            if(data.code==200){
                $('a#'+regionid).parent().remove();
                region.remove();
                Feng.success('线圈删除成功');
            }
        }, function (data) {
            Feng.error("线圈删除失败");
        });
        ajax.set({"regionids":regionid});
        ajax.start();
    };
    if(regionid){
        Feng.confirm('是否删除选中线圈？',delCallback);
    }else{
        Feng.info('请选择线圈');
    }
};
/**
 * 显示线圈
 */
coilObj.coil_toggle = function (e) {
    clearTimeout(coilObj.clickTimeId);
    coilObj.clickTimeId = setTimeout(function () {
        var target = e.target;
        if(target.tagName.toLowerCase()=='input'){
            target = target.parentNode;
        }else{
            var $checkbox = $(target).children('input');
            $checkbox.prop('checked',!$checkbox.prop('checked'));
            if(!$checkbox.prop('checked')){
                $('#checkAll').prop('checked', $checkbox.prop('checked'));
            }
        }
        if($(target).children('input').prop('checked')){
            var regionid = $(target).attr('id');
            var pointsRatio = $(target).attr('data-points');
            var linecolor = $(target).attr('data-linecolor');
            var linethickness = $(target).attr('data-linethickness');
            var points = calculate(coilObj.image.obj.width(), coilObj.image.obj.height(), coilObj.image.obj.x(), coilObj.image.obj.y(), pointsRatio);
            Svg.polygon(points, linethickness, linecolor).attr({'pointsRatio': pointsRatio, 'regionid': regionid}).click(coilObj.coilClick).dblclick(coilObj.coilDblClick);
        }else{
            var polygons = SVG.select('polygon').members;
            polygons.forEach(function (item) {
                if(item.attr('pointsRatio')==$(target).attr('data-points')){
                    item.remove();
                }
            });
        }
    },200);
};

/**
 * 线圈单击选中
 */
coilObj.coilClick = function (e) {
    if($('#draw_pen').hasClass('img-tool-active')) return;
    var target = e.target;
    var oldSta = $(target).attr('fill');
    var polygons = SVG.select('polygon').members;
    $.each(polygons, function (i, item) {
        item.attr('fill', 'yellow');
    });
    if(oldSta!=coilObj.polygonFocusColor){
        $(target).attr('fill', coilObj.polygonFocusColor);
    }
    $(".panel-collapse").collapse('hide');
    var regionid = $(target).attr("regionid");
    $("#collapse-"+regionid).collapse('toggle');
};
/**
 * 线圈双击打开配置
 */
coilObj.coilDblClick = function (e) {
    var regionid = $(e.target).attr('regionid');
    var regionitem = $('#coil_list').find('a#'+regionid)[0];
    var propandfunc = coilObj.getPropAndFunc();
    if(propandfunc){
        coilObj.configLayer(regionitem, propandfunc.propList, propandfunc.propList2, propandfunc.funcList, propandfunc.funcArgList, propandfunc.refPropFuncList, propandfunc.customValue);
    }
};

/**
 * 初始化线圈列表
 */
coilObj.prop = [];
function getProp(attr) {
    attr.forEach(function (item) {
        if (item.AllSubAttribute && item.AllSubAttribute.length > 0) {
            getProp(item.AllSubAttribute);
        }else{
            coilObj.prop.push(item.AttributeName);
            return true;
        }
    });
    return coilObj.prop;
}
function getFunc(func) {
    var result = [];
    func.forEach(function (item) {
        var x = item.FunctionName;
        if(item.AllFunctionParams){
            var params = [];
            item.AllFunctionParams.forEach(function (t) {
                params.push(t.Name+':'+t.Value);
            });
            x = x + '(' + params.join(',') + ')';
        }
        result.push(x);
    });
    return result.join('<br>');
}
coilObj.init = function () {
    var ajax = new $ax(baseUrl + "/drawregion/regionlist", function (data) {
            var regionList = "";
            $.each(data, function (i, item) {
                item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
                item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
                regionList = regionList + "<div class='panel panel-default'><a id='"+item.id+"' title='"+item.areaname+"' class='list-group-item' data-number='"+item.sortno+"' data-points='"+item.areapath2+"' data-config='"+item.jsoninfo+"' data-linecolor='"+item.linecolor+"' data-linethickness='"+item.linethickness+"' data-toggle='collapse' data-parent='#coil_list' href='#collapse-"+item.id+"'>"+
                    item.areaname + "<i class='fa fa-chevron-right'></i></a>";
                var jsoninfo = JSON.parse(item.jsoninfo);
                coilObj.prop = [];
                coilObj.prop = getProp(jsoninfo.Attributions);
                regionList = regionList + '<div id="collapse-'+item.id+'" class="panel-collapse collapse" style="border-left: solid 1px #ddd;border-right: solid 1px #ddd;border-bottom: solid 1px #ddd;">' +
                    '<div class="panel-body">' +
                    '<table class="table">' +
                    '<tbody>' +
                    '<tr>' +
                    '<td width="45px">属性</td>' +
                    '<td class="prop">'+coilObj.prop.join('<br>')+'</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td width="45px">功能</td>' +
                    '<td class="func">' + getFunc(jsoninfo.Functions) + '</td>' +
                    '</tr>' +
                    '</tbody>' +
                    '</table>' +
                    '</div>' +
                    '</div></div>';
                var points = calculate(coilObj.image.obj.width(), coilObj.image.obj.height(), coilObj.image.obj.x(), coilObj.image.obj.y(), item.areapath2);
                Svg.polygon(points, item.linethickness, item.linecolor, coilObj.DEFAULT.fill).attr({'pointsRatio': item.areapath2,'regionid': item.id}).click(coilObj.coilClick).dblclick(coilObj.coilDblClick);
            });
            $('#coil_list').append(regionList);
        },
        function (data) {
            Feng.error('获取线圈列表失败！');
        });
    ajax.set({"deviceid":coilObj.deviceid,"presetid":coilObj.presetid});
    ajax.start();
};
/**
 * 初始化线圈配置信息
 */
coilObj.initConfig = function (configInfo, proptreeObj, directeObj, functreeObj) {
    var initCoinProp = function (attr) {
        attr.forEach(function (t) {
            if(!t.AllSubAttribute || !t.AllSubAttribute.length){
                var node = proptreeObj.getNodeByParam("id", t.Id, 0) || directeObj.getNodeByParam("id", t.Id, 0);
                if(null!=node){
                    proptreeObj.checkNode(node, true, true);
                }
            }else{
                initCoinProp(t.AllSubAttribute);
            }
        });
    };
    initCoinProp(configInfo.Attributions);
    configInfo.Functions.forEach(function (item) {
        var nodes = functreeObj.getNodesByParam("id", item.Id, 0);
        nodes.forEach(function (t) {
            if(t.id.toString()==item.Id) {
                functreeObj.checkNode(t, true, true);
                if(item.AllFunctionParams){
                    var list = t.children;
                    if(!list) return;
                    functreeObj.expandNode(t, true, false, true);
                    $.each(item.AllFunctionParams, function (index, x) {
                        if(!list[index]||!list[index].tId||$('#'+list[index].tId).length<=0) return;
                        switch (x.Type){
                            case 1:$('#'+list[index].tId).children('input').val(x.Value);
                                break;
                            case 2: $('#diyBtn_'+list[index].id).val(x.Value).trigger('change');
                                // if(x.Value=='2'){
                                //     $('#'+list[index].tId).find('input').val(x.Value);
                                // }
                                break;
                            case 3: var inputs = $('#'+list[index].tId).children('input');
                                var value = x.Value.split('-');
                                $(inputs[0]).val(value[0]);
                                $(inputs[1]).val(value[1]);
                                break;
                            case 4: $('#'+list[index].tId).children('input').val(x.Value);
                                break;
                        }
                    });
                }
            }
        });
    });
};

/**
 * 返回
 */
coilObj.back = function () {
    var url = document.referrer;
    if(url.indexOf('?')!=-1){
        url = url.substring(0, url.indexOf('?'));
    }
    location.href = url.substring(url.lastIndexOf('/')) + '?deviceid='+coilObj.deviceid+'&presetid='+coilObj.presetid;
};
/**
 * 设置线条粗细
 */
coilObj.slider = function (event) {
    var e = event || window.event;
    var target = e.target;
    var $divContainer = $(target).prev();
    $divContainer.toggle().css({
        left : $(target).outerWidth() + $(target).width() - 24 + "px",
        top : $(target).outerHeight() - $(target).height() - $divContainer.height() - 5 + "px"
    });
};
/**
 * 退出线圈绘制
 */
coilObj.quit = function () {
    $.each(this.circles, function (i, item) {
        item.remove();
    });
    $.each(this.lines, function (i, item) {
        item.remove();
    });
    this.dragLineObj.remove();
    position.firstPosition = null;
    position.priviousPositon = null;
    this.points = "";
    this.pointsRatio = "";
    this.pointsOffset = "";
    this.dragLineObj = null;
    this.lines = [];
    this.circles = [];

    $('#draw_pen').removeClass('img-tool-active');
    draw.off('mousedown', this.beginDraw).off('mousemove', this.dragLine);
};
/**
 * 线圈折叠事件
 */
$("#coil_list").on('click', '.list-group-item', function (e) {
    var collapseId = $(e.target).attr("href");
    $(".panel-collapse").collapse('hide');
    $(collapseId).collapse('toggle');
}).on('show.bs.collapse', '.panel-collapse', function () {
    var targetRegion = $(this).prev().attr('id');
    var polygons = SVG.select('polygon').members;
    polygons.forEach(function (item) {
        if(item.attr('regionid')==targetRegion){
            item.fill(coilObj.polygonFocusColor);
        }else{
            item.fill(coilObj.DEFAULT.fill);
        }
    });
    $(this).prev().children("i").removeClass("fa-chevron-right").addClass("fa-chevron-down");
}).on('hide.bs.collapse', '.panel-collapse', function () {
    var targetRegion = $(this).prev().attr('id');
    var polygons = SVG.select('polygon').members;
    polygons.forEach(function (item) {
        if(item.attr('regionid')==targetRegion){
            item.fill(coilObj.DEFAULT.fill);
        }
    });
    $(this).prev().children("i").removeClass("fa-chevron-down").addClass("fa-chevron-right");
});

$(function () {
    $( "#PTZSpeed-Slider" ).slider({
        // orientation: "vertical",
        range: "min",
        value: 3,
        min: 1,
        max: 10,
        animate: false,
        slide: function(event, ui) {
            $("#PTZSpeed").text(ui.value);
            coilObj.DEFAULT.radius = ui.value;
            Svg.change('width', ui.value);
            $(event.target).prev().text(ui.value);
        }
    });
    $('#color-picker').minicolors({
        control: $(this).attr('data-control') || 'hue',
        defaultValue: $(this).attr('data-defaultValue') || coilObj.DEFAULT.color,
        inline: $(this).attr('data-inline') === 'true',
        letterCase: $(this).attr('data-letterCase') || 'lowercase',
        opacity: $(this).attr('data-opacity'),
        position: $(this).attr('data-position') || 'top left',
        change: function(hex, opacity) {
            var log;
            try {
                log = hex ? hex : 'transparent';
                if( opacity ) log += ', ' + opacity;
                coilObj.DEFAULT.color = hex;
                Svg.change('color', hex);
            } catch(e) {}
        },
        theme: 'default'
    });
    /**
     * 触发画笔
     */
    $('#draw_pen').click(function (event) {
        if($(this).hasClass('img-tool-active')){
            coilObj.quit();
        }else{
            $(this).addClass('img-tool-active');
            draw.on('mousedown', coilObj.beginDraw).on('mousemove', coilObj.dragLine);
        }
    });
    $(document).click(function (e) {
        if(e.target.id!="slider-popover"){
            $("#SliderContainer").hide();
        }

    });
    $(document).keydown(function (e) {
        //按ESC键退出线圈绘制
        if(e.keyCode==27){
            coilObj.quit();
        }
    });
    // $('#checkAll').prop('checked', true);
    $('#svg-container').contextmenu({
        target: '#context-menu',
        before: function(e,context) {
            var sta = Boolean(coilObj.circles.length && $('#draw_pen').hasClass('img-tool-active'));
            return sta;
        },
        onItem: function (context, e) {
            switch ($(e.target).attr('name')){
                case 'previous':
                    coilObj.preStep();
                    break;
                case 'endDraw':
                    coilObj.endDrawSvg(e);
                    break;
            }

        }
    });

    $('.minicolors').css({"margin-left": "10px", "margin-right": "10px"});
});