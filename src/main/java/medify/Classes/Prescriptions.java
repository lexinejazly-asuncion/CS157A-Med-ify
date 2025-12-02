package medify.Classes;

import java.sql.Date;

public class Prescriptions {
    private int prescriptionID;
    private Date prescriptionDate;
    private String prescriptionName;
    private String dose;
    private int quantity;
    private int refills;
    private int patientID;
    private String patientName; //// Perform INNER JOIN with Patients to get patientName from patientID
    private String prescriptionStatus;


    //Constructor for a new Prescription object to initializes its attributes in the Prescriptions table
    public Prescriptions(int prescriptionID, Date prescriptionDate, int patientID, String patientName, String prescriptionName, String dose, int quantity, int refills, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionDate = prescriptionDate;
        this.patientID = patientID;
        this.patientName = patientName; // Perform INNER JOIN with Patients to get patientName from patientID
        this.prescriptionName = prescriptionName;
        this.dose = dose;
        this.quantity = quantity;
        this.refills = refills;
        this.prescriptionStatus = prescriptionStatus;
    }

    //Constructor for a new Prescription object for updating prescription status
    public Prescriptions(int prescriptionID, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }

    //Constructor for a new Prescription object for inserting a new prescription record
    public Prescriptions(Date date, int patientID, String prescriptionName, String dose, int quantity, int refills, String status){
        this.prescriptionDate = date;
        this.patientID = patientID;
        this.prescriptionName = prescriptionName;
        this.dose = dose;
        this.quantity = quantity;
        this.refills = refills;
        this.prescriptionStatus = status;
    }

    //Methods to get data
    public int getPrescriptionID() { return prescriptionID; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public String getPrescriptionName() { return prescriptionName; }
    public String getDose() { return dose; }
    public int getQuantity() { return quantity; }
    public int getRefills() { return refills; }
    public int getPatientID() { return patientID; }
    public String getPrescriptionStatus() { return prescriptionStatus; }
    public String getPatientName() { return patientName; }

    //Methods to set data
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public void setPrescriptionName(String prescriptionName) { this.prescriptionName = prescriptionName; }
    public void setDose(String dose) { this.dose = dose; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setRefills(int refills) { this.refills = refills; }
    public void setPatientID(int patientID) { this.patientID = patientID; }
    public void setPrescriptionStatus(String prescriptionStatus) { this.prescriptionStatus = prescriptionStatus; }
    public void setPatientName(String patientName) { this.patientName = patientName; }


}
