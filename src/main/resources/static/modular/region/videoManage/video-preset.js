var VideoPreset = {
    editItem: null,      //编辑预置位
    layerIndex: -1
};
/**
 * 初始化预置位列表
 */
VideoPreset.initPresets = function (deviceid) {
    var $presetlist = $('#presetlist');
    $presetlist.empty();
    $.post(baseURL+'/preset/presetlist',{'deviceid':deviceid}, function (data) {
        var html = "";
        $.each(data, function (i, item) {
            html = html + '<a href="##" id="'+item.presetid+'" title="'+item.presetname+'" class="list-group-item" data-device="'+deviceid+'" data-positionType="'+item.positiontype+'" data-YJ="'+item.presetidyj+'" data-presetalias="'+item.presetalias+'">'+item.presetname
                + '<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;"></span><span class="glyphicon glyphicon-screenshot pull-right hidden" title="转到" name="turnto" style="margin-right: 8px;"></span></a>';
        });
        if(!html){
            $presetlist.append('<p class="text-center control-tips">无预置位</p>');
            return;
        }
        $presetlist.append(html);
        $presetlist.on('mouseover', '.list-group-item', function () {
            $(this).children('span').removeClass('hidden');
        }).on('mouseleave', '.list-group-item', function () {
            $(this).children('span').addClass('hidden');
        });
    });
};
/**
 * 预置位转到
 */
VideoPreset.turnto = function (preset) {
    var deviceid = $(preset).attr('data-device');
    var presetid = preset.id;
    //去掉线圈
    if(VideoMager.svgDrawer){
        VideoMager.svgDrawer.remove();
        VideoMager.svgDrawer = null;
    }
    $('#svg-container').remove();
    VideoMager.openVideo(deviceid, presetid);
};
/**
 * 预置位编辑
 */
VideoPreset.edit = function (preset) {
    var $preset = $(preset);
    VideoPreset.layerIndex = layer.open({
        type: 1,
        title: "编辑预置位",
        area: ['600px', '410px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $('#preset_edit').html(),
        success: function (index, layero) {
            VideoPreset.editItem = $preset;
            VideoPtz.initPositionType();
            VideoPtz.initYJPreset();
            $('#presetname').val($preset.attr('title'));
            $('#presetid').val($preset.attr('id')).attr('disabled', true);
            $('#presetalias').val($preset.attr('data-presetalias'));
            var value = $preset.attr('data-positionType').split(','), positionName = [];
            var positionType = VideoPtz.getPositionType();
            for(var i=0;i<value.length;i++){
                for(var j=0;j<positionType.length;j++){
                    if(positionType[j].id==value[i]){
                        $('#positionType').val(value[i]).trigger('change');
                        break;
                    }
                }
            }
            $('#presetidyj').val($preset.attr('data-YJ')).trigger('change');
        },
        end: function () {
            VideoPreset.editItem = null;
        }
    });
};
/**
 * 保存预置位
 */
VideoPreset.save = function () {
    var presetname = $('#presetname').val();
    var presetid = $('#presetid').val();
    var presetalias = $('#presetalias').val();
    var positiontype = $('#positionType').find('option:selected').val();
    var presetidyj = $('#presetidyj').find('option:selected').val();
    if(!presetidyj){
        presetidyj = 0;
    }
    var presetData = {
        deviceid: VideoMager.deviceid,
        presetid: parseInt(presetid),
        presetname: presetname,
        presetalias: presetalias,
        positiontype: positiontype,
        presetidyj: parseInt(presetidyj)
    };
    $.post(baseURL + "/preset/presetupdate", presetData,function (result) {
        Feng.success("保存成功");
        VideoPreset.editItem.attr({
            'title': presetname,
            'data-presetalias': presetalias,
            'data-positionType': positiontype,
            'data-YJ': presetidyj,

        }).text(presetname).append('<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;"></span><span class="glyphicon glyphicon-screenshot pull-right hidden" title="转到" name="turnto" style="margin-right: 8px;"></span>');
        layer.close(VideoPreset.layerIndex);
    });
};
/**
 * 预置位删除
 */
VideoPreset.remove = function (preset) {
    var deviceid = $(preset).attr('data-device');
    var presetid = preset.id;
    var oper = function () {
        var ajax = new $ax(baseURL + "/preset/presetdel", function () {
            Feng.success("删除成功!");
            $(preset).remove();
        }, function (data) {
            Feng.error("删除失败!" + data.message + "!");
        });
        ajax.set({"deviceid":deviceid, "presetid": presetid});
        ajax.start();
    };
    Feng.confirm('是否删除预置位？', oper);
};
$('#presetlist').on('click', 'span.glyphicon', function (e) {
    var buttonname = $(e.target).attr('name');
    var preset = $(e.target).parent()[0];
    switch (buttonname){
        case 'turnto': VideoPreset.turnto(preset);
            break;
        case 'edit': VideoPreset.edit(preset);
            break;
        case 'remove': VideoPreset.remove(preset);
            break;
    }
});