package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

/**
 * @author chjiang
 * @since 2019/6/6 14:51
 */
public class ViBasicMemberVo extends PageReqAbstractModel {

    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
