import { createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const[name,setName] = useState(
    localStorage.getItem("user")
  )
  const [role,setRole] = useState(
    localStorage.getItem("role")
  );

  const [token, setToken] = useState(
    localStorage.getItem("token")
  );

  const login = (data) => {
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("name",data.name);
    setRole(data.role);
    setToken(data.token);
  };

  const logout = () => {
    localStorage.clear();
    setToken(null);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ token, role , name, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
