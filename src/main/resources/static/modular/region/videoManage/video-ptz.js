var VideoPtz = {
    positionType: [],  //道路类型列表
    yjpresetList: [],  //银江预置位列表
    layerIndex: -1,
};
/**
 * 云台操作
 */
VideoPtz.ptz = function (operator,operator2) {
    console.log("OPT:"+operator+"|"+operator2);
    if(!VideoMager.videoObj || !VideoMager.videoObj.getState() || VideoMager.videoObj.getState()=="idle"){
        // Feng.info('请打开设备');
        return;
    }
    $.post(baseURL+'/preset/ptz', {'deviceid':VideoMager.deviceid, 'operator':operator,'operator2':operator2});
};
/**
 * 获取道路类型
 */
VideoPtz.getPositionType = function () {
    var result = null;
    var ajax = new $ax(baseURL + "/device/proplist", function (data) {
        result = data;
    }, function (data) {
        Feng.error("获取道路类型失败：" + data.message + "!");
    });
    ajax.start();
    return result;
};
/**
 * 获取银江预置位列表
 */
VideoPtz.getYJPreset = function (yjdeviceid) {
    var result = null;
    var ajax = new $ax(baseURL + "/preset/yjpresetlistbydeviceidgb", function (data) {
        result = data;
    }, function (data) {
        Feng.error("获取银江预置位失败：" + data.message + "!");
    });
    ajax.set("deviceidgb", yjdeviceid);
    ajax.start();
    return result;
};
/**
 * 初始化类型
 */
VideoPtz.initPositionType = function () {
    var data = this.getPositionType(), options = "";
    $('#positionType').empty();
    if(data){
        $.each(data, function (i ,item) {
            options = options + '<option value="' + item.id + '">' + item.name + '</option>';
        });
        $('#positionType').append(options);
    }
};
/**
 * 初始化银江预置位
 */
VideoPtz.initYJPreset = function () {
    var data = this.getYJPreset(VideoMager.deviceid), options = "";
    $('#presetidyj').empty()
    if(data){
        $.each(data, function (i ,item) {
            options = options + '<option value="' + item.presetidyj + '">' + item.presetidyj + ":" + item.presetnameyj + '</option>';
        });
        $('#presetidyj').append(options);
    }
};
/**
 * 获取预置位
 */
VideoPtz.getPreset = function () {
    var options = "", presetSel = $('#presetSel');
    presetSel.empty();
    var ajax = new $ax(baseURL + "/preset/presetlist", function (data) {
        $.each(data, function (i ,item) {
            options = options + '<option value="' + item.presetid + '">' + item.presetname + '</option>';
        });
        options = '<optgroup label="已有预置位">' + options + '</optgroup><option value="-1">新增预置位</option>';
        presetSel.append(options);
        presetSel.selectpicker({
            'selectedText': 'cat'
        }).change(function () {
            var value = $(this).find('option:selected').val();
            if(value==-1){
                $('#preset_add_form').removeClass('hidden');
            }else{
                $('#preset_add_form').addClass('hidden');
            }
        });
    }, function (data) {
        Feng.error("获取预置位失败：" + data.message + "!");
    });
    ajax.set("deviceid", VideoMager.deviceid);
    ajax.start();
};
/**
 * 设置预置位
 */
VideoPtz.setPreset = function () {
    if(!VideoMager.videoObj || !VideoMager.videoObj.getState() || VideoMager.videoObj.getState()=="idle"){
        // Feng.info('视频未打开');
        return;
    }
    this.layerIndex = layer.open({
        type: 1,
        title: '设置预置位',
        area: ['700px','500px'],
        fix: false,
        maxmin: false,
        resize: false,
        content: $('#setpreset').html(),
        success: function (layero, index) {
            VideoPtz.getPreset();
            VideoPtz.initPositionType();
            VideoPtz.initYJPreset();
        }
    })

};
/**
 * 保存预置位设置
 */
VideoPtz.save = function () {
    var sel = $('#presetSel').find('option:selected').val();
    if(sel==-1){
        //add a new preset and bind to current direction
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
    $.post(baseURL + "/preset/presetadd", presetData, function (result) {
        Feng.success("保存成功");
        $('#presetlist').append('<a href="##" id="'+presetid+'" title="'+presetname+'" class="list-group-item" data-device="'+VideoMager.deviceid+'" data-positionType="'+positiontype+'" data-YJ="'+presetidyj+'" data-presetalias="'+presetalias+'">'+presetname
            + '<span class="glyphicon glyphicon-remove pull-right hidden" title="删除" name="remove" style="color: red;"></span><span class="glyphicon glyphicon-edit pull-right hidden" title="编辑" name="edit" style="margin-right: 8px;"></span><span class="glyphicon glyphicon-screenshot pull-right hidden" title="转到" name="turnto" style="margin-right: 8px;"></span></a>');
        layer.close(VideoPtz.layerIndex);
            var node = VideoMager.ztreeObj.getSelectedNodes()[0], parentNode = null;
            var newNode = {
                name: presetname,
                id: presetid,
                pId: VideoMager.deviceid,
                type: 'preset'
            };
            if(node.type=='preset'){
                parentNode = node.getParentNode();
            }else if(node.type=='device'){
                parentNode = node;
            }
            VideoMager.ztreeObj.addNodes(parentNode, newNode, true);
        });
    }else{
        //bind a existing preset to current direction
    }

};
