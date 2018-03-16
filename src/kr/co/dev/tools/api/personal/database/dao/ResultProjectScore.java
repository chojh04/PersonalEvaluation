
package kr.co.dev.tools.api.personal.database.dao;

import java.util.List;

public class ResultProjectScore {
	
	private String employeId;
	private String employeName;
	private String divisionName;
	private int divisionCode;
	private int position;	
	private String teamName;
	private int teamCode;
	private int employeLevel;
	
	private int seq;
	private String month;
	private int score1;	
	private int score2;
	private int score3;
	private int score4;
	private int score5;
	private int score6;
	private int score7;
	private int score8;
	private int status;
	private String dissent;
	private String comments1;
	private String comments2;
	private int confirmNumber;
	
	
	public void setEmploye(String employeId, String employeName, String divisionName, int divisionCode, int position, String teamName, int teamCode, int employeLevel){
		this.employeId = employeId;
		this.employeName = employeName;
		this.divisionName = divisionName;
		this.divisionCode = divisionCode;
		this.position = position;
		this.teamName = teamName;
		this.teamCode = teamCode;
		this.employeLevel = employeLevel;
	}
	
	public void setProjectScore(int seq, String month, int score1, int score2, int score3, int score4, int score5, int score6, int score7, int score8, int status, String dissent, String comments1, String comments2, int confirmNumber ){
		this.seq = seq;
		this.month = month;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
		this.score4 = score4;
		this.score5 = score5;
		this.score6 = score6;
		this.score7 = score7;
		this.score8 = score8;
		this.status = status;
		this.dissent = dissent;
		this.comments1 = comments1;
		this.comments2 = comments2;
		this.confirmNumber = confirmNumber;
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

	public int getDivisionCode() {
		return divisionCode;
	}

	public int getPosition() {
		return position;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getTeamCode() {
		return teamCode;
	}

	public int getSeq() {
		return seq;
	}

	public String getMonth() {
		return month;
	}
	
	public int getScore1() {
		return score1;
	}

	public int getScore2() {
		return score2;
	}

	public int getScore3() {
		return score3;
	}

	public int getScore4() {
		return score4;
	}

	public int getScore5() {
		return score5;
	}

	public int getScore6() {
		return score6;
	}

	public int getScore7() {
		return score7;
	}
	
	public int getScore8() {
		return score8;
	}

	public int getStatus() {
		return status;
	}

	public String getDissent() {
		return dissent;
	}

	public String getComments1() {
		return comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public int getConfirmNumber() {
		return confirmNumber;
	}

	public int getEmployeLevel() {
		return employeLevel;
	}
	
	
	
}


