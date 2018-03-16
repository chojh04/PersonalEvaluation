package kr.co.dev.tools.api.personal.database.dao;

import java.util.Date;

public class PerformanceScore {
	
	private String month;
	private String employeId;
	private int	score;
	private String dissent;
	private String comments;
	private String dissentComments;
	private int confirmNumber;
		
	public PerformanceScore(){}
	
	public void setPerformanceScore( String month, String employeId, String dissent, String comments, int confirmNumber) {
	
		this.month = month;
		this.employeId = employeId;	
		this.dissent = dissent;
		this.comments = comments;
		this.confirmNumber = confirmNumber;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getEmployeId() {
		return employeId;
	}
	public void setEmployeId(String employeId) {
		this.employeId = employeId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public String getDissent() {
		return dissent;
	}
	public void setDissent(String dissent) {
		this.dissent = dissent;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getConfirmNumber() {
		return confirmNumber;
	}

	public void setConfirmNumber(int confirmNumber) {
		this.confirmNumber = confirmNumber;
	}

	public String getDissentComments() {
		return dissentComments;
	}

	public void setDissentComments(String dissentComments) {
		this.dissentComments = dissentComments;
	}
	  
}
