var VideoScene = {
    picUrl: "",
    region: [],   //线圈数据
    svgDrawer: null,
    svgDrawerSize: {
        width: 0,
        height: 0
    },
    image: null,   //场景图对象
    item: null,    //当前查看对象
};
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
 * 初始化预置位下的场景图
 */
VideoScene.initScene = function (deviceid, presetid) {
    var $sceneContainer = $('#carousel-example-generic');
    $sceneContainer.empty();
    var ajax = new $ax(baseURL + "/scene/presetinfo", function (result) {
        var viewList = result.viewList;//场景图列表
        var html = "";
        $.each(viewList, function (i, item) {
            if(item.pic.indexOf(',')>=0){
                item.pic = item.pic.split(',')[0];
            }
            if(i==0){
                html = html + '<div class="item active">' +
                    '<img src="'+baseURL+item.pic+'">' +
                    '</div>';
            }else{
                html = html + '<div class="item">' +
                    '<img src="'+baseURL+item.pic+'">' +
                    '</div>';
            }
        });
        if(viewList.length==0){
            $sceneContainer.append('<p class="text-center control-tips">无场景图</p>');
        }else{
            $sceneContainer.append('<div class="carousel-inner" role="listbox">'+html+'</div>');
            if(viewList.length>1){
                $sceneContainer.append('<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">' +
                    '<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>' +
                    '<span class="sr-only">Previous</span>' +
                    '</a>' +
                    '<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">' +
                    '<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>' +
                    '<span class="sr-only">Next</span>' +
                    '</a>');
            }
        }
    }, function (data) {
        Feng.error("获取预置位场景图失败");
    });
    ajax.set({"deviceid":deviceid, "presetid":parseInt(presetid)});
    ajax.start();
};
/**
 * 获取线圈数据
 */
VideoScene.getRegions = function (deviceid, presetid) {
    this.region = [];
    var ajax = new $ax(baseURL + "/piccap/regioninfo", function (data) {
        $.each(data, function (i, item) {
            item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
            item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
            VideoScene.region.push(item.areapath2);
        });
    }, function (data) {
        Feng.error("获取预置线圈数据失败");
        return;
    });
    ajax.set({"deviceid":deviceid, "presetid":parseInt(presetid)});
    ajax.start();
};
/**
 * 查看详情
 */
VideoScene.details = function (e) {
    var picUrl = e.target.src;
    var maxWidth = document.body.clientWidth * 0.8;
    var maxHeight = document.body.clientHeight * 0.8;
    var subtitle = [], path = VideoMager.ztreeObj.getSelectedNodes()[0].getPath();
    $.each(path, function (i, item) {
        subtitle.push(item.name);
    });
    layer.open({
        type: 1,
        title: "场景图详情<span style='margin-left: 1em;'>("+subtitle.join('--')+")</span>",
        area: [maxWidth+'px', maxHeight+'px'],
        fix: false,
        maxmin: false,
        resize: false,
        content: $('#sceneDetails').html(),
        success: function (layero, index) {
            if(SVG.supported) {
                $('#imageContainer').height(maxHeight-155);
                VideoScene.svgDrawer = SVG('imageContainer').size('100%', '100%');
                VideoScene.svgDrawerSize.width = $('#imageContainer').width();
                VideoScene.svgDrawerSize.height = $('#imageContainer').height();
                VideoScene.image = VideoScene.svgDrawer.image(picUrl).loaded(function(loader) {
                    var imageWidth = (loader.width/loader.height)*VideoScene.svgDrawerSize.height;
                    var layerWidth = imageWidth + 50;
                    $(layero).width(layerWidth);
                    var offsetWidth = (layerWidth - imageWidth)/2;
                    this.size(imageWidth, VideoScene.svgDrawerSize.height).move(offsetWidth, 0);
                });
            }else{
                Feng.error("SVG not supported");
            }
            VideoScene.item = e.currentTarget;
            $('#previous').bind('click', VideoScene.previous_scene);
            $('#next').bind('click', VideoScene.next_scene);
            $('#region').bind('click', VideoScene.toggleRegion);
            $('#draw').bind('click', VideoScene.drawRegion);
        },
        end: function () {
            $("#previous").unbind();
            $("#next").unbind();
            $("#region").unbind();
            $("#draw").unbind();
            if(VideoScene.svgDrawer) {
                VideoScene.svgDrawer.remove();
            }
            if(VideoScene.item){
                VideoScene.item = null;
            }
        }
    });
};
/**
 * 上一张（场景图）
 */
VideoScene.previous_scene = function () {
    var prev = $(VideoScene.item).parent().prev().children('img');
    if(!prev.length) {
        Feng.info('已经是第一张');
        return;
    }
    VideoScene.item = prev[0];
    var url = $(VideoScene.item).attr('src');
    var polygons = SVG.select('polygon.layer-region').members;
    polygons.forEach(function (t) {
        t.remove();
    });
    VideoScene.image.remove();
    VideoScene.image = VideoScene.svgDrawer.image(url).loaded(function(loader) {
        var imageWidth = (loader.width/loader.height)*VideoScene.svgDrawerSize.height;
        var layerWidth = imageWidth + 50;
        var offsetWidth = (layerWidth - imageWidth)/2;
        this.size(imageWidth, VideoScene.svgDrawerSize.height).move(offsetWidth, 0);
    });
    $('#region').text('显示线圈');
};
/**
 * 下一张（场景图）
 */
VideoScene.next_scene = function () {
    var next = $(VideoScene.item).parent().next().children('img');
    if(!next.length) {
        Feng.info('已经是最后一张');
        return;
    }
    VideoScene.item = next[0];
    var url = $(VideoScene.item).attr('src');
    var polygons = SVG.select('polygon.layer-region').members;
    polygons.forEach(function (t) {
        t.remove();
    });
    VideoScene.image.remove();
    VideoScene.image = VideoScene.svgDrawer.image(url).loaded(function(loader) {
        var imageWidth = (loader.width/loader.height)*VideoScene.svgDrawerSize.height;
        var layerWidth = imageWidth + 50;
        var offsetWidth = (layerWidth - imageWidth)/2;
        this.size(imageWidth, VideoScene.svgDrawerSize.height).move(offsetWidth, 0);
    });
    $('#region').text('显示线圈');
};
/**
 * 查看线圈
 */
VideoScene.toggleRegion = function () {
    if(VideoScene.region.length){
        var polygons = SVG.select('polygon.layer-region').members;
        if(polygons.length){
            polygons.forEach(function (t) {
                t.remove();
            });
            $(this).text('显示线圈');
        }else{
            VideoScene.region.forEach(function (item) {
                var pointsString = calculate(VideoScene.image.width(), VideoScene.image.height(), VideoScene.image.x(), VideoScene.image.y(), item);
                VideoScene.svgDrawer.polygon(pointsString).addClass('layer-region').fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
            });
            $(this).text('隐藏线圈');
        }
    }else{
        Feng.info('当前预置位无线圈');
    }
};
/**
 * 绘制线圈
 */
VideoScene.drawRegion = function () {
    var url = $(VideoScene.item).attr('src');
    var preset = VideoMager.ztreeObj.getSelectedNodes()[0];
    var presetname = preset.name;
    var device = preset.getParentNode();
    var devicename = device.name;
    location.href = '/drawregion/drawregion?deviceid='+VideoMager.deviceid+'&presetid='+VideoMager.presetid+'&picUrl='+url+'&devicename='+devicename+'&presetname='+presetname;
};
$('#carousel-example-generic').on('dblclick', 'img', VideoScene.details);