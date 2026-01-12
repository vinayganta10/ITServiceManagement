import { TextField } from "@mui/material";

const TicketSearchBar = ({ searchTerm, setSearchTerm }) => {
  return (
    <TextField
      fullWidth
      label="Search tickets"
      placeholder="Search by subject or description"
      value={searchTerm}
      onChange={(e) => setSearchTerm(e.target.value)}
      sx={{ mb: 3 }}
    />
  );
};

export default TicketSearchBar;
