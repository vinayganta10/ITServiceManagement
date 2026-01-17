import { Box, Paper, Typography } from "@mui/material";

const ChatMessage = ({ msg, isOwn }) => {
  return (
    <Box
      display="flex"
      justifyContent={isOwn ? "flex-end" : "flex-start"}
    >
      <Paper
        sx={{
          p: 1.5,
          maxWidth: "70%",
          backgroundColor: isOwn ? "#DCF8C6" : "#FFFFFF",
        }}
      >
        {/* Sender Name */}
        {!isOwn && (
          <Typography
            variant="caption"
            sx={{ fontWeight: "bold", display: "block" }}
          >
            {msg.senderName}
          </Typography>
        )}

        {/* Message */}
        <Typography variant="body1">
          {msg.message}
        </Typography>
      </Paper>
    </Box>
  );
};

export default ChatMessage;
