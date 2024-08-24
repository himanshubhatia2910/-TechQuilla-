package application.backend.beans;

import java.time.LocalDateTime;
import java.util.Date;

public class MedicalTest {
    private String testId;
    private String testName;
    private String testDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MedicalTest() {
        testId=null;
        testName=null;
        testDescription=null;
        createdAt=null;
        updatedAt=null;
    }

    public MedicalTest(String testId, String testName, String testDescription, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.testId = testId;
        this.testName = testName;
        this.testDescription = testDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
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
        return "Test Id:'" + testId+"\t"+
                "Test Name:'" + testName + "\t"+
                "Test Description:'" + testDescription;
    }
}
