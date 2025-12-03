import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import { Outlet } from "react-router-dom";

export default function Layout({ userInfo, onLogout }) {
  return (
    <div style={{ display: "flex", height: "100vh" }}>
      <Sidebar />

      <div style={{ flexGrow: 1 }}>
        <Navbar userInfo={userInfo} onLogout={onLogout} />
        <div className="container">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
