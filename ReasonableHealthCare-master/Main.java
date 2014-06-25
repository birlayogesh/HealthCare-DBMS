import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.spi.RegisterableService;

import student.Student;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int desig = 0;
		String userID = null;
		while (true) {
			System.out.println("Enter your User Id :: ");
			userID = br.readLine();
			System.out.println("Enter your password :: ");
			String pass = br.readLine();

			Connection dbConnection = Utility.dbConnection;
			desig = Utility.authenticateUser(userID, pass, dbConnection);
			if (desig == -1) {
				System.out
						.println("Worng username or password!!! \n Please try again...");
				continue;
			}

			try {
				switch (desig) {

				case 1: // Student
					int choice = 0;
					do {
						System.out.println("1.   Make Appointment");
						System.out.println("2.   Cancel Appointment");
						System.out.println("3.   View Appointment");
						System.out.println("4.   Logout");
						choice = Integer.parseInt(br.readLine());
						if (choice == 1) {
							Utility.makeAppointment(userID);
						} else if (choice == 2) {
							Utility.cancelAppointment(userID);
						} else if (choice == 3) {
							Utility.viewAppointment(userID);
						}
					} while (choice != 4);
				case 5:
					while (true) {
						System.out.println("Menu:");
						System.out.println("1. Register Student Information");
						System.out.println("2. Update Student Information");
						System.out.println("3. Register Doctor Information");
						System.out.println("4. Update Doctor Information");
						System.out.println("5. Register Nurse Information");
						System.out.println("6. Update Nurse Information");
						System.out
								.println("7. Register Receptionist Information");
						System.out
								.println("8. Update Recpetionist Information");
						System.out.println("9. Exit");

						System.out.println("Enter your choice");
						// RegisterUtility.validateData();

						int ch;
						try {
							ch = Integer.parseInt(br.readLine());
							if (ch == 1) {
								RegisterUtility.registerStudent();

							} else if (ch == 2) {
								String studentId;
								studentId = MainUtility
										.getStudentID(" to be updated.");

								while (true) {
									System.out.println("Menu:");
									System.out
											.println("1 :Update Student Profile Info");
									System.out
											.println("2 :Update Student Insurance Info");
									System.out.println("3 :Exit");
									System.out.println("Enter your choice");
									int ch1 = Integer.parseInt(br.readLine());

									if (ch1 == 1) {

										RegisterUtility
												.updateStudentInfo(studentId);
									} else if (ch1 == 2) {
										RegisterUtility
												.updateStudentInsuranceInfo(studentId);

									} else if (ch1 == 3) {
										break;
									} else {
										System.out.println("Wrong Choice");
									}

								}

							} else if (ch == 3) {
								RegisterUtility.registerDoctor();

							} else if (ch == 4) {
								String doctorId;
								doctorId = MainUtility.getDoctorID();

								while (true) {
									System.out.println("Menu:");
									System.out
											.println("1 :Update Personal Profile:");
									System.out
											.println("2 :Update Professional Profile:");
									System.out
											.println("3 :Update Availability:");
									System.out.println("4 :Exit");
									System.out.println("Enter your choice");
									int ch1 = Integer.parseInt(br.readLine());

									if (ch1 == 1) {
										RegisterUtility
												.updateDoctorPersonalInfo(doctorId);
									} else if (ch1 == 2) {
										RegisterUtility
												.updateDoctorProfessionalInfo(doctorId);
									} else if (ch1 == 3) {
										RegisterUtility
												.updateDoctorAvailabilityInfo(doctorId);
									} else if (ch1 == 4) {
										break;
									} else {
										System.out.println("Wrong Choice.Please reenter");
									}

								}
								// RegisterUtility.updateInfo(doctorId);
							} else if (ch == 5) {
								RegisterUtility.registerNurse();
							} else if (ch == 6) {
								String nurseId;
								nurseId = MainUtility.getNurseID();
								RegisterUtility.updateNurse(nurseId);
							} else if (ch == 7) {
								RegisterUtility.registerReceptionist();
							} else if (ch == 8) {
								String receptionistId;
								receptionistId = MainUtility
										.getReceptionistID();
								RegisterUtility
										.updateReceptionist(receptionistId);
							} else if (ch == 9) {
								System.exit(1);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void handleStudent() {

	}
}
