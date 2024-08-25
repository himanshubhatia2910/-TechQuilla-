package application.backend.Junit;
import static org.junit.jupiter.api.Assertions.*;

import application.backend.beans.Doctor;
import application.backend.exceptions.UserNotFoundException;
import application.backend.service.*;

import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.List;

public class HospitalManagementSystemTest {

    LoginService loginService = new LoginServiceImpl();
    AdminService adminService = new AdminServiceImpl();
    StaffService staffService = new StaffServiceImpl();
    DoctorService doctorService = new DoctorServiceImpl();


    @Test
    public void testAdminLogin() {
        // TC001: Validate Admin Login
        String adminId = "A00001";
        String password = "Admin@1234";

        String result = null;
        try {
            result = loginService.getUserRole(adminId, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals("ADMIN", result, "Admin login failed.");
    }

    @Test
    public void testUserLogin() {
        // TC002: Validate User Login
        String userId = "U00001";
        String password = "User@1234";

        String result = null;
        try {
            result = loginService.getUserRole(userId, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals("USER", result, "User login failed.");
    }

    @Test
    public void testDoctorLogin() {
        // TC003: Validate Doctor Login
        String doctorId = "D00001";
        String password = "Doctor@1234";

        String result = null;
        try {
            result = loginService.getUserRole(doctorId, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals("DOCTOR", result, "Doctor login failed.");
    }

    @Test
    public void testInvalidLogin() {
        // TC004: Invalid Login
        String invalidId = "X00001";
        String password = "wrongpass";

        String result = null;
        try {
            result = loginService.getUserRole(invalidId, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(null, result, "Invalid login not handled properly.");
    }


    @Test
    public void testShowAllDoctorsList() {
        // TC006: Show All Doctors List
        List<Doctor> result = null;
        try {
            result = adminService.getAllDoctors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertTrue(result.contains("Doctor [ID: D001"), "Doctor list not displayed correctly.");
    }
//
//    @Test
//    public void testViewNext3DaysAppointments() {
//        // TC007: View Next 3 Days Appointments
//        String doctorId = "D001";
//
//        String result = adminService.viewNext3DaysAppointments(doctorId);
//        assertTrue(result.contains("appointments for Doctor ID"), "Appointments not displayed correctly.");
//    }
//
//    @Test
//    public void testViewCurrentDayAppointments() {
//        // TC008: View Current Day Appointments
//        String doctorId = "D001";
//
//        String result = adminService.viewCurrentDayAppointments(doctorId);
//        assertTrue(result.contains("today's appointments for Doctor ID"), "Current day appointments not displayed correctly.");
//    }
//
//    @Test
//    public void testCancelCurrentDayAppointment() {
//        // TC009: Cancel Current Day Appointment
//        String doctorId = "D001";
//        String appointmentId = "A123";
//
//        String result = adminService.cancelAppointment(doctorId, appointmentId);
//        assertEquals("Appointment with ID \"A123\" cancelled successfully", result, "Appointment cancellation failed.");
//    }
//
//    @Test
//    public void testShowAllPatientsList() {
//        // TC010: Show All Patients List
//        String result = adminService.showAllPatients();
//        assertTrue(result.contains("Patient [ID: P001"), "Patient list not displayed correctly.");
//    }
//
//    @Test
//    public void testRegisterNewPatient() {
//        // TC011: Register New Patient
//        Patient newPatient = new Patient("P010", "Bob", "1980-01-01", "1234567890", "123 Street, City");
//
//        String result = userService.registerNewPatient(newPatient);
//        assertEquals("New Patient Successfully Registered", result, "New patient registration failed.");
//    }
//
//    @Test
//    public void testBookAppointmentWithDoctor() {
//        // TC012: Book Appointment With Doctor
//        Appointment appointment = new Appointment("A124", "P001", "D001", "2024-09-01 10:00:00");
//
//        String result = userService.bookAppointment(appointment);
//        assertEquals("Appointment booked successfully", result, "Appointment booking failed.");
//    }
//
//    @Test
//    public void testAddScheduleForNext3Days() {
//        // TC013: Add Schedule for Next 3 Days (Doctor)
//        String doctorId = "D002";
//        String startTime = "09:00";
//        String endTime = "17:00";
//
//        String result = doctorService.addScheduleForNext3Days(doctorId, startTime, endTime);
//        assertEquals("Successfully added 3 schedules for Doctor ID \"D002\"", result, "Schedule addition failed.");
//    }
//
//    @Test
//    public void testSuggestMedicalTestForPatient() {
//        // TC014: Suggest Medical Test for a Patient (Doctor)
//        String patientId = "P001";
//        String testName = "Blood Test";
//
//        String result = doctorService.suggestMedicalTest(patientId, testName);
//        assertEquals("Test \"Blood Test\" suggested for Patient ID \"P001\"", result, "Medical test suggestion failed.");
//    }
//
//    @Test
//    public void testExitApplication() {
//        // TC015: Exit Application
//        String result = application.exit();
//        assertEquals("Thank you for using the Platform. Have a good day.", result, "Application exit failed.");
//    }
}
