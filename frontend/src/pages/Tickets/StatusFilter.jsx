import { Stack, Chip } from "@mui/material";

const STATUS_OPTIONS = ["ALL", "OPEN", "IN_PROGRESS", "RESOLVED"];

const StatusFilter = ({ status, setStatus }) => {
  return (
    <Stack direction="row" spacing={1} sx={{ mb: 3 }}>
      {STATUS_OPTIONS.map((option) => (
        <Chip
          key={option}
          label={option}
          clickable
          color={status === option ? "primary" : "default"}
          onClick={() => setStatus(option)}
        />
      ))}
    </Stack>
  );
};

export default StatusFilter;
