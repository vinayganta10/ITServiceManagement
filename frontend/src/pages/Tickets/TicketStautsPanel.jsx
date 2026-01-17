import {
  Paper,
  Typography,
  Stepper,
  Step,
  StepLabel,
  Button,
  Stack,
  Divider,
} from "@mui/material";

const steps = ["OPEN", "IN_PROGRESS", "CLOSED"];

const TicketStatusPanel = ({ ticket,ticketStatus, isAssignedAgent, onStatusChange }) => {
  const activeStep = steps.indexOf(ticketStatus);

  return (
    <Paper
      sx={{
        p: 3,
        height: "100%",
        position: "sticky",
        top: 90,
        margin:0
      }}
    >
      <Typography variant="h6" fontWeight="bold" gutterBottom>
        Ticket Progress
      </Typography>

      <Stepper activeStep={activeStep} orientation="vertical">
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label.replace("_", " ")}</StepLabel>
          </Step>
        ))}
      </Stepper>

      <Divider sx={{ my: 2 }} />

      {isAssignedAgent ? (
        <Stack spacing={1}>
          {ticketStatus === "OPEN" && (
            <Button
              variant="contained"
              onClick={() => onStatusChange("IN_PROGRESS")}
            >
              Move to In Progress
            </Button>
          )}

          {ticketStatus === "IN_PROGRESS" && (
            <Button
              variant="contained"
              color="success"
              onClick={() => onStatusChange("CLOSED")}
            >
              Mark as Closed
            </Button>
          )}
        </Stack>
      ) : (
        <Typography variant="caption" color="text.secondary">
          Only assigned agent can change status
        </Typography>
      )}
    </Paper>
  );
};

export default TicketStatusPanel;
