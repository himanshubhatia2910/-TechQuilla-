package application.backend.service;

import application.backend.beans.DoctorSchedule;

import java.sql.SQLException;
import java.util.List;

public interface DoctorService {
    int addScheduleForNextThreeDays(String doctorId, List<DoctorSchedule> schedules) throws SQLException;

    void suggestMedicalTest();

    void suggestPrescriptions();
}
