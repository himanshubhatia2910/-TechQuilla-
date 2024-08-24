package application.backend.dao;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.enums.AppointmentStatus;
import application.backend.exceptions.SlotAlreadyBookedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StaffDaoImpl implements StaffDao{
    @Override
    public boolean addNewPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO Patients (patient_id, name, date_of_birth, contact_info, address, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patient.getPatientId());
            statement.setString(2, patient.getName());
            statement.setObject(3, patient.getBirthDate());
            statement.setString(4, patient.getContactInfo());
            statement.setString(5, patient.getAddress());
            statement.setObject(6, patient.getCreatedAt());
            statement.setObject(7, patient.getUpdatedAt());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
    @Override
    public int getNextPatientId() throws SQLException {
        String query = "SELECT MAX(CAST(SUBSTRING(patient_id, 2) AS UNSIGNED)) FROM Patients";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1; // If no records, start from 1
            }
        }
    }

    @Override
    public void bookAppointment(Appointments appointment) throws SQLException, SlotAlreadyBookedException {
        // Check if the slot is already booked
        String checkQuery = "SELECT COUNT(*) FROM Appointments WHERE doctor_id = ? AND appointment_date = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setString(1, appointment.getDoctorId());
            checkStatement.setObject(2, appointment.getAppointmentDate());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SlotAlreadyBookedException("The selected time slot is already booked.");
            }
        }

        // If slot is available, proceed to book the appointment
        String insertQuery = "INSERT INTO Appointments (appointment_id, patient_id, doctor_id, appointment_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            insertStatement.setString(1, appointment.getAppointmentId());
            insertStatement.setString(2, appointment.getPatientId());
            insertStatement.setString(3, appointment.getDoctorId());
            insertStatement.setObject(4, appointment.getAppointmentDate());
            insertStatement.setString(5, appointment.getAppointmentStatus().toString());

            insertStatement.executeUpdate();
        }
    }
    public List<Doctor> getAvailableDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT doctor_id, name, specialization FROM Doctors";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getString("doctor_id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSpecialty(resultSet.getString("specialization"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }
    public String generateAppointmentId() throws SQLException {
        String query = "SELECT appointment_id FROM Appointments ORDER BY appointment_id DESC LIMIT 1";
        String lastId = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getString("appointment_id");
            }
        }
        int nextId = 1;
        if (lastId != null && lastId.startsWith("A")) {
            nextId = Integer.parseInt(lastId.substring(1)) + 1;
        }
        return String.format("A%05d", nextId);
    }
    public List<Appointments> getAppointmentsByDateRange(String doctorId, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String sql = "SELECT * FROM Appointments WHERE doctor_id = ?";
        if (startDate != null && endDate != null) {
            sql += " AND appointment_date BETWEEN ? AND ?";
        }
        List<Appointments> appointmentsList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, doctorId);
            if (startDate != null && endDate != null) {
                preparedStatement.setObject(2, startDate);
                preparedStatement.setObject(3, endDate);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointments appointment = new Appointments();
                appointment.setAppointmentId(resultSet.getString("appointment_id"));
                appointment.setPatientId(resultSet.getString("patient_id"));
                appointment.setDoctorId(resultSet.getString("doctor_id"));
                appointment.setAppointmentDate(resultSet.getObject("appointment_date", LocalDateTime.class));
                appointment.setAppointmentStatus(AppointmentStatus.valueOf(resultSet.getString("status")));
                appointmentsList.add(appointment);
            }
        }
        return appointmentsList;
    }
}
