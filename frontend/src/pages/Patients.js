import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Patients() {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/Med_ify_war_exploded/PatientsServlet?type=json")
      .then((res) => {
        console.log("Fetch status:", res.status);
        return res.json();
      })
      .then((data) => {
        console.log("Fetched patients:", data);
        setPatients(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        setLoading(false);
      });
  }, []);

  return (
    <div>
      <h2>Patients</h2>

      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginTop: 20,
        }}
      >

        {/* Navigate to Add Patient Page */}
        <button
          className="button"
          onClick={() => navigate("/patients/add")}
          style={{ backgroundColor: "#2563EB", color: "white" }}
        >
          Add Patient
        </button>
      </div>

      <table className="table" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>DOB</th>
            <th>Gender</th>
            <th>Address</th>
          </tr>
        </thead>

        <tbody>
          {loading ? (
            <tr>
              <td colSpan="5" style={{ textAlign: "center" }}>
                Loading...
              </td>
            </tr>
          ) : patients.length === 0 ? (
            <tr>
              <td colSpan="5" style={{ textAlign: "center" }}>
                No patients found.
              </td>
            </tr>
          ) : (
            patients.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.name}</td>
                <td>{p.dob}</td>
                <td>{p.gender}</td>
                <td>{p.address}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
