package medify.Classes;

import java.sql.Date;

public class MedicalRecords {
    private int patientID;
    private int doctorID;
    private int prescriptionID;
    private Date visitDate;

    public MedicalRecords(int patientID, int doctorID, int prescriptionID, Date visitDate) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.prescriptionID = prescriptionID;
        this.visitDate = visitDate;
    }

    public int getPatientID() { return patientID; }
    public int getDoctorID() { return doctorID; }
    public int getPrescriptionID() { return prescriptionID; }
    public Date getVisitDate() { return visitDate; }

    public void setPatientID(int patientID) { this.patientID = patientID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }
}
