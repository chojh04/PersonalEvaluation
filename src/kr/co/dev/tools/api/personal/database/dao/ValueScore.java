package kr.co.dev.tools.api.personal.database.dao;

public class ValueScore {
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
	private String comments;
	private int confirmNumber;
	
		
	public ValueScore(){}
	
	public void setValueScore(String month, String employeId, int score1, int score2, int score3, int score4, int score5,
			int score6, int score7, int score8, String comments, int confirmNumber) {		
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	
}
