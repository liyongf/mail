package com.sdzk.buss.web.common.service.impl;

import com.sdzk.buss.web.common.service.DataMapServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dataMapService")
@Transactional
public class DataMapService extends CommonServiceImpl implements DataMapServiceI {
    @Autowired
    private SystemService systemService;


    @Override
    public Map<String, String> getDepartMap() {
        List<Map<String, Object>> departs = systemService.findForJdbc("select id, departname from t_s_depart");
        Map<String, String> departMap = new HashMap<>();
        if (departs != null && departs.size()>0) {
            for (Map<String, Object> depart : departs) {
                departMap.put((String) depart.get("id"),(String) depart.get("departname"));
            }
        }
        return departMap;
    }


    @Override
    public Map<String, String> getUserMap() {
        List<Map<String, Object>> users = systemService.findForJdbc("select id, realname from t_s_base_user");
        Map<String, String> userMap = new HashMap<>();
        if (users != null && users.size() >0 ) {
            for (Map<String, Object> user : users) {
                userMap.put((String) user.get("id"),(String) user.get("realname"));
            }
        }
        return userMap;
    }


    @Override
    public Map<String, String> getAddressMap() {
        List<Map<String, Object>> addresses = systemService.findForJdbc("select id, address from t_b_address_info");
        Map<String, String> addressMap = new HashMap<>();
        if (addresses != null && addresses.size() > 0) {
            for (Map<String, Object> address : addresses) {
                addressMap.put((String) address.get("id"),(String) address.get("address"));
            }
        }
        return addressMap;
    }
}
