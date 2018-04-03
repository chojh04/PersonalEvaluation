package kr.co.dev.tools.api.personal.service;

import java.util.Date;
import java.util.List;

public class PerformanceValue{	
	/*Employe*/
	private String employeId;
	private String employeName; 
	private String divisionName;	
	private String teamName;
	private int authLevel;
	
	/*Perfomance*/
	private int perfomScore1;
	private int perfomScore2;
	private String dissent;
	private String performComments1;
	private String performComments2;
	private String dissentComments1;
	private String dissentComments2;
	private String month;	
	
	/*Value*/
	private List<Boolean> valueScore1;
	private List<Boolean> valueScore2;
	private String valueComments1;	
	private String valueComments2;	
	
	private double valueRatio;
	private double performRatio;
	
	
	public void setEmployeValues(String employeId, String employeName, String divisionName, String teamName, int authLevel){
		this.employeId 	  = employeId;
		this.employeName  = employeName;
		this.divisionName = divisionName;
		this.teamName	  = teamName;
		this.authLevel = authLevel;
	}
	
	public void setPerfomValues( int perfomScore1,int perfomScore2, String month){		
		this.perfomScore1 = perfomScore1;
		this.perfomScore2 = perfomScore2;		
		this.month	 = month;
	}
	
	public void setRatio(double performRatio, double valueRatio){
		this.performRatio = performRatio;
		this.valueRatio = valueRatio;
	}	
		
	public String getEmployeId() {
		return employeId;
	}

	public String getEmployeName() {
		return employeName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getMonth() {
		return month;
	}
	
	public int getPerfomScore1() {
		return perfomScore1;
	}
	
	public int getPerfomScore2() {
		return perfomScore2;
	}
	
	public String getValueComments1() {
		return valueComments1;
	}

	public void setValueComments1(String valueComments1) {
		this.valueComments1 = valueComments1;
	}

	public String getValueComments2() {
		return valueComments2;
	}

	public void setValueComments2(String valueComments2) {
		this.valueComments2 = valueComments2;
	}

	public String getDissent() {
		return dissent;
	}

	public String getPerformComments1() {
		return performComments1;
	}

	public String getPerformComments2() {
		return performComments2;
	}

	public void setDissent(String dissent) {
		this.dissent = dissent;
	}

	public void setPerformComments1(String performComments1) {
		this.performComments1 = performComments1;
	}

	public void setPerformComments2(String performComments2) {
		this.performComments2 = performComments2;
	}

	public int getAuthLevel() {
		return authLevel;
	}

	public double getValueRatio() {
		return valueRatio;
	}

	public double getPerformRatio() {
		return performRatio;
	}

	public List<Boolean> getValueScore1() {
		return valueScore1;
	}

	public void setValueScore1(List<Boolean> valueScore1) {
		this.valueScore1 = valueScore1;
	}

	public List<Boolean> getValueScore2() {
		return valueScore2;
	}

	public void setValueScore2(List<Boolean> valueScore2) {
		this.valueScore2 = valueScore2;
	}

	public String getDissentComments1() {
		return dissentComments1;
	}

	public void setDissentComments1(String dissentComments1) {
		this.dissentComments1 = dissentComments1;
	}

	public String getDissentComments2() {
		return dissentComments2;
	}

	public void setDissentComments2(String dissentComments2) {
		this.dissentComments2 = dissentComments2;
	}
	
}
