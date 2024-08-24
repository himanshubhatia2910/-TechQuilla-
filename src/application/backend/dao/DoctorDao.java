package application.backend.dao;

import application.backend.beans.DoctorSchedule;
import application.backend.beans.MedicalTest;
import application.backend.beans.PatientMedicalTest;
import application.backend.beans.Prescription;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface DoctorDao {
    boolean addDoctorSchedule(DoctorSchedule doctorSchedule) throws SQLException;
    List<MedicalTest> getAllMedicalTests() throws SQLException;
    void suggestTest(PatientMedicalTest patientMedicalTest) throws SQLException;
    List<MedicalTest> getSuggestedTests(String patientId) throws SQLException;
    void suggestPrescription(Prescription prescription) throws SQLException;
    List<Prescription> getPrescriptionsByPatientId(String patientId) throws SQLException;
    String generatePrescriptionId() throws SQLException;
    String getTestsForPatient(String patientId) throws SQLException;
}
