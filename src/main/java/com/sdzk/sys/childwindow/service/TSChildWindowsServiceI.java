package com.sdzk.sys.childwindow.service;

import com.sdzk.sys.childwindow.entity.ChildWindowSettingVO;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;


public interface TSChildWindowsServiceI extends CommonService{
    ChildWindowSettingVO getHomePageChildWinSetting(TSUser sessionUser);
}
