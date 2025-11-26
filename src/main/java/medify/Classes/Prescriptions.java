package medify.Classes;

import java.sql.Date;

public class Prescriptions {
    private int prescriptionID;
    private int appointmentID;
    private Date prescriptionDate;
    private int issuedByDoctorID;

    public Prescriptions(int prescriptionID, int appointmentID, Date prescriptionDate, int issuedByDoctorID) {
        this.prescriptionID = prescriptionID;
        this.appointmentID = appointmentID;
        this.prescriptionDate = prescriptionDate;
        this.issuedByDoctorID = issuedByDoctorID;
    }

    public int getPrescriptionID() { return prescriptionID; }
    public int getAppointmentID() { return appointmentID; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public int getIssuedByDoctorID() { return issuedByDoctorID; }

    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public void setIssuedByDoctorID(int issuedByDoctorID) { this.issuedByDoctorID = issuedByDoctorID; }
}
