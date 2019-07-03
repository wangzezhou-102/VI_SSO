package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

/**
 * @author chjiang
 * @since 2019/6/6 14:51
 */
public class ViPrivateMemberVo extends PageReqAbstractModel {

    private Integer repoId;

    private String searchValue;

    private Integer type = 0;

    private String[] delIds;

    private String file;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String[] getDelIds() {
        return delIds;
    }

    public void setDelIds(String[] delIds) {
        this.delIds = delIds;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
