package com.sdzk.buss.web.mapmanage.service;

import com.sdzk.buss.web.mapmanage.entity.TBMapManageEntity;
import org.jeecgframework.core.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

public interface TBMapManageServiceI extends CommonService {
    void delete(TBMapManageEntity entity) throws Exception;

    Serializable save(TBMapManageEntity entity) throws Exception;

    void saveOrUpdate(TBMapManageEntity entity) throws Exception;

    String upload(HttpServletRequest request) throws Exception;

    String getCurrentMapPath();

    void dwgupload(HttpServletRequest request, String departName, String mapId) throws Exception;
}
