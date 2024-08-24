package application.backend.beans;

import java.time.LocalDateTime;

public class PatientMedicalTest {
    private String patientId;
    private String testId;
    private LocalDateTime suggestedAt;

    // Constructors, Getters, Setters
    public PatientMedicalTest() { }

    public PatientMedicalTest(String patientId, String testId, LocalDateTime suggestedAt) {
        this.patientId = patientId;
        this.testId = testId;
        this.suggestedAt = suggestedAt;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public LocalDateTime getSuggestedAt() {
        return suggestedAt;
    }

    public void setSuggestedAt(LocalDateTime suggestedAt) {
        this.suggestedAt = suggestedAt;
    }

    @Override
    public String toString() {
        return "PatientMedicalTest{" +
                "patientId='" + patientId + '\'' +
                ", testId='" + testId + '\'' +
                ", suggestedAt=" + suggestedAt +
                '}';
    }
}
