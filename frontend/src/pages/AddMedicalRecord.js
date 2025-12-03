import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

export default function AddMedicalRecord() {
  const navigate = useNavigate();
  const location = useLocation();

  const params = new URLSearchParams(location.search);
  const isUpdate = params.get("mode") === "update";

  const [patientID, setPatientID] = useState("");
  const [doctorID, setDoctorID] = useState("");
  const [prescriptionID, setPrescriptionID] = useState("");
  const [visitDate, setVisitDate] = useState("");

  // Prefill fields ONCE when entering update mode
  useEffect(() => {
    if (isUpdate) {
      setPatientID(params.get("patientID") || "");
      setDoctorID(params.get("doctorID") || "");
      setPrescriptionID(params.get("prescriptionID") || "");
      setVisitDate(params.get("visitDate") || "");
    }
  }, [isUpdate]);   // ← FIX: remove params from dependencies

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch(
        "http://localhost:8080/Med_ify_war_exploded/MedicalRecordsServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            patientID,
            doctorID,
            prescriptionID,
            visitDate,
            mode: isUpdate ? "update" : "insert",
          }),
        }
      );

      if (res.ok) {
        alert(isUpdate ? "Record Updated!" : "Record Added!");
        navigate("/medical-records");
      } else {
        alert("Failed to save record.");
      }
    } catch (err) {
      console.error("POST error:", err);
      alert("Server error");
    }
  };

  return (
    <div>
      <h2>{isUpdate ? "Update Medical Record" : "Add Medical Record"}</h2>

      <form onSubmit={handleSubmit} style={{ maxWidth: "400px", marginTop: "20px" }}>

        <div style={{ marginBottom: "15px" }}>
          <label>Patient ID:</label>
          <input
            type="number"
            value={patientID}
            onChange={(e) => setPatientID(e.target.value)}
            required
            disabled={isUpdate} // Prevent changing PK
            style={{ width: "100%", padding: "8px", marginTop: "5px" }}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Doctor ID:</label>
          <input
            type="number"
            value={doctorID}
            onChange={(e) => setDoctorID(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", marginTop: "5px" }}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Prescription ID:</label>
          <input
            type="number"
            value={prescriptionID}
            onChange={(e) => setPrescriptionID(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", marginTop: "5px" }}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Visit Date:</label>
          <input
            type="date"
            value={visitDate}
            onChange={(e) => setVisitDate(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", marginTop: "5px" }}
          />
        </div>

        <button type="submit" className="button">
          {isUpdate ? "Update" : "Submit"}
        </button>
      </form>
    </div>
  );
}
