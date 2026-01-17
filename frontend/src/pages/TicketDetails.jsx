import { useParams } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import axios from "axios";
import {
  Container,
  Typography,
  Chip,
  Stack,
  Divider,
  CircularProgress,
  Grid,
  Paper,
  Box,
} from "@mui/material";
import { AuthContext } from "../context/AuthContext";
import TicketChat from "./Tickets/TicketChat";
import TicketStatusPanel from "./Tickets/TicketStautsPanel";

const TicketDetails = () => {
  const { id } = useParams();
  const { name, token } = useContext(AuthContext);
  const [ticket, setTicket] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState();
  const [ticketStatus, setTicketStatus] = useState("");

  useEffect(() => {
    fetchTicket();
  }, [id]);

  const fetchTicket = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/getTicket/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      const userDetails = await axios.get(`http://localhost:8080/api/getUser`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setUserId(userDetails.data.id);
      setTicket(res.data);
      setTicketStatus(res.data.status);
    } catch (err) {
      console.error("Error loading ticket", err);
    } finally {
      setLoading(false);
    }
  };

  const isAssignedAgent = ticket?.assignedTo?.id === userId;

  const updateStatus = async (newStatus) => {
    try {
      const res = await axios.post(
        `http://localhost:8080/api/updateStatusOfTicket`,
        {
          id: id,
          agentId: userId,
          status: newStatus,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setTicketStatus(newStatus);
    } catch (err) {
      console.error("Status update failed", err);
    }
  };

  if (loading) {
    return (
      <Stack alignItems="center" mt={6}>
        <CircularProgress />
      </Stack>
    );
  }

  if (!ticket) {
    return <Typography>Ticket not found</Typography>;
  }

  return (
    <Box sx={{ width: "100%", p: { xs: 2, md: 4 } }}>
      <Grid container spacing={3}>
        {/* LEFT MAIN CONTENT - Takes 9 columns (75%) */}
        <Grid item xs={12} lg={9}>
          <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: 2,
              border: "1px solid",
              borderColor: "divider",
              minHeight: "80vh",
            }}
          >
            {/* Header Section */}
            <Stack
              direction={{ xs: "column", sm: "row" }}
              justifyContent="space-between"
              alignItems="flex-start"
              spacing={2}
              mb={1}
            >
              <Typography
                variant="h4"
                fontWeight="800"
                sx={{ wordBreak: "break-word" }}
              >
                {ticket.subject}
              </Typography>
            </Stack>

            <Stack direction="row" spacing={1} mb={3}>
              <Chip
                label={ticket.status}
                color={ticket.status === "OPEN" ? "primary" : "default"}
                sx={{ fontWeight: "bold" }}
              />
              <Chip label={ticket.domain} variant="outlined" size="small" />
            </Stack>

            <Divider />

            {/* Description */}
            <Box sx={{ py: 3 }}>
              <Typography variant="subtitle1" fontWeight="bold">
                Description
              </Typography>
              <Typography
                color="text.secondary"
                sx={{ mt: 1, lineHeight: 1.6 }}
              >
                {ticket.description}
              </Typography>
            </Box>

            {/* Metadata Box */}
            <Box
              sx={{
                p: 3,
                bgcolor: "grey.50",
                borderRadius: 2,
                border: "1px dashed",
                borderColor: "divider",
              }}
            >
              <Grid container spacing={2}>
                <Grid item xs={12} sm={4}>
                  <Typography
                    variant="caption"
                    color="text.secondary"
                    fontWeight="bold"
                    display="block"
                  >
                    RAISED BY
                  </Typography>
                  <Typography variant="body2" fontWeight="600">
                    {ticket.raisedBy?.name || "Unknown"}
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <Typography
                    variant="caption"
                    color="text.secondary"
                    fontWeight="bold"
                    display="block"
                  >
                    ASSIGNED TO
                  </Typography>
                  <Typography variant="body2" fontWeight="600">
                    {ticket.assignedTo?.name || "Unassigned"}
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <Typography
                    variant="caption"
                    color="text.secondary"
                    fontWeight="bold"
                    display="block"
                  >
                    CREATED DATE
                  </Typography>
                  <Typography variant="body2" fontWeight="600">
                    {ticket.dateOfCreation
                      ? new Date(ticket.dateOfCreation).toLocaleString()
                      : "N/A"}
                  </Typography>
                </Grid>
              </Grid>
            </Box>

            <Divider sx={{ my: 4 }} />

            <Typography variant="h6" fontWeight="bold" gutterBottom>
              Discussion
            </Typography>
            {/*<TicketChat ticketId={id} token={token} userId={userId} />*/}
          </Paper>
        </Grid>

        {/* RIGHT STATUS PANEL - Takes 3 columns (25%) */}
        <Grid item xs={12} lg={3}>
          <TicketStatusPanel
            ticket={ticket}
            isAssignedAgent={isAssignedAgent}
            ticketStatus={ticketStatus}
            onStatusChange={updateStatus}
          />
        </Grid>
      </Grid>
    </Box>
  );
};

export default TicketDetails;
