import { useState, useContext } from "react";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Stack,
  Divider,
  CircularProgress,
  Zoom,
} from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import axios from "axios";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { Select, MenuItem, InputLabel, FormControl } from "@mui/material";

const DOMAIN_OPTIONS = [
  "Network issue",
  "Software issue",
  "Hardware issue",
  "Software request",
];

const CreateTicket = () => {
  const { token } = useContext(AuthContext);
  const navigate = useNavigate();

  const [subject, setSubject] = useState("");
  const [description, setDescription] = useState("");
  const [domain, setDomain] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await axios.post(
        "http://localhost:8080/api/addTicket",
        {
          subject,
          description,
          domain,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      const TicketId = res.data;
      setSuccess(true);

      // Navigate after short success animation
      setTimeout(() => {
        navigate(`/ticket/${TicketId}`);
      }, 1200);
    } catch (err) {
      console.error("Ticket creation failed", err);
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 6 }}>
      <Paper sx={{ p: 4 }}>
        {!success ? (
          <>
            <Typography variant="h4" fontWeight="bold">
              Create New Ticket
            </Typography>

            <Typography color="text.secondary" mt={1}>
              Describe your issue and our team will take care of it.
            </Typography>

            <Divider sx={{ my: 3 }} />

            <form onSubmit={handleSubmit}>
              <Stack spacing={3}>
                <TextField
                  label="Subject"
                  required
                  value={subject}
                  onChange={(e) => setSubject(e.target.value)}
                />

                <FormControl fullWidth required>
                  <InputLabel>Domain</InputLabel>
                  <Select
                    value={domain}
                    label="Domain"
                    onChange={(e) => setDomain(e.target.value)}
                  >
                    {DOMAIN_OPTIONS.map((option) => (
                      <MenuItem key={option} value={option}>
                        {option}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <TextField
                  label="Description"
                  multiline
                  minRows={4}
                  required
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />

                <Button
                  type="submit"
                  variant="contained"
                  size="large"
                  disabled={loading}
                >
                  {loading ? <CircularProgress size={22} /> : "Create Ticket"}
                </Button>
              </Stack>
            </form>
          </>
        ) : (
          <Stack
            alignItems="center"
            justifyContent="center"
            spacing={2}
            sx={{ height: 250 }}
          >
            <Zoom in={success}>
              <CheckCircleIcon color="success" sx={{ fontSize: 80 }} />
            </Zoom>

            <Typography variant="h6">Ticket created successfully!</Typography>
          </Stack>
        )}
      </Paper>
    </Container>
  );
};

export default CreateTicket;
