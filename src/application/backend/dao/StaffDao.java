package application.backend.dao;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.exceptions.SlotAlreadyBookedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface StaffDao {
    boolean addNewPatient(Patient patient) throws SQLException;
    public int getNextPatientId() throws SQLException;

    void bookAppointment(Appointments appointment) throws SQLException, SlotAlreadyBookedException;

    List<Doctor> getAvailableDoctors() throws SQLException;

    String generateAppointmentId() throws SQLException;

    List<Appointments> getAppointmentsByDateRange(String doctorId, LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
}
