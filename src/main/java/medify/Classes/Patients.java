package medify.Classes;

public class Patients {
    private int patientID;
    private String patientName;
    private java.sql.Date dob;
    private String gender;
    private String address;

    public Patients(int patientID, String patientName, java.sql.Date dob, String gender, String address){
        this.setPatientID(patientID);
        this.setPatientName(patientName);
        this.setDOB(dob);
        this.setGender(gender);
        this.setAddress(address);
    }

    public int getPatientID(){ return patientID; }
    public String getPatientName(){ return patientName; }
    public java.sql.Date getDOB(){ return dob; }
    public String getGender(){ return gender; }
    public String getAddress(){ return address; }

    public void setPatientID(int patientID){ this.patientID = patientID; }
    public void setPatientName(String patientName){ this.patientName = patientName; }
    public void setDOB(java.sql.Date dob){  this.dob = dob; }
    public void setGender(String gender){  this.gender = gender; }
    public void setAddress(String address){  this.address = address; }
}
