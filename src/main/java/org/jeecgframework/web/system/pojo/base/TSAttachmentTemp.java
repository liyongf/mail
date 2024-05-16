package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * Created by Administrator on 16-3-3.
 */
public class TSAttachmentTemp extends IdEntity implements java.io.Serializable {
    // 附件名称
    private String attachmenttitle;

    public String getAttachmenttitle() {
        return attachmenttitle;
    }

    public void setAttachmenttitle(String attachmenttitle) {
        this.attachmenttitle = attachmenttitle;
    }
}
