import { Box, Button, Container, Typography, Grid } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Lottie from "lottie-react";
import ticketAnimation from "../assets/ticket.json";
import DomainCards from "../components/DomainCards";

export default function Home() {
  const navigate = useNavigate();

  return (
    <Box sx={{ minHeight: "100vh", backgroundColor: "#0a192f" }}>
      {/* HERO */}
      <Box
        sx={{
          minHeight: "100vh",
          display: "flex",
          alignItems: "center",
          backgroundImage:
            "linear-gradient(rgba(10,25,47,0.9), rgba(10,25,47,0.9)), url('https://images.unsplash.com/photo-1537432376769-00a5b08e1a5c')",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <Container maxWidth="lg">
          <Grid
            container
            alignItems="center"
            justifyContent="space-between"
            spacing={6}
          >
            {/* LEFT CONTENT */}
            <Grid item xs={12} md={6}>
              <Typography
                variant="h3"
                sx={{
                  color: "#ffffff",
                  fontWeight: 700,
                  mb: 2,
                  animation: "fadeUp 0.8s ease",
                }}
              >
                IT Service Management
              </Typography>

              <Typography
                variant="h6"
                sx={{
                  color: "#cfd8dc",
                  mb: 4,
                  maxWidth: 520,
                  lineHeight: 1.6,
                  animation: "fadeUp 1s ease",
                }}
              >
                Seamlessly manage incidents, service requests, and support
                operations with a centralized ticketing platform built for
                modern IT teams.
              </Typography>

              <Button
                variant="contained"
                size="large"
                sx={{
                  px: 4,
                  py: 1.4,
                  fontWeight: 600,
                  backgroundColor: "#4fc3f7",
                  color: "#0a192f",
                  borderRadius: 2,
                  animation: "fadeUp 1.2s ease",
                  "&:hover": {
                    backgroundColor: "#29b6f6",
                  },
                }}
                onClick={() => navigate("/createTicket")}
              >
                Create a Ticket
              </Button>
            </Grid>

            {/* RIGHT LOTTIE */}
            <Grid item xs={12} md={6}>
              <Box
                sx={{
                  width: "100%",
                  maxWidth: 420,
                  mx: "auto",
                  animation: "float 3s ease-in-out infinite",
                }}
              >
                <Lottie animationData={ticketAnimation} loop />
              </Box>
            </Grid>
          </Grid>
        </Container>
      </Box>
      <DomainCards/>
      {/* BOTTOM CTA */}
      <Box
        sx={{
          py: 8,
          background: "linear-gradient(135deg, #102a43, #0a192f)",
          textAlign: "center",
        }}
      >
        <Container maxWidth="md">
          <Typography
            variant="h4"
            sx={{
              color: "#ffffff",
              fontWeight: 700,
              mb: 2,
            }}
          >
            Done have an account?
          </Typography>

          <Typography
            variant="h6"
            sx={{
              color: "#cfd8dc",
              mb: 4,
            }}
          >
            Raise a ticket and get assistance from the right team — faster and
            smarter.
          </Typography>

          <Button
            variant="contained"
            size="large"
            sx={{
              px: 5,
              py: 1.5,
              fontWeight: 600,
              fontSize: "1rem",
              borderRadius: 2,
              backgroundColor: "#4fc3f7",
              color: "#0a192f",
              "&:hover": {
                backgroundColor: "#29b6f6",
              },
            }}
            onClick={() => navigate("/signup")}
          >
            Create an Account now
          </Button>
        </Container>
      </Box>

      {/* FOOTER */}
      <Box
        sx={{
          py: 2,
          textAlign: "center",
          backgroundColor: "#081423",
          color: "#90caf9",
        }}
      >
        <Typography variant="body2">
          © {new Date().getFullYear()} IT Service Management System
        </Typography>
        <Typography variant="caption">Reliable • Secure • Scalable</Typography>
      </Box>

      {/* ANIMATIONS */}
      <style>
        {`
          @keyframes fadeUp {
            from {
              opacity: 0;
              transform: translateY(20px);
            }
            to {
              opacity: 1;
              transform: translateY(0);
            }
          }

          @keyframes float {
            0% { transform: translateY(0); }
            50% { transform: translateY(-12px); }
            100% { transform: translateY(0); }
          }
        `}
      </style>
    </Box>
  );
}
