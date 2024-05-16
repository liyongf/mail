package com.sdzk.buss.web.staticDemo.controller;


import com.sdzk.buss.web.accident.entity.TBAccidentLevelEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
@RequestMapping("/statiController")
public class StatiController {


    @RequestMapping(params = "fxlist")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/fxlist");
    }

    @RequestMapping(params = "wxysList")
    public ModelAndView wxysList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/wxysList");
    }

    @RequestMapping(params = "wxysDataList")
    public ModelAndView wxysDataList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/wxysDataList");
    }

    @RequestMapping(params = "fxgkList")
    public ModelAndView fxgkList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/fxgkList");
    }

    @RequestMapping(params = "wxys")
    public ModelAndView wxys(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/wxys");
    }

    @RequestMapping(params = "wxys2")
    public ModelAndView wxys2(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/wxys2");
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/staticDemo/fxgkList-add");
    }

    @RequestMapping(params = "fxListDatagrid")
    public void fxListDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("id","1");
        map1.put("accidentlevel","3306工作面");
        map1.put("standard","综采工作面");
        map1.put("color","风险描述");
        map1.put("sortindex","低风险");
        map1.put("createName","3");
        map1.put("createBy","最高管控层级");
        map1.put("createDate","最高管控责任人");
        map1.put("updateName","2018-09-14");
        map1.put("updateBy","2019-10-14");
        list.add(map1);
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "whysListDatagrid")
    public void whysListDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("id","1");
        map1.put("accidentlevel","危害因素");
        map1.put("standard","管控措施");
        map1.put("color","危害因素等级");
        map1.put("sortindex","管控单位");
        map1.put("createName","管控责任人");
        map1.put("createBy","信息来源");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","2");
        map1.put("accidentlevel","危害因素");
        map1.put("standard","管控措施");
        map1.put("color","危害因素等级");
        map1.put("sortindex","管控单位");
        map1.put("createName","管控责任人");
        map1.put("createBy","信息来源");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","3");
        map1.put("accidentlevel","危害因素");
        map1.put("standard","管控措施");
        map1.put("color","危害因素等级");
        map1.put("sortindex","管控单位");
        map1.put("createName","管控责任人");
        map1.put("createBy","信息来源");
        list.add(map1);
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "whysDataListDatagrid")
    public void whysDataListDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("id","1");
        map1.put("fxlx","放炮");
        map1.put("wxymc","炸药");
        map1.put("zy","机电");
        map1.put("accidentlevel","炸药和电雷管为分开运送");
        map1.put("standard","装车前，设专人看管，检查合格方可运送");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","2");
        map1.put("fxlx","放炮");
        map1.put("wxymc","炸药");
        map1.put("zy","机电");
        map1.put("accidentlevel","运送电雷管车辆未加盖加垫");
        map1.put("standard","装车前，设专人看管，检查合格方可运送");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","3");
        map1.put("fxlx","放炮");
        map1.put("wxymc","爆炸物品");
        map1.put("zy","井下爆破");
        map1.put("accidentlevel","井下爆破工作中，爆破人员不熟悉《煤矿安全规程》规定");
        map1.put("standard","安培中心负责对涉爆人员进行培训，安培中心主人负有管理责任，安全处负责监督管理");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","4");
        map1.put("fxlx","放炮");
        map1.put("wxymc","爆炸物品");
        map1.put("zy","井下爆破");
        map1.put("accidentlevel","井下爆破工作中，爆破人员不熟悉《煤矿安全规程》规定");
        map1.put("standard","安培中心负责对涉爆人员进行培训，安培中心主人负有管理责任，安全处负责监督管理");
        list.add(map1);
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "whysDatagrid")
    public void whysDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("id","1");
        map1.put("fxlx","放炮");
        map1.put("wxymc","炸药");
        map1.put("zy","机电");
        map1.put("accidentlevel","炸药和电雷管为分开运送");
        map1.put("standard","装车前，设专人看管，检查合格方可运送");
        map1.put("sl","2");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","2");
        map1.put("fxlx","放炮");
        map1.put("wxymc","炸药");
        map1.put("zy","机电");
        map1.put("accidentlevel","运送电雷管车辆未加盖加垫");
        map1.put("standard","装车前，设专人看管，检查合格方可运送");
        map1.put("sl","2");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","3");
        map1.put("fxlx","放炮");
        map1.put("wxymc","爆炸物品");
        map1.put("zy","井下爆破");
        map1.put("accidentlevel","井下爆破工作中，爆破人员不熟悉《煤矿安全规程》规定");
        map1.put("standard","安培中心负责对涉爆人员进行培训，安培中心主人负有管理责任，安全处负责监督管理");
        map1.put("sl","2");
        list.add(map1);

        map1 = new HashMap<String,String>();
        map1.put("id","4");
        map1.put("fxlx","放炮");
        map1.put("wxymc","爆炸物品");
        map1.put("zy","井下爆破");
        map1.put("accidentlevel","井下爆破工作中，爆破人员不熟悉《煤矿安全规程》规定");
        map1.put("standard","安培中心负责对涉爆人员进行培训，安培中心主人负有管理责任，安全处负责监督管理");
        map1.put("sl","2");
        map1.put("ls","<input type='radio' name='a' id='a' value='0'/>不通过&nbsp;&nbsp;<input type='radio' name='a' id='b' value='1'/>通过");
        list.add(map1);
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }

}
