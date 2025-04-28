package main;

import dao.HospitalServiceImpl;
import dao.IHospitalService;
import entity.Appointment;
import exception.PatientNumberNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        IHospitalService service = new HospitalServiceImpl();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Get Appointment by ID");
            System.out.println("2. Get Appointments for Patient");
            System.out.println("3. Get Appointments for Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String choiceStr = sc.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Appointment ID: ");
                        int aid = Integer.parseInt(sc.nextLine().trim());
                        Appointment a = service.getAppointmentById(aid);
                        System.out.println(a);
                        break;
                    case 2:
                        System.out.print("Enter Patient ID: ");
                        int pid = Integer.parseInt(sc.nextLine().trim());
                        List<Appointment> plist = service.getAppointmentsForPatient(pid);
                        plist.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter Doctor ID: ");
                        int did = Integer.parseInt(sc.nextLine().trim());
                        List<Appointment> dlist = service.getAppointmentsForDoctor(did);
                        dlist.forEach(System.out::println);
                        break;
                    case 4:
                        System.out.println("Enter details:");
                        System.out.print("Patient ID: ");
                        int spid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Doctor ID: ");
                        int sdocid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Appointment Date (YYYY-MM-DD): ");
                        String sdate = sc.nextLine().trim();
                        System.out.print("Description: ");
                        String sdesc = sc.nextLine().trim();
                        Appointment sapp = new Appointment(0, spid, sdocid, sdate, sdesc);
                        boolean scheduled = service.scheduleAppointment(sapp);
                        System.out.println(scheduled ? "Scheduled successfully" : "Failed to schedule");
                        break;
                    case 5:
                        System.out.println("Enter details:");
                        System.out.print("Appointment ID: ");
                        int uaid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Patient ID: ");
                        int upid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Doctor ID: ");
                        int udocid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Appointment Date (YYYY-MM-DD): ");
                        String udate = sc.nextLine().trim();
                        System.out.print("Description: ");
                        String udesc = sc.nextLine().trim();
                        Appointment uapp = new Appointment(uaid, upid, udocid, udate, udesc);
                        boolean updated = service.updateAppointment(uapp);
                        System.out.println(updated ? "Updated successfully" : "Failed to update");
                        break;
                    case 6:
                        System.out.print("Enter Appointment ID to cancel: ");
                        int caid = Integer.parseInt(sc.nextLine().trim());
                        boolean cancelled = service.cancelAppointment(caid);
                        System.out.println(cancelled ? "Cancelled successfully" : "Failed to cancel");
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (PatientNumberNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
