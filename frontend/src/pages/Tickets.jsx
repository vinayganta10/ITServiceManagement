import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import {
  Container,
  Typography,
  Stack,
  CircularProgress,
} from "@mui/material";
import { AuthContext } from "../context/AuthContext";
import TicketSearchBar from "./Tickets/TicketSearchBar";
import StatusFilter from "./Tickets/StatusFilter";
import DomainFilter from "./Tickets/DomainFilter";
import TicketCard from "./Tickets/TicketCard";

const Tickets = () => {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [domainFilter, setDomainFilter] = useState("ALL");
  const [searchTerm, setSearchTerm] = useState("");
  const { token } = useContext(AuthContext);
  useEffect(() => {
    if (token) fetchTickets();
  }, [token]);

  const domains = [...new Set(tickets.map((t) => t.domain))];

  const filteredTickets = tickets.filter((ticket) => {
    const matchesSearch = `${ticket.subject} ${ticket.description}`
      .toLowerCase()
      .includes(searchTerm.toLowerCase());

    const matchesStatus =
      statusFilter === "ALL" || ticket.status === statusFilter;

    const matchesDomain =
      domainFilter === "ALL" || ticket.domain === domainFilter;

    return matchesSearch && matchesStatus && matchesDomain;
  });

  const fetchTickets = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/getAllTickets", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(res.data);
      setTickets(res.data);
    } catch (err) {
      console.error("Error fetching tickets", err);
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

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Typography variant="h5" fontWeight="bold" gutterBottom>
        My Tickets
      </Typography>

      <TicketSearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />

      <StatusFilter status={statusFilter} setStatus={setStatusFilter} />

      <DomainFilter
        domains={domains}
        selectedDomain={domainFilter}
        setSelectedDomain={setDomainFilter}
      />
      {tickets.length === 0 ? (
        <Typography color="text.secondary">No tickets found</Typography>
      ) : (
        <Stack spacing={3}>
          {filteredTickets.map((ticket) => (
            <TicketCard key={ticket.id} ticket={ticket} />
          ))}
        </Stack>
      )}
    </Container>
  );
};

export default Tickets;
