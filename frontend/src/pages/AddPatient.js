import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function AddPatient() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [dob, setDob] = useState(""); // will be YYYY-MM-DD automatically from <input type="date">
  const [gender, setGender] = useState("Male");
  const [address, setAddress] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch(
        "http://localhost:8080/Med_ify_war_exploded/PatientsServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            patientName: name,
            dob: dob,          
            gender,
            address,
          }),
        }
      );

      if (res.ok) {
        alert("Patient added successfully!");
        navigate("/patients");
      } else {
        alert("Failed to add patient");
      }
    } catch (err) {
      console.error("POST error:", err);
      alert("Failed to add patient");
    }
  };

  return (
    <div>
      <h2>Add New Patient</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            required
          />
        </div>

        <div>
          <label>Date of Birth:</label>
          <input
            type="date"   
            value={dob}
            onChange={(e) => setDob(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Gender:</label>
          <select 
            value={gender} 
            onChange={(e) => setGender(e.target.value)}
          >
            <option>Male</option>
            <option>Female</option>
          </select>
        </div>

        <div>
          <label>Address:</label>
          <input
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            required
          />
        </div>

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}
