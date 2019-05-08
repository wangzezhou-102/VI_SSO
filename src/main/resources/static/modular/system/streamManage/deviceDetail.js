var baseUrl = '';
var DeviceDetail = {
    deviceid: "",       //设备id
    logid: -1,          //日志id
    maxLogs: 500,        //最多显示日志条数
    intervalIndex: -1    //interval编号
};
var qs = decodeURI(location.search);
if(qs.length){
    qs = qs.substring(1);
    var items = qs.split('&');
    items.forEach(function (t) {
        var item = t.split('=');
        DeviceDetail[item[0]] = item[1];
    });
}

/**
 * 页面unload之前清除进程资源
 * @param e
 */
window.onbeforeunload = function(e){
    clearInterval(DeviceDetail.intervalIndex);
};

/**
 * 匹配设备类型
 */
DeviceDetail.deviceTypeAdaper = function (type) {
    var text = "";
    switch (type.toString()){
        case "1": text = "球机";
            break;
        case "2": text = "半球";
            break;
        case "3": text = "固定枪机";
            break;
        case "4": text = "遥控枪机";
            break;
        default: text = "未知类型";
            break;
    }
    return text;
};

/**
 * 匹配设备状态
 */
DeviceDetail.deviceStatusAdaper = function (state) {
    var text = "";
    switch (state.toString()){
        case "0": text = '<span class="label label-default">未启动</span>';
            break;
        case "1": text = '<span class="label label-primary">已启动</span>';
            break;
        case "2": text = '<span class="label label-info">正在启动...</span>';
            break;
        case "3": text = '<span class="label label-info">正在停止...</span>';
            break;
        case "4": text = '<span class="label label-warning-light">设备服务断开</span>';
            break;
        case "5": text = '<span class="label label-warning-light">SRS服务断开</span>';
            break;
        case "6": text = '<span class="label label-danger">VMC服务异常</span>';
            break;
        case "7": text = '<span class="label label-default">UAC下线</span>';
            break;
        default: text = '<span class="label label-default">未知状态</span>';
            break;
    }
    return text;
};

/**
 * 匹配加密方式
 */
DeviceDetail.encryptTypeAdaper = function (type) {
    var text = "";
    switch (type.toString()){
        case "0": text = "不加密";
            break;
        default: text = "未知方式";
            break;
    }
    return text;
};

/**
 * 匹配日志类型
 */
DeviceDetail.logTypeAdaper = function (type) {
    var text = "";
    switch (type.toString()){
        case "1": text = "启动";
            break;
        case "2": text = "停止";
            break;
        case "3": text = "其他";
            break;
        case "4": text = "异常";
            break;
        case "5": text = "码率";
            break;
        case "6": text = "丢包率";
            break;
        default: text = "未知类型";
            break;
    }
    return text;
};

/**
 * 获取设备信息
 */
DeviceDetail.getInfo = function () {
    var ajax = new $ax(baseUrl + "/stream/get_device_detail", function (data) {
        $(".small-title").text(data.devicename+'['+data.deviceid+']');
        $(".deviceid").text(data.deviceid);
        $(".devicename").text(data.devicename);
        $(".devicetype").text(DeviceDetail.deviceTypeAdaper(data.type));
        $(".devicelogic").text(data.logicid);
        $(".devicealias").text(data.devicealias);
        $(".devicearea").text(data.areaname);
        $(".hlscount").text(data.hlscount);
        $(".encrypttype").text(DeviceDetail.encryptTypeAdaper(data.hlscrypto));
        $(".hlsaccount").text(data.hls_name);
        $(".epsaccount").text(data.eps_name);

    }, function (data) {
        Feng.error("请求设备信息异常");
    });
    ajax.set("deviceid", this.deviceid);
    ajax.start();
};

/**
 * 获取设备状态和日志
 */
DeviceDetail.getLog = function (logid) {
    var $logContainer = $(".log-container"), html = "", over = 0;
    var ajax = new $ax(baseUrl + "/stream/get_status_logs", function (data) {
        $.each(data.logs, function (i ,item) {
            if(i==0){
                DeviceDetail.logid = item.id;
            }
            html = '<span class="log">'+item.createtime+' ['+DeviceDetail.logTypeAdaper(item.notifytype)+'] '+item.description+'</span>' + html;
        });
        var logs = $logContainer.find(".log").length;
        if((logs+data.logs.length)>DeviceDetail.maxLogs){
            over = logs + data.logs.length - DeviceDetail.maxLogs;
            $logContainer.find(".log:lt("+over+")").remove();
        }
        if(data.logs.length){
            $logContainer.append(html).scrollTop($logContainer.prop("scrollHeight"));
        }
        if(data.status.length){
            var status = data.status[0];
            $(".device-status").html(DeviceDetail.deviceStatusAdaper(status.streamstate));
            if(status.streamstate==1){
                $(".rtmp-url").text("rtmp://"+status.hlsip+":"+status.hlsport+"/"+status.deviceid+"/livestream");
                $(".coderate").text(status.coderate);
                $(".packetlossrate").text(status.packetlossrate);
                $(".startup").attr("disabled", true);
                $(".stop").attr("disabled", false);
                $(".preview").removeClass("hidden");
            }else {
                $(".rtmp-url").text("-");
                $(".coderate").text("-");
                $(".packetlossrate").text("-");
                $(".startup").attr("disabled", false);
                $(".stop").attr("disabled", true);
                $(".preview").addClass("hidden");
            }
        }

    }, function (data) {
        Feng.error("请求日志异常");
    });
    ajax.set({"deviceid": DeviceDetail.deviceid, logid: logid});
    ajax.start();
};

/**
 * 停止
 */
DeviceDetail.stop = function (event) {
    var _this = event.currentTarget;
    $(_this).button('loading');
    var ajax = new $ax(baseUrl + "/stream/stop", function (data) {
        if(data.code==200){
            Feng.success("操作成功");
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("操作异常");
    });
    ajax.set("devices", this.deviceid);
    ajax.start();
    $(_this).button('reset');
};

/**
 * 启动
 */
DeviceDetail.startUp = function (event) {
    var _this = event.currentTarget;
    $(_this).button('loading');
    var ajax = new $ax(baseUrl + "/stream/start", function (data) {
        if(data.code==200){
            Feng.success("操作成功");
        }else{
            Feng.info(data.message);
        }
    }, function (data) {
        Feng.error("操作异常");
    });
    ajax.set("devices", this.deviceid);
    ajax.start();
    $(_this).button('reset');
};

/**
 * 查看视频
 */
DeviceDetail.showVideo = function (event) {
    var player = null;
    var url = $(event.currentTarget).prev().text();
    layer.open({
        type: 1,
        title:  ["视频"],
        offset: 'r',
        shade: 0,
        area: ['400px', '300px'],
        closeBtn: 1,
        shadeClose: false,
        content: $("#showVideo").html(),
        success: function (layero, index) {
            player = cyberplayer("dvplay").setup({
                stretching: "exactfit",
                file: url,
                autostart: true,
                width: '100%',
                height: '100%',
                repeat: false,
                volume: 100,
                controls: false,
                controlbar: {
                    barLogo: false
                }
            });
        },
        cancel: function(index, layero) {
            player.remove();
        },
        end: function () {
            player.remove();
        }
    });
};

/**
 * 清空日志
 */
DeviceDetail.cleanLog =function () {
    $(".log-container").empty();
};

$(function () {
    DeviceDetail.getInfo();
    DeviceDetail.getLog(-1);
    DeviceDetail.intervalIndex = setInterval(function () {
        DeviceDetail.getLog(DeviceDetail.logid);
    }, 3000);
});