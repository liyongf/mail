package com.sdzk.buss.web.aqbzh.pojo.dto;


public class QualityControlTableIds {

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 表id
     */
    private String ids;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public QualityControlTableIds(Integer parentId, String ids) {
        this.parentId = parentId;
        this.ids = ids;
    }
    public QualityControlTableIds() {

    }



}
