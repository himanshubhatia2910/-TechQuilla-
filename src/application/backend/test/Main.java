package application.backend.test;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.exceptions.UserNotFoundException;
import application.backend.service.AdminService;
import application.backend.service.AdminServiceImpl;
import application.backend.service.LoginService;
import application.backend.service.LoginServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice=0;
        System.out.println("Enter Unique ID:");
        String id = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next();
        LoginService loginService = new LoginServiceImpl();
        AdminService adminService = new AdminServiceImpl();

        String role = null;
        try {
            role = loginService.getUserRole(id, password);
            switch (role){
                case "ADMIN":
                    System.out.println("Hello Admin");
                    int adminChoice=0;
                    do{
                        System.out.println("--------------------------------------------");
                        System.out.println("1. Import new Doctors List(Json).");
                        System.out.println("2. Show All Doctors List.");
                        System.out.println("3. Next 3 Day Appointments of Doctor");
                        System.out.println("4. View Doctor's Current Day Appointment");
                        System.out.println("5. Cancel Current Day Appointment of Doctor");
                        System.out.println("6. Show All Patients");
                        System.out.println("7. Exit");
                        System.out.println("--------------------------------------------");
                        System.out.println("Choose a operation to Perform:");
                        adminChoice=sc.nextInt();
                        switch (adminChoice){
                            case 1:
                                try {
                                    String jsonFilePath = "src/application/doctorsList.json";
                                    int count=adminService.importDoctorsFromJson(jsonFilePath);
                                    if(count!=0) System.out.println(count+" Duplicate Entries Found. So not inserted.");
                                    System.out.println("Doctors imported successfully.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                System.out.println("List of Existing Doctors:");
                                List<Doctor> doctors = adminService.getAllDoctors();
                                for (Doctor doctor : doctors) {
                                    System.out.println(doctor);
                                }
                                break;
                            case 3:
                                System.out.println("Enter Doctor ID to see Appointments:");
                                String doctorId = sc.next();
                                System.out.println("------------------------------------");
                                System.out.println("Showing Next 3 Day Appointments of Doctor:");
                                List<Appointments> appointments = adminService.getNextThreeDaysAppointments(doctorId);
                                if (appointments.isEmpty()) {
                                    System.out.println("No appointments found for the next 3 days.");
                                } else {
                                    for (Appointments appointment : appointments) {
                                        System.out.println(appointment);
                                    }
                                }
                                break;
                            case 4:
                                try {
                                    System.out.println("Enter Doctor ID:");
                                    String docId = sc.next();
                                    System.out.println("--------------------------------------");
                                    List<Appointments> todayAppointments = adminService.getCurrentDayAppointments(docId);
                                    System.out.println("Today's Appointments for Doctor ID " + docId + ":");
                                    if(todayAppointments.isEmpty()) {
                                        System.out.println("No appointments found for today.");
                                    }
                                    for (Appointments appointment : todayAppointments) {
                                        System.out.println(appointment);
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 5:
                                try {
                                    System.out.println("Enter Doctor ID:");
                                    String docId = sc.next();
                                    System.out.println("--------------------------------------");
                                    List<Appointments> todayAppointments = adminService.getCurrentDayAppointments(docId);
                                    System.out.println("Today's Appointments for Doctor ID " + docId + ":");
                                    if(todayAppointments.isEmpty()) {
                                        System.out.println("No appointments found for today.");
                                    }
                                    for (Appointments appointment : todayAppointments) {
                                        System.out.println(appointment);
                                    }
                                    System.out.println("Enter Appointment ID:");
                                    String appId = sc.next();
                                    Appointments cancelledAppointment = adminService.cancelCurrentDayAppointment(docId, appId);
                                    if (cancelledAppointment != null) {
                                        System.out.println("Appointment successfully cancelled:");
                                        System.out.println(cancelledAppointment);
                                    } else {
                                        System.out.println("No appointment found to cancel.");
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 6:
                                System.out.println("List of Existing Patients:");
                                List<Patient> patients = adminService.getAllPatients();
                                for (Patient p : patients) {
                                    System.out.println(p);
                                }
                                break;
                            case 7:
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid Choice");
                        }
                    }while(adminChoice!=7);
                    break;
                case "USER":
                    System.out.println("Hello User");
                    break;
                case "DOCTOR":
                    System.out.println("Hello Doctor");
                    break;
                default:
                    System.out.println("User not Found in Records.");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

