package application.backend.beans;

import application.backend.enums.ScheduleStatus;

import java.time.LocalDateTime;
import java.util.Date;

public class DoctorSchedule {
    private String scheduleId;
    private String doctorId;
    private Date date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
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

    public DoctorSchedule(String scheduleId, String doctorId, Date date, LocalDateTime startTime, LocalDateTime endTime, ScheduleStatus scheduleStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
