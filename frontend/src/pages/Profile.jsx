import { useContext, useEffect, useState } from "react";
import axios from "axios";
import {
  Avatar,
  Box,
  Card,
  CardContent,
  CircularProgress,
  Divider,
  Grid,
  Typography,
} from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import EmailIcon from "@mui/icons-material/Email";
import BusinessIcon from "@mui/icons-material/Business";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import BadgeIcon from "@mui/icons-material/Badge";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { AuthContext } from "../context/AuthContext";

export default function Profile() {
  const { email } = useContext(AuthContext); // assuming user.email exists
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const {token} = useContext(AuthContext);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/getUserByEmail`,
          {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
        );
        setProfile(res.data);
      } catch (err) {
        console.error("Failed to load profile", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [email]);

  if (loading) {
    return (
      <Box sx={{ minHeight: "80vh", display: "flex", justifyContent: "center", alignItems: "center" }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box
      sx={{
        minHeight: "100vh",
        background: "linear-gradient(135deg, #e3f2fd, #bbdefb)",
        p: 4,
      }}
    >
      <Card
        sx={{
          maxWidth: 900,
          mx: "auto",
          borderRadius: 4,
          boxShadow: 6,
        }}
      >
        <CardContent sx={{ p: 4 }}>
          {/* Header */}
          <Box sx={{ display: "flex", alignItems: "center", gap: 3, mb: 3 }}>
            <Avatar sx={{ width: 80, height: 80, bgcolor: "primary.main" }}>
              <PersonIcon fontSize="large" />
            </Avatar>
            <Box>
              <Typography variant="h5">{profile.name}</Typography>
              <Typography color="text.secondary">{profile.type}</Typography>
            </Box>
          </Box>

          <Divider sx={{ mb: 3 }} />

          {/* Details */}
          <Grid container spacing={3}>
            <ProfileItem icon={<EmailIcon />} label="Email" value={profile.email} />
            <ProfileItem icon={<BusinessIcon />} label="Organization" value={profile.organization} />
            <ProfileItem icon={<LocationOnIcon />} label="Location" value={profile.location} />
            <ProfileItem icon={<BadgeIcon />} label="Role" value={profile.type} />
            <ProfileItem
              icon={<CalendarMonthIcon />}
              label="Joined On"
              value={new Date(profile.joinedAt).toLocaleDateString()}
            />
          </Grid>
        </CardContent>
      </Card>
    </Box>
  );
}

/* Reusable row */
function ProfileItem({ icon, label, value }) {
  return (
    <Grid item xs={12} sm={6}>
      <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
        {icon}
        <Box>
          <Typography variant="body2" color="text.secondary">
            {label}
          </Typography>
          <Typography variant="body1">{value || "-"}</Typography>
        </Box>
      </Box>
    </Grid>
  );
}
