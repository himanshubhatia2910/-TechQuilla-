package application.backend.dao;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.enums.AppointmentStatus;
import application.backend.enums.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDaoImpl implements AdminDao{
    Connection connection;

    public AdminDaoImpl() {
        connection = DBUtil.getConnection();
    }

    public boolean doesDoctorExist(String doctorId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Doctors WHERE doctor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctorId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        String insertUserQuery = "INSERT INTO Users (user_id, name, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        String insertDoctorQuery = "INSERT INTO Doctors (doctor_id,user_id, name, specialization, contact_info, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery);
             PreparedStatement doctorStatement = connection.prepareStatement(insertDoctorQuery)) {

            // Insert into Users table
            userStatement.setString(1, doctor.getUserId());
            userStatement.setString(2, doctor.getName());
            userStatement.setString(3, doctor.getPassword());
            userStatement.setString(4, Roles.DOCTOR.toString());
            userStatement.setObject(5, doctor.getCreatedAt());
            userStatement.setObject(6, doctor.getUpdatedAt());
            userStatement.executeUpdate();

            // Insert into Doctors table
            doctorStatement.setString(1, doctor.getDoctorId());
            doctorStatement.setString(2, doctor.getUserId());
            doctorStatement.setString(3, doctor.getName());
            doctorStatement.setString(4, doctor.getSpecialty());
            doctorStatement.setString(5, doctor.getContactInfo());
            doctorStatement.setObject(6, doctor.getCreatedAt());
            doctorStatement.setObject(7, doctor.getUpdatedAt());
            doctorStatement.executeUpdate();
        }
        catch (SQLException e){
            connection.rollback();
            System.out.println("Duplicate Entry");
        }
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM Doctors"; // Adjust the query as needed
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getString("doctor_id"));
                doctor.setUserId(resultSet.getString("user_id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSpecialty(resultSet.getString("specialization"));
                doctor.setContactInfo(resultSet.getString("contact_info"));
                doctor.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                doctor.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
                doctors.add(doctor);
            }
        }
        return doctors;
    }


    @Override
    public List<Appointments> getNextThreeDaysAppointments(String doctorId) throws SQLException {
        List<Appointments> appointments = new ArrayList<>();
        // Set up the timezone for IST
        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");

        // Get current time and time 3 days later in IST
        ZonedDateTime nowInIST = ZonedDateTime.now(istZoneId);
        LocalDateTime now = nowInIST.toLocalDateTime();
        LocalDateTime threeDaysLater = now.plusDays(3);

        String query = "SELECT * FROM Appointments WHERE doctor_id = ? AND appointment_date BETWEEN ? AND ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, doctorId);
            statement.setObject(2, now);
            statement.setObject(3, threeDaysLater);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Appointments appointment = new Appointments();
                    appointment.setAppointmentId(resultSet.getString("appointment_id"));
                    appointment.setPatientId(resultSet.getString("patient_id"));
                    appointment.setDoctorId(resultSet.getString("doctor_id"));
                    appointment.setAppointmentDate(resultSet.getObject("appointment_date", LocalDateTime.class));
                    appointment.setAppointmentStatus(AppointmentStatus.valueOf(resultSet.getString("status")));
                    appointment.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                    appointment.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    @Override
    public List<Appointments> getCurrentDayAppointments(String doctorId) throws SQLException {
        List<Appointments> appointments = new ArrayList<>();
        // Set up the timezone for IST
        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");

        // Get current time and current date in IST
        ZonedDateTime nowInIST = ZonedDateTime.now(istZoneId);
        LocalDateTime startOfDay = nowInIST.toLocalDateTime().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        String query = "SELECT * FROM Appointments WHERE doctor_id = ? AND appointment_date BETWEEN ? AND ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, doctorId);
            statement.setObject(2, startOfDay);
            statement.setObject(3, endOfDay);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Appointments appointment = new Appointments();
                    appointment.setAppointmentId(resultSet.getString("appointment_id"));
                    appointment.setPatientId(resultSet.getString("patient_id"));
                    appointment.setDoctorId(resultSet.getString("doctor_id"));
                    appointment.setAppointmentDate(resultSet.getObject("appointment_date", LocalDateTime.class));
                    appointment.setAppointmentStatus(AppointmentStatus.valueOf(resultSet.getString("status")));
                    appointment.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                    appointment.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    @Override
    public Appointments cancelCurrentDayAppointment(String doctorId, String appointmentId) throws SQLException {
        // Set up the timezone for IST
        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime nowInIST = ZonedDateTime.now(istZoneId);
        LocalDateTime startOfDay = nowInIST.toLocalDateTime().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        String checkQuery = "SELECT * FROM Appointments WHERE doctor_id = ? AND appointment_id = ? AND appointment_date BETWEEN ? AND ?";
        String updateQuery = "UPDATE Appointments SET status = 'CANCELLED' WHERE doctor_id = ? AND appointment_id = ?";
        Appointments updatedAppointment = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setString(1, doctorId);
            checkStatement.setString(2, appointmentId);
            checkStatement.setObject(3, startOfDay);
            checkStatement.setObject(4, endOfDay);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Appointment exists within the current day
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, doctorId);
                        updateStatement.setString(2, appointmentId);
                        updateStatement.executeUpdate();
                    }

                    // Fetch the updated appointment record
                    String fetchQuery = "SELECT * FROM Appointments WHERE appointment_id = ?";
                    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery)) {
                        fetchStatement.setString(1, appointmentId);
                        try (ResultSet resultSetUpdated = fetchStatement.executeQuery()) {
                            if (resultSetUpdated.next()) {
                                updatedAppointment = new Appointments();
                                updatedAppointment.setAppointmentId(resultSetUpdated.getString("appointment_id"));
                                updatedAppointment.setPatientId(resultSetUpdated.getString("patient_id"));
                                updatedAppointment.setDoctorId(resultSetUpdated.getString("doctor_id"));
                                updatedAppointment.setAppointmentDate(resultSetUpdated.getObject("appointment_date", LocalDateTime.class));
                                updatedAppointment.setAppointmentStatus(AppointmentStatus.valueOf(resultSetUpdated.getString("status")));
                                updatedAppointment.setCreatedAt(resultSetUpdated.getObject("created_at", LocalDateTime.class));
                                updatedAppointment.setUpdatedAt(resultSetUpdated.getObject("updated_at", LocalDateTime.class));
                            }
                        }
                    }
                }
            }
        }
        return updatedAppointment;
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> doctors = new ArrayList<>();
        String query = "SELECT * FROM Patients"; // Adjust the query as needed
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Patient p = new Patient();
                p.setPatientId(resultSet.getString("patient_id"));
                p.setName(resultSet.getString("name"));
                p.setBirthDate(resultSet.getObject("date_of_birth", LocalDate.class));
                p.setContactInfo(resultSet.getString("contact_info"));
                p.setAddress(resultSet.getString("address"));
                p.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                p.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
                doctors.add(p);
            }
        }
        return doctors;
    }
}
