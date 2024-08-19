package application.backend.service;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    int importDoctorsFromJson(String jsonFilePath);

    List<Doctor> getAllDoctors() throws SQLException;

    List<Appointments> getNextThreeDaysAppointments(String doctorId);

    List<Appointments> getCurrentDayAppointments(String docId) throws SQLException;

    Appointments cancelCurrentDayAppointment(String docId, String appId) throws SQLException;

    List<Patient> getAllPatients() throws SQLException;
}
