import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import {Box} from "@mui/material";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Tickets from "./pages/Tickets";
import TicketDetails from "./pages/TicketDetails";
import Profile from "./pages/Profile";
import ProtectedRoute from "./components/ProtectedRoute";
import NavBar from "./components/NavBar";
import CreateTicket from "./pages/CreateTicket"
import OAuthSuccess from "./pages/OAuthSuccess";


function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
      <Box>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/oauth-success" element={<OAuthSuccess />} />

          <Route element={<ProtectedRoute />}>
            <Route path="/tickets" element={<Tickets />} />
            <Route path="/ticket/:id" element={<TicketDetails />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/createTicket" element={<CreateTicket/>}/>
          </Route>
        </Routes>
       </Box> 
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
