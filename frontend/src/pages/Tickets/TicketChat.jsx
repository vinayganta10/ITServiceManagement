import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Box,
  Stack,
  TextField,
  IconButton,
  Paper,
} from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import ChatMessage from "../../components/chatMessage";
import {
  connectChatSocket,
  disconnectChatSocket,
  sendChatMessage,
} from "../chatSocket";

const TicketChat = ({ ticketId, token, userName }) => {
  const [messages, setMessages] = useState([]);
  const [text, setText] = useState("");

  // Load chat history
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/ticket/${ticketId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setMessages(res.data));
  }, [ticketId, token]);

  // WebSocket connection
  useEffect(() => {
    connectChatSocket(ticketId, (msg) => {
      setMessages((prev) => [...prev, msg]);
    });

    return () => disconnectChatSocket();
  }, [ticketId]);

  const handleSend = () => {
    if (!text.trim()) return;
    sendChatMessage(ticketId, text);
    setText("");
  };

  return (
    <Paper sx={{ p: 2, height: "100%" }}>
      <Stack spacing={2} height="100%">
        <Stack spacing={1} flex={1} overflow="auto">
          {messages.map((msg, i) => (
            <ChatMessage
              key={i}
              msg={msg}
              isOwn={msg.raisedBy?.id === userName?.id}
            />
          ))}
        </Stack>

        <Box display="flex">
          <TextField
            fullWidth
            placeholder="Type a message..."
            value={text}
            onChange={(e) => setText(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
          />
          <IconButton onClick={handleSend}>
            <SendIcon />
          </IconButton>
        </Box>
      </Stack>
    </Paper>
  );
};

export default TicketChat;
