package kr.co.dev.tools.api.personal.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.dev.tools.api.personal.database.dao.PersonalEvaluationDao;
import kr.co.dev.tools.api.personal.database.dao.Employe;
import kr.co.dev.tools.api.personal.database.dao.PerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ValueScore;
import kr.co.dev.tools.api.personal.database.dao.ProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultPerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ResultProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultValueScore;



@Service("PersonalEvalationService")
public class PersonalEvaluationServiceImpl implements PersonalEvaluationService{		
	static final List<Integer> officer = new ArrayList<Integer>();//임원	
	static final List<Integer> commandTeam = new ArrayList<Integer>();//관리본부	
	static final List<Integer> tGridTeam = new ArrayList<Integer>();//T-Grid 사업본부
	static final List<Integer> degitalTeam = new ArrayList<Integer>();	//Digital 사업본부
	static final List<Integer> distributeTeam = new ArrayList<Integer>();	//유통사업본부
	static final List<Integer> developTeam = new ArrayList<Integer>();		//기술개발연구소
	
	HttpSession session;
	
	protected final Logger logger = LoggerFactory.getLogger(PersonalEvaluationServiceImpl.class);
	
	@Autowired
	PersonalEvaluationDao personalEvalationDao;

	@Override
	public ResultLogin login(String employeId, String employePw) {
		// TODO Auto-generated method stub
		logger.debug("login() employeeId : " + employeId + " , employePwd : " + employePw);
	
		ResultLogin result = new ResultLogin();
	
		Employe employeInfo = personalEvalationDao.getEmploye(employeId);
		
		if(employeInfo == null)
		{
			logger.debug("No User Id => "+ employeId);
			result.setResultStatus(2);
			return result;
		}
		
		if(!employePw.equals(employeInfo.getPassword()))
		{
			logger.debug("Unmatched User Password => "+ employeInfo.getPassword() + "/"+ employePw);
			result.setResultStatus(3);
			return result;
		}
		
		result.setResultStatus(1);
		result.setEmployeeInfo(employeInfo.getEmployeId(), employeInfo.getEmployeName(),
				employeInfo.getTeamCode(), employeInfo.getDivisionCode(), employeInfo.getAuthLevel());
		logger.debug("result : " + result.toString());			

		return result;
	}

	@Override
	public HashMap<String, Object> getProjectScore(String employeId, String yyyyMM) {
		logger.info("getProjectScoreList | IN |");
		// TODO Auto-generated method stub	
		List<ResultProjectScore> resultList = new ArrayList<ResultProjectScore>();
		List<ProjectScore> resultHistoryList = new ArrayList<ProjectScore>();
		ResultProjectScore resultProjectScore = new ResultProjectScore();
		ProjectScore projectScore = personalEvalationDao.getProjectScore(employeId, yyyyMM);
		ProjectScore projectScoreHistory = personalEvalationDao.getProjectScoreHistory(employeId, yyyyMM);
		Employe employe = personalEvalationDao.getEmploye(employeId);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		setRatio();
		int scoreRatio = 0;
					
		if(employe.getEmployeId().equals(projectScore.getEmployeId()))
		{								
			if(projectScore.getScore8()<1)
			{						
				switch(employe.getDivisionCode()){
					case 0 : scoreRatio = officer.get(employe.getPosition()); break;
					case 1 : scoreRatio = commandTeam.get(employe.getPosition()); break;
					case 2 : scoreRatio = tGridTeam.get(employe.getPosition()); break;
					case 3 : scoreRatio = degitalTeam.get(employe.getPosition()); break;
					case 4 : scoreRatio = distributeTeam.get(employe.getPosition()); break;
					case 5 : scoreRatio = developTeam.get(employe.getPosition()); break;
				}						
			}
			else
			{
				scoreRatio = projectScore.getScore8();
			}
			resultProjectScore.setProjectScore(projectScore.getSeq(), 
											   projectScore.getMonth(),
											   projectScore.getScore1(),
											   projectScore.getScore2(),
											   projectScore.getScore3(),
											   projectScore.getScore4(),
											   projectScore.getScore5(),
											   projectScore.getScore6(),
											   projectScore.getScore7(),
											   scoreRatio,
											   projectScore.getStatus(),projectScore.getDissent(),
											   projectScore.getComments1(), projectScore.getComments2(),
											   projectScore.getConfirmNumber());
		}	
		resultProjectScore.setEmploye(employe.getEmployeId(), 
						  employe.getEmployeName(), 
						  employe.getDivisionName(), 
						  employe.getDivisionCode(), 
						  employe.getPosition(), 
						  ((employe.getDivisionCode()>0 && employe.getTeamCode()==0)? "부서장" : employe.getTeamName()), 
						  employe.getTeamCode(),
						  employe.getAuthLevel()
						  );
		
		resultList.add(resultProjectScore);
		resultHistoryList.add(projectScoreHistory);
		resultMap.put("resultProjectScore",resultList);
		resultMap.put("scoreHistory",resultHistoryList);
		
		
		
		logger.info("getProjectScoreList | OUT |");
		
		return resultMap;	
	}
	
	@Override
	public HashMap<String, Object> getProjectScoreAll(String employeId, String yyyyMM) {
		logger.info("getProjectScoreListALL | IN |");
		// TODO Auto-generated method stub	
		List<ResultProjectScore> resultList = new ArrayList<ResultProjectScore>();
		List<ProjectScore> projectScoreList = personalEvalationDao.getProjectScoreAll(employeId, yyyyMM);
		List<ProjectScore> projectScoreHistoryList = personalEvalationDao.getProjectScoreAllHistory(employeId, yyyyMM);
		List<Employe> employeList = personalEvalationDao.getEmployeList();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
	
		setRatio();
		int scoreRatio = 0;
		
		for(Employe employe : employeList){
			
			ResultProjectScore resultProjectScore = new ResultProjectScore();
			
			for(ProjectScore projectScore : projectScoreList)
			{
				if(employe.getEmployeId().equals(projectScore.getEmployeId()))
				{								
					if(projectScore.getScore8()<1)
					{						
						switch(employe.getDivisionCode()){
							case 0 : scoreRatio = officer.get(employe.getPosition()); break;
							case 1 : scoreRatio = commandTeam.get(employe.getPosition()); break;
							case 2 : scoreRatio = tGridTeam.get(employe.getPosition()); break;
							case 3 : scoreRatio = degitalTeam.get(employe.getPosition()); break;
							case 4 : scoreRatio = distributeTeam.get(employe.getPosition()); break;
							case 5 : scoreRatio = developTeam.get(employe.getPosition()); break;
						}						
					}
					else
					{
						scoreRatio = projectScore.getScore8();
					}
					resultProjectScore.setProjectScore(projectScore.getSeq(),
													   projectScore.getMonth(),
													   projectScore.getScore1(),
													   projectScore.getScore2(),
													   projectScore.getScore3(),
													   projectScore.getScore4(),
													   projectScore.getScore5(),
													   projectScore.getScore6(),
													   projectScore.getScore7(),
													   scoreRatio,
													   projectScore.getStatus(), projectScore.getDissent(),
													   projectScore.getComments1(), projectScore.getComments2(),
													   projectScore.getConfirmNumber());
				}				
			}
			
			//history
			
			resultProjectScore.setEmploye(employe.getEmployeId(), 
					  employe.getEmployeName(), 
					  employe.getDivisionName(), 
					  employe.getDivisionCode(), 
					  employe.getPosition(), 
					  ((employe.getDivisionCode()>0 && employe.getTeamCode()==0)? "부서장" : employe.getTeamName()), 
					  employe.getTeamCode(),
					  employe.getAuthLevel());

			resultList.add(resultProjectScore);			
		}
		resultMap.put("resultProjectScore",resultList);
		resultMap.put("scoreHistory",projectScoreHistoryList);		
		
		logger.debug("getProjectScoreList=>"+projectScoreList.size()+"EmployeList=>"+employeList.size());
		logger.info("getProjectScoreListALL | OUT |");
		
		return resultMap;	

	}
	
	
	@Override
	public HashMap<String, Object> getProjectScoreDivision(String employeId, String yyyyMM, int divisionCode) {
		logger.info("getProjectScoreListDivision | IN |");
		// TODO Auto-generated method stub	
		List<ResultProjectScore> resultList = new ArrayList<ResultProjectScore>();
		List<ProjectScore> projectScoreList = personalEvalationDao.getProjectScoreAll(employeId, yyyyMM);
		List<ProjectScore> projectScoreHistoryList = personalEvalationDao.getProjectScoreAllHistory(employeId, yyyyMM);
		List<Employe> employeList = personalEvalationDao.getEmployeList();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		setRatio();
		int scoreRatio = 0;
		
		for(Employe employe : employeList){
			if(employe.getDivisionCode() == divisionCode){
				ResultProjectScore resultProjectScore = new ResultProjectScore();
				
				for(ProjectScore projectScore : projectScoreList){
					if(employe.getEmployeId().equals(projectScore.getEmployeId()))
					{								
						if(projectScore.getScore8()<1)
						{						
							switch(employe.getDivisionCode()){
								case 0 : scoreRatio = officer.get(employe.getPosition()); break;
								case 1 : scoreRatio = commandTeam.get(employe.getPosition()); break;
								case 2 : scoreRatio = tGridTeam.get(employe.getPosition()); break;
								case 3 : scoreRatio = degitalTeam.get(employe.getPosition()); break;
								case 4 : scoreRatio = distributeTeam.get(employe.getPosition()); break;
								case 5 : scoreRatio = developTeam.get(employe.getPosition()); break;
							}						
						}
						else
						{
							scoreRatio = projectScore.getScore8();
						}
						resultProjectScore.setProjectScore(projectScore.getSeq(),
														   projectScore.getMonth(),
														   projectScore.getScore1(),
														   projectScore.getScore2(),
														   projectScore.getScore3(),
														   projectScore.getScore4(),
														   projectScore.getScore5(),
														   projectScore.getScore6(),
														   projectScore.getScore7(),
														   scoreRatio,
														   projectScore.getStatus(), projectScore.getDissent(),
														   projectScore.getComments1(), projectScore.getComments2(),
														   projectScore.getConfirmNumber());
					}				
				}
				resultProjectScore.setEmploye(employe.getEmployeId(), 
						  employe.getEmployeName(), 
						  employe.getDivisionName(), 
						  employe.getDivisionCode(), 
						  employe.getPosition(), 
						  ((employe.getDivisionCode()>0 && employe.getTeamCode()==0)? "부서장" : employe.getTeamName()), 
						  employe.getTeamCode(),
						  employe.getAuthLevel());
	
				resultList.add(resultProjectScore);	
			}
		}
		resultMap.put("resultProjectScore",resultList);
		resultMap.put("scoreHistory",projectScoreHistoryList);
		
		logger.debug("getProjectScoreList=>"+projectScoreList.size()+"EmployeList=>"+employeList.size());
		logger.info("getProjectScoreListDivision | OUT |");
		
		return resultMap;	

	}

	
	@Override
	public boolean insertProjectScore(ProjectScore projectScore){
		logger.info("insertProjectScoreList | IN |");
		boolean state = false;
		state = personalEvalationDao.insertProjectScore(projectScore);
		logger.info("insertProjectScoreList | OUT |");
		return state;
	}

	@Override
	public boolean deleteProjectScore(List<Integer> seqList){
		// TODO Auto-generated method stub
		logger.info("deleteProjectScoreList | IN |");
		boolean state = false;
		
		HashMap<String, Object> seqMap = new HashMap<String, Object>();		
		seqMap.put("seqList", seqList);
		state = personalEvalationDao.deleteProjectScore(seqMap);
		logger.info("deleteProjectScoreList | OUT |");
		return state;		
	}
	
	@Override
	public boolean updateProjectScore(ProjectScore projectScore) {		
		// TODO Auto-generated method stub
		logger.info("updateProjectScoreList | IN |");
		boolean state = false;
		state = personalEvalationDao.updateProjectScore(projectScore);
		logger.info("updateProjectScoreList | OUT |");
		return state;
	}
	
	@Override
	public ResultPerformanceScore getPerformanceScore(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub		
		logger.info("getPerformanceScoreList | IN |");
		ResultPerformanceScore performanceScore = personalEvalationDao.getPerformanceScore(employeId, authLevel, yyyyMM);		
		logger.debug("getPerformanceScoreList=>"+performanceScore);
		logger.info("getPerformanceScoreList | OUT |");
		return performanceScore;
	}
	
	@Override
	public List<ResultPerformanceScore> getPerformanceScoreAll(String employeId, int authLevel,	String yyyyMM) {		
		// TODO Auto-generated method stub		
		logger.info("getPerformanceScoreListAll | IN |");
		List<ResultPerformanceScore> performanceScore = personalEvalationDao.getPerformanceScoreAll(employeId, authLevel, yyyyMM);		
		logger.debug("getPerformanceScoreList=>"+performanceScore);
		logger.info("getPerformanceScoreListAll | OUT |");
		return performanceScore;
	}

	@Override
	public boolean insertPerformanceScore(PerformanceScore performanceScore) {
		logger.info("insertPerformanceScoreList | IN |");
		boolean state = false;
		state = personalEvalationDao.insertPerformanceScore(performanceScore);
		logger.info("insertPerformanceScoreList | OUT |");
		return state;
	}

	@Override
	public boolean deletePerformanceScore(List<Integer> seqList) {
		// TODO Auto-generated method stub
		logger.info("deletePerformanceScoreList | IN |");
		boolean state = false;
		
		HashMap<String, Object> seqMap = new HashMap<String, Object>();		
		seqMap.put("seqList", seqList);
		state = personalEvalationDao.deletePerformanceScore(seqMap);
		logger.info("deletePerformanceScoreList | OUT |");
		return state;	
	}

	@Override
	public boolean updatePerformanceScore(PerformanceScore performanceScore) {
		logger.info("updatePerformanceScoreList | IN |");
		
		// TODO Auto-generated method stub
		boolean state = false;
		state = personalEvalationDao.updatePerformanceScore(performanceScore);
		
		logger.info("updatePerformanceScoreList | OUT |");
		return state;		
	}

	@Override
	public List<ResultValueScore> getValueScore(String searchId, int authLevel, String yyyyMM) 
	{
		logger.info("getValueScoreList | IN |");		
		// TODO Auto-generated method stub		
		List<ResultValueScore> valueScoreList = personalEvalationDao.getValueScore(searchId, authLevel, yyyyMM);
		logger.info("getValueScoreList | OUT |");
		return valueScoreList;
	}
	
	@Override
	public List<ResultValueScore> getValueScoreAll(String employeId, int authLevel, String yyyyMM) 
	{
		logger.info("getValueScoreListAll | IN |");		
		// TODO Auto-generated method stub	
		List<ResultValueScore> valueScoreList = personalEvalationDao.getValueScoreAll(employeId, authLevel, yyyyMM);

		logger.info("getValueScoreListAll | OUT |");
		
		return valueScoreList;
	}

	@Override
	public boolean insertValueScore(ValueScore valueScore) {
		logger.info("insertValueScoreList | IN |");
		
		boolean state = false;		
		state = personalEvalationDao.insertValueScore(valueScore);
		
		logger.info("insertValueScoreList | OUT |");
		
		return state;
		
	}

	@Override
	public boolean deleteValueScore(List<Integer> seqList) {
		// TODO Auto-generated method stub
		logger.info("deleteValueScoreList | IN |");
		boolean state = false;
		
		HashMap<String, Object> seqMap = new HashMap<String, Object>();		
		seqMap.put("seqList", seqList);
		state = personalEvalationDao.deleteValueScore(seqMap);
		logger.info("deleteValueScoreList | OUT |");
		return state;	
	}

	@Override
	public boolean updateValueScore(ValueScore valueceScore) {
		// TODO Auto-generated method stub
		logger.info("updateValueScoreList | IN |");
		boolean state = false;
		state = personalEvalationDao.updateValueScore(valueceScore);
		logger.info("updateValueScoreList | OUT |");
		return state;
	}
	
	@Override
	public boolean saveProjectScore(List<ProjectScore> projectScoreList) {
		// TODO Auto-generated method stub
		logger.info("saveProjectScoreList | IN | projectScoreList.size=>"+projectScoreList.size());
		boolean state = false;
		int count = 0;
		try{
			for(ProjectScore projectScore : projectScoreList){
				switch(projectScore.getConfirmNumber()){
					case 2:
						projectScore.setStatus(3);
						break;
					case 1:
						projectScore.setStatus(2);
						break;
					case 0:
						projectScore.setStatus(1);
						break;
					default:
						projectScore.setStatus(0);
						break;						
				}		
				
				//사장님 예외처리
				if(projectScore.getEmployeId().equals("990101")){projectScore.setStatus(3);}
				
				logger.debug("state"+projectScore.getStatus()+"saveStatus"+projectScore.getStatus()+"seq=>"+projectScore.getSeq());
				
				count = personalEvalationDao.getProjectScoreCount(projectScore.getEmployeId(), projectScore.getMonth());
				
				if(projectScore.getSeq()>0){
					state = personalEvalationDao.updateProjectScore(projectScore);
					if(projectScore.getStatus()==1){
						personalEvalationDao.updateProjectScoreHistory(projectScore);
					}
				}else{
					state = personalEvalationDao.insertProjectScore(projectScore);
					if(projectScore.getStatus()==1){
						personalEvalationDao.insertProjectScoreHistory(projectScore);
					}
				}
				if(!state){
					return state;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("saveProjectScoreList | OUT |");
		return state;
	}
	
	@Override
	public boolean saveProjectScoreAdmin(List<ProjectScore> projectScoreList) {
		// TODO Auto-generated method stub
		logger.info("saveProjectScoreList | IN | projectScoreList.size=>"+projectScoreList.size());
		boolean state = false;
		int count = 0;
		try{
			for(ProjectScore projectScore : projectScoreList){			
				//사장님 예외처리
				projectScore.setStatus(3);
				
				logger.debug("state"+projectScore.getStatus()+"saveStatus"+projectScore.getStatus()+"seq=>"+projectScore.getSeq());
				
				count = personalEvalationDao.getProjectScoreCount(projectScore.getEmployeId(), projectScore.getMonth());
				
				if(projectScore.getSeq()>0){
					state = personalEvalationDao.updateProjectScore(projectScore);
					if(projectScore.getStatus()==1){
						personalEvalationDao.updateProjectScoreHistory(projectScore);
					}
				}else{
					state = personalEvalationDao.insertProjectScore(projectScore);
					if(projectScore.getStatus()==1){
						personalEvalationDao.insertProjectScoreHistory(projectScore);
					}
				}
				if(!state){
					return state;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("saveProjectScoreList | OUT |");
		return state;
	}
	
	@Override
	public PerformanceValue getTotalScore(String employeId, int authLevel, String yyyyMM){
		// TODO Auto-generated method stub
		logger.info("getTotalScoreList | IN |");		
		PerformanceValue performanceValue = new PerformanceValue();			
		List<ResultValueScore> valueScoreList = getValueScore(employeId, authLevel, yyyyMM);
		ResultPerformanceScore performanceScore = getPerformanceScore(employeId, authLevel, yyyyMM);
		
		/*가치평가Data*/
		for(ResultValueScore valueScore : valueScoreList)
		{			
			if(performanceScore.getEmployeId().equals(valueScore.getEmployeId()))				
			{
				List<Boolean> ScoreList = new ArrayList<Boolean>();
				
				ScoreList.add((valueScore.getScore1()>0)? true : false);
				ScoreList.add((valueScore.getScore2()>0)? true : false);
				ScoreList.add((valueScore.getScore3()>0)? true : false);
				ScoreList.add((valueScore.getScore4()>0)? true : false);
				ScoreList.add((valueScore.getScore5()>0)? true : false);
				ScoreList.add((valueScore.getScore6()>0)? true : false);
				ScoreList.add((valueScore.getScore7()>0)? true : false);
				ScoreList.add((valueScore.getScore8()>0)? true : false);
				
				if(valueScore.getConfirmNumber()==1){				
					performanceValue.setValueComments1(valueScore.getComments());
					performanceValue.setValueScore1(ScoreList);
				}else if(valueScore.getConfirmNumber()==2){	
					performanceValue.setValueComments2(valueScore.getComments());
					performanceValue.setValueScore2(ScoreList);
				}
				logger.debug("valueScore1="+performanceValue.getValueScore1());
				logger.debug("valueScore2="+performanceValue.getValueScore2());
			}				
		}
				
		performanceValue.setEmployeValues(performanceScore.getEmployeId(), 
										  performanceScore.getEmployeName(), 
										  performanceScore.getDivisionName(),
										  ((performanceScore.getDivisionCode()>0 && performanceScore.getTeamCode()==0)? "부서장" : performanceScore.getTeamName()),
										  performanceScore.getAuthLevel());
		
		performanceValue.setPerfomValues(performanceScore.getScore1(), 
										 performanceScore.getScore2(), 
										 yyyyMM);
		
		performanceValue.setPerformComments1(performanceScore.getComments1());
		performanceValue.setPerformComments2(performanceScore.getComments2());

		performanceValue.setDissentComments1(performanceScore.getDissentComments1());
		performanceValue.setDissentComments2(performanceScore.getDissentComments2());
		
		performanceValue.setRatio((5 * 0.1), (5 * 0.1));
		
		logger.info("getTotalScoreList | OUT |");
		return performanceValue;
	}
	
	@Override
	public HashMap<String, Object> getTotalScoreMap(String employeId, int authLevel, String yyyyMM){
		// TODO Auto-generated method stub
		logger.info("getTotalScoreUserData | IN |employeId="+employeId+" : authLevel="+authLevel+"yyyyMM="+yyyyMM);				
									
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		ResultPerformanceScore performanceScore = getPerformanceScore(employeId, 0, yyyyMM);
		List<ResultValueScore> valueScoreList = getValueScoreAll(employeId, 0, yyyyMM);
		
		ResultPerformanceScore performanceScoreHistory = personalEvalationDao.getPerformanceScoreHistory(employeId, 0, yyyyMM);
		List<ResultValueScore> valueScoreHistoryList = personalEvalationDao.getValueScoreHistoryAll(employeId, 0, yyyyMM);
		
		
		
		/*가치평가Data*/
		for(ResultValueScore valueScore : valueScoreList)
		{
			List<Boolean> ScoreList = new ArrayList<Boolean>();
			if(performanceScore.getEmployeId().equals(valueScore.getEmployeId()))				
			{			
				ScoreList.add((valueScore.getScore1()>0)? true : false);
				ScoreList.add((valueScore.getScore2()>0)? true : false);
				ScoreList.add((valueScore.getScore3()>0)? true : false);
				ScoreList.add((valueScore.getScore4()>0)? true : false);
				ScoreList.add((valueScore.getScore5()>0)? true : false);
				ScoreList.add((valueScore.getScore6()>0)? true : false);
				ScoreList.add((valueScore.getScore7()>0)? true : false);
				ScoreList.add((valueScore.getScore8()>0)? true : false);						
					
				if(valueScore.getConfirmNumber()==1){
					resultMap.put("valueScore1", ScoreList);
					resultMap.put("comments1", valueScore.getComments());					
				}else if(valueScore.getConfirmNumber()==2){					
					resultMap.put("valueScore2", ScoreList);
					resultMap.put("comments2", valueScore.getComments());
				}				
			}				
		}
		logger.debug("valueScoreHistoryList"+valueScoreHistoryList.size());
		for(ResultValueScore valueScoreHistory : valueScoreHistoryList)
		{
			List<Boolean> ScoreList = new ArrayList<Boolean>();
			
			if(performanceScoreHistory.getEmployeId().equals(valueScoreHistory.getEmployeId()))				
			{			
				double score=0;
				
				if(valueScoreHistory.getScore1()>0) score++;
				if(valueScoreHistory.getScore2()>0) score++;
				if(valueScoreHistory.getScore3()>0) score++;
				if(valueScoreHistory.getScore4()>0) score++;
				if(valueScoreHistory.getScore5()>0) score++;
				if(valueScoreHistory.getScore6()>0) score++;
				if(valueScoreHistory.getScore7()>0) score++;
				if(valueScoreHistory.getScore8()>0) score++;									
				
				score = (score>0)? score+2 : 0;
				
				logger.debug("valueScoreHistory.getScore1()"+score);
				if(valueScoreHistory.getConfirmNumber()==1){
					resultMap.put("performScoreHistory1", (double)performanceScoreHistory.getScore1());
					resultMap.put("valueScoreHistory1", score);					
				}else if(valueScoreHistory.getConfirmNumber()==2){					
					resultMap.put("performScoreHistory2",  (double)performanceScoreHistory.getScore2());
					resultMap.put("valueScoreHistory2", score);
				}				
			}				
		}

		resultMap.put("performanceScore", performanceScore);		
		resultMap.put("valueRatio", (5 * 0.1));
		resultMap.put("performRatio",(5 * 0.1));
					
		logger.info("getTotalScoreUserData | OUT | performanceValueList");
		return resultMap;
	}
	

	@Override
	public List<PerformanceValue> getTotalScoreList(String employeId, int authLevel, String yyyyMM){
		// TODO Auto-generated method stub
		logger.info("getTotalScoreListAll | IN |");		
		List<PerformanceValue> performanceValueList = new ArrayList<PerformanceValue>();				
		List<ResultValueScore> valueScoreList = getValueScoreAll(employeId, authLevel, yyyyMM);
		List<ResultPerformanceScore> performanceScoreList = getPerformanceScoreAll(employeId, authLevel, yyyyMM);
						
		
		
		for(ResultPerformanceScore performanceScore: performanceScoreList)
		{
			PerformanceValue pV = new PerformanceValue();
			
			/*가치평가Data*/
			for(ResultValueScore valueScore : valueScoreList)
			{				
				if(performanceScore.getEmployeId().equals(valueScore.getEmployeId()))				
				{	
					List<Boolean> ScoreList = new ArrayList<Boolean>();
					ScoreList.add((valueScore.getScore1()>0)? true : false);
					ScoreList.add((valueScore.getScore2()>0)? true : false);
					ScoreList.add((valueScore.getScore3()>0)? true : false);
					ScoreList.add((valueScore.getScore4()>0)? true : false);
					ScoreList.add((valueScore.getScore5()>0)? true : false);
					ScoreList.add((valueScore.getScore6()>0)? true : false);
					ScoreList.add((valueScore.getScore7()>0)? true : false);
					ScoreList.add((valueScore.getScore8()>0)? true : false);	
					
					if(valueScore.getConfirmNumber()==1){
						pV.setValueScore1(ScoreList);	
					}else if(valueScore.getConfirmNumber()==2){	
						pV.setValueScore2(ScoreList);
					}			
				}				
			}
			
			/*가치평가Data*/			
			
			pV.setEmployeValues(performanceScore.getEmployeId(), 
								performanceScore.getEmployeName(), 
								performanceScore.getDivisionName(),
								((performanceScore.getDivisionCode()>0 && performanceScore.getTeamCode()==0)? "부서장" : performanceScore.getTeamName()),
								performanceScore.getAuthLevel());
			
			pV.setPerfomValues(performanceScore.getScore1(),performanceScore.getScore2(), yyyyMM);
			pV.setDissent(performanceScore.getDissent());
			pV.setPerformComments1(performanceScore.getComments1());
			pV.setPerformComments2(performanceScore.getComments2());
			pV.setDissentComments1(performanceScore.getDissentComments1());
			pV.setDissentComments2(performanceScore.getDissentComments2());
			pV.setRatio((5 * 0.1), (5 * 0.1));
			performanceValueList.add(pV);					
		}
				
		logger.info("getTotalScoreListAll | OUT |performanceValueList.size=>"+performanceValueList.size());
		return performanceValueList;
	}

	@Override
	public boolean saveTotalScore(PerformanceValue performanceValue, int confirmNumber) {
		logger.info("saveTotalScore | IN |");
		boolean isSucceeded = false;
		try
		{
			PerformanceScore performanceScore = new PerformanceScore();
			ValueScore valueScore = new ValueScore();
			List<Integer> valueInt = new ArrayList<Integer>();				
			
			performanceScore.setPerformanceScore(performanceValue.getMonth(), 
					 							 performanceValue.getEmployeId(),														 
					 							 "","",confirmNumber);
			if(confirmNumber==1)
			{					
				for(boolean value : performanceValue.getValueScore1()){
					if(value){
						valueInt.add(1);
					}else{
						valueInt.add(0);
					}
				}					
				performanceScore.setScore(performanceValue.getPerfomScore1());	
				performanceScore.setComments(performanceValue.getPerformComments1());
				performanceScore.setDissentComments(performanceValue.getDissentComments1());					
				valueScore.setComments(performanceValue.getValueComments1());					
			}				
			else if(confirmNumber==2)
			{
				for(boolean value : performanceValue.getValueScore2()){
					if(value){
						valueInt.add(1);
					}else{
						valueInt.add(0);
					}
				}
				
				performanceScore.setScore(performanceValue.getPerfomScore2());
				performanceScore.setComments(performanceValue.getPerformComments2());
				performanceScore.setDissentComments(performanceValue.getDissentComments2());
				valueScore.setComments(performanceValue.getValueComments2());
			}
			
			valueScore.setEmployeId(performanceValue.getEmployeId());
			valueScore.setMonth(performanceValue.getMonth());
			valueScore.setScore1(valueInt.get(0));
			valueScore.setScore2(valueInt.get(1));
			valueScore.setScore3(valueInt.get(2));
			valueScore.setScore4(valueInt.get(3));
			valueScore.setScore5(valueInt.get(4));
			valueScore.setScore6(valueInt.get(5));
			valueScore.setScore7(valueInt.get(6));
			valueScore.setScore8(valueInt.get(7));				
			valueScore.setConfirmNumber(confirmNumber);
			
			
			if(personalEvalationDao.getPerformanceScoreCount(performanceValue.getEmployeId(), 
															 confirmNumber, 
															 performanceValue.getMonth() ) > 0)
			{					
				isSucceeded = personalEvalationDao.updatePerformanceScore(performanceScore);
				if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.updatePerformanceScoreHistory(performanceScore); }
			}
			else
			{
				
				logger.debug("performanceScore.getDissentComments()"+performanceScore.getDissentComments());
				
				isSucceeded = personalEvalationDao.insertPerformanceScore(performanceScore);
				if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.insertPerformanceScoreHistory(performanceScore); }
			}
			
			
			if(personalEvalationDao.getValueScoreCount(performanceValue.getEmployeId(), 
					 confirmNumber, 
					 performanceValue.getMonth() ) > 0)
			{					
				isSucceeded = personalEvalationDao.updateValueScore(valueScore);
				if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.updateValueScoreHistory(valueScore);}
			}
			else
			{
				isSucceeded = personalEvalationDao.insertValueScore(valueScore);
				if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.insertValueScoreHistory(valueScore);}
			}	
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("saveTotalScore | OUT |");
		return isSucceeded;
	}
	
	@Override
	public boolean saveTotalScoreCEO(PerformanceValue performanceValue) {
		logger.info("saveTotalScore | IN |");
		boolean isSucceeded = false;
		try
		{
			PerformanceScore performanceScore = new PerformanceScore();
			ValueScore valueScore = new ValueScore();
			List<Integer> valueInt = new ArrayList<Integer>();				
			for(int confirmNumber=1; confirmNumber<=2; confirmNumber++){
				performanceScore.setPerformanceScore(performanceValue.getMonth(), 
						 							 performanceValue.getEmployeId(),														 
						 							 "","",confirmNumber);
				if(confirmNumber==1)
				{					
					for(boolean value : performanceValue.getValueScore1()){
						if(value){
							valueInt.add(1);
						}else{
							valueInt.add(0);
						}
					}					
					performanceScore.setScore(performanceValue.getPerfomScore1());	
					performanceScore.setComments(performanceValue.getPerformComments1());
					performanceScore.setDissentComments(performanceValue.getDissentComments1());					
					valueScore.setComments(performanceValue.getValueComments1());					
				}				
				else if(confirmNumber==2)
				{
					for(boolean value : performanceValue.getValueScore2()){
						if(value){
							valueInt.add(1);
						}else{
							valueInt.add(0);
						}
					}
					
					performanceScore.setScore(performanceValue.getPerfomScore2());
					performanceScore.setComments(performanceValue.getPerformComments2());
					performanceScore.setDissentComments(performanceValue.getDissentComments2());
					valueScore.setComments(performanceValue.getValueComments2());
				}
				
				valueScore.setEmployeId(performanceValue.getEmployeId());
				valueScore.setMonth(performanceValue.getMonth());
				valueScore.setScore1(valueInt.get(0));
				valueScore.setScore2(valueInt.get(1));
				valueScore.setScore3(valueInt.get(2));
				valueScore.setScore4(valueInt.get(3));
				valueScore.setScore5(valueInt.get(4));
				valueScore.setScore6(valueInt.get(5));
				valueScore.setScore7(valueInt.get(6));
				valueScore.setScore8(valueInt.get(7));				
				valueScore.setConfirmNumber(confirmNumber);
				
				if(personalEvalationDao.getPerformanceScoreCount(performanceValue.getEmployeId(), 
																 confirmNumber, 
																 performanceValue.getMonth() ) > 0)
				{					
					isSucceeded = personalEvalationDao.updatePerformanceScore(performanceScore);
					if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.updatePerformanceScoreHistory(performanceScore); }
				}
				else
				{
					
					logger.debug("performanceScore.getDissentComments()"+performanceScore.getDissentComments());
					
					isSucceeded = personalEvalationDao.insertPerformanceScore(performanceScore);
					if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.insertPerformanceScoreHistory(performanceScore); }
				}
				
				
				if(personalEvalationDao.getValueScoreCount(performanceValue.getEmployeId(), 
						 confirmNumber, 
						 performanceValue.getMonth() ) > 0)
				{					
					isSucceeded = personalEvalationDao.updateValueScore(valueScore);
					if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.updateValueScoreHistory(valueScore);}
				}
				else
				{
					isSucceeded = personalEvalationDao.insertValueScore(valueScore);
					if(performanceScore.getDissentComments().equals("") && isSucceeded){ personalEvalationDao.insertValueScoreHistory(valueScore);}
				}	
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
			
		logger.info("saveTotalScore | OUT |");
		return isSucceeded;
	}
	
	public void setRatio(){
	
		/*유통사업본부 */
		distributeTeam.add(0);
		distributeTeam.add(1);
		distributeTeam.add(2);
		distributeTeam.add(0);
		
		/*유통사업본부 */
		degitalTeam.add(0);
		degitalTeam.add(1);
		degitalTeam.add(2);
		degitalTeam.add(0);
				
		/*T-GRID사업본부 */
		tGridTeam.add(0);
		tGridTeam.add(1);
		tGridTeam.add(2);
		tGridTeam.add(0);
		
		/*기업부설연구소 */
		developTeam.add(1);
		developTeam.add(2);
		developTeam.add(3);
		developTeam.add(0);
				
		/*관리본부 */
		commandTeam.add(1);
		commandTeam.add(2);
		commandTeam.add(3);
		commandTeam.add(0);
		
		/*임원 */
		officer.add(0);
		officer.add(0);
		officer.add(0);
		officer.add(5);
	}

	@Override
	public boolean saveProjectDissent(ProjectScore projectScore) {
		// TODO Auto-generated method stub
		logger.debug("saveProjectDissent | IN |");
		
		boolean state = false;

		try
		{
			state = personalEvalationDao.updateProjectComments(projectScore);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		
		logger.debug("saveProjectDissent | OUT |");
		return state;
	}
	
	@Override
	public boolean savePerformDissent(PerformanceScore performanceScore){
		// TODO Auto-generated method stub
		logger.debug("savePerformDissent | IN |");
		
		boolean state = false;

		try
		{
			state = personalEvalationDao.updatePerformanceDissent(performanceScore);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		
		logger.debug("savePerformDissent | OUT |");
		return state;
	}

	@Override
	public Employe getEmploye(String employeId) {
		Employe employe = personalEvalationDao.getEmploye(employeId);
		return employe;
	}
	
	@Override
	public boolean updatePassword(String employeId ,String password){
		Employe employe = new Employe();
		employe.setEmployeId(employeId);
		employe.setPassword(password);
		boolean result = personalEvalationDao.updateEmploye(employe);
		return result;
	}
	
}
