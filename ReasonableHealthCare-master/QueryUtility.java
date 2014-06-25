

public class QueryUtility {

	public static String createInsertQuery(String tablename, String... values) {

		StringBuffer query = new StringBuffer();
		query.append("Insert into " + tablename + " values(");

		for (int i = 0; i < values.length; i++) {
			query.append("'" + values[i] + "',");
		}

		query.setLength(query.length() - 1);
		query.append(")");
		System.out.println("create query:" + query.toString());
		return query.toString();
	}

	public static String createUpdateQuery(String id, String idVal,
			String tablename, String... values) {

		/*
		 * String query = "UPDATE STUDENT set SSN='" + SSN + "',Sname='" + name
		 * + "',DOB='" + dob + "',Email='" + email + "',Phone='" + phone + "' "
		 * + "where StudentId='" + studentId + "'";
		 */
		
		StringBuffer query = new StringBuffer();
		query.append("Update " + tablename + " set ");
		for (int i = 0; i < values.length; i++) {
			query.append(values[i++] + "='" + values[i] + "',");
		}

		query.setLength(query.length() - 1);
		query.append(" where " + id + "='" + idVal + "'");
		System.out.println("update query" + query.toString());

		return query.toString();
	}

}
