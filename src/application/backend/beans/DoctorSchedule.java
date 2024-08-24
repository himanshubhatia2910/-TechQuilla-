package application.backend.beans;

import application.backend.enums.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DoctorSchedule {
    private String scheduleId;
    private String doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ScheduleStatus scheduleStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public DoctorSchedule() {
        scheduleId=null;
        doctorId=null;
        date=null;
        startTime=null;
        endTime=null;
        scheduleStatus=null;
        createdAt=null;
        updatedAt=null;
    }

    public DoctorSchedule(String scheduleId, String doctorId, LocalDate date, LocalTime startTime, LocalTime endTime, ScheduleStatus scheduleStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleStatus = scheduleStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
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
        return "DoctorSchedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", scheduleStatus=" + scheduleStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
