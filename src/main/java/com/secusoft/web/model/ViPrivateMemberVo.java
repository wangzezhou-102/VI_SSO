package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

/**
 * @author chjiang
 * @since 2019/6/6 14:51
 */
public class ViPrivateMemberVo extends PageReqAbstractModel {

    private Integer repoId;

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }
}
