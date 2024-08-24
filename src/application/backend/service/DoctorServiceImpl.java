package application.backend.service;

import application.backend.beans.*;
import application.backend.dao.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DoctorServiceImpl implements DoctorService{
    DoctorDao doctorDao;
    AdminDao adminDao;
    public DoctorServiceImpl(){
        this.doctorDao= new DoctorDaoImpl();
        this.adminDao= new AdminDaoImpl();
    }
    @Override
    public int addScheduleForNextThreeDays(String doctorId, List<DoctorSchedule> schedules) throws SQLException {
        int addedSchedules = 0;
        for (DoctorSchedule schedule : schedules) {
            String scheduleId = generateScheduleId(doctorId, schedule.getDate());
            schedule.setScheduleId(scheduleId);
            schedule.setCreatedAt(LocalDateTime.now());
            schedule.setUpdatedAt(LocalDateTime.now());
            boolean isScheduled = doctorDao.addDoctorSchedule(schedule);
            if (isScheduled) {
                addedSchedules++;
            }
        }
        return addedSchedules;
    }

    @Override
    public void suggestMedicalTest() {
        Scanner sc = new Scanner(System.in);
        printPattern();
        System.out.println("Available Patients:");
        try {
            List<Patient> plist=adminDao.getAllPatients();
            for (Patient p : plist) {
                System.out.println(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        printPattern();
        try{
            System.out.println("Available Medical Tests:");
            List<MedicalTest> tests = doctorDao.getAllMedicalTests();
            for (MedicalTest test : tests) {
                System.out.println(test);
            }
            System.out.println("Enter Patient ID to suggest a test:");
            String patientId = sc.next();
            System.out.println("Previously Suggested Tests for "+patientId+":");
            List<MedicalTest> suggestedTests = doctorDao.getSuggestedTests(patientId);
            for (MedicalTest test : suggestedTests) {
                System.out.println(test);
            }
            printPattern();
            System.out.println("Enter Test ID to suggest:");
            String testId = sc.next();
            PatientMedicalTest patientMedicalTest = new PatientMedicalTest(
                    patientId,
                    testId,
                    LocalDateTime.now()
            );

            doctorDao.suggestTest(patientMedicalTest);
            System.out.println("Test suggested successfully.");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void suggestPrescriptions() {
        try{
            Scanner sc = new Scanner(System.in);
            String prescriptionId = doctorDao.generatePrescriptionId();
            System.out.println("Generated Prescription ID: " + prescriptionId);

            // Suggesting a prescription
            System.out.println("Enter Appointment ID:");
            String appointmentId = sc.next();
            System.out.println("Enter Doctor ID:");
            String doctorId = sc.next();
            System.out.println("Enter Patient ID:");
            String patientId = sc.next();

            // Fetch tests for the patient
            String tests = doctorDao.getTestsForPatient(patientId);
            System.out.println("Tests for patient: " + tests);

            System.out.println("Enter Medicines:");
            String medicines = sc.next();

            Prescription prescription = new Prescription(
                    prescriptionId,
                    appointmentId,
                    doctorId,
                    patientId,
                    medicines,
                    tests,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            doctorDao.suggestPrescription(prescription);
            System.out.println("Prescription suggested successfully.");

            // Fetch and display prescriptions for a patient
            System.out.println("Enter Patient ID to fetch prescriptions:");
            String patientIdToFetch = sc.next();
            List<Prescription> prescriptions = doctorDao.getPrescriptionsByPatientId(patientIdToFetch);
            System.out.println("Prescriptions:");
            for (Prescription pres : prescriptions) {
                System.out.println(pres);
            }
        } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private String generateScheduleId(String doctorId, LocalDate date) {
        String query = "SELECT schedule_id FROM DoctorSchedules ORDER BY schedule_id DESC LIMIT 1";
        String lastId = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getString("schedule_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int nextId = 1;
        if (lastId != null && lastId.startsWith("S")) {
            nextId = Integer.parseInt(lastId.substring(1)) + 1;
        }
        return String.format("S%05d", nextId);
    }
    public static void printPattern(){
        System.out.println("-----------------------------------------------------------------------------");
    }


}
