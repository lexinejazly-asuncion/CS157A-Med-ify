package medify.Classes;

import java.sql.Timestamp;

public class Appointments {
    private int appointmentID;
    private int patientID;
    private int doctorID;
    private Timestamp apptTime;
    private String status;

    public Appointments(int appointmentID, int patientID, int doctorID, Timestamp apptTime, String status){
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.apptTime = apptTime;
        this.status = status;
    }

    public int getAppointmentID() { return appointmentID; }
    public int getPatientID() { return patientID; }
    public int getDoctorID() { return doctorID; }
    public Timestamp getApptTime() { return apptTime; }
    public String getStatus() { return status; }

    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }
    public void setApptTime(Timestamp apptTime) { this.apptTime = apptTime; }
    public void setStatus(String status) { this.status = status; }
}
