package application.backend.service;

import application.backend.beans.Appointments;
import application.backend.exceptions.InvalidPatientIdException;
import application.backend.exceptions.SlotAlreadyBookedException;

import java.sql.SQLException;
import java.util.List;

public interface StaffService {
    boolean addNewPatient() throws SQLException,InvalidPatientIdException;
    String generatePatientId() throws SQLException;
    void validatePatientId(String patientId) throws InvalidPatientIdException;

    void bookAppointment()throws SQLException, SlotAlreadyBookedException;
    List<Appointments> getCurrentDayAppointments(String doctorId) throws SQLException;
    List<Appointments> getNextThreeDaysAppointments(String doctorId) throws SQLException;
    List<Appointments> getNextWeekAppointments(String doctorId) throws SQLException;
    List<Appointments> getAllAppointments(String doctorId) throws SQLException;
}
