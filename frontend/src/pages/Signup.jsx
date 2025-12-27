import {
  TextField,
  Button,
  Box,
  Typography,
  Paper,
  MenuItem,
} from "@mui/material";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [type, setType] = useState("");

  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      await axios.post("http://localhost:8080/auth/signup", {
        name,
        email,
        password,
        type,
      });

      // Redirect to login after successful signup
      navigate("/login");
    } catch (err) {
      console.error("Signup failed", err);
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
        />

        <TextField
          label="Password"
          type="password"
          fullWidth
          margin="normal"
          onChange={(e) => setPassword(e.target.value)}
        />

        <TextField
          select
          label="Account Type"
          fullWidth
          margin="normal"
          value={type}
          onChange={(e) => setType(e.target.value)}
        >
          <MenuItem value="USER">User</MenuItem>
          <MenuItem value="AGENT">Agent</MenuItem>
        </TextField>

        <Button
          variant="contained"
          fullWidth
          sx={{ mt: 3, py: 1.2 }}
          onClick={handleSignup}
        >
          Sign Up
        </Button>
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
