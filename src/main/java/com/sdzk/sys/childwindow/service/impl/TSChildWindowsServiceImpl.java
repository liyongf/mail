package com.sdzk.sys.childwindow.service.impl;

import com.sdzk.sys.childwindow.entity.ChildWindowSettingVO;
import com.sdzk.sys.childwindow.entity.TSChildWindowsEntity;
import com.sdzk.sys.childwindow.entity.TSRoleChildWindowEntity;
import com.sdzk.sys.childwindow.entity.TSUserChildWindowEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdzk.sys.childwindow.service.TSChildWindowsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import java.util.List;

@Service("childWindowsService")
@Transactional
public class TSChildWindowsServiceImpl extends CommonServiceImpl implements TSChildWindowsServiceI {
    public ChildWindowSettingVO getHomePageChildWinSetting(TSUser sessionUser) {
        ChildWindowSettingVO vo = new ChildWindowSettingVO();

        CriteriaQuery cq = new CriteriaQuery(TSUserChildWindowEntity.class);
        cq.eq("userId", sessionUser.getId());
        cq.add();
        List<TSUserChildWindowEntity> userChildWinList = commonDao.getListByCriteriaQuery(cq, false);

        //取用户-子窗口关联
        if (!userChildWinList.isEmpty() && userChildWinList.size() > 0) {
            for (TSUserChildWindowEntity entity : userChildWinList) {
                String position = entity.getPosition();
                String childWindowId = entity.getChildWindowId();

                if (StringUtils.isNotBlank(childWindowId)) {
                    TSChildWindowsEntity childWindow = commonDao.getEntity(TSChildWindowsEntity.class, childWindowId);
                    if (childWindow != null) {
                        boolean isUsed = (Integer.parseInt(childWindow.getIsUsed()) == 1);

                        if ("left_top".equals(position) && isUsed) {
                            vo.setLeftTop(childWindow);
                        }
                        if ("center_top".equals(position) && isUsed) {
                            vo.setCenterTop(childWindow);
                        }
                        if ("right_top".equals(position) && isUsed) {
                            vo.setRightTop(childWindow);
                        }
                        if ("left_bottom".equals(position) && isUsed) {
                            vo.setLeftBottom(childWindow);
                        }
                        if ("center_bottom".equals(position) && isUsed) {
                            vo.setCenterBottom(childWindow);
                        }
                        if ("right_bottom".equals(position) && isUsed) {
                            vo.setRightBottom(childWindow);
                        }
                    }
                }
            }
        }

        //有未关联项时，取角色-子窗口关联
        if (!vo.isComplete()) {
            String sql = "SELECT roleid FROM t_s_role_user WHERE userid = '" + sessionUser.getId() + "' LIMIT 1";
            List<String> tmpList = commonDao.findListbySql(sql);
            String roleId = tmpList.get(0);
            tmpList.clear();

            if (vo.getLeftTop() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'left_top'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setLeftTop(entity);
                    }
                }
                tmpList.clear();
            }
            if (vo.getCenterTop() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'center_top'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setCenterTop(entity);
                    }
                }
                tmpList.clear();
            }
            if (vo.getRightTop() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'right_top'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setRightTop(entity);
                    }
                }
                tmpList.clear();
            }
            if (vo.getLeftBottom() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'left_bottom'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setLeftBottom(entity);
                    }
                }
                tmpList.clear();
            }
            if (vo.getCenterBottom() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'center_bottom'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setCenterBottom(entity);
                    }
                }
                tmpList.clear();
            }
            if (vo.getRightBottom() == null) {
                sql = "SELECT child_window_id FROM t_s_role_child_window WHERE role_id = '" + roleId + "' AND position = 'right_bottom'";
                tmpList = commonDao.findListbySql(sql);
                if (!tmpList.isEmpty() && tmpList.size() > 0) {
                    TSChildWindowsEntity entity = commonDao.getEntity(TSChildWindowsEntity.class, tmpList.get(0));
                    if (entity != null && "1".equals(entity.getIsUsed())) {
                        vo.setRightBottom(entity);
                    }
                }
                tmpList.clear();
            }
        }

        //仍有未关联项时，取默认配置
        if (!vo.isComplete()) {
            if (vo.getLeftTop() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "waitingTaskFrame");
                if (entity != null) {
                    vo.setLeftTop(entity);
                }
            }
            if (vo.getCenterTop() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "overTrendFrame");
                if (entity != null) {
                    vo.setCenterTop(entity);
                }
            }
            if (vo.getRightTop() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "majorRiskFrame");
                if (entity != null) {
                    vo.setRightTop(entity);
                }
            }
            if (vo.getLeftBottom() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "riskDynaFrame");
                if (entity != null) {
                    vo.setLeftBottom(entity);
                }
            }
            if (vo.getCenterBottom() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "hiddDangerByGroupFrame");
                if (entity != null) {
                    vo.setCenterBottom(entity);
                }
            }
            if (vo.getRightBottom() == null) {
                TSChildWindowsEntity entity = commonDao.findUniqueByProperty(TSChildWindowsEntity.class, "childWindowFrameId", "hiddDangerByProfFrame");
                if (entity != null) {
                    vo.setRightBottom(entity);
                }
            }
        }
        return vo;
    }
}