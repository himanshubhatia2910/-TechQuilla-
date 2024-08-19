package application.backend.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Patient {
    private String patientId;
    private String name;
    private LocalDate birthDate;
    private String contactInfo;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Patient() {
        patientId=null;
        name=null;
        birthDate=null;
        contactInfo =null;
        address=null;
        createdAt=null;
        updatedAt=null;
    }

    public Patient(String patientId, String name, LocalDate birthDate, String contactInfo, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.patientId = patientId;
        this.name = name;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "Patient ID:" + patientId +"\t\tName:"+name+"\t\tBirth Date:"+birthDate+"\t\tContact Info:"+contactInfo+"\t\tAddress:"+address;
    }
}
