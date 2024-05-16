package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dell on 2017/7/8.
 * 集团推送隐患接口
 */
@Controller
@RequestMapping("/hidden")
public class HiddenDangerController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private static final Logger logger = Logger.getLogger(HiddenDangerController.class);

    /**
     * 集团推送隐患接口
     * @param mineCode
     * @param token
     * @param reportContent
     * @return
     */
    @RequestMapping("/hiddenDangerReport.do")
    @ResponseBody
    public ApiResultJson hiddenDangerReport(String mineCode, String token, String reportContent){

        if (StringUtil.isEmpty(mineCode) || StringUtil.isEmpty(reportContent)) {
            return new ApiResultJson(ApiResultJson.CODE_400, ApiResultJson.CODE_400_MSG, null);
        }
        String secreKey = ResourceUtil.getConfigByName("token_group");
        if (!(ResourceUtil.getConfigByName("token_group").equals(secreKey))){
            return new ApiResultJson(ApiResultJson.CODE_202, "不是本矿所属集团公司,无接口调用权限", null);
        }
        //TODO token校验
        String decryptMineCode = null;
        String decryptToken = null;
        try {
            String params = AesUtil.decryptWithIV(token, secreKey);
            for (String param: params.split("&")) {
                String[] p = param.split("=");
                if (Constants.API_PARAM_KEY_TOKEN.equals(p[0])) {
                    decryptToken = p[1];
                } else if (Constants.API_PARAM_KEY_MINECODE.equals(p[0])){
                    decryptMineCode = p[1];
                }
            }
            if (!secreKey.equals(decryptToken)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "秘钥不正确", null);
            }
            if (!mineCode.equals(decryptMineCode)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "矿井编码不正确", null);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ApiResultJson(ApiResultJson.CODE_202, "秘钥不正确, 解析错误", null);
        }
        try {
            JSONArray ja =apiService.pushedHiddenDangerReceive(mineCode, reportContent);
            System.out.println(reportContent);
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, ja);
        } catch (Exception e) {
            logger.error(e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

}
