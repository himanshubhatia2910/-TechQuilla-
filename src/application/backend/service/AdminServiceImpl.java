package application.backend.service;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.dao.AdminDao;
import application.backend.dao.AdminDaoImpl;
import application.backend.dao.LoginDao;
import application.backend.dao.LoginDaoImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService{
    private final AdminDao adminDao;
    private final ObjectMapper objectMapper;

    public AdminServiceImpl() {
        this.adminDao = new AdminDaoImpl();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Register module for Java 8 date/time
    }

    @Override
    public int importDoctorsFromJson(String jsonFilePath) {
        List<Doctor> doctors = null;
        try {
            doctors = objectMapper.readValue(
                    new File(jsonFilePath),
                    new TypeReference<List<Doctor>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int count=0;
        for (Doctor doctor : doctors) {
            try {
                if (!adminDao.doesDoctorExist(doctor.getDoctorId())) {
                    adminDao.addDoctor(doctor);
                }
                else {
                    count++;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException{
        try {
            return adminDao.getAllDoctors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointments> getNextThreeDaysAppointments(String doctorId) {
        try {
            return adminDao.getNextThreeDaysAppointments(doctorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointments> getCurrentDayAppointments(String doctorId) throws SQLException {
        return adminDao.getCurrentDayAppointments(doctorId);
    }

    @Override
    public Appointments cancelCurrentDayAppointment(String doctorId, String appointmentId) throws SQLException {
        return adminDao.cancelCurrentDayAppointment(doctorId, appointmentId);
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        try {
            return adminDao.getAllPatients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
