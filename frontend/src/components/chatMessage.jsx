import { Box, Typography } from "@mui/material";

const ChatMessage = ({ msg, isOwn }) => {
  return (
    <Box
      sx={{
        alignSelf: isOwn ? "flex-end" : "flex-start",
        backgroundColor: isOwn ? "#1976d2" : "#e0e0e0",
        color: isOwn ? "white" : "black",
        px: 2,
        py: 1,
        borderRadius: 2,
        maxWidth: "70%",
      }}
    >
      <Typography variant="body2">{msg.message}</Typography>
      <Typography variant="caption">
        {new Date(msg.createdAt).toLocaleTimeString()}
      </Typography>
    </Box>
  );
};

export default ChatMessage;
