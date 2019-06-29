package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

/**
 * @author chjiang
 * @since 2019/6/6 14:51
 */
public class ViPrivateMemberVo extends PageReqAbstractModel {

    private Integer repoId;

    private String searchValue;

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
