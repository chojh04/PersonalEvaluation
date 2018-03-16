package kr.co.dev.tools.api.personal.database.dao;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import kr.co.dev.tools.api.personal.controller.PersonalEvaluationController;
import kr.co.dev.tools.api.personal.service.PersonalEvaluationServiceImpl;

@Repository
public class PersonalEvaluationDaoImpl implements PersonalEvaluationDao{	
	
	private final Logger logger = LoggerFactory.getLogger(PersonalEvaluationServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;
		
	@Resource(name="transactionManager")
	
	protected DataSourceTransactionManager transactionManager;	

	@Override
	public Employe getEmploye(String employeId) {
		// TODO Auto-generated method stub
		logger.info("getEmploye | IN | Param : employeId=>"+employeId);
		
		Employe result = new Employe();
		HashMap<String, Object> param = new HashMap<String, Object>();
		try
		{
			param.put("employeId",employeId);
			result = sqlSession.selectOne("Personal.getEmploye",param);
			if(result!=null){
				logger.info("getEmploye | OUT | Param : result=>"+result.getEmployeName());
			}
		}
		catch (Exception e)
		{		
			logger.error("exception : " + e.getMessage());
		}		
		return result;	
	}
	
	@Override
	public List<Employe> getEmployeList() {
		// TODO Auto-generated method stub
		logger.info("getEmploye | IN | NoParam");
		
		List<Employe> result = new ArrayList<Employe>();		
		try
		{
			result = sqlSession.selectList("Personal.getEmploye");
			if(result!=null){
				logger.info("getEmploye | OUT | Param : result=>"+result.size());
			}
		}
		catch (Exception e)
		{		
			logger.error("exception : " + e.getMessage());
		}		
		return result;	
	}

	@Override
	public boolean insertEmploye(Employe employe) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try
		{
			sqlSession.insert("");
			
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		return result;
	}

	@Override
	public boolean updateEmploye(Employe employe) {
		// TODO Auto-generated method stub
		logger.info("updateEmploye | IN | employe=>"+employe.getEmployeId());
		
		boolean result = false;	
		try
		{
			int resultValue = sqlSession.update("Personal.updateEmploye",employe);
			if(resultValue >= 1) result = true;
			logger.info("getEmploye | OUT | Param : result=>"+result);
		
		}
		catch (Exception e)
		{		
			logger.error("updateEmploye : " + e.getMessage());
		}		
		return result;	
	}

	@Override
	public boolean deleteEmploye(String seq) {
		// TODO Auto-generated method stub
	
		
		return false;
	}

	@Override
	public ProjectScore getProjectScore(String employeId, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getProjectScore | IN | Param : employeId=>"+employeId+"yyyyMM=>"+yyyyMM);
		
		ProjectScore result = new ProjectScore();		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);		
			map.put("month", yyyyMM);
			result = sqlSession.selectOne("Personal.getProjectScore",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		
		logger.info("getProjectScore | OUT | Param : result=>"+result);
				
		return result;
	}
	
	@Override
	public List<ProjectScore> getProjectScoreAll(String employeId, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getProjectScoreAll | IN | Param : employeId=>"+employeId+"yyyyMM=>"+yyyyMM);
		
		List<ProjectScore> result = new ArrayList<ProjectScore>();		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);		
			map.put("month", yyyyMM);
			map.put("searchOption", "all");
		
			result = sqlSession.selectList("Personal.getProjectScore",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		
		logger.info("getProjectScoreAll | OUT | Param : result=>"+result);
				
		return result;
	}
	

	
	
	

	@Override
	public boolean insertProjectScoreList(List<ProjectScore> projectScoreList) {		
		// TODO Auto-generated method stub		
		logger.info("insertProjectScore | IN |");
		logger.debug("Param : projectScoreList.size()=>"+projectScoreList.size());
		
		boolean result = false;
		
		try
		{
			for(ProjectScore projectScore : projectScoreList)
			{
				logger.debug("insertProjectScore() : projectScore.getEmpId"+projectScore.getEmployeId());
				int resultValue = sqlSession.insert("Personal.insertProjectScore",projectScore);
				if(resultValue >= 1) result = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("insertProjectScore | OUT | Param : result=>"+result);

		return result;
	}
	
	@Override
	public boolean insertProjectScore(ProjectScore projectScore) {		
		// TODO Auto-generated method stub		
		logger.info("insertProjectScore | IN |");
		
		boolean result = false;
		
		try
		{
			logger.debug("insertProjectScore() : projectScore.getEmpId"+projectScore.getEmployeId());
			int resultValue = sqlSession.insert("Personal.insertProjectScore",projectScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("insertProjectScore | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updateProjectScore(ProjectScore projectScore) {
		// TODO Auto-generated method stub
		logger.info("updateProjectScoreListDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updateProjectScore",projectScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updateProjectScoreListDAO | OUT | ");
		
		return result;
	}

	@Override
	public boolean deleteProjectScore(HashMap<String, Object> seqMap) {
		// TODO Auto-generated method stub
		logger.info("deleteProjectScoreDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.delete("Personal.deleteProjectScore",seqMap); 
			if(resultValue >= 1) result = true;
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("deleteProjectScoreDAO | OUT | ");
		return result;
	}

	@Override
	public ResultPerformanceScore getPerformanceScore(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getPerformanceScoregetPerformanceScoreByIdDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
		ResultPerformanceScore result = new ResultPerformanceScore();			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("authLevel", authLevel);
			map.put("month", yyyyMM);			
			result = sqlSession.selectOne("Personal.getPerformanceScoreById",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getPerformanceScoregetPerformanceScoreByIdDAO | OUT |");
		return result;
	}
	

	@Override
	public List<ResultPerformanceScore> getPerformanceScoreAll(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getPerformanceScoreAllDAO | IN | Param : employeId=>"+employeId+" yyyyMM=>"+yyyyMM);
		List<ResultPerformanceScore> result = new ArrayList<ResultPerformanceScore>();		
		
		try 
		{
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("employeId",employeId);
			Employe employe = sqlSession.selectOne("Personal.getEmploye",param);

			param.put("authLevel", authLevel);
			param.put("divisionCode", employe.getDivisionCode());
			param.put("teamCode", employe.getTeamCode());
			param.put("month", yyyyMM);		
		
			result = sqlSession.selectList("Personal.getPerformanceScore",param);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getPerformanceScoreAllDAO | OUT | Param : result=>"+result);
				
		return result;
	}


	@Override
	public boolean insertPerformanceScore(PerformanceScore performanceScore) {
		// TODO Auto-generated method stub		
		logger.debug("insertPerformanceScoreDAO | IN |");
		
		boolean result = false;
		
		try
		{
			logger.debug("insertProjectScore() : projectScore.getEmpId"+performanceScore.getEmployeId());
			int resultValue = sqlSession.insert("Personal.insertPerformanceScore",performanceScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.debug("insertPerformanceScoreDAO | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updatePerformanceScore(PerformanceScore performanceScore) {
		// TODO Auto-generated method stub
		logger.info("updatePerformanceScoreDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updatePerformanceScore",performanceScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updatePerformanceScoreDAO | OUT | ");
		
		return result;
	}

	@Override
	public boolean deletePerformanceScore(HashMap<String, Object> seqMap) {
		logger.info("deletePerformanceScoreDAO | IN |");
		boolean result = false;
		// TODO Auto-generated method stub			
		try 
		{
			int resultValue = sqlSession.delete("Personal.deletePerformanceScore",seqMap); 
			if(resultValue >= 1) result = true;
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}			
		logger.info("deletePerformanceScoreDAO | OUT | Param : result=>"+result);	
		return result;
	}
	
	public boolean updatePerformanceDissent(PerformanceScore performanceScore){
		// TODO Auto-generated method stub
		logger.info("updatePerformanceDissentDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updatePerformanceDissent",performanceScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updatePerformanceDissentDAO | OUT | ");
		
		return result;
	}
	

	@Override
	public List<ResultValueScore> getValueScore(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getValueScoreByIdDAO | IN | Param : employeId=>"+employeId+" yyyyMM=>"+yyyyMM);
		List<ResultValueScore> result = new ArrayList<ResultValueScore>();		
		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("confirmNumber", authLevel);
			map.put("month", yyyyMM);		
		
			result = sqlSession.selectList("Personal.getValueScoreById",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getValueScoreByIdDAO | OUT | Param : result=>"+result);
				
		return result;
	}
	

	@Override
	public List<ResultValueScore> getValueScoreAll(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getValueScoreAllDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
		List<ResultValueScore> result = new ArrayList<ResultValueScore>();			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("authLevel", authLevel);
			map.put("month", yyyyMM);			
			result = sqlSession.selectList("Personal.getValueScoreAll",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getValueScoreAllDAO | OUT | result.size()=>"+result.size());
		return result;
	}

	

	@Override
	public boolean insertValueScore(ValueScore valueScore) {
		// TODO Auto-generated method stub		
		logger.info("insertValueScoreDAO | IN | ");	
		
		boolean result = false;
		
		try
		{
			//logger.debug("insertProjectScore() : projectScore.getEmpId"+valueScore.getEmployeId());
			int resultValue = sqlSession.insert("Personal.insertValueScore",valueScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("insertValueScoreDAO | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updateValueScore(ValueScore valueScore) {
		// TODO Auto-generated method stub
		logger.info("updateValueScoreDAO | IN |");
		boolean result = false;
	
		try 
		{
			int resultValue = sqlSession.delete("Personal.updateValueScore",valueScore); 
			if(resultValue >= 1) result = true;
		}
		catch (Exception e) 
		{	
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
	
		logger.info("updateValueScoreDAO | OUT |");
		return result;
		
	}

	@Override
	public boolean deleteValueScore(HashMap<String, Object> seqMap) {
		// TODO Auto-generated method stub
		logger.info("deleteValueScoreDAO | IN |");
		boolean result = false;

		try 
		{
			int resultValue = sqlSession.delete("Personal.deleteValueScore",seqMap); 
			if(resultValue >= 1) result = true;
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		
		logger.info("deleteValueScoreDAO | OUT |");
		return result;
	}

	@Override
	public int getPerformanceScoreCount(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getPerformanceScoreCountDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
		int result = 0;			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("confirmNumber", authLevel);
			map.put("month", yyyyMM);			
			result = sqlSession.selectOne("Personal.getPerformanceScoreCount",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getPerformanceScoreCountDAO | OUT | ");
		return result;
	}

	@Override
	public int getValueScoreCount(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
				logger.info("getValueeScoreCountDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
				int result = 0;			
				try 
				{
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("employeId", employeId);
					map.put("confirmNumber", authLevel);
					map.put("month", yyyyMM);			
					result = sqlSession.selectOne("Personal.getValueScoreCount",map);			
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					logger.error("exception : " + e.getMessage());
				}
				logger.info("getValueeScoreCountDAO | OUT | ");
				return result;
	}


	@Override
	public ProjectScore getProjectScoreHistory(String employeId, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getProjectScore | IN | Param : employeId=>"+employeId+"yyyyMM=>"+yyyyMM);
		
		ProjectScore result = new ProjectScore();		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);		
			map.put("month", yyyyMM);
			result = sqlSession.selectOne("Personal.getProjectScoreHistory",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		
		logger.info("getProjectScore | OUT | Param : result=>"+result);
				
		return result;
	}
	
	@Override
	public List<ProjectScore> getProjectScoreAllHistory(String employeId, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getProjectScoreAll | IN | Param : employeId=>"+employeId+"yyyyMM=>"+yyyyMM);
		
		List<ProjectScore> result = new ArrayList<ProjectScore>();		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);		
			map.put("month", yyyyMM);
			map.put("searchOption", "all");
		
			result = sqlSession.selectList("Personal.getProjectScoreHistory",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		
		logger.info("getProjectScoreAll | OUT | Param : result=>"+result);
				
		return result;
	}
	
	@Override
	public boolean insertProjectScoreHistory(ProjectScore projectScore) {		
		// TODO Auto-generated method stub		
		logger.info("insertProjectScore | IN |");
		
		boolean result = false;
		
		try
		{
			logger.debug("insertProjectScore() : projectScore.getEmpId"+projectScore.getEmployeId());
			int resultValue = sqlSession.insert("Personal.insertProjectScoreHistory",projectScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("insertProjectScore | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updateProjectScoreHistory(ProjectScore projectScore) {
		// TODO Auto-generated method stub
		logger.info("updateProjectScoreListHistoryDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updateProjectScoreHistory",projectScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updateProjectScoreListHistoryDAO | OUT | ");
		
		return result;
	}

	@Override
	public boolean updateProjectComments(ProjectScore projectScore) {
		// TODO Auto-generated method stub
		logger.info("updateProjectScoreListDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updateProjectScoreComments",projectScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updateProjectScoreListDAO | OUT | ");
		
		return result;
	}

	
	@Override
	public boolean insertValueScoreHistory(ValueScore valueScore) {
		// TODO Auto-generated method stub		
		logger.info("insertValueScoreHistoryDAO | IN | ");	
		
		boolean result = false;
		
		try
		{
			//logger.debug("insertProjectScore() : projectScore.getEmpId"+valueScore.getEmployeId());
			int resultValue = sqlSession.insert("Personal.insertValueScoreHistory",valueScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("insertValueScoreHistoryDAO | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updateValueScoreHistory(ValueScore valueScore) {
		// TODO Auto-generated method stub
		logger.info("updateValueScoreHistoryDAO | IN |");
		boolean result = false;
	
		try 
		{
			int resultValue = sqlSession.delete("Personal.updateValueScoreHistory",valueScore); 
			if(resultValue >= 1) result = true;
		}
		catch (Exception e) 
		{	
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
	
		logger.info("updateValueScoreHistoryDAO | OUT |");
		return result;
		
	}


	@Override
	public boolean insertPerformanceScoreHistory(PerformanceScore performanceScore) {
		// TODO Auto-generated method stub		
		logger.debug("insertPerformanceScoreHistoryDAO | IN |");
		
		boolean result = false;
		
		try
		{
			int resultValue = sqlSession.insert("Personal.insertPerformanceScoreHistory",performanceScore);
			if(resultValue >= 1) result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.debug("insertPerformanceScoreHistoryDAO | OUT | Param : result=>"+result);

		return result;
	}

	@Override
	public boolean updatePerformanceScoreHistory(PerformanceScore performanceScore) {
		// TODO Auto-generated method stub
		logger.info("updatePerformanceScoreHistoryDAO | IN | ");
		boolean result = false;
		
		try 
		{
			int resultValue = sqlSession.update("Personal.updatePerformanceScoreHistory",performanceScore);
			if(resultValue >= 1) result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("updatePerformanceScoreHistoryDAO | OUT | ");
		
		return result;
	}

	@Override
	public ResultPerformanceScore getPerformanceScoreHistory(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getPerformanceScoregetPerformanceScoreByIdDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
		ResultPerformanceScore result = new ResultPerformanceScore();			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("authLevel", authLevel);
			map.put("month", yyyyMM);			
			result = sqlSession.selectOne("Personal.getPerformanceScoreByIdHistory",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getPerformanceScoregetPerformanceScoreByIdDAO | OUT |");
		return result;
	}
	

	@Override
	public List<ResultPerformanceScore> getPerformanceScoreHistoryAll(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getPerformanceScoreAllDAO | IN | Param : employeId=>"+employeId+" yyyyMM=>"+yyyyMM);
		List<ResultPerformanceScore> result = new ArrayList<ResultPerformanceScore>();		
		
		try 
		{
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("employeId",employeId);
			Employe employe = sqlSession.selectOne("Personal.getEmploye",param);

			param.put("authLevel", authLevel);
			param.put("divisionCode", employe.getDivisionCode());
			param.put("teamCode", employe.getTeamCode());
			param.put("month", yyyyMM);		
		
			result = sqlSession.selectList("Personal.getPerformanceScoreHistory",param);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getPerformanceScoreAllDAO | OUT | Param : result=>"+result);
				
		return result;
	}


	@Override
	public List<ResultValueScore> getValueScoreHistory(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getValueScoreByIdDAO | IN | Param : employeId=>"+employeId+" yyyyMM=>"+yyyyMM);
		List<ResultValueScore> result = new ArrayList<ResultValueScore>();		
		
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("confirmNumber", authLevel);
			map.put("month", yyyyMM);		
		
			result = sqlSession.selectList("Personal.getValueScoreByIdHistory",map);	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getValueScoreByIdDAO | OUT | Param : result=>"+result);
				
		return result;
	}
	

	@Override
	public List<ResultValueScore> getValueScoreHistoryAll(String employeId, int authLevel, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getValueScoreAllDAO | IN | employeId=>"+employeId+" : authLevel=>"+authLevel+" : yyyyMM=>"+yyyyMM);
		List<ResultValueScore> result = new ArrayList<ResultValueScore>();			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);
			map.put("authLevel", authLevel);
			map.put("month", yyyyMM);			
			result = sqlSession.selectList("Personal.getValueScoreHistoryAll",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getValueScoreAllDAO | OUT | result.size()=>"+result.size());
		return result;
	}

	@Override
	public int getProjectScoreCount(String employeId, String yyyyMM) {
		// TODO Auto-generated method stub
		logger.info("getValueScoreAllDAO | IN | employeId=>"+employeId+" :  yyyyMM=>"+yyyyMM);
		int result = 0;			
		try 
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("employeId", employeId);			
			map.put("month", yyyyMM);			
			result = sqlSession.selectOne("Personal.getProjectScoreCount",map);			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
		}
		logger.info("getValueScoreAllDAO | OUT | result=>"+result);
		return result;
	}


}
