package application.backend.beans;

import application.backend.enums.Roles;

import java.time.LocalDateTime;
import java.util.Date;

//Collection of all hospital entities like Doctor,User(Staff),Admin
public class Users {

    private String userId;
    private String name;
    private String password;

    //Enum for Role contain DOCTOR,ADMIN,USER(Staff).
    private Roles role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Users() {
        userId=null;
        name=null;
        password=null;
        role=null;
        createdAt=null;
        updatedAt=null;
    }

    public Users(String userId, String name, String password, Roles role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
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
        return "Users{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
