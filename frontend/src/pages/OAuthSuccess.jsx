import { useEffect, useContext,useRef } from "react";
import { useNavigate } from "react-router-dom";
import {AuthContext} from "../context/AuthContext"

export default function OAuthSuccess() {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);
  const ranRef = useRef(false);

  useEffect(() => {
if (ranRef.current) return; // ✅ PREVENT second run
    ranRef.current = true;

    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");
    const role = params.get("role");
    const name = params.get("name");
    const data ={token,role,name};
    console.log(data);
    
    if (data) {
      login(data);
      navigate("/", { replace: true });
    }
  }, []);

  return null;
}
