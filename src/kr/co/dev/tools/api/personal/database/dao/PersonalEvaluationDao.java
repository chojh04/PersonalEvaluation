package kr.co.dev.tools.api.personal.database.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public interface PersonalEvaluationDao {
		
	/*
	 * CRUD OF EMPLOYEE
	 */
	public Employe getEmploye(String employeId);	
	public List<Employe> getEmployeList();
	public boolean insertEmploye(Employe employe);	
	public boolean updateEmploye(Employe employe);	
	public boolean deleteEmploye(String employeeId);
	
	
	/*
	 * CRUD OF PROJECT SCORE 
	 */
	public ProjectScore getProjectScore(String employeeId, String yyyyMM);
	public int getProjectScoreCount(String employeeId, String yyyyMM);
	public List<ProjectScore> getProjectScoreAll(String employeeId, String yyyyMM);
	public boolean insertProjectScoreList(List<ProjectScore> projectScoreList);
	public boolean insertProjectScore(ProjectScore projectScore);	
	public boolean updateProjectScore(ProjectScore projectScore);	
	public boolean updateProjectComments(ProjectScore projectScore);
	public boolean deleteProjectScore(HashMap<String, Object> seqMap);	
	
	
	
	public ProjectScore getProjectScoreHistory(String employeeId, String yyyyMM);
	public List<ProjectScore> getProjectScoreAllHistory(String employeeId, String yyyyMM);	
	public boolean insertProjectScoreHistory(ProjectScore projectScore);	
	public boolean updateProjectScoreHistory(ProjectScore projectScore);	
	
	
	
	/*
	 * CRUD OF VALUE SCORE 
	 */
	public List<ResultValueScore> getValueScore(String employeId, int authLevel, String yyyyMM);
	public List<ResultValueScore> getValueScoreHistory(String employeId, int authLevel, String yyyyMM);
	public List<ResultValueScore> getValueScoreAll(String employeId, int authLevel, String yyyyMM);
	public List<ResultValueScore> getValueScoreHistoryAll(String employeId, int authLevel, String yyyyMM);
	public boolean insertValueScore(ValueScore valueScore);	
	public boolean updateValueScore(ValueScore valueScore);	
	public boolean deleteValueScore(HashMap<String, Object> seqMap);
	public boolean insertValueScoreHistory(ValueScore valueScore);
	public boolean updateValueScoreHistory(ValueScore valueScore);	
	
	
	/*
	 * CRUD OF PERFORMANCE SCORE 
	 */
	public ResultPerformanceScore getPerformanceScore(String employeId, int authLevel, String yyyyMM);
	public ResultPerformanceScore getPerformanceScoreHistory(String employeId, int authLevel, String yyyyMM);
	public List<ResultPerformanceScore> getPerformanceScoreAll(String employeId, int authLevel, String yyyyMM);	
	public List<ResultPerformanceScore> getPerformanceScoreHistoryAll(String employeId, int authLevel, String yyyyMM);
	public boolean insertPerformanceScore(PerformanceScore performanceScore);	
	public boolean updatePerformanceScore(PerformanceScore performanceScore);	
	public boolean updatePerformanceDissent(PerformanceScore performanceScore);	
	public boolean deletePerformanceScore(HashMap<String, Object> seqMap);
	public boolean insertPerformanceScoreHistory(PerformanceScore performanceScore);	
	public boolean updatePerformanceScoreHistory(PerformanceScore performanceScore);	
	

	
	public int getPerformanceScoreCount(String employeId, int authLevel, String yyyyMM);
	public int getValueScoreCount(String employeId, int authLevel, String yyyyMM);
	
	
	
	
	
}

