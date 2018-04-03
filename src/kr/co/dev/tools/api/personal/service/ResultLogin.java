package kr.co.dev.tools.api.personal.service;


public class ResultLogin {

	private int resultStatus; // 0 : 성공, 1 : 없는 아이디, 2 : 패스워드 불일치 , -1 : 오류 
	
	private String employeId;
	private String employeName;
	private int teamCode;
	private int divisionCode;
	private int authLevel;
	
	public ResultLogin()
	{
		resultStatus = -1;
	}
	
	public void setEmployeeInfo(String employeId, String employeName, 
			int teamCode, int divisionCode, int authLevel)
	{
		this.employeId = employeId;
		this.employeName = employeName;
		this.teamCode = teamCode;
		this.divisionCode = divisionCode;
		this.authLevel = authLevel;
	}

	public int getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(int resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getEmployeId() {
		return employeId;
	}

	public String getEmployeName() {
		return employeName;
	}

	public int getTeamCode() {
		return teamCode;
	}

	public int getDivisionCode() {
		return divisionCode;
	}

	public int getAuthLevel() {
		return authLevel;
	}

	@Override
	public String toString() {
		return String
				.format("ResultLogin [resultStatus=%s, employeeId=%s, name=%s, teamCode=%s, divisionCode=%s, authorLevel=%s]",
						resultStatus, employeId, employeName, teamCode, divisionCode,
						authLevel);
	}
	
	
	
	
	
	
}

