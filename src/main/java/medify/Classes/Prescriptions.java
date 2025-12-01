package medify.Classes;

import java.sql.Date;

public class Prescriptions {
    private int prescriptionID;
    private Date prescriptionDate;
    private String prescriptionName;
    private String dose;
    private int quantity;
    private int refills;
    private String patientName;
    private String doctorName;
    private String prescriptionStatus;


    //Constructor for a new Prescription object to initializes its attributes.
    public Prescriptions(int prescriptionID, Date prescriptionDate, String prescriptionName, String dose, int quantity, int refills, String patientName, String doctorName, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionDate = prescriptionDate;
        this.prescriptionName = prescriptionName;
        this.dose = dose;
        this.quantity = quantity;
        this.refills = refills;
        this.patientName = patientName; // Perform INNER JOIN with Patients to get patientName from patientID
        this.doctorName = doctorName; // Perform INNER JOIN with Doctors to get doctorName from issuedByDoctorID
        this.prescriptionStatus = prescriptionStatus;
    }

    //Methods to get data
    public int getPrescriptionID() { return prescriptionID; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public String getPrescriptionName() { return prescriptionName; }
    public String getDose() { return dose; }
    public int getQuantity() { return quantity; }
    public int getRefills() { return refills; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getPrescriptionStatus() { return prescriptionStatus; }

    //Methods to set data
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public void setPrescriptionName(String prescriptionName) { this.prescriptionName = prescriptionName; }
    public void setDose(String dose) { this.dose = dose; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setRefills(int refills) { this.refills = refills; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setPrescriptionStatus(String prescriptionStatus) { this.prescriptionStatus = prescriptionStatus; }
}
