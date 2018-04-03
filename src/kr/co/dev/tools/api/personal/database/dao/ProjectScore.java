package kr.co.dev.tools.api.personal.database.dao;

import java.util.List;

public class ProjectScore {	
	private int seq;
	private String month;
	private String employeId;
	private int score1;
	private int score2;
	private int score3;
	private int score4;
	private int score5;
	private int score6;
	private int score7;
	private int score8;
	private String dissent;
	private String comments1;
	private String comments2;
	private int status;
	private int confirmNumber;

	public ProjectScore(){}
	
	public ProjectScore(String month, String employeId, int score1, int score2, int score3, int score4, int score5,
			int score6, int score7, int score8, String dissent, String comments1, String comments2, int status, int confirmNumber) {
		super();
		this.month = month;
		this.employeId = employeId;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
		this.score4 = score4;
		this.score5 = score5;
		this.score6 = score6;
		this.score7 = score7;
		this.score8 = score8;
		this.dissent = dissent;
		this.comments1 = comments1;
		this.comments2 = comments2;
		this.status = status;
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
	public int getScore3() {
		return score3;
	}
	public void setScore3(int score3) {
		this.score3 = score3;
	}
	public int getScore4() {
		return score4;
	}
	public void setScore4(int score4) {
		this.score4 = score4;
	}
	public int getScore5() {
		return score5;
	}
	public void setScore5(int score5) {
		this.score5 = score5;
	}
	public int getScore6() {
		return score6;
	}
	public void setScore6(int score6) {
		this.score6 = score6;
	}
	public int getScore7() {
		return score7;
	}
	public void setScore7(int score7) {
		this.score7 = score7;
	}	
	public int getScore8() {
		return score8;
	}
	public void setScore8(int score8) {
		this.score8 = score8;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getConfirmNumber() {
		return confirmNumber;
	}

	public void setConfirmNumber(int confirmNumber) {
		this.confirmNumber = confirmNumber;
	}
	
	
	
}
