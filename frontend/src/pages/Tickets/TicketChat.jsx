import React, { useEffect, useState } from "react";
import axios from "axios";
import { Box, Stack, TextField, IconButton, Paper } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import ChatMessage from "../../components/chatMessage";
import {
  connectChatSocket,
  disconnectChatSocket,
  sendChatMessage,
} from "../chatSocket";

const TicketChat = ({ ticketId, token, userId }) => {
  const [messages, setMessages] = useState([]);
  const [text, setText] = useState("");

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/ticket/${ticketId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setMessages(res.data));
  }, [ticketId, token]);

  useEffect(() => {
    connectChatSocket(token, ticketId, (msg) => {
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
    <Paper
      elevation={0} 
      sx={{
        p: 2,
        height: "500px", 
        width: "100%",
        display: "flex",
        flexDirection: "column",
        bgcolor: "grey.50", 
        border: "1px solid",
        borderColor: "divider",
        boxSizing: "border-box",
      }}
    >
      <Stack spacing={2} sx={{ height: "100%", width: "100%" }}>
        {/* Message Area */}
        <Stack
          spacing={1}
          flex={1}
          sx={{
            overflowY: "auto",
            pr: 1, // Padding for scrollbar
            width: "100%",
          }}
        >
          {messages.map((msg, i) => (
            <ChatMessage key={i} msg={msg} isOwn={msg.senderId === userId} />
          ))}
        </Stack>

        {/* Input Area */}
        <Box
          display="flex"
          alignItems="center"
          sx={{
            pt: 2,
            borderTop: "1px solid",
            borderColor: "divider",
            width: "100%",
          }}
        >
          <TextField
            fullWidth
            size="small"
            placeholder="Type a message..."
            value={text}
            onChange={(e) => setText(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
            sx={{ bgcolor: "white" }}
          />
          <IconButton color="primary" onClick={handleSend} sx={{ ml: 1 }}>
            <SendIcon />
          </IconButton>
        </Box>
      </Stack>
    </Paper>
  );
};

export default TicketChat;
