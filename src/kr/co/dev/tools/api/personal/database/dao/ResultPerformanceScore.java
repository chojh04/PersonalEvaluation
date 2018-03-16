
package kr.co.dev.tools.api.personal.database.dao;

import java.sql.Date;
import java.util.List;

public class ResultPerformanceScore {
	private String employeId;
	private String month;
	private String employeName ; 
	private int authLevel;
	private String divisionName;
	private int divisionCode;	
	private String teamName;
	private int teamCode;
	private int score1;
	private int score2;
	private String dissent;
	private String comments1;
	private String comments2;
	private String dissentComments1;
	private String dissentComments2;
	public String getEmployeId() {
		return employeId;
	}
	public void setEmployeId(String employeId) {
		this.employeId = employeId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getEmployeName() {
		return employeName;
	}
	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public int getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(int divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(int teamCode) {
		this.teamCode = teamCode;
	}
	public int getScore1() {
		return score1;
	}
	public void setScore1(int score1) {
		this.score1 = score1;
	}
	public int getScore2() {
		return score2;
	}
	public void setScore2(int score2) {
		this.score2 = score2;
	}
	public String getDissent() {
		return dissent;
	}
	public void setDissent(String dissent) {
		this.dissent = dissent;
	}
	public String getComments1() {
		return comments1;
	}
	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}
	public String getComments2() {
		return comments2;
	}
	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}
	public int getAuthLevel() {
		return authLevel;
	}
	public void setAuthLevel(int authLevel) {
		this.authLevel = authLevel;
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


