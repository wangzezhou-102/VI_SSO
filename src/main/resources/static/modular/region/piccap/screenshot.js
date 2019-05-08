var baseURL = '';
var Sceneshoter = {
    ztreeObj: null,
    videoObj: cyberplayer("dvplay"),
    layerIndex: -1,
    rtmpAddress: "",
    svgDrawer: SVG('svg-container').size('100%', '100%'),
    regionObj: {
        imageW: 0,
        imageH: 0,
        regions: []
    }
};

/**
 * 预置位点击
 */
Sceneshoter.onClickPreset = function (e, treeId, treeNode) {
    if(treeNode.type=="device" || treeNode.type=="preset") {
        $('.shotContainer').empty();
        var deviceid = treeNode.id,presetid = 0;
        if(treeNode.type=="preset") {
            presetid = treeNode.id;
            deviceid = treeNode.pId;
            Sceneshoter.initPicList(deviceid, presetid, treeNode.name);
        }
        var polygons = SVG.select('polygon').members;
        if(polygons.length){
            polygons.forEach(function (t) {
                t.remove();
            });
        }
        Sceneshoter.startVideo(deviceid, presetid);
    }
};
/**
 * 打开视频
 */
Sceneshoter.startVideo = function (deviceid, presetid) {
    var ajax = new $ax(baseURL + "/piccap/presetinfo", function (data) {
        if(data.status=='1'){
            var rtmpAddress = data.rtmpAddress;
            if(Sceneshoter.rtmpAddress!=rtmpAddress){
                if(Sceneshoter.videoObj){
                    Sceneshoter.videoObj.remove();
                }
                Sceneshoter.videoObj = cyberplayer("dvplay").setup({
                    stretching: "uniform",
                    file: rtmpAddress,
                    autostart: true,
                    width: '100%',
                    height: '100%',
                    repeat: false,
                    volume: 100,
                    controls: true,
                    controlbar: {
                        barLogo: false
                    }
                });
                Sceneshoter.rtmpAddress = rtmpAddress;
            }
        }else{
            Feng.info("无码流");
            Sceneshoter.videoObj.stop();
            Sceneshoter.rtmpAddress = "";
            if(Sceneshoter.videoObj){
                Sceneshoter.videoObj.remove();
            }
        }
    },function (data) {
        Feng.error('视频打开出错');
        if(Sceneshoter.videoObj){
            Sceneshoter.videoObj.remove();
        }
    });
    ajax.set({"deviceid":deviceid, "presetid":presetid});
    ajax.start();
};
/**
 * 初始化截图列表
 */
Sceneshoter.initPicList = function (deviceid, presetid, presetname) {
    var ajax = new $ax(baseURL + "/piccap/presetinfo", function (data) {
        var $shotContainer = $('.shotContainer');
        $shotContainer.empty();
        var screenshotList = data.screenshotList;
        $.each(screenshotList, function (i, item) {
            var html = '<div class="scene_item piccap-item" ondblclick="SceneInfo.details(event)">' +
                '<div class="scene_image">' +
                '<img src="'+baseURL+item.picpath+'" id="'+item.id+'" class="image">' +
                '</div>' +
                '<input type="hidden" data-deviceid="'+deviceid+'" data-presetid="'+presetid+'"/>' +
                '<span class="glyphicon glyphicon-remove-circle remove"></span>' +
                '<div class="pic-info">' +
                '<p><label>预置位:</label><span>'+presetname+'</span></p>' +
                '<p><label>截图时间:</label><span>'+item.createtime+'</span></p>' +
                '</div>' +
                '</div>';
            $shotContainer.append(html);
        });

    }, function (data) {
        Feng.error("获取截图列表失败");
    });
    ajax.set({"deviceid":deviceid, "presetid":presetid});
    ajax.start();
};
/**
 * 删除截图
 */
Sceneshoter.picDel = function (event) {
    var oper = function () {
        var $picItem = $(event.target).parent();
        var picId = $picItem.find('img').attr('id');
        var ajax = new $ax(baseURL + "/scene/screenshotdel", function (data) {
            Feng.success('删除成功');
            $picItem.remove();
        }, function (data) {
            Feng.error("删除失败");
        });
        ajax.set({
            'screenshotid': picId
        });
        ajax.start();
    };
    Feng.confirm("是否删除截图？", oper);
    event.stopImmediatePropagation();
};
/**
 * 截图
 */
Sceneshoter.shot = function () {
    var presetItem = Sceneshoter.ztreeObj.getSelectedNodes()[0];
    if(!presetItem || presetItem.type!='preset'){
        Feng.info('请选择预置位视频');
        return;
    }
    if(!Sceneshoter.rtmpAddress){
        Feng.info('无码流');
        return;
    }

    var deviceid = presetItem.pId;
    var presetid = presetItem.id;
    var presetname = presetItem.name;
    var param = {
        'rtmpaddr': Sceneshoter.rtmpAddress,
        'deviceid': deviceid,
        'presetid': presetid
    };
    var ajax = new $ax(baseURL + "/piccap/capture", function (data) {
        Feng.success('截图成功');
        var html = '<div class="scene_item piccap-item" ondblclick="SceneInfo.details(event)">' +
            '<div class="scene_image">' +
            '<img src="'+baseURL+data.picpath+'" id="'+data.id+'" class="image">' +
            '</div>' +
            '<input type="hidden" data-deviceid="'+data.deviceid+'" data-presetid="'+data.presetid+'"/>' +
            '<span class="glyphicon glyphicon-remove-circle remove"></span>' +
            '<div class="pic-info">' +
            '<p><label>预置位:</label><span>'+presetname+'</span></p>' +
            '<p><label>截图时间:</label><span>'+data.createtime+'</span></p>' +
            '</div>' +
            '</div>';
        $('.shotContainer').append(html);
    }, function (data) {
        Feng.error("截图失败");
    });
    ajax.set(param);
    ajax.start();
};
/**
 * 查看线圈
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
Sceneshoter.checkRegions = function (e) {
    var target = e.target;
    if($(target).attr('data-state')==1){
        var polygons = SVG.select('polygon').members;
        if(polygons.length){
            polygons.forEach(function (t) {
                t.remove();
            });
        }
        $(target).attr('data-state', 0).text('查看线圈');
    }else{
        var presetItem = Sceneshoter.ztreeObj.getSelectedNodes()[0];
        if(!presetItem || presetItem.type!='preset'){
            Feng.info('请选择预置位');
            return;
        }
        if(!Sceneshoter.videoObj || Sceneshoter.videoObj.getState()=="idle"){
            Feng.info('视频未打开');
            return;
        }
        var ajax = new $ax(baseURL + "/piccap/region_scene_info", function (data) {
            var regions = [];
            $.each(data.regionList, function (i, item) {
                item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
                item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
                regions.push(item.areapath2);
            });
            Sceneshoter.regionObj.regions = regions;
            if(regions.length<=0) {
                Feng.info('当前预置位无线圈');
                return;
            }
            if(data.sceneList.length<=0){
                Feng.info('没有场景图，无法显示线圈');
                return;
            }
            var img = new Image();
            img.src = baseURL + data.sceneList[0].pic;
            img.onload = function(){
                //获取截图尺寸（用图片尺寸替代播放流尺寸）
                Sceneshoter.regionObj.imageW = img.width;
                Sceneshoter.regionObj.imageH = img.height;
                Sceneshoter.drawRegions();
                $(target).attr('data-state', 1).text('隐藏线圈');
            };
        }, function (data) {
            Feng.error("获取流尺寸失败");
        });
        ajax.set({"deviceid":presetItem.pId,"presetid":presetItem.id});
        ajax.start();
    }
};
Sceneshoter.drawRegions = function () {
    var polygons = SVG.select('polygon').members;
    if(polygons.length){
        polygons.forEach(function (t) {
            t.remove();
        });
    }
    var videoOffset = {
        x: 0,
        y: 0
    }, imageW, imageH;
    //播放器窗口尺寸
    var playerW = Sceneshoter.videoObj.getWidth();
    var playerH = Sceneshoter.videoObj.getHeight();
    if(Sceneshoter.regionObj.imageW>Sceneshoter.regionObj.imageH){
        imageW = playerW;
        imageH = playerW*(Sceneshoter.regionObj.imageH/Sceneshoter.regionObj.imageW);
        videoOffset.y = (playerH - imageH)/2;
    }else if(Sceneshoter.regionObj.imageW>Sceneshoter.regionObj.imageH){
        imageW = playerH*(Sceneshoter.regionObj.imageW/Sceneshoter.regionObj.imageH);
        imageH = playerH;
        videoOffset.x = (playerW - imageW)/2;
    }else if(Sceneshoter.regionObj.imageW==Sceneshoter.regionObj.imageH){
        if(playerW>playerH){
            imageW = playerH*(Sceneshoter.regionObj.imageW/Sceneshoter.regionObj.imageH);
            imageH = playerH;
            videoOffset.x = (playerW - imageW)/2;
        }else if(playerW<playerH){
            imageW = playerW;
            imageH = playerW*(Sceneshoter.regionObj.imageH/Sceneshoter.regionObj.imageW);
            videoOffset.y = (playerH - imageH)/2;
        }else if(playerW==playerH){
            imageW = playerW;
            imageH = playerH;
        }
    }
    Sceneshoter.regionObj.regions.forEach(function (item) {
        var pointsString = calculate(imageW, imageH, videoOffset.x, videoOffset.y, item);
        Sceneshoter.svgDrawer.polygon(pointsString).fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
    });
};
/**
 * 查看回放
 */
Sceneshoter.playback = function () {
    var presetItem = Sceneshoter.ztreeObj.getSelectedNodes()[0];
    if(!presetItem || presetItem.type!='preset'){
        Feng.info('请选择预置位');
        return;
    }
    Sceneshoter.layerIndex = layer.open({
        type: 1,
        title: '选择回放视频',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#playback_selectLayer').html(),
        success: function (layero, index) {
            $('#playback_list').empty();
            var ajax = new $ax(baseURL + "/scene/screenshotdel", function (data) {
                if(data){
                    var html = "";
                    $.each(data, function (i, item) {
                        html = html + '<a href="##" id="'+item.id+'" title="'+item.name+'" class="list-group-item">' +
                            '<input type="radio" name="playbackItem" checked>' + item.name +
                            '</a>';
                    });
                    $('#playback_list').append(html);
                }else{
                    Feng.info('设备无视频录像');
                }
            }, function (data) {
                Feng.error("获取回放列表失败");
            });
            ajax.set({
                'deviceid': presetItem.pId,
                'presetid': presetItem.id
            });
            // ajax.start();
        }
    });
};
/**
 * 回放列表
 */
Sceneshoter.playbacklist_taggle = function (event) {
    var e = event || window.event;
    var target = e.target;
    if(target.tagName.toLowerCase()=='input'){
        target = target.parentNode;
    }else{
        var $radio = $(target).children('input');
        $radio.prop('checked',!$radio.prop('checked'));
    }
};
/**
 * layer确定
 */
Sceneshoter.layer_sure = function () {
    var playbackItem = $('#playback_list').find('input[type="radio"]:checked');
    if(!playbackItem){
        Feng.info('请选择回放项');
        return;
    }
    playbackItem.parent();
    layer.close(Sceneshoter.layerIndex);
};
/**
 * 初始化设备列表
 */
Sceneshoter.initDeviceList = function () {
    var ztree = new $ZTree("device_tree", baseURL + "/piccap/devicelist");
    ztree.bindOnClick(Sceneshoter.onClickPreset);
    Sceneshoter.ztreeObj = ztree.init();
    var filterFunc = function (node){
        return (node.level==0 && !node.children);
    };
    var nodes = Sceneshoter.ztreeObj.getNodesByFilter(filterFunc, false);
    Sceneshoter.ztreeObj.hideNodes(nodes);
    $('#searcher').keyup(function () {
        var keyword = $(this).val();
        ztree.filter(Sceneshoter.ztreeObj, keyword);
    });
};

$(function () {
    Sceneshoter.initDeviceList();

    $('.shotContainer').on('click', '.remove', Sceneshoter.picDel);
    $('#svg-container').resize(function () {
        var polygons = SVG.select('polygon').members;
        if(polygons.length<=0) return;
        Sceneshoter.drawRegions();
    });
});