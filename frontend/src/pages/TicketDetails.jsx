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
} from "@mui/material";
import { AuthContext } from "../context/AuthContext";
import TicketChat from "./Tickets/TicketChat";

const TicketDetails = () => {
  const { id } = useParams();
  const { name, token } = useContext(AuthContext);
  const [ticket, setTicket] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTicket();
  }, [id]);

  const fetchTicket = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/getTicket/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setTicket(res.data);
    } catch (err) {
      console.error("Error loading ticket", err);
    } finally {
      setLoading(false);
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
    <Container maxWidth="md" sx={{ mt: 4 }}>
      {/* Header */}
      <Typography variant="h4" fontWeight="bold">
        {ticket.subject}
      </Typography>

      <Stack direction="row" spacing={1} mt={2}>
        <Chip label={ticket.status} />
        <Chip label={ticket.domain} />
      </Stack>

      <Divider sx={{ my: 3 }} />

      {/* Description */}
      <Typography variant="h6">Description</Typography>
      <Typography color="text.secondary" mt={1}>
        {ticket.description}
      </Typography>

      <Divider sx={{ my: 3 }} />

      {/* Meta */}
      <Stack spacing={1}>
        <Typography variant="body2">
          Raised By: {ticket.raisedBy?.name}
        </Typography>
        <Typography variant="body2">
          Assigned To: {ticket.assignedTo?.name ?? "Unassigned"}
        </Typography>
        <Typography variant="body2">
          Created:{" "}
          {new Date(ticket.dateOfCreation).toLocaleString()}
        </Typography>
      </Stack>

      <Divider sx={{ my: 4 }} />

      <Typography variant="h6">Discussion</Typography>
      <Divider sx={{ my: 4 }} />

      <TicketChat ticketId={id} token={token} userName={name}  />

    </Container>
  );
};

export default TicketDetails;
