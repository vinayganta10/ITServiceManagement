/* eslint-disable no-unused-vars */

import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Container,
  Card,
  CardContent,
  Typography,
  Chip,
  Stack,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

const Tickets = () => {
    const [tickets, setTickets] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const token = localStorage.getItem("token");

      const res = await axios.get("http://localhost:8080/api/getAllTickets", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setTickets(res.data);
      console.log(tickets);
    } catch (err) {
      console.error("Error fetching tickets", err);
    }
  };

  return (
    <div>Tickets</div>
  )
}

export default Tickets;