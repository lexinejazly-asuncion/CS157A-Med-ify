import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function MedicalRecords() {
  const navigate = useNavigate();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);

  // Load medical records from Tomcat API
  useEffect(() => {
    fetch("http://localhost:8080/Med_ify_war_exploded/MedicalRecordsServlet?type=json")
      .then((res) => res.json())
      .then((data) => {
        console.log("Fetched records:", data);
        setRecords(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        setLoading(false);
      });
  }, []);

  return (
    <div>
      <h2>Medical Records</h2>

      {/* ADD NEW RECORD BUTTON */}
      <button
        className="button"
        style={{ marginTop: 15, background: "#2563EB", color: "white" }}
        onClick={() => navigate("/medical-records/add")}
      >
        Add Medical Record
      </button>

      <table className="table" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>Patient ID</th>
            <th>Doctor ID</th>
            <th>Prescription ID</th>
            <th>Visit Date</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {loading ? (
            <tr>
              <td colSpan="5" style={{ textAlign: "center" }}>Loading...</td>
            </tr>
          ) : records.length === 0 ? (
            <tr>
              <td colSpan="5" style={{ textAlign: "center" }}>No records found.</td>
            </tr>
          ) : (
            records.map((r, index) => (
              <tr key={index}>
                <td>{r.patientID}</td>
                <td>{r.doctorID}</td>
                <td>{r.prescriptionID}</td>
                <td>{r.visitDate}</td>

                {/* EDIT BUTTON */}
                <td>
                  <button
                    style={{
                      padding: "6px 12px",
                      background: "#10B981",
                      color: "white",
                      border: "none",
                      borderRadius: "4px",
                      cursor: "pointer",
                    }}
                    onClick={() =>
                      navigate(
                        `/medical-records/edit?mode=update&patientID=${r.patientID}&doctorID=${r.doctorID}&prescriptionID=${r.prescriptionID}&visitDate=${r.visitDate}`
                      )
                    }
                  >
                    Edit
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
