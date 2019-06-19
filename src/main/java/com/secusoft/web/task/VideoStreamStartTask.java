package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.shipinapi.model.StreamRequest;

import java.util.List;
import java.util.TimerTask;

/**
 * 启动码流定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class VideoStreamStartTask extends TimerTask {

    private ViSurveyTaskBean viSurveyTaskBean;

    public VideoStreamStartTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    private static ViTaskDeviceMapper viTaskDeviceMapper = SpringContextHolder.getBean(ViTaskDeviceMapper.class);

    private static ViSurveyTaskMapper viSurveyTaskMapper = SpringContextHolder.getBean(ViSurveyTaskMapper.class);

    @Override
    public void run() {
        for (ViTaskDeviceBean viTaskDeviceBean : viSurveyTaskBean.getViTaskDeviceList()) {
            ViTaskDeviceBean bean = new ViTaskDeviceBean();
            bean.setDeviceId(viTaskDeviceBean.getDeviceId());
            bean.setStatus(1);
            List<ViTaskDeviceBean> viTaskDeviceBeanList = viTaskDeviceMapper.getViTaskDeviceBeanByObject(bean);
            //判断设备是否已启用或者状态是否为1
            if (viTaskDeviceBean.getStatus() != 1 && viTaskDeviceBeanList.size() == 0) {
                StreamRequest streamRequest = new StreamRequest();
                streamRequest.setDeviceId(viTaskDeviceBean.getDeviceId());

                String requestStr = JSON.toJSONString(streamRequest);
                String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);

                JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if (BizExceptionEnum.OK.getCode() == Integer.parseInt(code)) {
                    viTaskDeviceBean.setStatus(1);
                }else{
                    viTaskDeviceBean.setStatus(0);
                }
                viTaskDeviceMapper.updateViTaskDevice(viTaskDeviceBean);
            }
        }
    }
}
