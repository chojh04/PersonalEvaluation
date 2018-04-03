package kr.co.dev.tools.api.personal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.datastax.driver.core.Session;
import com.datastax.shaded.netty.handler.codec.http.HttpRequest;

import kr.co.dev.tools.api.personal.service.ResultLogin;
import kr.co.dev.tools.api.personal.service.PerformanceValue;
import kr.co.dev.tools.api.personal.service.PersonalEvaluationService;
import kr.co.dev.tools.api.personal.database.dao.Employe;
import kr.co.dev.tools.api.personal.database.dao.PerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultPerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ResultProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultValueScore;
import kr.co.dev.tools.api.personal.database.dao.ValueScore;

@Controller("PersonalEvalation")
@SessionAttributes("Member")
public class PersonalEvaluationController {
	protected final Logger logger = LoggerFactory.getLogger(PersonalEvaluationController.class);	
	
	@Autowired
	PersonalEvaluationService personalEvalationService;	
	
	@RequestMapping(value="/login")
	public String login()
	{
		logger.debug("loginGo");		
		return "/sub/login/login";	
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request)
	{
		logger.debug("logoutGo");	
		request.getSession().invalidate();
		return "/sub/login/login";	
	}
	
	@RequestMapping(value="/main")
	public String main()
	{
		return "/sub/main";	
	}
	
	@RequestMapping(value="/password")
	public String password(HttpServletRequest request)
	{				
		Employe employeInfo = personalEvalationService.getEmploye((String)(request.getSession().getAttribute("employeId")));
		request.setAttribute("employeInfo", employeInfo);
		return "/sub/setting/password";	
	}
	
	@RequestMapping(value="/updatePassword")
	@ResponseBody
	public String savePassword(
							@RequestParam(value="password") String password,
							HttpServletRequest request)
	{			
		logger.info("savePassword | IN | Param: password="+password);
		String msg = "";
		boolean result = personalEvalationService.updatePassword((String)(request.getSession().getAttribute("employeId")),password);
		
		if(result){
			msg = "S";
		}else{
			msg = "F";
		}
		
		return msg;	
	}
	
	@RequestMapping(value="/projectList")
	public String project(@RequestParam(value="yyyyMM", defaultValue="") String yyyyMM,
						  HttpServletRequest request)
	{
		request.setAttribute("yyyyMM", yyyyMM);
		return "/sub/project/projectList";	
	}
	
	@RequestMapping(value="/projectListAdmin")
	public String projectListAdmin(@RequestParam(value="yyyyMM", defaultValue="") String yyyyMM,
						  HttpServletRequest request)
	{
		request.setAttribute("yyyyMM", yyyyMM);
		return "/sub/project/projectListAdmin";	
	}
	
	//프로젝트 데이터 조회
	@RequestMapping(value="/projectData")
	public String grtProjectData(@RequestParam String yyyyMM,
							  @RequestParam String searchOption,
							  @RequestParam(value="divisionCode", defaultValue="0") int divisionCode,
							  HttpServletRequest request,
							  Model model)
	{		
			logger.debug("grtProjectData | IN |");
			HashMap<String , Object> result = new HashMap<String, Object>();		
			
			if(searchOption.equals("all")){
				if(divisionCode>0){
					result =  personalEvalationService.getProjectScoreDivision((String)request.getSession().getAttribute("employeId"), yyyyMM,divisionCode);
				}else{
					result =  personalEvalationService.getProjectScoreAll((String)request.getSession().getAttribute("employeId"), yyyyMM);
				}				
			}else{
				result =  personalEvalationService.getProjectScore((String)request.getSession().getAttribute("employeId"), yyyyMM);
			}
			
			logger.debug("grtProjectData | OUT |");
			
			request.setAttribute("projectScoreList", (List<ResultProjectScore>)result.get("resultProjectScore"));
			request.setAttribute("scoreHistoryList", (List<ProjectScore>)result.get("scoreHistory"));
			
			return "/sub/project/projectData";	
	}
	
	@RequestMapping(value="/projectDataAdmin")
	public String grtProjectDataAdmin(@RequestParam String yyyyMM,
							  @RequestParam String searchOption,
							  @RequestParam(value="divisionCode", defaultValue="0") int divisionCode,
							  HttpServletRequest request,
							  Model model)
	{		
			logger.debug("grtProjectData | IN |");
			HashMap<String , Object> result = new HashMap<String, Object>();		
			
			if(searchOption.equals("all")){
				if(divisionCode>0){
					result =  personalEvalationService.getProjectScoreDivision((String)request.getSession().getAttribute("employeId"), yyyyMM,divisionCode);
				}else{
					result =  personalEvalationService.getProjectScoreAll((String)request.getSession().getAttribute("employeId"), yyyyMM);
				}				
			}else{
				result =  personalEvalationService.getProjectScore((String)request.getSession().getAttribute("employeId"), yyyyMM);
			}
			
			logger.debug("grtProjectData | OUT |");
			
			request.setAttribute("projectScoreList", (List<ResultProjectScore>)result.get("resultProjectScore"));
			request.setAttribute("scoreHistoryList", (List<ProjectScore>)result.get("scoreHistory"));
			
			return "/sub/project/projectDataAdmin";	
	}
	
	//프로젝트 데이터 저장
	@RequestMapping(value="/saveProjectScore", produces="application/text; charset=euc-kr")
	@ResponseBody
	public String saveProjectScore
	( 
								   @RequestParam("seq[]") List<Integer> seq,
								   @RequestParam("employeId[]") List<String> employeId,
								   @RequestParam("score1[]") List<Integer> score1,
								   @RequestParam("score2[]") List<Integer> score2,
								   @RequestParam("score3[]") List<Integer> score3,
								   @RequestParam("score4[]") List<Integer> score4,
								   @RequestParam("score5[]") List<Integer> score5,
								   @RequestParam("score6[]") List<Integer> score6,
								   @RequestParam("score7[]") List<Integer> score7,
								   @RequestParam("score8[]") List<Integer> score8,
								   @RequestParam("month") String yyyyMM,
								   @RequestParam("confirmNumber[]") List<Integer> confirmNumber,								   
								   HttpServletRequest request,
								   Model model)
	{
		List<ProjectScore> ProjectScoreList = new ArrayList<ProjectScore>();
		String res;
		boolean isSuccessed = false;
		
		for(int i=0; i<employeId.size(); i++)
		{
			ProjectScore  projectScore = new ProjectScore(yyyyMM,  
														  employeId.get(i),  
														  score1.get(i),  
														  score2.get(i),
														  score3.get(i), 
														  score4.get(i), 
														  score5.get(i),  
														  score6.get(i),
														  score7.get(i),
														  score8.get(i), "", "", "", 0, 0);		
			projectScore.setSeq(seq.get(i));
			projectScore.setConfirmNumber(confirmNumber.get(i));
			ProjectScoreList.add(projectScore);
		}
		
		if(request.getSession().getAttribute("employeId").equals("kpc_admin")){
			isSuccessed = personalEvalationService.saveProjectScoreAdmin(ProjectScoreList);
		}else{
			isSuccessed = personalEvalationService.saveProjectScore(ProjectScoreList);
		}
		
		
		if(isSuccessed){
			res = "S";
		}else{
			res = "F";
		}		
		return res;	
	}
	
	@RequestMapping(value="/popProjectDissent")
	public String projectDissentPop(@RequestParam(value="employeId") String employeId,
		 						    @RequestParam(value="yyyyMM") String yyyyMM,
								    HttpServletRequest request)
	{	
		HashMap<String , Object> result = new HashMap<String, Object>();	
		result =  personalEvalationService.getProjectScore(employeId, yyyyMM);
		for(ResultProjectScore projectScore : (List<ResultProjectScore>)result.get("resultProjectScore")){
			request.setAttribute("employeId", employeId);
			request.setAttribute("yyyyMM", yyyyMM);
			request.setAttribute("status", projectScore.getStatus());
			request.setAttribute("dissent", projectScore.getDissent());			
			request.setAttribute("comments1", projectScore.getComments1());
			request.setAttribute("comments2", projectScore.getComments2());
			request.setAttribute("authLevel", projectScore.getEmployeLevel());
		}		
		return "/sub/project/projectDissentPop";	
	}
	
	@RequestMapping(value="/saveProjectDissent")
	@ResponseBody
	public String projectDissentPop(@RequestParam(value="employeId") String employeId,
		 						    @RequestParam(value="yyyyMM") String yyyyMM,
		 						    @RequestParam(value="dissent") String dissent,
		 						    @RequestParam(value="authLevel") int authLevel,
		 						    @RequestParam(value="comments1", defaultValue="") String comments1,
		 						    @RequestParam(value="comments2", defaultValue="") String comments2,
								    HttpServletRequest request)
	{	

		ProjectScore projectScore = new ProjectScore();
		String res;	
		int status = 0;
		logger.info("saveProjectDissent | IN | authLevel=>"+authLevel);
		switch(authLevel){
			case 0 : status = 4; break;
			case 1 : status = 5; break;
			case 2 : status = 6; break;
		}
		
		projectScore.setEmployeId(employeId);
		projectScore.setMonth(yyyyMM);
		projectScore.setDissent(dissent);
		projectScore.setComments1(comments1);
		projectScore.setComments2(comments2);
		projectScore.setStatus(status);	
		projectScore.setConfirmNumber(1);
		
		if(personalEvalationService.saveProjectDissent(projectScore)){
			res = "S";
		}else{
			res = "F";
		}			
		return res;	
	}
	
	@RequestMapping(value="/performanceList")
	public String performanceList(@RequestParam(value="yyyyMM", defaultValue="") String yyyyMM,
												HttpServletRequest request)
	{	
		request.setAttribute("yyyyMM", yyyyMM);
		return "/sub/performance/performanceList";	
	}
	
	@RequestMapping(value="/performanceListAdmin")
	public String performanceAdmin(@RequestParam(value="yyyyMM", defaultValue="") String yyyyMM,
												HttpServletRequest request)
	{	
		request.setAttribute("yyyyMM", yyyyMM);
		return "/sub/performance/performanceListAdmin";	
	}
	
	@RequestMapping(value="/performanceListData")
	public String performanceListData(@RequestParam String yyyyMM,
								 	 @RequestParam String searchOption,
								 	 HttpServletRequest request,
									 Model model)
	{		
				
		logger.debug("performanceListData searchSDate=>"+yyyyMM+"session=>"+request.getSession().getAttribute("authLevel"));
		
		List<PerformanceValue> performanceValueList = new ArrayList<PerformanceValue>();
		
		performanceValueList =  personalEvalationService.getTotalScoreList((String)request.getSession().getAttribute("employeId"),
																(Integer)request.getSession().getAttribute("authLevel"),
																yyyyMM);
	
		request.setAttribute("performanceValueList", performanceValueList);		
		
		logger.debug("performanceListData | OUT | performanceValueListSize => "+performanceValueList.size());			
		return "/sub/performance/performanceListData";	
	}
	
	@RequestMapping(value="/performanceListDataAdmin")
	public String performanceListDataAdmin(@RequestParam String yyyyMM,
								 	 @RequestParam String searchOption,
								 	 HttpServletRequest request,
									 Model model)
	{		
				
		logger.debug("performanceListData searchSDate=>"+yyyyMM+"session=>"+request.getSession().getAttribute("authLevel"));
		
		List<PerformanceValue> performanceValueList = new ArrayList<PerformanceValue>();
		
		performanceValueList =  personalEvalationService.getTotalScoreList((String)request.getSession().getAttribute("employeId"),
																(Integer)request.getSession().getAttribute("authLevel"),
																yyyyMM);
	
		request.setAttribute("performanceValueList", performanceValueList);		
		
		logger.debug("performanceListData | OUT | performanceValueListSize => "+performanceValueList.size());			
		return "/sub/performance/performanceListDataAdmin";	
	}
	
	@RequestMapping(value="/performanceConfirm1")
	public String performanceStep1(@RequestParam String employeId,
								   @RequestParam String yyyyMM,
								   @RequestParam int  authLevel,
								   HttpServletRequest request,
								   Model model){
		PerformanceValue performanceValue = new PerformanceValue();
		
		performanceValue =  personalEvalationService.getTotalScore(employeId,
																	authLevel,
																	yyyyMM);	
	
		request.setAttribute("performanceValue", performanceValue);		
		return "/sub/performance/performanceConfirm1";	
	}
	
	
	
	@RequestMapping(value="/performanceConfirm2")
	public String performanceStep2(@RequestParam String employeId,
								   @RequestParam String yyyyMM,
								   @RequestParam int  authLevel,
								   HttpServletRequest request,
								   Model model){
		PerformanceValue performanceValue = new PerformanceValue();
		
		performanceValue =  personalEvalationService.getTotalScore(employeId,
																authLevel,
																yyyyMM);	
	
		request.setAttribute("performanceValue", performanceValue);		
		return "/sub/performance/performanceConfirm2";	
	}
	
	
	@RequestMapping(value="/performanceData")
	public String performanceData(@RequestParam String employeId,
								  @RequestParam String yyyyMM,
								  HttpServletRequest request,
								  Model model){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = personalEvalationService.getTotalScoreMap(employeId,
																  (Integer)request.getSession().getAttribute("authLevel"),
																  yyyyMM);	
		ResultPerformanceScore resPerfom = (ResultPerformanceScore)resultMap.get("performanceScore");

		request.setAttribute("performanceScore", resPerfom);
		request.setAttribute("valueScore1", (List<Boolean>)resultMap.get("valueScore1"));
		request.setAttribute("valueScore2", (List<Boolean>)resultMap.get("valueScore2"));
		request.setAttribute("comments1", (String)resultMap.get("comments1"));
		request.setAttribute("comments2", (String)resultMap.get("comments2"));
		request.setAttribute("valueRatio", (Double)resultMap.get("valueRatio"));
		request.setAttribute("performRatio", (Double)resultMap.get("performRatio"));
		request.setAttribute("performScoreHistory1", (Double)resultMap.get("performScoreHistory1"));
		request.setAttribute("valueScoreHistory1", (Double)resultMap.get("valueScoreHistory1"));
		request.setAttribute("performScoreHistory2", (Double)resultMap.get("performScoreHistory2"));
		request.setAttribute("valueScoreHistory2", (Double)resultMap.get("valueScoreHistory2"));
		
		
		return "/sub/performance/performanceData";	
	}
		
	@RequestMapping(value="/performanceDataAdmin")
	public String performanceDataAdmin(@RequestParam String employeId,
								  @RequestParam String yyyyMM,
								  HttpServletRequest request,
								  Model model){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = personalEvalationService.getTotalScoreMap(employeId,
																  (Integer)request.getSession().getAttribute("authLevel"),
																  yyyyMM);	
		ResultPerformanceScore resPerfom = (ResultPerformanceScore)resultMap.get("performanceScore");

		request.setAttribute("performanceScore", resPerfom);
		request.setAttribute("month", yyyyMM);
		request.setAttribute("valueScore1", (List<Boolean>)resultMap.get("valueScore1"));
		request.setAttribute("valueScore2", (List<Boolean>)resultMap.get("valueScore2"));
		request.setAttribute("comments1", (String)resultMap.get("comments1"));
		request.setAttribute("comments2", (String)resultMap.get("comments2"));
		request.setAttribute("valueRatio", (Double)resultMap.get("valueRatio"));
		request.setAttribute("performRatio", (Double)resultMap.get("performRatio"));
		request.setAttribute("performScoreHistory1", (Double)resultMap.get("performScoreHistory1"));
		request.setAttribute("valueScoreHistory1", (Double)resultMap.get("valueScoreHistory1"));
		request.setAttribute("performScoreHistory2", (Double)resultMap.get("performScoreHistory2"));
		request.setAttribute("valueScoreHistory2", (Double)resultMap.get("valueScoreHistory2"));
		
		
		return "/sub/performance/performanceDataAdmin";	
	}
	
	@RequestMapping(value="/savePerformScore", produces="application/text; charset=euc-kr")
	@ResponseBody
	public String savePerformData(@RequestParam("employeId") String employeId,
								  @RequestParam("month") String yyyyMM,
								  @RequestParam("performScore") int performScore,								 
								  @RequestParam("confirmNumber") int confirmNumber,
								  @RequestParam("valueScore[]") List<Boolean> valueBoolean,
								  @RequestParam(value="valueComments", defaultValue="") String valueComments,								  
								  @RequestParam(value="performComments", defaultValue="") String performComments,
								  @RequestParam(value="dissentComments", defaultValue="") String dissentComments,
								  HttpServletRequest request,
								  Model model){		
		logger.info("savePerformScore | START | confirmNumber="+confirmNumber);
		PerformanceValue performanceValue = new PerformanceValue();	
		String res = "";
		boolean result = false;
		
		performanceValue.setEmployeValues(employeId, "", "", "", confirmNumber);
		if(confirmNumber==1 || employeId.equals("990101")){
			performanceValue.setPerfomValues(performScore, 0, yyyyMM);
			performanceValue.setValueComments1(valueComments);
			performanceValue.setPerformComments1(performComments);
			performanceValue.setValueScore1(valueBoolean);
			performanceValue.setDissentComments1(dissentComments);
		}
		
		if(confirmNumber==2 || employeId.equals("990101")){
			performanceValue.setPerfomValues(0, performScore, yyyyMM);
			performanceValue.setValueComments2(valueComments);
			performanceValue.setPerformComments2(performComments);
			performanceValue.setValueScore2(valueBoolean);
			performanceValue.setDissentComments2(dissentComments);
		}
		
		if(employeId.equals("990101")){	
			performanceValue.setPerfomValues(performScore, performScore, yyyyMM);
		}
		
		if(employeId.equals("990101")){
			result = personalEvalationService.saveTotalScoreCEO(performanceValue);
		}else{
			result = personalEvalationService.saveTotalScore(performanceValue, confirmNumber);
		}
		
		if(result){
			res = "S";
		}else{
			res = "F";
		}
		
		logger.debug("confirmNumber=>"+confirmNumber);
		logger.info("savePerformScore | END |");
		
		return res;	
	}
	
	@RequestMapping(value="/savePerformScoreAdmin", produces="application/text; charset=euc-kr")
	@ResponseBody
	public String savePerformDataAdmin(@RequestParam("employeId") String employeId,
								  @RequestParam("month") String yyyyMM,
								  @RequestParam("performScore[]") List<Integer> performScore,
								  @RequestParam("values1[]") List<Boolean> valueBoolean1,
								  @RequestParam("values2[]") List<Boolean> valueBoolean2,
								  @RequestParam(value="valueComments[]", defaultValue="") List<String> valueComments,								  
								  @RequestParam(value="performComments[]", defaultValue="") List<String> performComments,
								  @RequestParam(value="dissentComments[]", defaultValue="") List<String> dissentComments,
								  HttpServletRequest request,
								  Model model){		
		logger.info("savePerformScoreAdmin | START | employeId="+employeId);
		PerformanceValue performanceValue = new PerformanceValue();	
		String res = "";
		boolean result = false;
		int index=0;		
		for(int confirmNumber=1; confirmNumber<=performScore.size(); confirmNumber++){
			index = (confirmNumber-1);
			performanceValue.setEmployeValues(employeId, "", "", "", confirmNumber);
			if(confirmNumber==1){
				performanceValue.setPerfomValues(performScore.get(index), 0, yyyyMM);
				performanceValue.setValueComments1(valueComments.get(index));
				performanceValue.setPerformComments1(performComments.get(index));
				performanceValue.setValueScore1(valueBoolean1);
				performanceValue.setDissentComments1(dissentComments.get(index));
			}
			
			if(confirmNumber==2){
				performanceValue.setPerfomValues(0, performScore.get(index), yyyyMM);
				performanceValue.setValueComments2(valueComments.get(index));
				performanceValue.setPerformComments2(performComments.get(index));
				performanceValue.setValueScore2(valueBoolean2);
				performanceValue.setDissentComments2(dissentComments.get(index));
			}
			
			
			result = personalEvalationService.saveTotalScore(performanceValue, confirmNumber);
			
			
			if(result){
				res = "S";
			}else{
				res = "F";
			}
		}
		logger.info("savePerformScoreAdmin | END | result : "+result);
		
		return res;	
	}
	
	@RequestMapping(value="/savePerformDissent")
	@ResponseBody
	public String savePerformDissent(@RequestParam(value="employeId") String employeId,
		 						    @RequestParam(value="month") String yyyyMM,
		 						    @RequestParam(value="dissent") String dissent,
		 						   @RequestParam(value="useAuthLevel") int authLevel,
								    HttpServletRequest request)
	{	
		PerformanceScore performanceScore = new PerformanceScore();
		String res;	
		int status = 0;
		performanceScore.setEmployeId(employeId);
		performanceScore.setMonth(yyyyMM);
		performanceScore.setDissent(dissent);
		performanceScore.setConfirmNumber(authLevel);	
	
		if(personalEvalationService.savePerformDissent(performanceScore)){
			res = "S";
		}else{
			res = "F";
		}			
		return res;	
	}
	
	
	
	
	
	@RequestMapping(value="/statstics")
	public String statistics(){				
		return "/sub/statstics/statstics";	
	}
	
	@RequestMapping(value="/loginProc", produces="application/text; charset=euc-kr")
	@ResponseBody	
	public String login_proc(@RequestParam("employeId") String employId,
							 @RequestParam("employePw") String employPw,
							 HttpServletRequest request,
							 Model model){
		logger.debug("requestParam:"+employId);
		logger.debug("requestParam:"+employPw);
		

		String msg = "";
		ResultLogin resMemberInfo = personalEvalationService.login(employId,employPw);
		if(resMemberInfo.getResultStatus() == 1){
			request.getSession().setAttribute("employeId", resMemberInfo.getEmployeId());
			request.getSession().setAttribute("employeName", resMemberInfo.getEmployeName());
			request.getSession().setAttribute("authLevel", resMemberInfo.getAuthLevel());
			request.getSession().setAttribute("teamCode", resMemberInfo.getTeamCode());
			msg = resMemberInfo.getEmployeName();
		}else{
			msg = "fail";
		}
		
		logger.debug("loginId=>"+request.getSession().getAttribute("employeId")+" : loginAuthLevel"+resMemberInfo.getAuthLevel()+"msg->"+msg);		
		return msg;
	}		
}
	

