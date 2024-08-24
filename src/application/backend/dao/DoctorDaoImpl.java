package application.backend.dao;

import application.backend.beans.DoctorSchedule;
import application.backend.beans.MedicalTest;
import application.backend.beans.PatientMedicalTest;
import application.backend.beans.Prescription;

import javax.print.Doc;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao{
    private Connection connection;

    public DoctorDaoImpl () {
        this.connection = DBUtil.getConnection();
    }

    @Override
    public boolean addDoctorSchedule(DoctorSchedule schedule) throws SQLException {
        String query = "INSERT INTO DoctorSchedules (schedule_id, doctor_id, date, start_time, end_time, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, schedule.getScheduleId());
            preparedStatement.setString(2, schedule.getDoctorId());

            // Convert LocalDate to java.sql.Date
            LocalDate date = schedule.getDate();
            preparedStatement.setDate(3, Date.valueOf(date));

            // Convert LocalTime to java.sql.Time
            LocalTime startTime = schedule.getStartTime();
            preparedStatement.setTime(4, Time.valueOf(startTime));

            LocalTime endTime = schedule.getEndTime();
            preparedStatement.setTime(5, Time.valueOf(endTime));

            preparedStatement.setString(6, schedule.getScheduleStatus().name());

            // You can store LocalDateTime as a timestamp or use null
            preparedStatement.setTimestamp(7, null); // created_at
            preparedStatement.setTimestamp(8, null); // updated_at

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Error adding doctor schedule: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<MedicalTest> getAllMedicalTests() throws SQLException {
        List<MedicalTest> tests = new ArrayList<>();
        String query = "SELECT * FROM MedicalTests";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String testId = resultSet.getString("test_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");

                MedicalTest test = new MedicalTest(
                        testId,
                        name,
                        description,
                        createdAt.toLocalDateTime(),
                        updatedAt.toLocalDateTime()
                );
                tests.add(test);
            }
        }
        return tests;
    }
    @Override
    public void suggestTest(PatientMedicalTest patientMedicalTest) throws SQLException {
        String query = "INSERT INTO patient_medical_tests (patient_id, test_id, suggested_at) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patientMedicalTest.getPatientId());
            preparedStatement.setString(2, patientMedicalTest.getTestId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(patientMedicalTest.getSuggestedAt()));

            preparedStatement.executeUpdate();
        }
    }
    @Override
    public List<MedicalTest> getSuggestedTests(String patientId) throws SQLException {
        List<MedicalTest> tests = new ArrayList<>();
        String query = "SELECT mt.test_id, mt.name, mt.description " +
                "FROM patient_medical_tests pmt " +
                "JOIN MedicalTests mt ON pmt.test_id = mt.test_id " +
                "WHERE pmt.patient_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String testId = resultSet.getString("test_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");

                    MedicalTest test = new MedicalTest(testId, name, description, null, null);
                    tests.add(test);
                }
            }
        }
        return tests;
    }
    @Override
    public void suggestPrescription(Prescription prescription) throws SQLException {
        String query = "INSERT INTO Prescriptions (prescription_id, appointment_id, doctor_id, patient_id, medicines, tests, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, prescription.getPrescriptionId());
            preparedStatement.setString(2, prescription.getAppointmentId());
            preparedStatement.setString(3, prescription.getDoctorId());
            preparedStatement.setString(4, prescription.getPatientId());
            preparedStatement.setString(5, prescription.getMedicines());
            preparedStatement.setString(6, prescription.getTests());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(prescription.getCreatedAt()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(prescription.getUpdatedAt()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(String patientId) throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM Prescriptions WHERE patient_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String prescriptionId = resultSet.getString("prescription_id");
                    String appointmentId = resultSet.getString("appointment_id");
                    String doctorId = resultSet.getString("doctor_id");
                    String patientIdResult = resultSet.getString("patient_id");
                    String medicines = resultSet.getString("medicines");
                    String tests = resultSet.getString("tests");
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

                    Prescription prescription = new Prescription(prescriptionId, appointmentId, doctorId, patientIdResult, medicines, tests, createdAt, updatedAt);
                    prescriptions.add(prescription);
                }
            }
        }
        return prescriptions;
    }

    @Override
    // Method to auto-generate prescription ID
    public String generatePrescriptionId() throws SQLException {
        String query = "SELECT COUNT(*) FROM Prescriptions";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1) + 1;
                return "PR" + String.format("%05d", count);
            }
        }
        return "PR00001"; // Default if there are no existing prescriptions
    }

    @Override
    // Method to get tests for a patient
    public String getTestsForPatient(String patientId) throws SQLException {
        StringBuilder tests = new StringBuilder();
        String query = "SELECT test_id FROM patient_medical_tests WHERE patient_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tests.append(resultSet.getString("test_id")).append(", ");
                }
            }
        }
        // Remove trailing comma and space
        if (tests.length() > 0) {
            tests.setLength(tests.length() - 2);
        }
        return tests.toString();
    }
}
