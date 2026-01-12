import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Box,
  Button,
} from "@mui/material";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import PersonIcon from "@mui/icons-material/Person";
import LogoutIcon from "@mui/icons-material/Logout";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function NavBar() {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();

  const isLoggedIn = !!localStorage.getItem("token");

  // ✅ Detect auth pages
  const isAuthPage =
    location.pathname === "/login" || location.pathname === "/signup";

  const handleLogout = () => {
    logout();
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <AppBar
      position="sticky"
      elevation={1}
      sx={{
        backgroundColor: "#ffffff",
        color: "#1976d2",
      }}
    >
      <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
        {/* Logo */}
        <Typography
          variant="h6"
          component={Link}
          to="/"
          sx={{
            textDecoration: "none",
            color: "#1976d2",
            fontWeight: 700,
            letterSpacing: 1,
          }}
        >
          ITSM
        </Typography>

        {/* Right Section */}
        <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
          {/* ❌ Hide buttons on login/signup pages */}
          {!isAuthPage && !isLoggedIn && (
            <>
              <Button
                component={Link}
                to="/login"
                variant="outlined"
                sx={{ fontWeight: 600 }}
              >
                Login
              </Button>

              <Button
                component={Link}
                to="/signup"
                variant="contained"
                sx={{ fontWeight: 600 }}
              >
                Sign Up
              </Button>
            </>
          )}

          {/* ✅ Logged in user */}
          {isLoggedIn && (
            <>
              <IconButton
                component={Link}
                to="/tickets"
                sx={{ color: "#1976d2" }}
              >
                <ConfirmationNumberIcon fontSize="large" />
              </IconButton>

              <IconButton
                component={Link}
                to="/profile"
                sx={{ color: "#1976d2" }}
              >
                <PersonIcon fontSize="large" />
              </IconButton>

              <IconButton
                onClick={handleLogout}
                sx={{ color: "#d32f2f" }}
              >
                <LogoutIcon fontSize="large" />
              </IconButton>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
}
