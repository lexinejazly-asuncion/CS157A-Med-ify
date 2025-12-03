import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function UpdatePrescription() {
  const [form, setForm] = useState({
    prescriptionID: "",
    newStatus: ""
  });

  const navigate = useNavigate(); // <-- ADD THIS

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new URLSearchParams();
    data.append("mode", "update");
    data.append("prescriptionID", form.prescriptionID);
    data.append("newStatus", form.newStatus);

    const res = await fetch(
      "http://localhost:8080/Med_ify_war_exploded/PrescriptionsServlet",
      {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: data.toString(),
      }
    );

    if (res.ok) {
      alert("Status updated!");
      navigate("/prescriptions"); // <-- FIXED
    } else {
      alert("Error updating status.");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Update Prescription Status</h2>

      <form onSubmit={handleSubmit} style={{ maxWidth: 400 }}>
        <div>
          <label>Prescription ID:</label>
          <br />
          <input
            type="number"
            placeholder="e.g., 1"
            required
            value={form.prescriptionID}
            onChange={(e) =>
              setForm({ ...form, prescriptionID: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>New Status:</label>
          <br />
          <select
            required
            value={form.newStatus}
            onChange={(e) =>
              setForm({ ...form, newStatus: e.target.value })
            }
          >
            <option value="">Select Status</option>
            <option value="Processing">Processing</option>
            <option value="Filled">Filled</option>
            <option value="Completed">Completed</option>
          </select>
        </div>

        <button type="submit" style={{ marginTop: 15 }}>
          Update Status
        </button>
      </form>
    </div>
  );
}
