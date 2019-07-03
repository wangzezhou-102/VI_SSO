package com.secusoft.web.mapper;

import com.secusoft.web.model.ViPsurveyAlarmBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author chjiang
 * @since 2019/6/19 14:24
 */
public interface ViPsurveyAlarmMapper {

    /**
     * 添加布控告警
     *
     * @param viPsurveyAlarmBean
     */
    void insertViPsurveyAlaram(ViPsurveyAlarmBean viPsurveyAlarmBean);


    /**
     * 根据布控任务id获得布控报警数量
     */
    Integer selectIdNumberBytaskId(@Param("taskIds") List<String> taskIds);

    ViPsurveyAlarmBean getViPsurveyAlaramByBean(ViPsurveyAlarmBean viPsurveyAlarmBean);
}
