var baseURL = '';
var SceneMager = {
    ztreeObj: null,
    keywords: null,
    layerIndex: -1

};
var qs = decodeURI(location.search);

/**
 * 打开弹出框
 */
SceneMager.openLayer = function (id, title, size, callback) {
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: size,
        fix: false,
        maxmin: false,
        resize: false,
        content: $('#'+id).html(),
        success: function (layero, index) {
            if(callback){
                callback();
            }
        },
        end: function () {
            $("#setScene").unbind();
        }
    });
};

function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
}

/**
 * 初始化设备列表
 */
SceneMager.initDeviceList = function () {
    var ztree = new $ZTree("deviceTree", baseURL + "/scene/devicelist");
    ztree.bindOnClick(SceneMager.onClickPreset);
    var ajax = new $ax(baseURL + "/scene/devicelist", function(data) {
        $.each(data, function (i, item) {
            if(item.type=='device'){
                switch (item.devicetype){
                    case 1: if(item.ps2ts && item.ps2ts==1){
                        item.icon = '../../static/img/custom_ztree/circle_camera_online.png';
                    }else{
                        item.icon = '../../static/img/custom_ztree/circle_camera_offline.png';
                    }
                        break;
                    case 2: if(item.ps2ts && item.ps2ts==1){
                        item.icon = '../../static/img/custom_ztree/circle_camera_online.png';
                    }else{
                        item.icon = '../../static/img/custom_ztree/circle_camera_offline.png';
                    }
                        break;
                    case 3: if(item.ps2ts && item.ps2ts==1){
                        item.icon = '../../static/img/custom_ztree/rect_camera_online.png';
                    }else{
                        item.icon = '../../static/img/custom_ztree/rect_camera_offline.png';
                    }
                        break;
                    case 4: if(item.ps2ts && item.ps2ts==1){
                        item.icon = '../../static/img/custom_ztree/rect_camera_online.png';
                    }else{
                        item.icon = '../../static/img/custom_ztree/rect_camera_offline.png';
                    }
                        break;
                }
            }else if(item.type=='preset'){
                item.icon = '../../static/img/custom_ztree/preset.png';
            }else if(item.type=='area'){
                item.icon = '../../static/img/custom_ztree/area.png';
            }
        });
        SceneMager.ztreeObj = ztree.initLocal(data);
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    });
    ajax.start();
    var filterFunc = function (node){
        return (node.level==0 && !node.children);
    };
    var nodes = SceneMager.ztreeObj.getNodesByFilter(filterFunc, false);
    SceneMager.ztreeObj.hideNodes(nodes);
    $('#searcher').keyup(function (e) {
        var keyword = $(this).val();
        if(e.keyCode==13 || (keyword==''&&e.keyCode==8)){
            ztree.filter(SceneMager.ztreeObj, keyword);
        }
    });
    $('.glyphicon-search').click(function () {
        var keyword = $('#searcher').val();
        ztree.filter(SceneMager.ztreeObj, keyword);
    });
    if(qs.length){
        qs = qs.substring(1);
        var items = qs.split('&'), param = [];
        items.forEach(function (t) {
            var item = t.split('=');
            param.push(item[1]);
        });
        var device = SceneMager.ztreeObj.getNodeByParam("id", param[0], null);
        if(device){
            SceneMager.ztreeObj.expandNode(device, true, true, true);
            var preset = SceneMager.ztreeObj.getNodeByParam("id", param[1], device);
            SceneMager.ztreeObj.selectNode(preset);
            SceneMager.getPreView(preset.pId,preset.id);
        }
        qs = "";
    }
};

/**
 * 点击预置位
 */
SceneMager.onClickPreset = function (e, treeId, treeNode) {
    $('#sceneList .row').empty();
    $('#shotList .row').empty();
    if(treeNode.type=="preset"){
        SceneMager.getPreView(treeNode.pId,treeNode.id);
    }
};
/**
 * 获取场景图和截图
 */
SceneMager.getPreView = function (deviceid, presetid) {
    var html = "";
    $.post(baseURL + "/scene/presetinfo",{"deviceid":deviceid,"presetid":presetid},function (result) {
        var viewList = result.viewList;//场景图列表
        var screenshotList = result.screenshotList;//截图列表
        $(".scene-total").text(viewList.length);
        $(".shot-total").text(screenshotList.length);
        $.each(viewList, function (i, item) {
            if(item.pic.indexOf(',')>=0){
                item.pic = item.pic.split(',')[0];
            }
            SceneMager.getKeyWords(item);
            html = html + '<div class="col-md-6 col-sm-12 card-item">' +
                '<div class="thumbnail">' +
                '<img id="'+item.id+'" src="'+baseURL+item.pic+'" data-type="scene" alt="场景图">' +
                '<div class="caption">' +
                '<h3>'+item.picdescription+'</h3>' +
                '<p>'+item.keyword+'</p>' +
                '<p class="footer-time">'+item.createtime+'</p>' +
                '<i class="iconfont icon-pencil drawRegion" title="线圈配置"></i>' +
                '<i class="iconfont icon-shanchu removePic" title="删除场景图"></i>' +
                '</div>' +
                '</div>' +
                '</div>';
        });
        $('#sceneList .row').append(html);
        html = "";
        $.each(screenshotList, function (i, item) {
            html = html + '<div class="col-md-6 col-sm-12 card-item">' +
                '<div class="thumbnail">' +
                '<img id="'+item.id+'" src="'+baseURL+item.picpath+'" data-type="shot" alt="截图">' +
                '<div class="caption">' +
                '<h3>'+item.picname+'</h3>' +
                '<p class="footer-time">'+item.createtime+'</p>' +
                '<i class="iconfont icon-shezhi setScene" title="设为场景图"></i>' +
                '<i class="iconfont icon-shanchu removePic" title="删除截图"></i>' +
                '</div>' +
                '</div>' +
                '</div>';
        });
        $('#shotList .row').append(html);

        $("#sceneList img").bootstrapViewer({
            shownCallback: SceneMager.drawRegion,
            hideCallback: SceneMager.cleanRegion
        });
        $("#shotList img").bootstrapViewer();
    });
};

/**
 * 上传截图
 */
SceneMager.uploadPic = function () {
    var node = SceneMager.ztreeObj.getSelectedNodes()[0];
    if(!node || node.type!='preset') {
        Feng.info('请选择预置位');
        return;
    }
    var deviceid = node.pId.toString();
    var presetid = parseInt(node.id);
    var uploadSuc = function (message) {
        message = JSON.parse(message);
        var html = '<div class="col-md-6 col-sm-12 card-item">' +
            '<div class="thumbnail">' +
            '<img id="'+message.id+'" src="'+baseURL+message.picpath+'" data-type="shot" alt="截图">' +
            '<div class="caption">' +
            '<h3>'+message.picname+'</h3>' +
            '<p class="footer-time">'+formatDateTime(message.createtime)+'</p>' +
            '<i class="iconfont icon-shezhi setScene" title="设为场景图"></i>' +
            '<i class="iconfont icon-shanchu removePic" title="删除截图"></i>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#shotList .row').append(html);
        $("#"+message.id).bootstrapViewer();
        $(".shot-total").text(parseInt($(".shot-total").text())+1);
    };
    var callback = function () {
        imageUpload(baseURL + '/scene/piccapupload/'+deviceid+'/'+presetid, 'jpg', uploadSuc);
    };
    SceneMager.openLayer('uploadPic',  '上传截图', ['800px','480px'], callback);
};

/**
 * 设置为场景图
 */
SceneMager.setScene = function () {
    var _this = this, html = "", $keyword = null;
    SceneMager.layerIndex = layer.open({
        type: 1,
        title: '设置场景图',
        area: ['500px', '250px'],
        fix: false,
        maxmin: true,
        content: $('#setToScene').html(),
        success: function () {
            $keyword = $('#keyword');
            SceneMager.item = $(_this).parent().prev();
        },
        end: function () {
            SceneMager.item = null;
        }
    });
    var ajax = new $ax(baseURL + "/scene/keywordlist", function (data) {
        $.each(data, function (i, item) {
            html = html + '<option value="'+item.id+'">'+item.name+'</option>';
        });
        $keyword.empty();
        $keyword.append(html);
        $keyword.selectpicker({
            'selectedText': 'cat'
        });
    }, function (data) {
        Feng.error("获取场景图关键词失败");
        return;
    });
    ajax.start();
};

/**
 * 获取关键词
 */
SceneMager.getKeyWords = function (result) {
    if(!SceneMager.keywords) {
        $.ajax({
            type: 'post',
            url: baseURL + "/scene/keywordlist",
            async: false,
            success: function (data) {
                SceneMager.keywords = data;

            }
        });
    }
    var keyword = [];
    if(result.keyword){
        result.keyword = eval ("(" + result.keyword + ")");
        $.each(result.keyword, function (i, item) {
            SceneMager.keywords.forEach(function (t) {
                if(t.id==item){
                    keyword.push(t.name);
                    return;
                }
            });
        });
        result.keyword = keyword.join('，');
    }
};

/**
 * 保存场景图
 */
SceneMager.saveScene = function () {
    var picid = $(SceneMager.item).attr('id');
    var scenename = $('#scenename').val();
    var keywords = $('#keyword').val();
    if(!scenename){
        Feng.error('请输入场景图名称');
        return;
    }
    if(!keywords){
        Feng.error('请选择场景图关键词');
        return;
    }
    var param = {
        "screenshotid": picid,
        "scenename": scenename,
        "keywordids": JSON.stringify(keywords)
    };
    var ajax = new $ax(baseURL + "/scene/sceneadd", function (data) {
        Feng.success('设置成功');
        layer.close(SceneMager.layerIndex);
        SceneMager.getKeyWords(data);
        var html = '<div class="col-md-6 col-sm-12 card-item">' +
            '<div class="thumbnail">' +
            '<img id="'+data.id+'" src="'+baseURL+data.pic+'" data-type="scene" alt="场景图">' +
            '<div class="caption">' +
            '<h3>'+data.picdescription+'</h3>' +
            '<p>'+data.keyword+'</p>' +
            '<p class="footer-time">'+data.createtime+'</p>' +
            '<i class="iconfont icon-pencil drawRegion" title="线圈配置"></i>' +
            '<i class="iconfont icon-shanchu removePic" title="删除场景图"></i>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#sceneList .row').append(html);
        $("#"+data.id).bootstrapViewer({
            shownCallback: SceneMager.drawRegion,
            hideCallback: SceneMager.cleanRegion
        });
        $(".scene-total").text(parseInt($(".scene-total").text())+1);
    }, function (data) {
        Feng.error("设置失败");
    });
    ajax.set(param);
    ajax.start();
};
/**
 * 删除图片
 */
SceneMager.picDel = function (event) {
    var _this = this, $total;
    var oper = function () {
        var url = "";
        var data = {};
        var $picItem = $(_this).parent().prev();
        var type = $picItem.attr('data-type');
        var picId = $picItem.attr('id');
        if(type=='scene'){
            url = baseURL + "/scene/scenedel";
            $total = $(".scene-total");
            data.sceneid = picId;
        }else if(type=='shot'){
            url = baseURL + "/scene/screenshotdel";
            $total = $(".shot-total");
            data.screenshotid = picId;
        }
        var ajax = new $ax(url, function (data) {
            Feng.success('删除成功');
            $picItem.parents('.card-item').remove();
            $total.text($total.text()-1);
        }, function (data) {
            Feng.error("删除失败");
        });
        ajax.set(data);
        ajax.start();
    };
    Feng.confirm("是否删除？", oper);
    event.stopImmediatePropagation();
};

/**
 * 计算线圈节点数据
 * @param image_width
 * @param image_height
 * @param x
 * @param y
 * @param pointsRatio
 * @returns {string}
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

/**
 * 显示场景图线圈
 */
SceneMager.drawRegion = function () {
    var $image = $("#imageContainer").find('img');
    var node = SceneMager.ztreeObj.getSelectedNodes()[0];
    var deviceid = node.pId.toString();
    var presetid = parseInt(node.id);
    var region = new Array(), svg;
    var ajax = new $ax(baseURL + "/piccap/regioninfo", function (data) {
        $.each(data, function (i, item) {
            item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
            item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
            region.push(item.areapath2);
        });
    }, function (data) {
        Feng.error("获取预置线圈数据失败");
        return;
    });
    ajax.set({"deviceid":deviceid, "presetid":presetid});
    ajax.start();
    if(SVG.supported) {
        svg = SVG('imageContainer').size('100%', '100%').style({'position': 'absolute', 'left': 0, 'top': 0}).mouseover(function () {
            this.node.style.cursor='zoom-out';
        }).click(function () {
            $('#bootstrapViewer').modal('hide');
        });
        region.forEach(function (item) {
            var pointsString = calculate($image.width(), $image.height(), 0, 0, item);
            svg.polygon(pointsString).fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
        });
    }else{
        Feng.error("SVG not supported");
    }
};

/**
 * 移除场景图线圈
 */
SceneMager.cleanRegion = function () {
    var polygons = SVG.select('polygon').members;
    if(polygons.length){
        polygons.forEach(function (t) {
            t.remove();
        });
    }
    SVG('imageContainer').off().remove();
};


/**
 * 线圈配置
 */
SceneMager.regionConfig = function () {
    var preset = SceneMager.ztreeObj.getSelectedNodes()[0];
    var deviceid = preset.pId.toString();
    var presetid = parseInt(preset.id);
    var picUrl = $(this).parent().prev().attr("src");
    var presetname = preset.name;
    var device = preset.getParentNode();
    var devicename = device.name;
    location.href = '/drawregion/drawregion?deviceid='+deviceid+'&presetid='+presetid+'&picUrl='+picUrl+'&devicename='+devicename+'&presetname='+presetname;
};

$(function () {
    SceneMager.initDeviceList();

    $('.tz-gallery')
        .on('click', '.drawRegion', SceneMager.regionConfig)
        .on('click', '.setScene', SceneMager.setScene)
        .on('click', '.removePic', SceneMager.picDel);
});