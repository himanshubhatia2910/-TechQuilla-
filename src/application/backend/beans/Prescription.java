package application.backend.beans;

import java.time.LocalDateTime;
import java.util.Date;

public class Prescription {
    private String prescriptionId;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String medicines;
    private String tests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Prescription() {
        prescriptionId = null;
        appointmentId = null;
        patientId = null;
        doctorId = null;
        medicines = null;
        tests = null;
        createdAt = null;
        updatedAt = null;
    }

    public Prescription(String prescriptionId, String appointmentId, String patientId, String doctorId, String medicines, String tests, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicines = medicines;
        this.tests = tests;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", medicines='" + medicines + '\'' +
                ", tests='" + tests + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
