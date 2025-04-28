package dao;

import entity.Appointment;
import java.util.List;

public interface IHospitalService {
    Appointment getAppointmentById(int appointmentId) throws Exception;
    List<Appointment> getAppointmentsForPatient(int patientId) throws Exception;
    List<Appointment> getAppointmentsForDoctor(int doctorId) throws Exception;
    boolean scheduleAppointment(Appointment appointment) throws Exception;
    boolean updateAppointment(Appointment appointment) throws Exception;
    boolean cancelAppointment(int appointmentId) throws Exception;
}
