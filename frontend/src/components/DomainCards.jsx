import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  Grid,
} from "@mui/material";
import BuildIcon from "@mui/icons-material/Build";
import ComputerIcon from "@mui/icons-material/Computer";
import MemoryIcon from "@mui/icons-material/Memory";
import NetworkCheckIcon from "@mui/icons-material/NetworkCheck";

const domains = [
  {
    title: "Software Repair",
    description: "Application issues, crashes, OS errors and fixes",
    icon: <ComputerIcon fontSize="large" />,
  },
  {
    title: "Hardware Repair",
    description: "Laptop, desktop, printer and peripheral issues",
    icon: <BuildIcon fontSize="large" />,
  },
  {
    title: "Software Asset Request",
    description: "Request licensed software and tools",
    icon: <MemoryIcon fontSize="large" />,
  },
  {
    title: "Network Repair",
    description: "WiFi, VPN, LAN and connectivity problems",
    icon: <NetworkCheckIcon fontSize="large" />,
  },
];

export default function DomainCards() {
  return (
    <Box
      sx={{
        py: 8,
        backgroundColor: "#0a192f",
      }}
    >
      <Container maxWidth="lg">
        <Typography
          variant="h4"
          sx={{
            color: "#ffffff",
            fontWeight: 700,
            textAlign: "center",
            mb: 5,
          }}
        >
          Our Support Domains
        </Typography>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
            gap: 4,
          }}
        >
          {domains.map((domain, index) => (
            <Card
              key={index}
              sx={{
                backgroundColor: "#102a43",
                color: "#ffffff",
                borderRadius: 3,
                transition: "all 0.3s ease",
                cursor: "pointer",
                "&:hover": {
                  transform: "translateY(-8px)",
                  boxShadow: "0 12px 30px rgba(0,0,0,0.4)",
                },
              }}
            >
              <CardContent sx={{ textAlign: "center" }}>
                <Box sx={{ mb: 2, color: "#4fc3f7" }}>{domain.icon}</Box>

                <Typography variant="h6" fontWeight={600} gutterBottom>
                  {domain.title}
                </Typography>

                <Typography variant="body2" sx={{ color: "#cfd8dc" }}>
                  {domain.description}
                </Typography>
              </CardContent>
            </Card>
          ))}
        </Box>
      </Container>
    </Box>
  );
}
