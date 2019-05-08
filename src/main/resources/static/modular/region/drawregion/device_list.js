var baseUrl = '';
var picBaseUrl = '';
var Drawregion = {
    deviceid: "",       //设备id
    presetid: "",       //预置位id
    picUrl: "",     //当前场景图url
    ztreeObj: null,      //ztree对象
    svg: null
};
var qs = decodeURI(location.search);
/**
 * 获取场景图
 * @param deviceid
 * @param presetid
 */
Drawregion.getPreView = function (deviceid,presetid) {
    var addToList = function (result) {
        var keywordList = [];
        $.ajax({
            type: 'post',
            url: baseUrl + "/scene/keywordlist",
            async: false,
            success: function (data) {
                keywordList = data;
            }
        });
        result.forEach(function (item) {
            var keyword = [];
            if(item.keyword){
                item.keyword = eval ("(" + item.keyword + ")");
                $.each(item.keyword, function (i, item) {
                    keywordList.forEach(function (t) {
                        if(t.id==item){
                            keyword.push(t.name);
                            return;
                        }
                    });
                });
                item.keyword = keyword.join('，');
            }
            var html = '<div class="row scene_item" data-deviceId="'+item.deviceid+'" data-presetId="'+item.presetid+'">'+
                '<div class="col-sm-7 scene_image">'+
                '<img src="'+picBaseUrl+item.pic+'" style="border: solid 1px black;max-width: 50%;">'+
                '</div>'+
                '<div class="col-sm-5 scene_infoCon">'+
                '<div class="scene_info">'+
                '<p><label>场景图片:</label><span id="img_name">'+item.picdescription+'</span></p>'+
                '<p><label>关键词:</label><span id="key_word">'+item.keyword+'</span></p>'+
                '<p><label>截图时间:</label><span id="img_time">'+item.createtime+'</span></p>'+
                '</div>'+
                '</div>'+
                '</div>';
            $('#scene').append(html);
        });
    };
    var ajax = new $ax(baseUrl + "/drawregion/previewlist", function (result) {
        $('#scene').empty();
        addToList(result);
    }, function () {
        Feng.error("获取场景图失败");
    });
    ajax.set({"deviceid":deviceid,"presetid":presetid});
    ajax.start();
};
/**
 * 选中设备
 * @param e
 * @param treeId
 * @param treeNode
 */
Drawregion.onClickPreset = function (e, treeId, treeNode) {
    if(treeNode.type=="preset"){
        Drawregion.getPreView(treeNode.pId,treeNode.id);
    }
};
/**
 * 查看详情
 */
Drawregion.showImage = function () {
    if($('.scene_active').length){
        var title = $('.scene_active #img_name').html();
        var layerW = document.body.clientWidth * 0.7;
        var layerH = document.body.clientHeight * 0.7;
        layer.open({
            type: 1,
            title: [title,'font-size: 18px;'],
            area: [layerW+'px', layerH+'px'],
            fix: false, //不固定
            maxmin: true,
            resize: false,
            content: '<div id="image-container" style="width: 100%;height: 100%;overflow: hidden;"></div>',
            success: function (layero, index) {
                layerH = layerH - 42;
                Drawregion.svg = SVG('image-container').size('100%', '100%');
                Drawregion.svg.image(Drawregion.picUrl).loaded(function(loader) {
                    var width_ratio = layerW/loader.width;
                    var height_ratio = layerH/loader.height;
                    var image_width, image_height, image_offset = {
                        x: 0,
                        y: 0
                    };
                    if(width_ratio>height_ratio){
                        var ratio1 = loader.width/loader.height;
                        image_width = layerH*ratio1;
                        image_height = layerH;
                        image_offset.x = (layerW-image_width)/2;
                        this.size(image_width, image_height).move(image_offset.x, 0);
                    }else{
                        var ratio2 = loader.height/loader.width;
                        image_width = layerW;
                        image_height = layerW*ratio2;
                        image_offset.y = (layerH-image_height)/2;
                        this.size(image_width, image_height).move(0, image_offset.y);
                    }
                    Drawregion.drawRegions(image_width, image_height, image_offset.x, image_offset.y);
                });
            }
        });
    }else {
        Feng.info('请选择场景图');
    }
};
/**
 * 绘制线圈
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
Drawregion.drawRegions = function (imageW, imageH, x, y) {
    var ajax = new $ax(baseUrl + "/piccap/regioninfo", function (result) {
        $.each(result, function (i, item) {
            item.areapath2 = item.areapath2.replace(/\s/g,'').replace(/]\[/g," ");
            item.areapath2 = item.areapath2.substring(1,item.areapath2.length-1);
            var pointsString = calculate(imageW, imageH, x, y, item.areapath2);
            Drawregion.svg.polygon(pointsString).fill('yellow').attr({'stroke': '#db913d','stroke-width': 3,'fill-opacity': 0.2, 'stroke-dasharray':"5,5"});
        });
    }, function () {
        Feng.error("获取场景图失败");
    });
    ajax.set({"deviceid":Drawregion.deviceid,"presetid":Drawregion.presetid});
    ajax.start();
};
/**
 * 选中场景图
 * @param event
 */
Drawregion.sceneClick = function (event) {
    $('.scene_item').removeClass('scene_active');
    $(this).addClass('scene_active');
    Drawregion.deviceid = $(this).attr('data-deviceId');
    Drawregion.presetid = $(this).attr('data-presetId');
    Drawregion.picUrl = $(this).find('img').attr('src');
};



Drawregion.regionConfig = function () {
    if($('.scene_active').length){
        location.href = '/drawregion/drawregion?deviceid='+Drawregion.deviceid+'&presetid='+Drawregion.presetid+'&picUrl='+Drawregion.picUrl;
    }else {
        Feng.info('请选择场景图');
    }
};
/**
 * 初始化设备列表
 */
Drawregion.initDeviceList = function () {
    var ztree = new $ZTree("deviceTree", baseUrl + "/drawregion/devicetree");
    ztree.bindOnClick(Drawregion.onClickPreset);
    Drawregion.ztreeObj = ztree.init();
    var filterFunc = function (node){
        return (node.level==0 && !node.children);
    };
    var nodes = Drawregion.ztreeObj.getNodesByFilter(filterFunc, false);
    Drawregion.ztreeObj.hideNodes(nodes);
    $('#searcher').keyup(function () {
        var keyword = $(this).val();
        ztree.filter(Drawregion.ztreeObj, keyword);

    });
    if(qs.length){
        qs = qs.substring(1);
        var items = qs.split('&'), param = [];
        items.forEach(function (t) {
            var item = t.split('=');
            param.push(item[1]);
        });
        var device = Drawregion.ztreeObj.getNodeByParam("id", param[0], null);
        if(device){
            Drawregion.ztreeObj.expandNode(device, true, true, true);
            $.each(device.children, function (i, item) {
                if(item.id==param[1]){
                    Drawregion.ztreeObj.selectNode(item);
                }
            });
            this.getPreView(param[0], param[1]);
        }
    }
};

$(function () {
    Drawregion.initDeviceList();
    $('#scene').on('click', '.scene_item', Drawregion.sceneClick).on('dblclick', '.scene_item', Drawregion.showImage);
});