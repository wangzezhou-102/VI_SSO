package com.secusoft.web.utils;

import com.secusoft.web.model.ViSurveyTaskBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * 启动布控定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class SurveyStartTask extends TimerTask {

    private ViSurveyTaskBean viSurveyTaskBean;

    public SurveyStartTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    @Override
    public void run() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("SurveyStartTask："+df.format(new Date()));
        System.out.println(new Date());
        System.out.println("你指定2013-11-27号15:34:01分执行已经触发！");
    }
}
