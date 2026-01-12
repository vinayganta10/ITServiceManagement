import {
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";

const DomainFilter = ({ domains, selectedDomain, setSelectedDomain }) => {
  return (
    <FormControl fullWidth sx={{ mb: 3 }}>
      <InputLabel>Domain</InputLabel>
      <Select
        value={selectedDomain}
        label="Domain"
        onChange={(e) => setSelectedDomain(e.target.value)}
      >
        <MenuItem value="ALL">All Domains</MenuItem>

        {domains.map((domain) => (
          <MenuItem key={domain} value={domain}>
            {domain}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
};

export default DomainFilter;
