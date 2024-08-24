package application.backend.test;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.DoctorSchedule;
import application.backend.beans.Patient;
import application.backend.enums.ScheduleStatus;
import application.backend.exceptions.InvalidPatientIdException;
import application.backend.exceptions.SlotAlreadyBookedException;
import application.backend.exceptions.UserNotFoundException;
import application.backend.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void printPattern(){
        System.out.println("-----------------------------------------------------------------------------");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Login into the System:");
        System.out.println("Enter Unique ID:");
        String id = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next();
        printPattern();
        LoginService loginService = new LoginServiceImpl();
        AdminService adminService = new AdminServiceImpl();
        StaffService staffService = new StaffServiceImpl();
        DoctorService doctorService = new DoctorServiceImpl();
        String role = null;
        try {
            role = loginService.getUserRole(id, password);
            switch (role){
                case "ADMIN":
                    System.out.println("Hello Admin");
                    int adminChoice=0;
                    do{
                        printPattern();
                        System.out.println("1. Import new Doctors List(Json).");
                        System.out.println("2. Show All Doctors List.");
                        System.out.println("3. Next 3 Day Appointments of Doctor");
                        System.out.println("4. View Doctor's Current Day Appointment");
                        System.out.println("5. Cancel Current Day Appointment of Doctor");
                        System.out.println("6. Show All Patients");
                        System.out.println("7. Exit");
                       printPattern();
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
                                printPattern();
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
                                    printPattern();
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
                                    printPattern();
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
                                System.out.println("Thank you for using the Platform. Have a good day.");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid Choice");
                        }
                    }while(adminChoice!=7);
                    break;
                case "USER":
                    System.out.println("Hello User");
                    int userChoice=0;
                    do{
                        printPattern();
                        System.out.println("1. Register new Patient");
                        System.out.println("2. Book Appointment With Required Doctor");
                        System.out.println("3. View Appointments");
                        System.out.println("4. Exit");
                        printPattern();
                        System.out.println("Choose a Operation to Perform:");
                        userChoice=sc.nextInt();
                        printPattern();
                        switch (userChoice){
                            case 1:
                                    try{
                                        boolean status= staffService.addNewPatient();
                                        if(status) System.out.println("New Patient Successfully Registered.");
                                        else System.out.println("Error Registering New Patient.");
                                    }
                                    catch (InvalidPatientIdException e){
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                            case 2:
                                try{
                                    staffService.bookAppointment();
                                }
                                catch (SQLException | SlotAlreadyBookedException e){
                                    System.out.println(e.getMessage());
                                }

                            case 3:
                                printPattern();
                                System.out.println("1. View Current Day Appointment of Doctor");
                                System.out.println("2. View Next 3 day Appointment of Doctor");
                                System.out.println("3. View Next 7 day's Appointment of Doctor");
                                System.out.println("4. View All the Appointments of doctor");
                                System.out.println("Choose a operation to perform:");
                                int AppointmentChoice=sc.nextInt();
                                switch (AppointmentChoice){
                                    case 1:System.out.println("Enter Doctor ID:");
                                        String docIdToday = sc.next();
                                        printPattern();
                                        try {
                                            List<Appointments> todayAppointments = staffService.getCurrentDayAppointments(docIdToday);
                                            System.out.println("Today's Appointments:");
                                            if (todayAppointments.isEmpty()) {
                                                System.out.println("No appointments found for today.");
                                            } else {
                                                for (Appointments appointment : todayAppointments) {
                                                    System.out.println(appointment);
                                                }
                                            }
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Enter Doctor ID:");
                                        String docIdNext3Days = sc.next();
                                        printPattern();
                                        try {
                                            List<Appointments> next3DaysAppointments = staffService.getNextThreeDaysAppointments(docIdNext3Days);
                                            System.out.println("Next 3 Days' Appointments:");
                                            if (next3DaysAppointments.isEmpty()) {
                                                System.out.println("No appointments found for the next 3 days.");
                                            } else {
                                                for (Appointments appointment : next3DaysAppointments) {
                                                    System.out.println(appointment);
                                                }
                                            }
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    case 3:System.out.println("Enter Doctor ID:");
                                        String docIdNextWeek = sc.next();
                                        printPattern();
                                        try {
                                            List<Appointments> nextWeekAppointments = staffService.getNextWeekAppointments(docIdNextWeek);
                                            System.out.println("Next Week's Appointments:");
                                            if (nextWeekAppointments.isEmpty()) {
                                                System.out.println("No appointments found for the next week.");
                                            } else {
                                                for (Appointments appointment : nextWeekAppointments) {
                                                    System.out.println(appointment);
                                                }
                                            }
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    case 4:System.out.println("Enter Doctor ID:");
                                        String docIdAll = sc.next();
                                        printPattern();
                                        try {
                                            List<Appointments> allAppointments = staffService.getAllAppointments(docIdAll);
                                            System.out.println("All Appointments:");
                                            if (allAppointments.isEmpty()) {
                                                System.out.println("No appointments found.");
                                            } else {
                                                for (Appointments appointment : allAppointments) {
                                                    System.out.println(appointment);
                                                }
                                            }
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Invalid Choice");
                                }
                                break;
                            case 4:
                                System.out.println("Thank you for using the Platform. Have a good day.");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid Choice");
                        }
                    }while(userChoice!=4);
                    break;
                case "DOCTOR":
                    System.out.println("Hello Doctor");
                    int doctorChoice=0;
                    do{
                        System.out.println("1. Add Schedule for next 3 Days");
                        System.out.println("2. View Current Day Appointments");
                        System.out.println("3. View Next 3 Days Appointment");
                        System.out.println("4. Cancel a existing appointment");
                        System.out.println("5. Suggest Medical Test for a Patient");
                        System.out.println("6. Suggest Medicines for a patient");
                        System.out.println("7. Exit");
                        System.out.println("Please select a operation to perform:");
                        doctorChoice=sc.nextInt();
                        switch(doctorChoice){
                            case 1:
                                List<DoctorSchedule> schedules = new ArrayList<>();
                                for (int i = 1; i <= 3; i++) {
                                    LocalDate date = LocalDate.now().plusDays(i);

                                    System.out.println("Enter start time (HH:mm:ss) for "+date+": ");
                                    LocalTime startTime = LocalTime.parse(sc.next());
                                    System.out.println("Enter end time (HH:mm:ss): ");
                                    LocalTime endTime = LocalTime.parse(sc.next());

                                    DoctorSchedule schedule = new DoctorSchedule(
                                            null, id, date, startTime, endTime, ScheduleStatus.AVAILABLE, null, null);
                                    schedules.add(schedule);
                                }

                                try {
                                    int count = doctorService.addScheduleForNextThreeDays(id, schedules);
                                    System.out.println("Successfully added " + count + " schedules for the next 3 days.");
                                } catch (SQLException e) {
                                    System.out.println("Error adding schedules: " + e.getMessage());
                                }
                                break;
                            case 2:
                                printPattern();
                                try {
                                    List<Appointments> todayAppointments = staffService.getCurrentDayAppointments(id);
                                    System.out.println("Today's Appointments:");
                                    if (todayAppointments.isEmpty()) {
                                        System.out.println("No appointments found for today.");
                                    } else {
                                        for (Appointments appointment : todayAppointments) {
                                            System.out.println(appointment);
                                        }
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                printPattern();
                                try {
                                    List<Appointments> next3DaysAppointments = staffService.getNextThreeDaysAppointments(id);
                                    System.out.println("Next 3 Days' Appointments:");
                                    if (next3DaysAppointments.isEmpty()) {
                                        System.out.println("No appointments found for the next 3 days.");
                                    } else {
                                        for (Appointments appointment : next3DaysAppointments) {
                                            System.out.println(appointment);
                                        }
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 4:
                                try{
                                    printPattern();
                                    List<Appointments> todayAppointments = adminService.getCurrentDayAppointments(id);
                                    System.out.println("Today's Appointments for Doctor ID " + id + ":");
                                    if(todayAppointments.isEmpty()) {
                                        System.out.println("No appointments found for today.");
                                    }
                                    for (Appointments appointment : todayAppointments) {
                                        System.out.println(appointment);
                                    }
                                    System.out.println("Enter Appointment ID:");
                                    String appId = sc.next();
                                    Appointments cancelledAppointment = adminService.cancelCurrentDayAppointment(id, appId);
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
                            case 5:
                                doctorService.suggestMedicalTest();

                                break;
                            case 6:
                                //doctorService.suggestPrescriptions();
                                break;
                            case 7: System.out.println("Thank you for using the Platform. Have a good day.");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }while (doctorChoice!=7);
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

