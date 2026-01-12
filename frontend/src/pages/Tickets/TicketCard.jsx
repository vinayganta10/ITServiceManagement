import { Card, CardContent, Typography, Chip, Stack } from "@mui/material";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

const TicketCard = ({ ticket }) => {
  const navigate = useNavigate();
  const {role} = useContext(AuthContext);

  const getStatusColor = (status) => {
      switch (status) {
        case "OPEN":
          return "error";
        case "IN_PROGRESS":
          return "warning";
        case "RESOLVED":
          return "success";
        default:
          return "primary";
      }
    };

  return (
    <Card
      variant="outlined"
      onClick={() => navigate(`/ticket/${ticket.id}`)}
      sx={{
        borderRadius: 2,
        cursor: "pointer",
        transition: "0.3s",
        "&:hover": { boxShadow: 3 },
      }}
    >
      <CardContent>
        <Stack spacing={1}>
          {/* Subject */}
          <Typography variant="h6" fontWeight="bold">
            {ticket.subject}
          </Typography>

          {/* Domain */}
          <Chip
            label={ticket.domain}
            size="small"
            sx={{ alignSelf: "flex-start", mt: 1 }}
          />

          {/* Raised by*/}
          {role !== "user" && (
            <Typography variant="caption" color="text.secondary">
              Raised By: {ticket.raisedBy.name}
            </Typography>
          )}

          {/* Status + Assigned */}
          <Stack
            direction="row"
            justifyContent="space-between"
            alignItems="center"
            mt={2}
          >
            <Chip
              label={ticket.status}
              color={getStatusColor(ticket.status)}
              size="small"
            />

            <Typography variant="caption" color="text.secondary">
              Agent: {ticket.assignedTo ? ticket.assignedTo.name : "Unassigned"}
            </Typography>
          </Stack>

          {/* Dates */}
          <Typography variant="caption" color="text.secondary" mt={1}>
            Created:{" "}
            {ticket.dateOfCreation
              ? new Date(ticket.dateOfCreation).toLocaleString()
              : "-"}
          </Typography>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default TicketCard;
