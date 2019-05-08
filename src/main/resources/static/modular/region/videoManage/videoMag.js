var baseURL = '';
var VideoMager = {
    ztreeObj: null,
    videoObj: cyberplayer("dvplay"),
    rtmpAddress: "",
    svgDrawer: null,  //svg对象
    deviceid: "",   //当前设备id
    presetid: "",   //当前预置位id
    regionObj: {
        imageW: 0,
        imageH: 0,
        regions: null
    },               //保存视频线圈信息
    layerIndex: -1
};
VideoMager.openLayer = function (id, title, width, height) {
    width = width ? width:document.body.clientWidth * 0.8;
    height = height ? height:document.body.clientHeight * 0.8;
    var Mlayero = null;
    this.layerIndex = layer.open({
        type: 1,
        title: title,
        area: [width+'px',height+'px'],
        fix: false,
        maxmin: false,
        resize: false,
        content: $('#' + id).html(),
        success: function (layero, index) {
            Mlayero = layero;
        },
        cancel: function(index, layero){
            if($('#img').length){
                VideoMager.abandonPic();
            }
        }
    });
    return Mlayero;
};
var qs = decodeURI(location.search);
/**
 * 初始化设备列表
 */
VideoMager.initDeviceList = function () {
    var ztree = new $ZTree("device_tree", baseURL + "/scene/devicelist");
    ztree.bindOnClick(VideoMager.onClickTree);
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
        VideoMager.ztreeObj = ztree.initLocal(data);
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    });
    ajax.start();
    var filterFunc = function (node){
        return (node.level==0 && !node.children);
    };
    var nodes = VideoMager.ztreeObj.getNodesByFilter(filterFunc, false);
    VideoMager.ztreeObj.hideNodes(nodes);
    $('#searcher').keyup(function (e) {
        var keyword = $(this).val();
        if(e.keyCode==13 || (keyword==''&&e.keyCode==8)){
            ztree.filter(VideoMager.ztreeObj, keyword);
        }
    });
    if(qs.length){
        qs = qs.substring(1);
        var items = qs.split('&'), param = [];
        items.forEach(function (t) {
            var item = t.split('=');
            param.push(item[1]);
        });
        var device = VideoMager.ztreeObj.getNodeByParam("id", param[0], null);
        if(device){
            VideoMager.ztreeObj.expandNode(device, true, true, true);
            // $.each(device.children, function (i, item) {
            //     if(item.id==param[1]){
            //         VideoMager.ztreeObj.selectNode(item);
            //     }
            // });
            // this.openVideo(param[0], param[1]);
            // VideoScene.initScene(param[0], param[1]);
            // VideoPreset.initPresets(param[0]);
            // VideoMager.initCruise(param[0]);
        }
        qs = "";
    }
};
/**
 * 点击设备/预置位
 */
VideoMager.onClickTree = function (e, treeId, treeNode) {
    if(treeNode.type=="device" || treeNode.type=="preset"){
        var deviceid = treeNode.id,presetid = 0;
        if(treeNode.type=="preset") {
            deviceid = treeNode.pId;
            presetid = treeNode.id;
            VideoScene.getRegions(deviceid, presetid);
            VideoScene.initScene(deviceid, presetid);
        }else if(treeNode.type=="device"){
            $('#carousel-example-generic').empty();
        }
        VideoMager.openVideo(deviceid, presetid);
        VideoPreset.initPresets(deviceid);
        // VideoMager.initCruise(deviceid);
        VideoMager.deviceid = deviceid;
        VideoMager.presetid = presetid;
        if(VideoMager.svgDrawer){
            VideoMager.svgDrawer.remove();
            VideoMager.svgDrawer = null;
        }
        $('#svg-container').remove();
        $('#showRegion').attr('data-state', 0);
    }
};
/**
 * 打开视频
 */
VideoMager.openVideo = function (deviceid, presetid) {
    var ajax = new $ax(baseURL + "/piccap/presetinfo", function (data) {
        if(data.status=='1'){
            var rtmpAddress = data.rtmpAddress;
            if(VideoMager.rtmpAddress!=rtmpAddress){
                if(VideoMager.videoObj){
                    VideoMager.videoObj.remove();
                }
                VideoMager.videoObj = cyberplayer("dvplay").setup({
                    stretching: "exactfit",
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
                VideoMager.rtmpAddress = rtmpAddress;
            }
        }else{
            Feng.info("无码流");
            VideoMager.videoObj.stop();
            VideoMager.rtmpAddress = "";
            if(VideoMager.videoObj){
                VideoMager.videoObj.remove();
            }
        }
    },function (data) {
        Feng.error('视频打开出错');
        if(VideoMager.videoObj){
            VideoMager.videoObj.remove();
        }
    });
    ajax.set({"deviceid":deviceid, "presetid":presetid});
    ajax.start();
};
/**
 * 截图
 */
VideoMager.shot = function () {
    var presetItem = VideoMager.ztreeObj.getSelectedNodes()[0];
    if(!presetItem || presetItem.type!='preset'){
        Feng.info('请选择预置位视频');
        return;
    }
    if(!VideoMager.rtmpAddress){
        Feng.info('无码流');
        return;
    }

    var deviceid = presetItem.pId;
    var presetid = presetItem.id;
    var param = {
        'rtmpaddr': VideoMager.rtmpAddress,
        'deviceid': deviceid,
        'presetid': presetid
    };
    var ajax = new $ax(baseURL + "/piccap/capture", function (data) {
        VideoMager.openShot(presetItem, data);
    }, function (data) {
        Feng.error("截图失败");
    });
    ajax.set(param);
    ajax.start();
};
/**
 * 打开截图
 */
VideoMager.openShot = function (presetItem, data) {
    var Mlayer = VideoMager.openLayer('shotScene', '截图');
    $('#img').find('img').height(document.body.clientHeight*0.8-255).attr({'src':baseURL+data.picpath, 'id':data.id}).load(function () {
        $(Mlayer).width($(this).width()+50);
    });
    var name = presetItem.getParentNode().name + '_' + presetItem.name + '_', html = "", $keyword = $('#keyword');
    new $ax(baseURL + "/scene/keywordlist", function (data) {
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
    }).start();
    $('#picType').bind("change",function(e){
        var value = $('#picType').find('option:selected').val();
        if(value=='1') {
            $('#scenename').val('').parents('.form-group').addClass('hidden');
            $keyword.selectpicker('val', []).parents('.form-group').addClass('hidden');
        }else if(value=='2'){
            $('#scenename').val(name).parents('.form-group').removeClass('hidden');
            $keyword.parents('.form-group').removeClass('hidden');
        }
    });
};
/**
 * 保存图片
 */
VideoMager.saveScene = function () {
    var picType = $('#picType').find('option:selected').val();
    if(picType=='1'){
        //截图
        Feng.info('截图成功');
        layer.close(VideoMager.layerIndex);
        return;
    }
    var picid = $('#img').find('img').attr('id');
    var picurl = $('#img').find('img').attr('src');
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
        layer.close(VideoMager.layerIndex);
        var html = '<div class="item active">' +
            '<img src="'+picurl+'">' +
            '</div>';
        var $carousel = $('#carousel-example-generic');
        var listbox = $carousel.find('.carousel-inner');
        if(listbox.length){
            $(listbox[0]).find('div.item.active').removeClass('active');
            $(listbox[0]).append(html);
            var control = $carousel.find('.carousel-control');
            if(!control.length){
                $carousel.append('<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">' +
                    '<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>' +
                    '<span class="sr-only">Previous</span>' +
                    '</a>' +
                    '<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">' +
                    '<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>' +
                    '<span class="sr-only">Next</span>' +
                    '</a>');
            }
        }else{
            $carousel.empty().append('<div class="carousel-inner" role="listbox">'+html+'</div>');
        }
    }, function (data) {
        Feng.error("设置失败");
    });
    ajax.set(param);
    ajax.start();
};
/**
 *
 */
VideoMager.abandonPic = function () {
    var picid = $('#img').find('img').attr('id');
    new $ax(baseURL + "/piccap/delscreenshot", function (data) {
        $('#keyword').selectpicker('destroy');
        layer.close(VideoMager.layerIndex);
    }, function (data) {
        Feng.error('删除截图失败');
    }).set('id', picid).start();
};
/**
 * 查看回放
 */
VideoMager.playback = function () {
    var presetItem = VideoMager.ztreeObj.getSelectedNodes()[0];
    if(!presetItem || presetItem.type!='preset'){
        Feng.info('请选择预置位');
        return;
    }
    VideoMager.openLayer('playback_selectLayer', '选择回放视频', 800, 500);
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
};
/**
 * 回放列表
 */
VideoMager.playbacklist_taggle = function (event) {
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
 * 开始回放
 */
VideoMager.startPlayback = function () {
    var playbackItem = $('#playback_list').find('input[type="radio"]:checked');
    if(!playbackItem){
        Feng.info('请选择回放项');
        return;
    }
    playbackItem.parent();
    layer.close(VideoMager.layerIndex);
};
/**
 * 查看线圈
 */
VideoMager.checkRegions = function (e) {
    var target = e.target;
    if(target.nodeName=='I'){
        target = $(target).parent()[0];
    }
    if($(target).attr('data-state')==1){
        $(target).attr('data-state', 0);
        if(VideoMager.svgDrawer){
            VideoMager.svgDrawer.remove();
            VideoMager.svgDrawer = null;
        }
        $('#svg-container').remove();
    }else{
        var presetItem = VideoMager.ztreeObj.getSelectedNodes()[0];
        if(!presetItem || presetItem.type!='preset'){
            Feng.info('请选择预置位');
            return;
        }
        if(!VideoMager.videoObj || VideoMager.videoObj.getState()=="idle"){
            Feng.info('视频未打开');
            return;
        }
        var ajax = new $ax(baseURL + "/piccap/region_scene_info", function (data) {
            //获取截图尺寸（用图片尺寸替代播放流尺寸）
            var img = new Image();
            for(var i=0;i<data.sceneList.length;i++){
                img.src = baseURL + data.sceneList[i].pic;
                if(img.width>0){
                    VideoMager.regionObj.imageW = img.width;
                    VideoMager.regionObj.imageH = img.height;
                    break;
                }
            }
            var regions = [];
            $.each(data.regionList, function (i, item) {
                item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
                item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
                regions.push(item.areapath2);
            });
            VideoMager.regionObj.regions = regions;
            if(regions.length<=0) {
                Feng.info('当前预置位无线圈');
                return;
            }
            $('#tvcontainer').append('<div id="svg-container" style="position: absolute;left: 0;top: 0;z-index: 500;width: 100%;height: 100%;opacity: 0.5;"></div>');
            VideoMager.svgDrawer = SVG('svg-container').size('100%', '100%');
            VideoMager.drawRegions();
            $(target).attr('data-state', 1);
        }, function (data) {
            Feng.error("获取流尺寸失败");
        });
        ajax.set({"deviceid":presetItem.pId,"presetid":presetItem.id});
        ajax.start();
    }
};
/**
 * 绘制
 */
VideoMager.drawRegions = function () {
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
    var playerW = VideoMager.videoObj.getWidth();
    var playerH = VideoMager.videoObj.getHeight();
    var stretching = VideoMager.videoObj.getStretching();
    if(stretching=='exactfit'){
        imageW = playerW;
        imageH = playerH;
    }else if(stretching=='uniform'){
        var radiusW = playerW/VideoMager.regionObj.imageW;
        var radiusH = playerH/VideoMager.regionObj.imageH;
        if(radiusW<radiusH){
            imageW = playerW;
            imageH = VideoMager.regionObj.imageH*radiusW;
            videoOffset.y = (playerH - imageH)/2;
        }else if(radiusW>radiusH){
            imageW = VideoMager.regionObj.imageW*radiusH;
            imageH = playerH;
            videoOffset.x = (playerW - imageW)/2;
        }else if(radiusW==radiusH){
            imageW = playerW;
            imageH = playerH;
        }
    }
    VideoMager.regionObj.regions.forEach(function (item) {
        var pointsString = calculate(imageW, imageH, videoOffset.x, videoOffset.y, item);
        VideoMager.svgDrawer.polygon(pointsString).fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
    });
};
/**
 * 调整视频
 */
VideoMager.VCRadapter = function (e) {
    if(!VideoMager.videoObj || VideoMager.videoObj.getState()=="idle"){
        Feng.info('视频未打开');
        return;
    }
    var target = e.target;
    if(target.nodeName=='I'){
        target = $(target).parent()[0];
    }
    if(VideoMager.svgDrawer){
        VideoMager.svgDrawer.remove();
        VideoMager.svgDrawer = null;
    }
    $('#svg-container').remove();
    $('#showRegion').attr('data-state', 0);
    VideoMager.videoObj.remove();
    var adapt = '';
    if($(target).attr('data-state')==1){
        adapt = 'uniform';
        $(target).attr('data-state', 0);
        $(target).children('i').attr('class', 'fa fa-arrows-alt');
    }else{
        adapt = 'exactfit';
        $(target).attr('data-state', 1);
        $(target).children('i').attr('class', 'fa fa-compress');
    }
    VideoMager.videoObj = cyberplayer("dvplay").setup({
        stretching: adapt,
        file: VideoMager.rtmpAddress,
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
};

VideoMager.fadeIn = function (event) {
    var $this = $(event.currentTarget);
    $this.fadeOut();
    if($this.next().hasClass('hidden')){
        $this.next().removeClass('hidden');
    }
    $this.next().fadeIn();
    var maxHeight = $(".ztree").css("max-height");
    $(".ztree").css("max-height", parseInt(maxHeight)-$this.next().height()+'px');
};

VideoMager.fadeOut = function (event) {
    var $parent = $(event.currentTarget).parent();
    $parent.fadeOut();
    if($parent.prev().hasClass('hidden')){
        $parent.prev().removeClass('hidden');
    }
    $parent.prev().fadeIn();
    var maxHeight = $(".ztree").css("max-height");
    $(".ztree").css("max-height", parseInt(maxHeight)+$parent.height()+'px');
};

window.onresize = function () {
    if($('.oper-control').is(':visible')){
        $(".ztree").css("max-height", $(".tree-panel").height()-$('.oper-control').height()-100+'px');
    }else{
        $(".ztree").css("max-height", $(".tree-panel").height()-120+'px');
    }

};

$(function () {
    VideoMager.initDeviceList();
    $( "#adjFocus-Slider" ).slider({
        // orientation: "vertical",
        range: "min",
        value: 3,
        min: 1,
        max: 10,
        animate: false,
        slide: function(event, ui) {
        }
    });
    $( "#gatFocus-Slider" ).slider({
        // orientation: "vertical",
        range: "min",
        value: 3,
        min: 1,
        max: 10,
        animate: false,
        slide: function(event, ui) {
        }
    });

    $('#tvcontainer').resize(function () {
        var polygons = SVG.select('polygon').members;
        if(polygons.length<=0) return;
        VideoMager.drawRegions();
    });
});