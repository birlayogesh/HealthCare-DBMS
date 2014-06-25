
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RegisterUtility {

	static Connection dbConnection = DBConnection.createConnection();
	static Statement stmt;
	static ResultSet rs = null;
	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));

	public static void registerStudent() {

		String name, dob, email, SSN, phone, insId;
		String amount = null, expiry = null;
		String queryStudentId, studentId, queryInsurance, query, hasQuery;
		try {

			SSN = MainUtility.getSSN();
			name = MainUtility.getName("student name");
			dob = MainUtility.getDate("DOB(MM-dd-yyyy)");
			email = MainUtility.getEmail();
			phone = MainUtility.getPhoneNumber();
			insId = MainUtility.getInsuranceID();

			// HAS TABLE ENTRY VALUES START
			stmt = dbConnection.createStatement();
			queryStudentId = "SELECT studentid.nextval FROM dual";
			rs = stmt.executeQuery(queryStudentId);
			rs.next();
			studentId = rs.getString("NEXTVAL");
			queryInsurance = "SELECT * FROM Insurance";

			rs = stmt.executeQuery(queryInsurance);
			while (rs.next()) {
				if (insId.equals(rs.getString("INSURANCEID"))) {
					amount = rs.getString("DEDUCTIBLEAMOUNT");
				}
			}
			expiry = MainUtility.getDate("Insurance expiry date(MM-dd-yyyy)");

			query = QueryUtility.createInsertQuery("student", studentId, SSN,
					name, dob, email, phone, "ACTIVE");
			stmt.executeUpdate(query);

			/*
			 * query =
			 * "INSERT INTO STUDENT(StudentID, SSN, Sname, DOB, Email, Phone, status) VALUES "
			 * + "('" + studentId + "','" + SSN + "','" + name + "','" + dob +
			 * "','" + email + "','" + phone + "','ACTIVE')";
			 */

			hasQuery = QueryUtility.createInsertQuery("HAS", studentId, insId,
					amount, expiry);
			stmt.executeUpdate(hasQuery);
			/*
			 * hasQuery = "Insert into HAS VALUES('" + studentId + "','" + insId
			 * + "','" + amount + "','" + expiry + "')";
			 */
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static void updateStudentInsuranceInfo(String studentId) {

		String insuranceId,expiresOn,expiry;
		String queryInsurance=null,amount=null;

		try {

			
			System.out.println("Please enter your new Insurance Id");
			
			insuranceId=MainUtility.getInsuranceID();
			
			queryInsurance = "SELECT * FROM Insurance";

			rs = stmt.executeQuery(queryInsurance);
			while (rs.next()) {
				if (insuranceId.equals(rs.getString("INSURANCEID"))) {
					amount = rs.getString("DEDUCTIBLEAMOUNT");
				}
			}
			expiry = MainUtility.getDate("Insurance expiry date(MM-dd-yyyy)");

			
			/*
			 * String query = "UPDATE STUDENT set SSN='" + SSN + "',Sname='" +
			 * name + "',DOB='" + dob + "',Email='" + email + "',Phone='" +
			 * phone + "' " + "where StudentId='" + studentId + "'";
			 * System.out.println(query);
			 */

			String query = QueryUtility.createUpdateQuery("studentid",
					studentId, "has", "insuranceId", insuranceId, "amountpaid",amount, "expireson",
					expiry);

			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//
	public static void updateStudentInfo(String studentId) {

		String name, dob, email, SSN, phone;

		try {

			SSN = MainUtility.getSSN();
			name = MainUtility.getName("updated student name");
			dob = MainUtility.getDate("updated DOB(MM-dd-yyyy)");
			email = MainUtility.getEmail();
			phone = MainUtility.getPhoneNumber();
			/*
			 * String query = "UPDATE STUDENT set SSN='" + SSN + "',Sname='" +
			 * name + "',DOB='" + dob + "',Email='" + email + "',Phone='" +
			 * phone + "' " + "where StudentId='" + studentId + "'";
			 * System.out.println(query);
			 */

			String query = QueryUtility.createUpdateQuery("studentid",
					studentId, "student", "SSN", SSN, "sname", name, "DOB",
					dob, "Email", email, "Phone", phone);

			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateNurse(String nurseId) {
		// employeeid,name,ssn,phone,email,nurseId
		String SSN, ename, phone, email;

		try {
			SSN = MainUtility.getSSN();
			ename = MainUtility.getName("updated nurse name.");
			phone = MainUtility.getPhoneNumber();
			email = MainUtility.getEmail();
			stmt = dbConnection.createStatement();

			String query = QueryUtility.createUpdateQuery("employeeid",
					nurseId, "employee", "ename", ename, "ssn", SSN, "phone",
					phone, "Email", email);

			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateReceptionist(String recepId) {
		// employeeid,name,ssn,phone,email,nurseId
		String SSN, ename, phone, email;

		try {
			SSN = MainUtility.getSSN();
			ename = MainUtility.getName("updated receptionist name.");
			phone = MainUtility.getPhoneNumber();
			email = MainUtility.getEmail();
			stmt = dbConnection.createStatement();

			String query = QueryUtility.createUpdateQuery("employeeid",
					recepId, "employee", "ename", ename, "ssn", SSN, "phone",
					phone, "Email", email);

			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void registerNurse() {
		// employeeid,name,ssn,phone,email,nurseId
		String SSN, ename, phone, email;

		try {
			SSN = MainUtility.getSSN();
			ename = MainUtility.getName("nurse name");
			phone = MainUtility.getPhoneNumber();
			email = MainUtility.getEmail();
			stmt = dbConnection.createStatement();

			String queryEmployeeId = "SELECT employeeid.nextval FROM dual";
			rs = stmt.executeQuery(queryEmployeeId);
			rs.next();
			String employeeId = rs.getString("NEXTVAL");

			String nurseString = QueryUtility.createInsertQuery("nurse",
					employeeId);
			stmt.executeUpdate(nurseString);
			System.out.println(nurseString);

			String employeeString = QueryUtility.createInsertQuery("employee",
					employeeId, ename, SSN, phone, email);
			stmt.executeUpdate(employeeString);
			System.out.println(employeeString);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void registerReceptionist() {
		// employeeid,name,ssn,phone,email,nurseId
		String SSN, ename, phone, email;

		try {
			SSN = MainUtility.getSSN();
			ename = MainUtility.getName("receptionist name");
			phone = MainUtility.getPhoneNumber();
			email = MainUtility.getEmail();
			stmt = dbConnection.createStatement();

			String queryEmployeeId = "SELECT employeeid.nextval FROM dual";
			rs = stmt.executeQuery(queryEmployeeId);
			rs.next();
			String employeeId = rs.getString("NEXTVAL");

			String nurseString = QueryUtility.createInsertQuery("recpetionist",
					employeeId);
			stmt.executeUpdate(nurseString);
			System.out.println(nurseString);

			String employeeString = QueryUtility.createInsertQuery("employee",
					employeeId, ename, SSN, phone, email);
			stmt.executeUpdate(employeeString);
			System.out.println(employeeString);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void registerDoctor() {
		String SSN, ename, phone, specialization, charges, email;
		String avail[] = new String[5];
		try {

			SSN = MainUtility.getSSN();
			ename = MainUtility.getName("doctor name");
			phone = MainUtility.getPhoneNumber();
			email = MainUtility.getEmail();
			specialization = MainUtility.getName("specialization");
			charges = MainUtility.getNumber(" charges");

			avail = MainUtility.getAvailability();
			stmt = dbConnection.createStatement();

			String queryEmployeeId = "SELECT employeeid.nextval FROM dual";
			rs = stmt.executeQuery(queryEmployeeId);
			rs.next();
			String employeeId = rs.getString("NEXTVAL");

			String employeeString = QueryUtility.createInsertQuery("employee",
					employeeId, ename, SSN, phone, email);
			stmt.executeUpdate(employeeString);
			System.out.println(employeeString);

			String doctorString = QueryUtility.createInsertQuery("doctor",
					employeeId, specialization, charges);
			stmt.executeUpdate(doctorString);
			System.out.println(doctorString);
			/*
			 * String doctorString = "Insert into doctor values('" + employeeId
			 * + "','" + specialization + "','" + charges + "')";
			 */

			/*
			 * String employeeString = "Insert into employee values('" +
			 * employeeId + "','" + ename + "','" + SSN + "','" + phone + "','"
			 * + email + "')";
			 */

			String availString = QueryUtility.createInsertQuery("availability",
					employeeId, avail[1], avail[2], avail[3], avail[4],
					avail[5]);
			stmt.executeUpdate(availString);
			stmt.executeUpdate("commit");
			System.out.println(availString);

			/*
			 * String availString = "Insert into availability values('" +
			 * employeeId + "','" + avail[1] + "','" + avail[2] + "','" +
			 * avail[3] + "','" + avail[4] + "','" + avail[5] + "')";
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDoctorPersonalInfo(String employeeId) {

		String name, dob, email, SSN, phone;
		String query;
		try {

			SSN = MainUtility.getSSN();
			name = MainUtility.getName("doctor name");
			email = MainUtility.getEmail();
			phone = MainUtility.getPhoneNumber();

			/*
			 * query = "UPDATE Employee set SSN='" + SSN + "',Sname='" + name +
			 * "',',Email='" + email + "',Phone='" + phone + "' " +
			 * "where employeeId='" + employeeId + "'";
			 * System.out.println(query);
			 */
			query = QueryUtility.createUpdateQuery("employeeId", employeeId,
					"Employee", "SSN", SSN, "ename", name, "Email", email,
					"Phone", phone);
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDoctorProfessionalInfo(String employeeId) {

		String specialization, charges;
		String query;
		try {

			specialization = MainUtility.getName("specialization");
			charges = MainUtility.getNumber(" charges");

			query = QueryUtility.createUpdateQuery("doctorId", employeeId,
					"doctor", "specialization", specialization, "charges",
					charges);

			/*
			 * query = "UPDATE doctor set specialization='" + specialization +
			 * "',charges='" + charges + "' " + "where doctorId='" + employeeId
			 * + "'"; System.out.println(query);
			 */
			stmt = dbConnection.createStatement();

			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateDoctorAvailabilityInfo(String employeeId) {

		String avail[] = new String[5];
		String query;
		try {

			avail = MainUtility.getAvailability();

			query = QueryUtility.createUpdateQuery("doctorId", employeeId,
					"availability", "mon", avail[1], "tue", avail[2], "wed",
					avail[3], "thu", avail[4], "fri" + avail[5]);

			/*
			 * query = "UPDATE availability set mon='" + avail[1] + "',tue='" +
			 * avail[2] + "',wed='" + avail[3] + "',thu='" + avail[4] +
			 * "',fri='" + avail[5] + "' where doctorId='" + employeeId + "'";
			 */
			System.out.println(query);

			stmt = dbConnection.createStatement();

			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void makeAppointment() {

		String studentId, empName, empId, appDate, amountSPaid, amountIPaid, purpose, appTime;
		String queryAppointmentId, appointmentId;
		int employeeId;
		try {

			studentId = MainUtility.getStudentID("");
			empId = MainUtility.getEmployeeId();
			appDate = MainUtility.getDate("appointment date(MM-dd-yyyy)");
			amountSPaid = MainUtility.getAmountStudentPaid(studentId);
			amountIPaid = String.valueOf(100 - Integer.parseInt(amountSPaid));
			purpose = MainUtility.getName("purpose of visit.");
			Timestamp timestamp;

			while (true) {
				System.out
						.println("Please enter appointment time in format(MM-dd-yyyy hh:mm:ss)");

				appTime = br.readLine();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd-yyyy hh:mm:ss");
				Date parsedDate = (Date) dateFormat.parse(appTime);
				timestamp = new java.sql.Timestamp(parsedDate.getTime());

				// boolean val =
				// ValidationUtility.checkValidAppointmentTime(empName);
				// if (val == true)
				if (true)
					break;
				else
					System.out
							.println("Appointment Time is Invalid.Please reenter another appointment");
			}

			stmt = dbConnection.createStatement();
			queryAppointmentId = "SELECT appointmentid.nextval FROM dual";
			rs = stmt.executeQuery(queryAppointmentId);
			rs.next();
			appointmentId = rs.getString("NEXTVAL");

			/*
			 * String query = QueryUtility.createInsertQuery("Appointment",
			 * appointmentId, studentId, empId, appDate, appTime, amountSPaid,
			 * amountIPaid, purpose, "TBD");
			 */

			String query = "INSERT INTO APPOINTMENT(AppointmentID, StudentID, EmployeeID, AppointmentDate, StartTime, StudentPaid, InsurancePaid,Purpose,Notes) "
					+ "VALUES "
					+ "('"
					+ appointmentId
					+ "','"
					+ studentId
					+ "','"
					+ empId
					+ "','"
					+ appDate
					+ "','"
					+ timestamp
					+ "','"
					+ amountSPaid
					+ "','"
					+ amountIPaid
					+ "','"
					+ purpose + "','TBD')";

			System.out.println(query);
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAppointmentNotes() {

		String notes;
		String query;
		String appId;

		try {
			appId = MainUtility.getAppointmentID();
			notes = MainUtility.getName("notes of visit.");

			query = QueryUtility.createUpdateQuery("appointmentId", appId,
					"appointment", "notes", notes);

			/*
			 * query = "UPDATE appointment set notes='" + notes + "' " +
			 * "where appointmentId='" + appId + "'";
			 */
			System.out.println(query);
			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.executeUpdate("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
