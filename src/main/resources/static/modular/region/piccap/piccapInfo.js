var SceneInfo = {
    item: null,//当前显示截图
    url: "",//当前显示截图的utl
    region: [],
    svgDrawer: null,
    svgDrawerSize: {
        width: 0,
        height: 0
    },
    image: null
};

SceneInfo.details = function () {
    var e = event || window.event;
    this.item = e.currentTarget;
    this.url = $(this.item).find('img').attr('src');
    var $hInput = $($(this.item).find('input:hidden')[0]);
    var deviceid = $hInput.attr('data-deviceid');
    var presetid = $hInput.attr('data-presetid');
    var ajax = new $ax(baseURL + "/piccap/regioninfo", function (data) {
        $.each(data, function (i, item) {
            item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
            item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
            SceneInfo.region.push(item.areapath2);
        });
    }, function (data) {
        Feng.error("获取预置线圈数据失败");
        return;
    });
    ajax.set({"deviceid":deviceid, "presetid":parseInt(presetid)});
    ajax.start();
    layer.open({
        type: 1,
        title: '场景图详情',
        area: ['800px', '510px'], //宽高
        fix: false, //不固定
        maxmin: true,
        resize: false,
        content: $('#details').html(),
        success: function (layero, index) {
            if(SVG.supported) {
                SceneInfo.svgDrawer = SVG('imageContainer').size('100%', '100%');
                SceneInfo.svgDrawerSize.width = $('#imageContainer').width();
                SceneInfo.svgDrawerSize.height = $('#imageContainer').height();
                SceneInfo.image = SceneInfo.svgDrawer.image(SceneInfo.url).loaded(function(loader) {
                    var imageWidth = (loader.width/loader.height)*SceneInfo.svgDrawerSize.height;
                    var offsetWidth = (SceneInfo.svgDrawerSize.width - imageWidth)/2;
                    this.size(imageWidth, SceneInfo.svgDrawerSize.height).move(offsetWidth, 0);
                });
            }else{
                Feng.error("SVG not supported");
            }
            $('#previous').bind('click', SceneInfo.previous);
            $('#next').bind('click', SceneInfo.next);
            $('#region').bind('click', SceneInfo.showRegion);
        },
        end: function () {
            $("#previous").unbind();
            $("#next").unbind();
            $("#region").unbind();
            SceneInfo.svgDrawer.remove();
            SceneInfo.region = [];
        }
    });
};
/**
 * 上一张
 */
SceneInfo.previous = function () {
    var prev = $(SceneInfo.item).prev();
    if(!prev.length) {
        Feng.info('已经是第一张');
        return;
    }
    SceneInfo.item = prev[0];
    SceneInfo.url = $(SceneInfo.item).find('img').attr('src');
    var polygons = SVG.select('polygon.layer-region').members;
    polygons.forEach(function (t) {
        t.remove();
    });
    SceneInfo.image.remove();
    SceneInfo.image = SceneInfo.svgDrawer.image(SceneInfo.url).loaded(function(loader) {
        var imageWidth = (loader.width/loader.height)*SceneInfo.svgDrawerSize.height;
        var offsetWidth = (SceneInfo.svgDrawerSize.width - imageWidth)/2;
        this.size(imageWidth, SceneInfo.svgDrawerSize.height).move(offsetWidth, 0);
    });
    $('#region').text('显示线圈');
};
/**
 * 下一张
 */
SceneInfo.next = function () {
    var next = $(SceneInfo.item).next();
    if(!next.length) {
        Feng.info('已经是最后一张');
        return;
    }
    SceneInfo.item = next[0];
    SceneInfo.url = $(SceneInfo.item).find('img').attr('src');
    var polygons = SVG.select('polygon.layer-region').members;
    polygons.forEach(function (t) {
        t.remove();
    });
    SceneInfo.image.remove();
    SceneInfo.image = SceneInfo.svgDrawer.image(SceneInfo.url).loaded(function(loader) {
        var imageWidth = (loader.width/loader.height)*SceneInfo.svgDrawerSize.height;
        var offsetWidth = (SceneInfo.svgDrawerSize.width - imageWidth)/2;
        this.size(imageWidth, SceneInfo.svgDrawerSize.height).move(offsetWidth, 0);
    });
    $('#region').text('显示线圈');
};
/**
 * 查看线圈
 */
SceneInfo.showRegion = function () {
    if(SceneInfo.region.length){
        var polygons = SVG.select('polygon.layer-region').members;
        if(polygons.length){
            polygons.forEach(function (t) {
                t.remove();
            });
            $(this).text('显示线圈');
        }else{
            SceneInfo.region.forEach(function (item) {
                var pointsString = calculate(SceneInfo.image.width(), SceneInfo.image.height(), SceneInfo.image.x(), SceneInfo.image.y(), item);
                SceneInfo.svgDrawer.polygon(pointsString).addClass('layer-region').fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
            });
            $(this).text('隐藏线圈');
        }
    }else{
        Feng.info('当前预置位无线圈');
    }

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