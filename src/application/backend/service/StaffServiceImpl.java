package application.backend.service;

import application.backend.beans.Appointments;
import application.backend.beans.Doctor;
import application.backend.beans.Patient;
import application.backend.dao.DBUtil;
import application.backend.dao.StaffDao;
import application.backend.dao.StaffDaoImpl;
import application.backend.enums.AppointmentStatus;
import application.backend.enums.Roles;
import application.backend.exceptions.InvalidPatientIdException;
import application.backend.exceptions.SlotAlreadyBookedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffServiceImpl implements StaffService{
    StaffDao staffDao;

    public StaffServiceImpl() {
        staffDao = new StaffDaoImpl();
    }

    @Override
    public boolean addNewPatient() throws SQLException,InvalidPatientIdException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Please add Following details to add new Patient");
        System.out.println("Choose a way to assign Patient ID:");
        System.out.println("1. Auto-generate Patient ID");
        System.out.println("2. Enter Patient ID manually");
        int idChoice = sc.nextInt();

        String patientId = null;

        if (idChoice == 1) {
            try {
                patientId = generatePatientId();
                System.out.println("Generated Patient ID: " + patientId);
            } catch (SQLException e) {
                System.out.println("Error generating patient ID: " + e.getMessage());
            }
        } else if (idChoice == 2) {
            System.out.println("Enter Patient ID (must start with 'P' followed by 5 digits):");
            patientId = sc.next();
            try {
                validatePatientId(patientId);
            } catch (InvalidPatientIdException e) {
                throw new InvalidPatientIdException("Invalid Patient ID. It should start with 'P' followed by exactly 5 digits.");
            }
        } else {
            System.out.println("Invalid choice");
        }
        sc.nextLine();
        System.out.println("Enter Patient Name:");
        String name = sc.nextLine();
        System.out.println("Enter Date of Birth (DD/MM/YYYY):");
        String dob = sc.nextLine();
        System.out.println("Enter Contact Info:");
        String contactInfo = sc.next();
        System.out.println("Enter Address:");
        String address = sc.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfBirth = null;
        try {
            dateOfBirth = LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in DD/MM/YYYY format.");
        }

        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setName(name);
        patient.setBirthDate(dateOfBirth);
        patient.setContactInfo(contactInfo);
        patient.setAddress(address);
        patient.setCreatedAt(LocalDateTime.now());
        patient.setUpdatedAt(LocalDateTime.now());

        return staffDao.addNewPatient(patient);
    }
    public String generatePatientId() throws SQLException {
        int nextId = staffDao.getNextPatientId(); // Implement this in DAO to get the last ID and increment
        return String.format("P%05d", nextId);
    }

    public void validatePatientId(String patientId) throws InvalidPatientIdException {
        if (!patientId.matches("^P\\d{5}$")) {
            throw new InvalidPatientIdException("Invalid Patient ID. It should start with 'P' followed by exactly 5 digits.");
        }
    }

    @Override
    public void bookAppointment()throws SQLException, SlotAlreadyBookedException {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Available Doctors:");
            List<Doctor> doctors = staffDao.getAvailableDoctors();
            for (Doctor doctor : doctors) {
                System.out.println("ID: " + doctor.getDoctorId() + ", Name: " + doctor.getName() + ", Specialization: " + doctor.getSpecialty());
            }

            System.out.println("Enter Doctor ID to book an appointment:");
            String doctorId = sc.next();
            System.out.println("Enter Patient ID:");
            String patientId = sc.next();
            System.out.println("Enter Appointment Date and Time (DD/MM/YYYY HH:MM):");
            String appointmentDateTime = sc.next() + " " + sc.next();

            // Date format for DD/MM/YYYY HH:MM
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime appointmentDate;

            try {
                appointmentDate = LocalDateTime.parse(appointmentDateTime, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date and time format. Please use DD/MM/YYYY HH:MM format.");
                return;
            }

            // Generate a new appointment ID
            String appointmentId = staffDao.generateAppointmentId();

            // Create an appointment object
            Appointments appointment = new Appointments();
            appointment.setAppointmentId(appointmentId);
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);

            // Attempt to book the appointment
            staffDao.bookAppointment(appointment);
            System.out.println("Appointment booked successfully with ID: " + appointmentId);

        } catch (SlotAlreadyBookedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    @Override
    public List<Appointments> getCurrentDayAppointments(String doctorId) throws SQLException {
        LocalDate today = LocalDate.now();
        return staffDao.getAppointmentsByDateRange(doctorId, today.atStartOfDay(), today.atTime(LocalTime.MAX));
    }

    @Override
    public List<Appointments> getNextThreeDaysAppointments(String doctorId) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);
        return staffDao.getAppointmentsByDateRange(doctorId, today.atStartOfDay(), threeDaysLater.atTime(LocalTime.MAX));
    }

    @Override
    public List<Appointments> getNextWeekAppointments(String doctorId) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate oneWeekLater = today.plusWeeks(1);
        return staffDao.getAppointmentsByDateRange(doctorId, today.atStartOfDay(), oneWeekLater.atTime(LocalTime.MAX));
    }

    @Override
    public List<Appointments> getAllAppointments(String doctorId) throws SQLException {
        return staffDao.getAppointmentsByDateRange(doctorId, null, null);
    }
}
