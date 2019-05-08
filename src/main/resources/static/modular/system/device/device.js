$(function () {

    //获取设备列表
    $.post("/device/devicelist",{keyword:null,areacode:0,platetype:0},function (result) {
        console.log(result);
    });
    //获取设备详情
    $.post("/device/detail",{"deviceid":"330117029920200062"},function (result) {
        console.log(result);
    });
    //更新区域
    $.post("/device/updatearea",{"deviceid":"330117029920200062","areacode":330103},function (result) {
        console.log(result);
    });
    //更新设备别名
    $.post("/device/updatedevicealias",{"deviceid":"330117029920200062","devicealias":"新市街文昌巷"},function (result) {
        console.log(result);
    });
    //更新设备逻辑号
    $.post("/device/updatelogicid",{"deviceid":"330117029920200062","logicid":992},function (result) {
        console.log(result);
    });

    //获取平台列表
    $.post("http://192.168.2.38:8006/device/plateformlist",function (result) {
        console.log(result);
    });

    var newGbAreacode = {
        areacode:330186,
        areaname:"杭州市xx区"
    };
    //添加区域码
    $.post("/areacode/areacodeadd",newGbAreacode,function (result) {
        console.log(result);

    });

    //删除区域码
    $.post("/areacode/areacodedel",{areacode:330186},function (result) {
        console.log(result);
    });
    //获取所有区域码
    $.post("/areacode/areacodelist",function (result) {
        console.log(result);
    });


    var newPreset={
        deviceid:"330117019920200074",
        presetid:5,
        presetname:"预置位5",
        presetalias:"预置位5",
        positiontype:"1002000000",
        yjpresetid:0
    };
    //预置位列表
    $.post("/preset/presetlist",{deviceid:"330117019920200074"},function (result) {
        console.log(result);
    });
    // //添加预置位
    // $.post("/preset/presetadd",newPreset,function (result) {
    //     console.log(result);
    // });
    // //删除预置位
    // $.post("/preset/presetdel",{deviceid:"330117019920200074",presetid:5},function (result) {
    //     console.log(result);
    // });
    newPreset.presetalias = "0000000000";
    $.post("/preset/presetupdate",newPreset,function (result) {
        console.log(result);
    });

});