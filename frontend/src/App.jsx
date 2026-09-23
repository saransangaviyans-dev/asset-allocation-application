import "./index.css";
import { useState } from "react";
import EmployeeDashboard from "./components/EmployeeDashboard";
import AdminDashboard from "./components/AdminDashboard";

function App() {
  const [currentUser, setCurrentUser] = useState({ id: 6, role: "EMPLOYEE" });

  return (
    <div className="min-h-screen bg-gray-50 font-sans text-gray-800">
      {/* Clean Professional Navbar */}
      <nav className="bg-blue-600 text-white shadow-md py-4 px-8 flex justify-between items-center">
        <h1 className="text-2xl font-bold tracking-wide">
          Asset Allocation Platform
        </h1>

        <div className="flex items-center gap-3 bg-blue-700 px-3 py-1.5 rounded-md border border-blue-500">
          <label className="text-sm font-medium text-blue-100">
            Simulated Role:{" "}
          </label>
          <select
            className="bg-white text-gray-900 border-none rounded text-sm p-1 focus:ring-2 focus:ring-blue-300"
            value={currentUser.role}
            onChange={(e) =>
              setCurrentUser({
                id: e.target.value === "ADMIN" ? 1 : 6,
                role: e.target.value,
              })
            }
          >
            <option value="EMPLOYEE">Employee (ID: 6)</option>
            <option value="ADMIN">Admin (ID: 1)</option>
          </select>
        </div>
      </nav>

      {/* Main Content Area */}
      <main className="max-w-6xl mx-auto mt-8 px-4">
        {currentUser.role === "ADMIN" ? (
          <AdminDashboard currentUser={currentUser} />
        ) : (
          <EmployeeDashboard currentUser={currentUser} />
        )}
      </main>
    </div>
  );
}

export default App;
