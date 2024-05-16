package com.sdzk.buss.api.service.impl;

import com.sdzk.buss.api.entity.WexinOpenId;
import com.sdzk.buss.api.service.WexinOpenServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;

@Service("WexinOpenService")
@Transactional
public class WexinOpenServiceImpl extends CommonServiceImpl implements WexinOpenServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((WexinOpenId )entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((WexinOpenId)entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((WexinOpenId)entity);
    }

    public boolean doAddSql(WexinOpenId w) {
        return false;
    }


    public boolean doUpdateSql(WexinOpenId w) {
        return false;
    }


    public boolean doDelSql(WexinOpenId w) {
        return false;
    }
}
