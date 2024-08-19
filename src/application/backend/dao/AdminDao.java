package application.backend.dao;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    void addDoctor(Doctor doctor) throws SQLException;
    boolean doesDoctorExist(String doctorId) throws SQLException;

    List<Doctor> getAllDoctors() throws SQLException;

    List<Appointments> getNextThreeDaysAppointments(String doctorId) throws SQLException;

    List<Appointments> getCurrentDayAppointments(String doctorId) throws SQLException;

    Appointments cancelCurrentDayAppointment(String doctorId, String appointmentId) throws SQLException;

    List<Patient> getAllPatients() throws SQLException;
}
