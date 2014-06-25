

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.EmailValidator;

public class ValidationUtility {

	public static boolean validateAvailability(String availability) {
		Pattern pattern = Pattern.compile("\\d{2}:\\d{2}-\\d{2}:\\d{2}");
		Matcher matcher = pattern.matcher(String.valueOf(availability));
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean validateEmail(String email) {
		EmailValidator ev = EmailValidator.getInstance();
		boolean val = ev.isValid(email);
		return val;
	}

	public static boolean validateIfNumber(String charges) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(String.valueOf(charges));
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validatePhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matcher = pattern.matcher(String.valueOf(phoneNumber));
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean dobValidation(String dob) {
		DateValidator dv = DateValidator.getInstance();
		boolean dateVal = dv.isValid(dob, "MM-dd-yyyy", true);
		return dateVal;

	}
	
	public static boolean isFutureDate(String date){
		DateValidator dv = DateValidator.getInstance();
		boolean dateVal = dv.isValid(date, "MM-dd-yyyy", true);
		return dateVal;
	}
	

	public static boolean nameValidation(String name) {
		if (Pattern.matches("[a-zA-Z a-zA-Z]+", name))
			return true;
		else
			return false;
	}

	public static boolean ssnValidation(String SSN) {
		Pattern pattern = Pattern.compile("\\d{9}");
		Matcher matcher = pattern.matcher(String.valueOf(SSN));
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkStudentIDExist(String sid) {
		Connection conn = DBConnection.createConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		String query = "select studentId from student";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (sid.equals(rs.getString("StudentId")))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean checkInsuranceIDExist(String insId) {
		Connection conn = DBConnection.createConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		String query = "select insuranceid from insurance";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (insId.equals(rs.getString("insuranceid")))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
