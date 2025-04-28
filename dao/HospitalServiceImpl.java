package dao;

import entity.Appointment;
import exception.PatientNumberNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {
    private static final String DB_PROPS = "db.properties";

    @Override
    public Appointment getAppointmentById(int appointmentId) throws Exception {
        String sql = "SELECT * FROM Appointment WHERE appointmentId = ?";
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getString("appointmentDate"),
                        rs.getString("description")
                    );
                }
                return null;
            }
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) throws Exception {
        // Check if patient exists
        String checkSql = "SELECT patientId FROM Patient WHERE patientId = ?";
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, patientId);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (!rs.next()) {
                    throw new PatientNumberNotFoundException("Patient ID " + patientId + " not found.");
                }
            }
        }
        String sql = "SELECT * FROM Appointment WHERE patientId = ?";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getString("appointmentDate"),
                        rs.getString("description")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) throws Exception {
        String sql = "SELECT * FROM Appointment WHERE doctorId = ?";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getString("appointmentDate"),
                        rs.getString("description")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) throws Exception {
        String sql = "INSERT INTO Appointment (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setString(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getDescription());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean updateAppointment(Appointment appointment) throws Exception {
        String sql = "UPDATE Appointment SET patientId=?, doctorId=?, appointmentDate=?, description=? WHERE appointmentId=?";
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setString(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getDescription());
            ps.setInt(5, appointment.getAppointmentId());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean cancelAppointment(int appointmentId) throws Exception {
        String sql = "DELETE FROM Appointment WHERE appointmentId = ?";
        try (Connection conn = util.DBConnUtil.getConnection(DB_PROPS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
}
