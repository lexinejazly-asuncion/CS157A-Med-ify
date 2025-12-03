import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Prescriptions() {
  const [prescriptions, setPrescriptions] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/Med_ify_war_exploded/PrescriptionsServlet?type=json")
      .then((res) => res.json())
      .then((data) => {
        console.log("Fetched prescriptions:", data);
        setPrescriptions(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        setLoading(false);
      });
  }, []);

  return (
    <div>
      <h2>Prescriptions</h2>

      {/* BUTTONS JUST LIKE PATIENTS.JS */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginTop: 20,
        }}
      >
        <button
          className="button"
          onClick={() => navigate("/prescriptions/add")}
          style={{ backgroundColor: "#2563EB", color: "white" }}
        >
          Add Prescription
        </button>

        <button
          className="button"
          onClick={() => navigate("/prescriptions/update")}
          style={{ backgroundColor: "#2563EB", color: "white" }}
        >
          Update Prescription
        </button>
      </div>

      <table className="table" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Patient ID</th>
            <th>Patient Name</th>
            <th>Medication</th>
            <th>Dose</th>
            <th>Quantity</th>
            <th>Refills</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {loading ? (
            <tr>
              <td colSpan="9" style={{ textAlign: "center" }}>
                Loading...
              </td>
            </tr>
          ) : prescriptions.length === 0 ? (
            <tr>
              <td colSpan="9" style={{ textAlign: "center" }}>
                No prescriptions found.
              </td>
            </tr>
          ) : (
            prescriptions.map((p) => (
              <tr key={p.prescriptionID}>
                <td>{p.prescriptionID}</td>
                <td>{p.prescriptionDate}</td>
                <td>{p.patientID}</td>
                <td>{p.patientName}</td>
                <td>{p.prescriptionName}</td>
                <td>{p.dose}</td>
                <td>{p.quantity}</td>
                <td>{p.refills}</td>
                <td>{p.prescriptionStatus}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
