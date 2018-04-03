package kr.co.dev.tools.api.personal.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.dev.tools.api.personal.database.dao.Employe;
import kr.co.dev.tools.api.personal.database.dao.PerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultPerformanceScore;
import kr.co.dev.tools.api.personal.database.dao.ResultProjectScore;
import kr.co.dev.tools.api.personal.database.dao.ResultValueScore;
import kr.co.dev.tools.api.personal.database.dao.ValueScore;

public interface PersonalEvaluationService
{	
	
	public ResultLogin login(String employeId, String employePwd);
	public Employe getEmploye(String employeId);
	public boolean updatePassword(String employeId, String employePwd);

	//프로젝트점수 조회
	public HashMap<String, Object> getProjectScore(String employeId, String yyyyMM); 
	public HashMap<String, Object> getProjectScoreAll(String employeId, String yyyyMM); // 조건 기간 까지, 특정 회원 전체 프로젝트 기여도 조회
	public HashMap<String, Object> getProjectScoreDivision(String employeId, String yyyyMM, int divisionCode); // 조건 기간 까지, 특정 회원 전체 프로젝트 기여도 조회
	
	public boolean saveProjectScore(List<ProjectScore> projectScoreList);
	public boolean saveProjectScoreAdmin(List<ProjectScore> projectScoreList); 
	public boolean saveProjectDissent(ProjectScore projectScore);
	
	public boolean insertProjectScore(ProjectScore projectScore);	
	public boolean deleteProjectScore(List<Integer> seqList);
	public boolean updateProjectScore(ProjectScore projectScore);
	
	
	
	//성과 점수조회
	public ResultPerformanceScore getPerformanceScore(String searchId, int authLevel, String yyyyMM);
	public List<ResultPerformanceScore> getPerformanceScoreAll(String employeId, int authLevel, String yyyyMM);
	public boolean insertPerformanceScore(PerformanceScore performanceScore);
	public boolean deletePerformanceScore(List<Integer> seqList);
	public boolean updatePerformanceScore(PerformanceScore performanceScore);
	public boolean savePerformDissent(PerformanceScore performanceScore);
	
	//가치평가 점수조회
	public List<ResultValueScore> getValueScore(String searchId, int authLevel, String yyyyMM);
	public List<ResultValueScore> getValueScoreAll(String employeId, int authLevel, String yyyyMM);
	public boolean insertValueScore(ValueScore valueScoreList);
	public boolean deleteValueScore(List<Integer> seqList);
	public boolean updateValueScore(ValueScore valueceScore);
	
	
	public PerformanceValue getTotalScore(String searchId, int authLevel, String yyyyMM);
	public HashMap<String, Object> getTotalScoreMap(String employeId, int authLevel, String yyyyMM);
	public List<PerformanceValue> getTotalScoreList(String employeId, int authLevel, String yyyyMM);	
	public boolean saveTotalScore(PerformanceValue performanceValue, int confirmNumber);
	public boolean saveTotalScoreCEO(PerformanceValue performanceValue);
	
	
}
