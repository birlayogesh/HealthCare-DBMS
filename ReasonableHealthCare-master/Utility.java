import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import oracle.sql.TIMESTAMP;


public class Utility {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static Connection dbConnection =  DBConnection.createConnection();
	
	public static int authenticateUser(String userName, String pass,Connection dbConnection){
		ResultSet rs = null;
		try {
			Statement statement = dbConnection.createStatement();
			rs = statement.executeQuery("Select designation from Login where loginId ="+userName+"and password='"+pass+"'");
			if(rs.next()){
				String desig = rs.getString("designation");
				if(desig.equals("Student"))
					return 1;
				if(desig.equals("Doctor"))
					return 2;
				if(desig.equals("Receptionist"))
					return 3;
				if(desig.equals("Nurse"))
					return 4;
				if(desig.equals("Admin"))
					return 5;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	public static void cancelAppointment(String userId) throws NumberFormatException, IOException{
		HashMap<Integer,Integer> hm = viewAppointment(userId);
		System.out.println("Select Appointment to cancel :: ");
		int id = Integer.parseInt(br.readLine());
		try {
			Statement stmt = dbConnection.createStatement();
			String query = "delete from appointment where appointmentid = "+hm.get(id);
			stmt.executeQuery(query);
			dbConnection.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Your appointment has been cancelled..\n");
	}
	
	
	public static HashMap<Integer,Integer> viewAppointment(String userId){
		System.out.println("Your appointments are ::  ");
		HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
		try {
			Statement stmt = dbConnection.createStatement();
			//String query = "Select * from appointment where userId="+userId;
			String query1 = "select appointmentid,AppointmentDate,ENAME,StartTime" 
				     +" from   Student,Doctor,Appointment,Employee" 
				     +" where  Student.studentId = Appointment.StudentId" 
				     +" and    Appointment.EmployeeId =  Doctor.DoctorId" 
				     +" and    Doctor.DoctorId = Employee.EmployeeId"
				     +" and    Appointment.studentId="+userId +" order by starttime";
			
			ResultSet rs = stmt.executeQuery(query1);
			
			int count=1;
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
			while(rs.next()){
				Timestamp ts = rs.getTimestamp("startTime");
				Calendar c = Calendar.getInstance();
				c.set(ts.getYear(), ts.getMonth(), ts.getDate(), ts.getHours(), ts.getMinutes());
				String appointmentDetails = String.valueOf(count) +"  "+rs.getString("ename") +"   "+df.format(c.getTime());
				hm.put(count, rs.getInt("appointmentId"));
				System.out.println(appointmentDetails);
				count++;
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		return hm;
		
	}
	
	

	
	public static void makeAppointment(String userId) throws IOException, SQLException{
		
		System.out.println("1. Search the doctor by Specialization ");
		System.out.println("2. Search the doctor by Name ");
		System.out.println("3. Search by Reason for visit ");
		System.out.println("4. Go Back");
		
		int choice = Integer.parseInt(br.readLine());
		String doctorId = null;
		if(choice == 4)
			return;
		switch(choice){
		case 1:
			doctorId = searchDoctorBySpecialization();
			break;
		case 2:
			doctorId = searchDoctorByName();
			break;
		case 3:
			doctorId = searchDoctorByReason();
		}	
		HashMap<Integer,String> hm = new HashMap<Integer,String>();
		Date d = displayAvailability(doctorId,hm);
		doBooking(hm,doctorId,userId,d);
	}
	
	
	public static String searchDoctorByReason() throws NumberFormatException, IOException{
		String reason[] = {"Pain","Acne","Allergy","Ankle Problem","Arm Problem","ASTHMA PROBLEM ","COLD/FLU/SINUS SYMPTOMS","EYE PROBLEM","Heart Problem","Leg Pain","Physical","Vaccination"};
		HashMap<Integer,String> hm = new HashMap<Integer,String>();
		for(int i=1;i<=reason.length;i++){
			hm.put(i, reason[i-1]);
			System.out.println(i +"    "+reason[i-1]);
		}
		
		System.out.println("Select a reason for you visit ::  ");
		int choice = Integer.parseInt(br.readLine());
		String reasonOfVisit = hm.get(choice);
		String specialization = null;
		if(reasonOfVisit.equalsIgnoreCase("Pain") || reasonOfVisit.equalsIgnoreCase("Acne") ||  reasonOfVisit.equalsIgnoreCase("COLD/FLU/SINUS SYMPTOMS") || reasonOfVisit.equalsIgnoreCase("Physical") || reasonOfVisit.equalsIgnoreCase("Vaccination")){
			specialization = "General Physicain";
		}else if(reasonOfVisit.equalsIgnoreCase("Allergy")){
			specialization = "Alergist";
		}else if(reasonOfVisit.equalsIgnoreCase("Ankle Problem") || reasonOfVisit.equalsIgnoreCase("Arm Problem") || reasonOfVisit.equalsIgnoreCase("Leg Pain")){
			specialization = "bones";
		}else if(reasonOfVisit.equalsIgnoreCase("EYE PROBLEM")){
			specialization = "Eye Specialist";
		}else if(reasonOfVisit.equalsIgnoreCase("Heart Problem")){
			specialization = "Cardiologist";
		}
		
		try{
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("Select employeeID,ename,specialization from doctor,employee where employee.employeeid=doctor.doctorId and doctor.specialization='"+specialization+"'");
			int num=1;
			System.out.println("Sr.No.          Doctor         Specialization");
			while(rs.next()){
				hm.put(num, rs.getString("employeeId"));
				String doc = rs.getString("ename");
				System.out.println(num+"     "+doc+"    "+rs.getString("specialization"));
				num++;
			}
		}catch (SQLException e)	{
			e.printStackTrace();
		}
			
		System.out.println("Select Doctor :: ");
		
		
		String docID = hm.get(Integer.parseInt(br.readLine())); 
		
		return docID;
		
	}
	
	
	public static String searchDoctorByName() throws IOException{
		System.out.println("Enter Doctor's Name :: ");
		String name = br.readLine();
		HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
		int count =1;
		try {
			Statement stmt = dbConnection.createStatement();
			String query = "Select * from doctor,employee where doctor.doctorid = employee.employeeid and ename like '%"+name+"%'";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Sr. No.      Name");
			while(rs.next()){
				hm.put(count, rs.getInt("employeeId"));
				System.out.println(count +"  "+rs.getString("ename"));
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Select a doctor :: ");
		int id = Integer.parseInt(br.readLine());
		return String.valueOf(hm.get(id));
		
		
	}
	
	// Function to confirm booking by adding a tuple in the appointment table.
	public static void doBooking(HashMap<Integer,String> hm,String doctorId,String userId,Date d) throws NumberFormatException, IOException, SQLException{
		System.out.println("Select a time slot :: ");
		int choice = Integer.parseInt(br.readLine());
		String timeSlot = (String) hm.get(choice);
		String[] temp = timeSlot.split(":");
		
		d.setHours(Integer.parseInt(temp[0]));
		d.setMinutes(Integer.parseInt(temp[1]));
		
		
		long dateLong = d.getTime();
		java.sql.Date appointmentDate = new java.sql.Date(dateLong);
		java.sql.Time sqlTime = new java.sql.Time(dateLong);
	    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(dateLong);
	      
	   // int isSuccess = performCreditChecking();
	    
	    //Perform Credit card checks
	    System.out.println("Enter Name on Card :: ");
		String name = br.readLine();
		
		System.out.println("Enter Card Type :: ");
		String cardType = br.readLine();
		
		System.out.println("Whether credit/Debit ::");
		String CD = br.readLine();
		
		System.out.println("Enter Card Number :: ");
		String cardNumber = br.readLine();
		
		System.out.println("Enter expiration date ::");
		String expirationDate = br.readLine();
		
		System.out.println("Enter billing address :: ");
		String billing = br.readLine();
		
		String paymentInformation = cardType +","+CD+","+expirationDate +","+cardNumber;
	    
		double charges[] = chargeInsurance(userId,doctorId);
		boolean isSuccess = performCreditChecking();
		
		System.out.println(Arrays.toString(charges));
		  
		if(isSuccess){
			Statement stmt = dbConnection.createStatement();
			String query = "insert into appointment(appointmentId,studentId,employeeid,appointmentdate,starttime,studentpaid,insurancepaid,purpose,notes,billing,payinfo) values (AppointmentID.nextval,"+userId+","+doctorId+",TO_DATE('"+appointmentDate+"','YYYY-MM-DD'),TO_TIMESTAMP('"+sqlTimestamp+"','YYYY-MM-DD HH24:MI:SS.FF9'),"+charges[0]+","+charges[1]+",'','','"+billing+"','"+paymentInformation+"')";
			stmt.executeQuery(query);
			query ="update has set amountPaid = "+charges[2]+"where studentid="+userId;
			stmt.executeUpdate(query);
			dbConnection.commit();
			System.out.println("Your appointment has been booked..\n");
		}else{
			System.out.println("Transaction Failure. Please try again.");
		}
		
		
	}
	
	
	public static double[] chargeInsurance(String userId,String doctorId){
		
		double studentPaid=0;
		double insurancePaid=0;
		int charges=0,maxDeduct=0,amountPaid=0;
		try {
			Statement stmt = dbConnection.createStatement();
			
			// Fetch Doctor charges.
			String query = "select charges from employee,doctor where employee.employeeid = doctor.doctorid and employee.employeeid ="+doctorId;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
				charges = rs.getInt("charges");
				
			
			// Get deductible for insurance.
			query = "select deductibleamount from insurance,student,has where student.studentid = has.studentid and has.insuranceid = insurance.insuranceid and student.studentid ="+userId;
			rs = stmt.executeQuery(query);
			while(rs.next())
				maxDeduct = rs.getInt("deductibleamount");
			
			//Get the amount which student has paid
			query = "select amountpaid from has,student where student.studentid = has.studentid and student.studentid ="+userId;
			rs = stmt.executeQuery(query);
			while(rs.next())
				amountPaid = rs.getInt("amountPaid");
			
			if(amountPaid >= maxDeduct){
				studentPaid=0;
				insurancePaid = charges;
			}else{
				int amountRemaining = maxDeduct - amountPaid;
				if(amountRemaining >= charges){
					studentPaid = 0.2 * charges;
					insurancePaid = charges - studentPaid;
				}else{
					
					studentPaid = 0.2 * amountRemaining;
					insurancePaid = amountRemaining - studentPaid + charges - amountRemaining;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		double arr[] = new double[3];
		arr[0]=studentPaid;
		arr[1]=insurancePaid;
		arr[2]=amountPaid+studentPaid;
		return arr;
	}
	
	public static boolean performCreditChecking() throws IOException{
		return true;
	}
	
	public static Date displayAvailability(String doctorId,HashMap<Integer,String> hm) throws SQLException, IOException{
		System.out.println("Fetching availability.. Please wait...");
		Statement statement = dbConnection.createStatement();
		LinkedList<String> ls = getSlots();
		//HashMap<Integer,String> hm = new HashMap<Integer,String>();
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		
		Date d=null;
		String str = null;
		
		do{
			System.out.println("Enter Date (MM-DD-YYYY) :: ");
			str = br.readLine();
			try {
				d = df.parse(str);
				break;
			} catch (ParseException e) {
				System.out.println("Format not supported \n Please Enter date in mentioned format..");
			}
		}while(str.equalsIgnoreCase("-1"));
		
		java.sql.Date appointmentDate = new java.sql.Date(d.getTime());
	
		String query = 	"select * from appointment where employeeId = '"+doctorId+"' and appointmentdate=TO_DATE('"+appointmentDate+"','YYYY-MM-DD')";
		ResultSet res = statement.executeQuery(query);
		while(res.next()){
			TIMESTAMP t = new TIMESTAMP(res.getTimestamp("starttime"));
			String minutes = null;
			Timestamp tt = t.timestampValue();
			int hrs = tt.getHours();
			int min = tt.getMinutes();
			if(min == 0)
				minutes = String.valueOf(min) +"0";
			else
				minutes = String.valueOf(min);
			String temp = String.valueOf(hrs)+":"+minutes;
			ls.remove(temp);
			
		}
		
		System.out.println("Availabale Times are as follows :: ");
		Iterator i = ls.iterator();
		int num = 1;
		System.out.println("SR.No.        Available Time");
		
		while(i.hasNext()){
			String value = (String)i.next();
			System.out.println(num+"        "+value);
			hm.put(num, value);
			num++;
		}
		return d;
	}
	
	
	public static LinkedList<String> getSlots(){
		LinkedList<String> ls = new LinkedList<String>();
		int start = 7;
		int end = 12;    //8PM
		int slots = (end-start)*3-1;
		int slotLength = 20;
		int startwith = 0;
		String minutes = null;
		for(int i=0;i<slots;i++){
			if(startwith%60 == 0){
				start++;
				minutes = String.valueOf(startwith%60) +"0";
			}else	
				minutes = String.valueOf(startwith%60);
			String str = String.valueOf(start) +":"+String.valueOf(minutes);
			ls.add(str);
			startwith = startwith+slotLength;
		}
		return ls;
	}
	
	
	
	
	public static String searchDoctorBySpecialization() throws IOException, SQLException{
		Statement stmt  = dbConnection.createStatement();
		ResultSet rs = stmt.executeQuery("Select distinct specialization from doctor");
		
		
		HashMap<Integer,String> hm = new HashMap<Integer,String>();
		int num=1;
		System.out.println("Sr.No.          Specialization");
		while(rs.next()){
			hm.put(num, rs.getString("Specialization"));
			String spec = rs.getString("specialization");
			System.out.println(num+"    "+spec);
			num++;
		}
		
		
		System.out.println("Select Specialization :: ");
		int choice = Integer.parseInt(br.readLine());
		rs = stmt.executeQuery("Select employeeID,ename from doctor,employee where employee.employeeid=doctor.doctorId and doctor.specialization='"+hm.get(choice)+"'");
		num=1;
		System.out.println("Sr.No.          Doctor");
		while(rs.next()){
			hm.put(num, rs.getString("employeeId"));
			String doc = rs.getString("ename");
			System.out.println(num+"     "+doc);
			num++;
		}
		System.out.println("Select Doctor :: ");
		
		//System.out.println("Fetching availability.. Please wait...");
		String docID = hm.get(Integer.parseInt(br.readLine())); 
		return docID;
	}
	
}
