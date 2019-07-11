package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

/**
 * @author chjiang
 * @since 2019/6/6 14:51
 */
public class ViBasicMemberVo extends PageReqAbstractModel {

    private String objectId;

    private Integer repoId;

    private String searchValue;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
