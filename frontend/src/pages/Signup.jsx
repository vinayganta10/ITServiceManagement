import {
  TextField,
  Button,
  Box,
  Typography,
  Paper,
  MenuItem,
  IconButton,
  InputAdornment,
  Snackbar,
  Alert,
  Link,
} from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [type, setType] = useState("");

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [toastOpen, setToastOpen] = useState(false);

  const navigate = useNavigate();

  const passwordsMatch = password.length > 0 && password === confirmPassword;

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const isEmailValid = emailRegex.test(email);

  const handleSignup = async () => {
    try {
      await axios.post("http://localhost:8080/auth/signup", {
        name,
        email,
        password,
        type,
      });

      // show success toast
      setToastOpen(true);

      // redirect after short delay
      setTimeout(() => navigate("/login"), 2000);
    } catch (err) {
      console.error("Signup failed", err);
    }
  };

  return (
    <>
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
            width: 420,
            p: 4,
            borderRadius: 3,
            animation: "fadeSlide 0.8s ease-in-out",
          }}
        >
          <Typography variant="h5" textAlign="center" mb={3}>
            Create Account
          </Typography>

          <TextField
            label="Name"
            fullWidth
            margin="normal"
            onChange={(e) => setName(e.target.value)}
          />

          <TextField
            label="Email"
            fullWidth
            margin="normal"
            onChange={(e) => setEmail(e.target.value)}
            error={email.length > 0 && !isEmailValid}
            helperText={
              email.length > 0 && !isEmailValid
                ? "Enter a valid email address"
                : ""
            }
          />

          {/* Password */}
          <TextField
            label="Password"
            type={showPassword ? "text" : "password"}
            fullWidth
            margin="normal"
            onChange={(e) => setPassword(e.target.value)}
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    onClick={() => setShowPassword(!showPassword)}
                    edge="end"
                  >
                    {showPassword ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              ),
            }}
          />

          {/* Confirm Password */}
          <TextField
            label="Confirm Password"
            type={showConfirmPassword ? "text" : "password"}
            fullWidth
            margin="normal"
            onChange={(e) => setConfirmPassword(e.target.value)}
            error={confirmPassword.length > 0 && !passwordsMatch}
            helperText={
              confirmPassword.length > 0 && !passwordsMatch
                ? "Passwords do not match"
                : ""
            }
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                    edge="end"
                  >
                    {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              ),
            }}
          />

          <TextField
            select
            label="Account Type"
            fullWidth
            margin="normal"
            value={type}
            onChange={(e) => setType(e.target.value)}
          >
            <MenuItem value="user">User</MenuItem>
            <MenuItem value="agent">Agent</MenuItem>
          </TextField>

          <Button
            variant="contained"
            fullWidth
            sx={{ mt: 3, py: 1.2 }}
            disabled={
              !name ||
              !email ||
              !isEmailValid ||
              !password ||
              !confirmPassword ||
              !type ||
              !passwordsMatch
            }
            onClick={handleSignup}
          >
            Sign Up
          </Button>
          <Typography variant="body2" textAlign="center" sx={{ mt: 2 }}>
            Already have an account?{" "}
            <Link
              component="button"
              variant="body2"
              onClick={() => navigate("/login")}
              sx={{ fontWeight: 600 }}
            >
              Please login
            </Link>
          </Typography>
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

      {/* Success Toast */}
      <Snackbar
        open={toastOpen}
        autoHideDuration={3000}
        onClose={() => setToastOpen(false)}
        anchorOrigin={{ vertical: "top", horizontal: "center" }}
      >
        <Alert severity="success" variant="filled">
          Account created! Please login.
        </Alert>
      </Snackbar>
    </>
  );
}
