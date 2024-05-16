package com.sdzk.buss.api.service;
import com.sdzk.buss.api.entity.WexinOpenId;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface WexinOpenServiceI extends CommonService {

    <T> void delete(T entity);

    <T> Serializable save(T entity);

    <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * @param id
     * @return
     */
    boolean doAddSql(WexinOpenId w);
    /**
     * 默认按钮-sql增强-更新操作
     * @param id
     * @return
     */
    boolean doUpdateSql(WexinOpenId w);
    /**
     * 默认按钮-sql增强-删除操作
     * @param id
     * @return
     */
    boolean doDelSql(WexinOpenId w);
}
