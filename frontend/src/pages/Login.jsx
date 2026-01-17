import { useNavigate, useLocation } from "react-router-dom";
import {
  TextField,
  Button,
  Snackbar,
  Alert,
  Box,
  Typography,
  Paper,
} from "@mui/material";
import axios from "axios";
import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";

export default function Login() {
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");
  const location = useLocation();

  const redirectTo = location.state?.redirectTo || "/";

  const [toastOpen, setToastOpen] = useState(false);
  const [errorToast, setErrorToast] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (email.length === 0 || password.length === 0) {
      setError("Please enter username and password to login");
      setErrorToast(true);
      return;
    }
    try {
      const res = await axios.post("http://localhost:8080/auth/login",{
        email,password
      })
      console.log(res.status);

      if (res.status === 401) {
        setError("Username or password is incorrect");
        setErrorToast(true);
      } else if (res.status === 200) {
        login(res.data);
        setUsername(email.split(".")[0]);
        setToastOpen(true);
        setTimeout(() => navigate(redirectTo, { replace: true }), 1200);
      }
    } catch (err) {
      console.error("Login failed", err);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "linear-gradient(135deg, #1976d2, #42a5f5)",
      }}
    >
      <Paper
        elevation={10}
        sx={{
          width: 400,
          p: 4,
          borderRadius: 3,
          animation: "fadeSlide 0.8s ease-in-out",
        }}
      >
        <Typography variant="h5" textAlign="center" mb={3}>
          ITSM Login
        </Typography>

        <TextField
          label="Email"
          fullWidth
          margin="normal"
          onChange={(e) => setEmail(e.target.value)}
        />

        <TextField
          label="Password"
          type="password"
          fullWidth
          margin="normal"
          onChange={(e) => setPassword(e.target.value)}
        />

        <Button
          variant="contained"
          fullWidth
          sx={{ mt: 3, py: 1.2 }}
          onClick={handleLogin}
        >
          Login
        </Button>
        <Typography
          variant="body2"
          textAlign="center"
          sx={{ mt: 2, cursor: "pointer", color: "primary.main" }}
          onClick={() => navigate("/signup")}
        >
          Create an account?
        </Typography>
        <Snackbar
          open={toastOpen}
          autoHideDuration={3000}
          onClose={() => setToastOpen(false)}
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
        >
          <Alert severity="success" variant="filled">
            Hi {username}!
          </Alert>
        </Snackbar>
        <Snackbar
          open={errorToast}
          autoHideDuration={2000}
          onClose={() => setToastOpen(false)}
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
        >
          <Alert
            severity="error"
            variant="filled"
            onClose={() => setErrorToast(false)}
          >
            Username or password is incorrect
          </Alert>
        </Snackbar>
      </Paper>

      {/* Animation */}
      <style>
        {`
          @keyframes fadeSlide {
            from {
              opacity: 0;
              transform: translateY(20px);
            }
            to {
              opacity: 1;
              transform: translateY(0);
            }
          }
        `}
      </style>
    </Box>
  );
}
