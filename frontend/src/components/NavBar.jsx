import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Box,
} from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import PersonIcon from "@mui/icons-material/Person";
import LogoutIcon from "@mui/icons-material/Logout";
import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function Navbar() {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
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
            fontWeight: 600,
          }}
        >
          ITSM
        </Typography>

        {/* Icons */}
        <Box>
          <IconButton component={Link} to="/" sx={{ color: "#1976d2" }}>
            <HomeIcon />
          </IconButton>

          <IconButton
            component={Link}
            to="/tickets"
            sx={{ color: "#1976d2" }}
          >
            <ConfirmationNumberIcon />
          </IconButton>

          <IconButton
            component={Link}
            to="/profile"
            sx={{ color: "#1976d2" }}
          >
            <PersonIcon />
          </IconButton>

          <IconButton onClick={handleLogout} sx={{ color: "#d32f2f" }}>
            <LogoutIcon />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
}
