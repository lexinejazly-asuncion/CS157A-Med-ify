package medify.Classes;

public class Doctors {
    private int doctorID;
    private String doctorName;
    private String phoneNumber;
    private String department;

    public Doctors(int doctorID, String doctorName, String phoneNumber, String department){
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public int getDoctorID() { return doctorID; }
    public String getDoctorName() { return doctorName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDepartment() { return department; }

    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setDepartment(String department) { this.department = department; }
}
