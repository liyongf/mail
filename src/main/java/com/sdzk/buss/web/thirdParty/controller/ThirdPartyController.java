package com.sdzk.buss.web.thirdParty.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;

import com.sdzk.buss.web.fine.entity.TBFineEntity;
import com.sdzk.buss.web.homePage.entity.HiddenDangerByGroupVO;
import com.sdzk.buss.web.homePage.entity.HiddenDangerByProfessionVO;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.*;

import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/thirdPartyController")
public class ThirdPartyController extends BaseController {
	@Autowired
	private SystemService systemService;
	@Autowired
	private DataMapServiceI dataMapService;
	@Autowired
	private TBAddressInfoServiceI tBAddressInfoService;



	@RequestMapping("/yhList")
	@ResponseBody
	public ApiResultJson yhList(HttpServletRequest request, HttpServletResponse response){
		try {
			StringBuffer sql = new StringBuffer();
			String shift  = "zz.shift";
			String groupCode = "workShift";
			shift = replaceBySql(shift,groupCode);
			String examType = "zz.exam_type";
			String groupCodeExamType = "examType";
			String manageType = "zz.manage_type";
			String groupCodeManageType = "manageType";
			examType = replaceBySql(examType,groupCodeExamType);
			manageType = replaceBySql(manageType,groupCodeManageType);
			String hiddenNature = replaceBySql("zz.hidden_nature","hiddenLevel");
			String handelStatus = replaceBySql("zz.handlel_status","handelStatus");
			String hiddenType = replaceBySql("zz.hidden_type","hiddenType");
			String riskType = replaceBySql("zz.risk_type","risk_type");
			sql.append("select zz.id,DATE_FORMAT(zz.exam_date,'%Y/%m/%d') examDateTemp,"+shift+"  checkShiftTemp,\n" +
					"ta.hazard_name dangerSource,tb.address addressTemp,tc.departname dutyUnitDepartname,zz.duty_man dutyManTemp,mtu.departname manageDutyUnitDepartname,mtm.realname manageDutyManTemp,tf.fillCardManNames fillCardManNames,\n"+examType+
					"  examTypeTemp,"+hiddenNature+"  hiddenNatureTemp,"+riskType+"  riskTypeTemp,zz.problem_desc problemDescTemp,\n" + manageType+
					"  manageTypeTemp,\n" +
					"REPLACE(REPLACE(zz.deal_type,'1','限期整改'),'2','现场处理') dealTypeTemp,\n" +
					"DATE_FORMAT(zz.limit_date,'%Y/%m/%d') limitDatetemp,DATE_FORMAT(zz.modify_date,'%Y/%m/%d') modifyDateTemp,\n" +
					"IFNULL(td.realname,zz.modify_man) modifyManTemp,DATE_FORMAT(zz.review_date,'%Y/%m/%d') reviewDate,\n" +
					"te.realname reviewManTemp,"+handelStatus+" handlelStatusTemp,zz.rect_measures rectMeasuresTemp,zz.review_report reviewReportTemp,"+hiddenType+" hiddenTypeTemp,zz.roll_back_remark rollBackRemarkTemp,\n" +
					"zz.manage_type manageType\n" +
					"FROM\n" +
					"(");
			StringBuffer sqlTemp = new StringBuffer();
			sqlTemp.append("select hde.hidden_number,hde.id,hde.sjjc_check_man, hde.exam_date, hde.manage_type, hde.shift,hde.danger_id,hde.address,hde.duty_unit,hde.duty_man,hde.manage_duty_unit,hde.manage_duty_man_id,hde.fill_card_manids,hde.exam_type,hde.hidden_nature,hde.risk_type,hde.hidden_type,hde.problem_desc,hde.deal_type,hde.limit_date,hdh.modify_date,hdh.modify_man,hdh.review_date,hdh.review_man,hdh.handlel_status,hdh.rect_measures,hdh.review_report,hdh.roll_back_remark from t_b_hidden_danger_handle hdh left join t_b_hidden_danger_exam hde on hdh.hidden_danger_id = hde.id where 1=1 ");
			sqlTemp.append(" and hdh.handlel_status <> '"+ Constants.HANDELSTATUS_DRAFT+"'");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtils.isNotBlank(startDate)){
				sqlTemp.append( " and hde.exam_date >='"+startDate+"'");
			}
			if(StringUtils.isNotBlank(endDate)){
				sqlTemp.append(" and hde.exam_date <='"+endDate+"' ");
			}
			if(!StringUtils.isNotBlank(startDate)&&!StringUtils.isNotBlank(endDate)){
				sqlTemp.append( " and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=  hde.exam_date");
			}
			sql.append(sqlTemp);
			sql.append(") zz LEFT JOIN \n" +
					"(select ds.id,h.hazard_name from t_b_danger_source  ds LEFT JOIN t_b_hazard_manage h on ds.hazard_manage_id = h.id  ) ta on zz.danger_id = ta.id \n" +
					"LEFT JOIN (SELECT t.id,t.address from t_b_address_info t) tb ON zz.address = tb.id \n" +
					"LEFT JOIN (SELECT d.id,d.departname from t_s_depart d) tc on zz.duty_unit = tc.id\n" +
					"LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) td on zz.modify_man = td.id\n" +
					"LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) te on zz.review_man = te.id\n" +
					"LEFT JOIN (SELECT d.id,d.departname from t_s_depart d) mtu on zz.manage_duty_unit = mtu.id\n" +
					"LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) mtm on zz.manage_duty_man_id = mtm.id\n" +
					"LEFT JOIN (SELECT a.id,case a.exam_type when 'sjjc' then a.sjjc_check_man else " +
					/* "GROUP_CONCAT(tf.realname) " +*/
					"a.fill_card_manids " +
					"END fillCardManNames from ("+sqlTemp.toString()+") a\n" +
					"LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) tf on FIND_IN_SET(tf.id,a.fill_card_manids)\n" +
					"GROUP BY a.id" +
					") tf on zz.id = tf.id  order by zz.exam_date desc");
			List<Map<String,Object>> retList = systemService.findForJdbc(sql.toString());
			for(int i = 0;i<retList.size();i++){
				String useridArray = String.valueOf(retList.get(i).get("fillCardManNames"));
				String[] ids = useridArray.split(",");
				String name = "";
				for(String id : ids){
					TSUser user = systemService.getEntity(TSUser.class,id);
					name="";
					if(user!=null){
						if(name==""){
							name = name +user.getRealName();
						}else{
							name = name + "," +user.getRealName();
						}
					}else if(StringUtil.isNotEmpty(id)){
						if (name == "") {
							name = name + id;
						} else {
							name = name + "," + id;
						}
					}
				}
				retList.get(i).put("fillCardManNames",name);
			}
			return new ApiResultJson(retList);
		} catch (Exception e) {
			LogUtil.error("隐患列表查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}


	private String replaceBySql(String first,String groupCode){
		List<TSType> types = ResourceUtil.allTypes.get(groupCode.toLowerCase());

		if(types.size()>9){
			//字典中的1和10，优先replace字典编码为10的，避免出现编码为10的“顶板”被替换为“冲击地压0”
			Collections.sort(types, new Comparator<TSType>() {
				@Override
				public int compare(TSType o1, TSType o2) {
					String code1 = o1.getTypecode();
					String code2 = o2.getTypecode();

					if(code1.length()>code2.length()){
						return -1;
					}else{
						return 0;
					}
				}
			});
		}

		for(TSType type : types){
			first = "REPLACE("+first+",'"+type.getTypecode()+"','"+type.getTypename()+"')";
		}
		return first;
	}


	@RequestMapping("/swList")
	@ResponseBody
	public ApiResultJson swList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);
			List<TBThreeViolationsEntity> tBThreeViolationss=new ArrayList<>();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				if(StringUtils.isNotBlank(startDate)){
					cq.ge("vioDate", sdFormat.parse(startDate));
				}
				if(StringUtils.isNotBlank(endDate)){
					cq.le("vioDate", sdFormat.parse(endDate));
				}
				if(!StringUtils.isNotBlank(startDate)&&!StringUtils.isNotBlank(endDate)){
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.MONTH, -1);
					Date m = c.getTime();
					String mon = sdFormat.format(m);
					cq.ge("vioDate", sdFormat.parse(mon));
				}
				cq.add();
				cq.addOrder("createDate", SortDirection.desc);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			tBThreeViolationss = this.systemService.getListByCriteriaQuery(cq,false);
			Map<String, String> category = initDicMap("violaterule_wzfl", "违章分类");
			Map<String, String> qualitative = initDicMap("violaterule_wzdx", "违章定性");
			Map<String, String> shift = initDicMap("workShift", "班次");
			List<Map<String, Object>> departs = systemService.findForJdbc("select id, departname from t_s_depart");
			Map<String, String> departMap = new HashMap<>();
			if (departs != null && departs.size() > 0) {
				for (Map<String, Object> depart : departs) {
					departMap.put((String) depart.get("id"), (String) depart.get("departname"));
				}
			}
			List<Map<String, Object>> users = systemService.findForJdbc("select id, realname from t_s_base_user");
			Map<String, String> userMap = new HashMap<>();
			if (users != null && users.size() > 0) {
				for (Map<String, Object> user : users) {
					userMap.put((String) user.get("id"), (String) user.get("realname"));
				}
			}
			List<Map<String, Object>> addresses = systemService.findForJdbc("select id, address from t_b_address_info");
			Map<String, String> addressMap = new HashMap<>();
			if (addresses != null && addresses.size() > 0) {
				for (Map<String, Object> address : addresses) {
					addressMap.put((String) address.get("id"), (String) address.get("address"));
				}
			}


			if (tBThreeViolationss != null && tBThreeViolationss.size() > 0) {
				for (TBThreeViolationsEntity entity : tBThreeViolationss) {
					entity.setVioDateTemp(sdFormat.format(entity.getVioDate()));
					entity.setShiftTemp(shift.get(entity.getShift()));
					entity.setFindUnitsTemp(departMap.get(entity.getFindUnits()));
					entity.setAddressTemp(addressMap.get(entity.getVioAddress()));
					entity.setVioUnitesNameTemp(departMap.get(entity.getVioUnits()));
					entity.setVioPeopleTemp(entity.getVioPeople());
					entity.setEmployeeNumTemp(entity.getEmployeeNum());
					entity.setVioCategoryTemp(category.get(entity.getVioCategory()));
					entity.setVioQualitativeTemp(qualitative.get(entity.getVioQualitative()));
					entity.setStopPeopleTemp(entity.getStopPeople());
					String vioLevelCode = entity.getVioLevel();
					String vioLevelName = DicUtil.getTypeNameByCode("vio_level", vioLevelCode);
					entity.setVioLevelTemp(vioLevelName);
					CriteriaQuery cqFine = new CriteriaQuery(TBFineEntity.class, dataGrid);
					try{
						cqFine.add(Restrictions.sqlRestriction(" this_.id in (select fine_id from t_b_hidden_vio_fine_rel where vio_id = '"+entity.getId()+"')"));
					}catch(Exception e){
						e.printStackTrace();
					}
					cq.add();
					List<TBFineEntity> list = this.systemService.getListByCriteriaQuery(cqFine, false);
					String fineMoney = "";
					for(TBFineEntity bean : list){
						if(fineMoney==""){
							fineMoney = fineMoney + bean.getFineMoney();
						}else{
							fineMoney = fineMoney + "," + bean.getFineMoney();
						}
					}
					entity.setFineMoneyTemp(fineMoney);
				}
			}
			return new ApiResultJson(tBThreeViolationss);
		} catch (Exception e) {
			LogUtil.error("三违列表查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}
	private Map<String, String> initDicMap(String code, String name){
		Map<String, String> map =  new HashMap<>();
		TSTypegroup group = systemService.getTypeGroup(code,name);
		if (group != null && group.getTSTypes()!=null && group.getTSTypes().size()>0) {
			for (TSType type : group.getTSTypes()) {
				map.put(type.getTypecode(), type.getTypename());
			}
		}
		return map;
	}

	@RequestMapping("/fxList")
	@ResponseBody
	public ApiResultJson fxList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				cq.eq("status", com.sddb.common.Constants.RISK_IDENTIFI_STATUS_REVIEW);
				cq.eq("isDel", com.sddb.common.Constants.RISK_IS_DEL_FALSE);
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				if(StringUtils.isNotBlank(startDate)){
					cq.ge("createDate", sdFormat.parse(startDate));
				}
				if(StringUtils.isNotBlank(endDate)){
					cq.le("createDate", sdFormat.parse(endDate));
				}
				if(!StringUtils.isNotBlank(startDate)&&!StringUtils.isNotBlank(endDate)){
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.MONTH, -1);
					Date m = c.getTime();
					String mon = sdFormat.format(m);
					cq.ge("createDate", sdFormat.parse(mon));
				}
				cq.add();
				cq.addOrder("createDate", SortDirection.desc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			cq.add();
			List<RiskIdentificationEntity> riskIdentificationEntityList = this.systemService.getListByCriteriaQuery(cq,false);
			List<Map<String, Object>> result= new ArrayList<>();
			if (riskIdentificationEntityList != null && riskIdentificationEntityList.size() > 0) {
				for (RiskIdentificationEntity riskIdentificationEntity : riskIdentificationEntityList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id",riskIdentificationEntity.getId());
					map.put("address",riskIdentificationEntity.getAddress()==null?"":riskIdentificationEntity.getAddress().getAddress());
					map.put("postName",riskIdentificationEntity.getPost()==null?"":riskIdentificationEntity.getPost().getPostName());
					map.put("riskType",DicUtil.getTypeNameByCode("risk_type", riskIdentificationEntity.getRiskType()));
					map.put("riskDesc",riskIdentificationEntity.getRiskDesc());
					map.put("riskLevel",DicUtil.getTypeNameByCode("factors_level", riskIdentificationEntity.getRiskLevel()));
					map.put("manageLevel",DicUtil.getTypeNameByCode("identifi_mange_level", riskIdentificationEntity.getManageLevel()).equals("")?riskIdentificationEntity.getManageLevel():DicUtil.getTypeNameByCode("identifi_mange_level", riskIdentificationEntity.getManageLevel()));
					map.put("dutyManager",riskIdentificationEntity.getDutyManager());
					if(riskIdentificationEntity.getIdentifiDate()!=null){
						map.put("identifiDate",sdFormat.format(riskIdentificationEntity.getIdentifiDate()));
					}else{
						map.put("identifiDate","");
					}
					if(riskIdentificationEntity.getExpDate()!=null){
						map.put("expDate",sdFormat.format(riskIdentificationEntity.getExpDate()));
					}else{
						map.put("expDate","");
					}
					map.put("identificationType",DicUtil.getTypeNameByCode("identifi_from", riskIdentificationEntity.getIdentificationType()));
					if(StringUtil.isNotEmpty((String) map.get("id"))){
						RiskIdentificationEntity bean =systemService.getEntity(RiskIdentificationEntity.class,(String) map.get("id"));
						List<RiskFactortsRel> relList = bean.getRelList();
						map.put("hazardFactortsNum", relList.size() + "");
					}
					result.add(map);
				}

			}

			return new ApiResultJson(result);
		} catch (Exception e) {
			LogUtil.error("风险列表查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}

	//首页数据接口
	@RequestMapping("/dbrwList")
	@ResponseBody
	public ApiResultJson dbrwList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			String rectSql = "SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status in ('1','4')  ";
			String reviewSql = "SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status='3' ";
			String examSql = "select count(id) from t_b_risk_identification where is_del='0' and status ='1' ";
			//查询未到期的通知公告
			String endtimesql="select count(id) from t_s_notice where notice_term>=Date(NOW())";

			List<Object> rectList = systemService.findListbySql(rectSql);
			List<Object> reviewList = systemService.findListbySql(reviewSql);
			List<Object> examList = systemService.findListbySql(examSql);
			List<Object> endtimelist=systemService.findListbySql(endtimesql);

			List<Map<String, Object>> result= new ArrayList<>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("daizhenggai", rectList.get(0).toString());
			map.put("daifucha", reviewList.get(0).toString());
			map.put("daishenhe", examList.get(0).toString());
			map.put("endtimelist",endtimelist.get(0).toString());
			result.add(map);
			return new ApiResultJson(result);
		} catch (Exception e) {
			LogUtil.error("待办任务查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}


	@RequestMapping("/yhbmfbList")
	@ResponseBody
	public ApiResultJson yhbmfbList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtil.isEmpty(startDate)){
				startDate = DateUtils.getDate("yyyy-MM") + "-01";
			}
			if(StringUtil.isEmpty(endDate)){
				endDate = DateUtils.getDate("yyyy-MM-dd");
			}
			List<HiddenDangerByGroupVO> voList = new ArrayList<HiddenDangerByGroupVO>();
			//获得指定部门的全部隐患
			//只要数据没有异常的话，这个是不会出现分母为0的情况的，这里暂时不做验证处理
			String totalSql = "select count(id) totalcount, 0 closecount, t.duty_unit dutyUnitId from(\n" +
					"SELECT\n" +
					"\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
					"FROM\n" +
					"\tt_b_hidden_danger_handle handle\n" +
					"LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit in (select id from t_s_depart where delete_flag = '0') and handle.handlel_status<>'00'  ";
			String closeSql = "select 0 totalcount,count(id) closecount ,t.duty_unit dutyUnitId from(\n" +
					"SELECT\n" +
					"\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
					"FROM\n" +
					"\tt_b_hidden_danger_handle handle\n" +
					"LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit in (select id from t_s_depart where delete_flag = '0')  and handlel_status='5'  \n";
			if(StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)){
				totalSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
				closeSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
			}
			if(StringUtils.isNotBlank(startDate)){
				totalSql += " and exam.exam_date>='"+startDate+"' ";
				closeSql += " and exam.exam_date>='"+startDate+"' ";
			}
			if(StringUtils.isNotBlank(endDate)){
				totalSql += " and exam.exam_date<='"+endDate+"' ";
				closeSql += " and exam.exam_date<='"+endDate+"' ";

			}
			totalSql += ")as t GROUP BY t.duty_unit";
			closeSql += ")as t GROUP BY t.duty_unit";
			String hiddenNumSql = "SELECT temp.dutyUnitId, sum(totalcount) totalcount, sum(closecount) closecount FROM ("+totalSql+" union all "+closeSql+" ) temp GROUP BY temp.dutyUnitId ORDER BY totalcount desc";
			List<Map<String,Object>> hiddenNumList = systemService.findForJdbc(hiddenNumSql);
			for(Map<String,Object> map :hiddenNumList){
				HiddenDangerByGroupVO vo = new HiddenDangerByGroupVO();
				vo.setId(String.valueOf(map.get("dutyUnitId")));
				TSDepart tsDepart = systemService.getEntity(TSDepart.class,String.valueOf(map.get("dutyUnitId")));
				if(tsDepart!=null){
					vo.setName(tsDepart.getDepartname());
				}
				vo.setTotal(Double.parseDouble(String.valueOf(map.get("totalcount"))));
				vo.setClose(Double.parseDouble(String.valueOf(map.get("closecount"))));
				vo.setNotClose(vo.getTotal()-vo.getClose());
				voList.add(vo);
			}
			return new ApiResultJson(voList);
		} catch (Exception e) {
			LogUtil.error("隐患部门分布查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}


	@RequestMapping("/zdfxList")
	@ResponseBody
	public ApiResultJson zdfxList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
			cq.eq("riskLevel", "1");
			cq.eq("status","3");
			cq.eq("isDel","0");
			cq.add();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cq.add(Restrictions.sqlRestriction("this_.id in (select id from t_b_risk_identification where status='3' and is_del = '0' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null) )"));
			List<RiskIdentificationEntity> riskIdentificationEntityList = systemService.getListByCriteriaQuery(cq, false);
			List<Map<String, Object>> result= new ArrayList<>();
			if(riskIdentificationEntityList!=null&&riskIdentificationEntityList.size() > 0){
				for (RiskIdentificationEntity riskIdentificationEntity : riskIdentificationEntityList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fx", riskIdentificationEntity.getRiskDesc());
					result.add(map);
				}
			}
			return new ApiResultJson(result);
		} catch (Exception e) {
			LogUtil.error("重大风险查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}


	@RequestMapping("/fxdtList")
	@ResponseBody
	public ApiResultJson fxdtList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			List<Map<String, Object>> result= new ArrayList<>();
			Map<String, Object> map = new HashMap<String, Object>();
			String aNum = "";
			String bNum = "";
			String cNum = "";
			String dNum="";
			JSONObject jo = new JSONObject();
			Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
			Map<Object,Integer> res=new HashMap<>();
			for (Map.Entry<String,Object> entry:dynamicLevel.entrySet()){
				if (res.containsKey(entry.getValue())){
					res.put(entry.getValue(),res.get(entry.getValue())+1);
				}else{
					res.put(entry.getValue(),1);
				}
			}
			res.remove(null);
			if(res.get("1")!=null){
				aNum = String.valueOf(res.get("1"));
			}else{
				aNum = "0";
			}
			if(res.get("2")!=null){
				bNum = String.valueOf(res.get("2"));
			}else{
				bNum = "0";
			}
			if(res.get("3")!=null){
				cNum = String.valueOf(res.get("3"));
			}else{
				cNum = "0";
			}
			if(res.get("4")!=null){
				dNum = String.valueOf(res.get("4"));
			}else{
				dNum = "0";
			}
			map.put("aNum", aNum);
			map.put("bNum", bNum);
			map.put("cNum", cNum);
			map.put("dNum", dNum);
			result.add(map);
			return new ApiResultJson(result);
		} catch (Exception e) {
			LogUtil.error("风险动态分级预警查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}

	@RequestMapping("/sfztqsList")
	@ResponseBody
	public ApiResultJson sfztqsList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtil.isEmpty(startDate)){
				startDate = DateUtils.getDate("yyyy-MM") + "-01";
			}
			if(StringUtil.isEmpty(endDate)){
				endDate = DateUtils.getDate("yyyy-MM-dd");
			}
			List<Map<String, Object>> result= new ArrayList<>();
			Map<String, Object> map = new HashMap<String, Object>();

			//获取当月风险点数量
			String addrSql = "select count(id) from t_b_address_info where is_delete='0' and isShowData = '1' and (start_date <= '"+startDate+"' or start_date is null)  and (end_date  >= '"+endDate+"' or end_date is null)";
			//获取年度风险总数
			String yearRiskSql = "select count(id) from t_b_risk_identification where status='3' and is_del = '0' and identifi_date <= now() and (exp_date >= now() or exp_date is null) ";
			//获取当月隐患闭环总数
			String closeHiddSql = "select count(*) from t_b_hidden_danger_exam exam RIGHT JOIN t_b_hidden_danger_handle handle ON handle.hidden_danger_id = exam.id where handle.handlel_status!='5' and handle.handlel_status!='00'  ";




			List<Object> addrList = systemService.findListbySql(addrSql);
			List<Object> yearRiskList = systemService.findListbySql(yearRiskSql);
			List<Object> closeHiddList = systemService.findListbySql(closeHiddSql);

			map.put("addrNum", addrList.get(0).toString());
			map.put("yearRiskNum", yearRiskList.get(0).toString());
			map.put("closeHiddNum", closeHiddList.get(0).toString());

			result.add(map);
			return new ApiResultJson(result);
		} catch (Exception e) {
			LogUtil.error("双防总体趋势查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}

	@RequestMapping("/yhlxList")
	@ResponseBody
	public ApiResultJson yhlxList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtil.isEmpty(startDate)){
				startDate = DateUtils.getDate("yyyy-MM") + "-01";
			}
			if(StringUtil.isEmpty(endDate)){
				endDate = DateUtils.getDate("yyyy-MM-dd");
			}
			String sql = "SELECT count(id) count, risk_type FROM t_b_hidden_danger_exam WHERE exam_date >= '"+startDate+"' and exam_date <= '"+endDate+"' and risk_type IS NOT NULL AND risk_type <> '' GROUP BY risk_type ORDER BY count DESC";
			List<Object[]> riskList = systemService.findListbySql(sql);
			List<HiddenDangerByProfessionVO> voList = new ArrayList<HiddenDangerByProfessionVO>();

			if(riskList!=null && riskList.size()>0){
				for(int i=0; i<riskList.size(); i++){
					HiddenDangerByProfessionVO vo = new HiddenDangerByProfessionVO();
					vo.setNumber(Double.parseDouble(riskList.get(i)[0].toString()));
					vo.setProfession(riskList.get(i)[1].toString());
					vo.setProfessionName(DicUtil.getTypeNameByCode("risk_type", riskList.get(i)[1].toString()));
					voList.add(vo);
				}
			}

			List<HiddenDangerByProfessionVO> resultList = new ArrayList<HiddenDangerByProfessionVO>();
			String[] yeProfessionList = new String[voList.size()];
			int step = 0;
			for(int i=0; i<voList.size(); i++){
				int flag = 0;
				for(int j=0; j<yeProfessionList.length; j++){
					if (voList.get(i).getProfession().equals(yeProfessionList[j])){
						flag = 1;
					}else{
						continue;
					}
				}
				if(0==flag){
					yeProfessionList[step] = voList.get(i).getProfession();
					step ++;
				}
			}

			for (int m=0; m<step; m++){
				HiddenDangerByProfessionVO temp = new HiddenDangerByProfessionVO();
				temp.setNumber(0.0);
				temp.setProfession(yeProfessionList[m]);
				temp.setProfessionName(DicUtil.getTypeNameByCode("risk_type", yeProfessionList[m]));
				for (HiddenDangerByProfessionVO v : voList){
					if (yeProfessionList[m].equals(v.getProfession())){
						temp.setNumber(temp.getNumber() + v.getNumber());
					}
				}
				resultList.add(temp);
			}

			//voList数组进行排序
			Collections.sort(resultList, new Comparator<HiddenDangerByProfessionVO>() {
				@Override
				public int compare(HiddenDangerByProfessionVO o1, HiddenDangerByProfessionVO o2) {
					if(o1.getNumber() < o2.getNumber()){
						return 0;
					}else{
						return -1;
					}
				}
			});
			return new ApiResultJson(resultList);
		} catch (Exception e) {
			LogUtil.error("隐患类型分布查询错误",e);
			return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
		}
	}
}
