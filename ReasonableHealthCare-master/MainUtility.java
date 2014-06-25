import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.EmailValidator;

public class MainUtility {
	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));
	static Connection conn = DBConnection.createConnection();
	static Statement stmt = null;
	static ResultSet rs = null;

	public static String[] getAvailability() {

		int i = 1;
		String avail[] = new String[6];
		try {

			while (true) {
				System.out.println("Please enter the availability for " + i
						+ "day of week");
				avail[i] = br.readLine();
				Pattern pattern = Pattern
						.compile("\\d{2}:\\d{2}-\\d{2}:\\d{2}");
				Matcher matcher = pattern.matcher(String.valueOf(avail[i]));

				if (matcher.matches()) {
					String arry[] = avail[i].split("-");
					String beginTime[] = arry[0].split(":");
					String endTime[] = arry[1].split(":");
						if(Integer.parseInt(beginTime[0])<Integer.parseInt(endTime[0])){
							i++;
						}
						else if(Integer.parseInt(beginTime[0])>Integer.parseInt(endTime[0])){
							System.out.println("Invalid entry.Please renenter");
						}else{
							if(Integer.parseInt(beginTime[1]) < Integer.parseInt(endTime[1]) && (Integer.parseInt(endTime[1]) - Integer.parseInt(beginTime[1])>20 )){
								i++;
							}else{
								System.out.println("Invalid entry.Please renenter");
							}
						}
				} else {
					System.out
							.println("Invalid format for availability.It should be 9 digit number.Please reenter");
				}
				if (matcher.matches() && i > 5) {
					return avail;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avail;
	}

	public static String getEmail() {

		String email = null;
		System.out.println("Please enter email-id");
		try {
			while (true) {
				email = br.readLine();
				EmailValidator ev = EmailValidator.getInstance();
				boolean val = ev.isValid(email);
				if (val) {
					return email;
				} else {
					System.out
							.println("Invalid format.Please re-enter email-id(Eg:ydbirla@ncsu.edu)");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

	public static String getNumber(String nameString) {

		String charges = null;
		System.out.println("Please enter " + nameString);
		try {
			charges = br.readLine();
			while (true) {
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(String.valueOf(charges));
				if (matcher.matches()) {
					return charges;
				} else {
					System.out
							.println("Invalid format.Please re-enter charges(Eg.20)");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPhoneNumber() {

		String phoneNumber = null;
		System.out.println("Please enter phoneNumber");
		try {
			while (true) {
				phoneNumber = br.readLine();
				Pattern pattern = Pattern.compile("\\d{10}");
				Matcher matcher = pattern.matcher(String.valueOf(phoneNumber));
				if (matcher.matches()) {
					return phoneNumber;
				} else {
					System.out
							.println("Invalid format.Please re-enter phone(Eg.9193493860)");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneNumber;
	}

	public static String getDate(String nameString) {

		String dob = null;
		System.out.println("Please enter " + nameString);

		try {
			while (true) {
				dob = br.readLine();
				DateValidator dv = DateValidator.getInstance();
				boolean dateVal = dv.isValid(dob, "MM-dd-yyyy", true);

				if (dateVal == true) {
					return dob;
				} else {
					System.out
							.println("Invalid format.Please re-enter Date in format(10-OCT-89)");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dob;

	}

	public static String getName(String nameString) {
		String name;
		System.out.println("Please enter " + nameString);
		try {
			while (true) {
				name = br.readLine();
				if (name.matches("^[a-z A-Z]+$")) {
					return name;
				} else {
					System.out
							.println("Invalid format.Please re-enter Name with only characters(Eg. abcd)");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSSN() {

		String SSN;
		System.out.println("Please enter SSN");
		try {
			while (true) {
				SSN = br.readLine();
				Pattern pattern = Pattern.compile("\\d{9}");
				Matcher matcher = pattern.matcher(String.valueOf(SSN));
				if (matcher.matches()) {
					return SSN;
				} else {
					System.out
							.println("Invalid format.Please re-enter SSN(Eg. 123456789)");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStudentID(String nameString) {

		String query = "select studentId from student ";
		System.out.println("Please enter your Student Id" + nameString);
		while (true) {
			try {

				String sid = br.readLine();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (sid.equals(rs.getString("StudentId")))
						return sid;
				}
				System.out.println("Wrong Student Id.Please reenter");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static String getAppointmentID() {

		String query = "select appId from appointment";
		String appId;
		System.out.println("Please enter your Appointment Id");
		while (true) {
			try {
				appId = br.readLine();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (appId.equals(rs.getString("appointmentId")))
						return appId;
				}
				System.out.println("Invalid Appointment Id");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static String getDoctorID() {

		String query = "select doctorID from doctor";
		String did;
		System.out.println("Please enter doctorID");
		while (true) {
			try {
				did = br.readLine();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (did.equals(rs.getString("doctorId")))
						return did;
				}
				System.out.println("Doctor Id doesn't exist.Please reenter.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static String getReceptionistID() {

		String query = "select nurseID from nurse";
		String nid;
		System.out.println("Please enter nurseID");
		while (true) {
			try {
				nid = br.readLine();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (nid.equals(rs.getString("nurseId")))
						return nid;
				}
				System.out.println("Nurse Id doesn't exist.Please reenter.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static String getNurseID() {

		String query = "select nurseID from nurse";
		String nid;
		System.out.println("Please enter nurseID");
		while (true) {
			try {
				nid = br.readLine();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (nid.equals(rs.getString("nurseId")))
						return nid;
				}
				System.out.println("Nurse Id doesn't exist.Please reenter.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static String getEmployeeId() {

		String query = "select * from employee";
		System.out.println("Please enter your Doctor's Name");

		try {

			String ename = br.readLine();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				if (ename.equals(rs.getString("ename")))
					return rs.getString(1);
			}
			System.out.println("Wrong Doctor name.Please reenter");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getInsuranceID() {

		while (true) {
			try {
				String insId;
				System.out.println("Please enter insurance id");
				insId = br.readLine();
				String query = "select insuranceid from insurance";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					if (insId.equals(rs.getString("insuranceid")))
						return insId;
				}
				System.out.println("Invalid Insurance ID. Please reenter");
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static String getAmountStudentPaid(String stuId) {

		String query = "select * from has";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (stuId.equals(rs.getString(1)))
					return rs.getString(3);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
