<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%!

	public String getBeforeMonth(String yyyymmdd){
		String yyyymm;
		
		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6, 8));
	
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm-1, dd);
		
		cal.add(Calendar.MONTH, -1);
		
		yyyymm = new SimpleDateFormat("yyyyMM").format(cal.getTime());
		
		return yyyymm;
	}

%>


