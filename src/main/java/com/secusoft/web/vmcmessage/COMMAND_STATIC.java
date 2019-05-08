package com.secusoft.web.vmcmessage;

/**
 * VMC SERVER 服务器通信命令相关值
 * Created by ChenYongHeng on 2017/7/5.
 */
public class COMMAND_STATIC {
    public static final String COMMAND_Change = "10131003";
    public static final String COMMAND_GetClientOnlineUsers = "10131002";

    public static final String Change_Type_Devicechange = "1";
    public static final String Change_Type_Camerachange = "2";
    public static final String Change_Type_Userchange = "3";
    public static final String Change_Type_Customerchange = "4";
    public static final String Change_Type_Monitor = "5";

    //////////////////////城市大脑
    public static final String Change_Type_SyncGBDevice = "12";//同步国标平台
    public static final String Change_Type_SyncYJDevice = "13";//同步银江平台
    public static final String Change_Type_RegionProp = "6";//线圈属性变化通知
    public static final String Change_Type_Presetchange = "7";//预置位变化通知
    public static final String Change_Type_Presetcontrol = "8";//转到预置位云台操作通知
    public static final String Change_Type_YJdevicechange = "10";//银江平台映射变化通知
    public static final String Change_Type_YJpresetchange = "11";//银江平台预置位映射变化通知
    public static final String Change_Type_RegionFuncchange = "17";
    public static final String Change_Type_RegionPropRefFuncchange = "18";
    public static final String Change_Type_PS2TS = "21";//转码控制
    public static final String Change_Type_HLSID = "23";//绑定解绑流媒体账号
    public static final String Change_Type_Region_Device_Scene = "24";
    public static final String Change_Type_PresetSet  = "25";  //设置预置位通知
    public static final String Change_Type_PTZcontrol  = "26";  //云台控制 Operator: 0停止, 1左下，2下，3右下，4左，6右，7左上，8上，9右上（小键盘数字顺序）
    public static final String Change_Type_PresetUpdate  = "27";  //更新预置位通知
    public static final String Change_Type_CruiseApply  = "31";  //应用已配置巡航计划
    public static final String Change_Type_PollingApply  = "45";
    
    public static final String Operator_Region = "1"; //Operator: 1:线圈，2：场景，3：设备
    public static final String Operator_Scene = "2";
    public static final String Operator_Device = "3";


    public static final String Change_Operator_Start = "0";//转码开始
    public static final String Change_Operator_Stop = "1";//转码停止
    public static final String Change_Operator_Add = "1";
    public static final String Change_Operator_Modify = "2";
    public static final String Change_Operator_Delete= "3";
    public static final String Change_Operator_Bind= "4";
    public static final String Change_Operator_UBind= "5";


    public static final String Comment_RegionPropchange = "areapropchange";


}
