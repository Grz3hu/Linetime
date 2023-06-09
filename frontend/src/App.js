import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import "jquery/dist/jquery.min.js";
import "bootstrap/dist/js/bootstrap.min.js";
import Navbar from './layout/Navbar';
import Home from './pages/Home';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Register from './users/Register';
import Login from './users/Login';
import EditUser from './users/EditUser';
import ViewUser from './users/ViewUser';
import ViewTimeline from './timelines/ViewTimeline';
import BrowseTimeline from './timelines/BrowseTimelines';
import AddTimeline from './timelines/AddTimeline';
import BrowseUsers from './users/BrowseUsers';
import AddEvent from './timelines/AddEvent';
import { SetAuthToken } from './auth/SetAuthToken';


function App() {
  const token = localStorage.getItem("token");
  if (token) {
    SetAuthToken(token);
  }

  return (
    <div className="App">
      <Router>
        <Navbar />

        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/edituser/:id" element={<EditUser />} />
          <Route exact path="/user/:id" element={<ViewUser />} />
          <Route exact path="/users" element={<BrowseUsers />} />
          <Route exact path="/timeline/:id" element={<ViewTimeline />} />
          <Route exact path="/browse_timelines" element={<BrowseTimeline />} />
          <Route exact path="/add_timeline" element={<AddTimeline />} />
          <Route exact path="/add_event/:id" element={<AddEvent />} />
        </Routes>

      </Router>
    </div>
  );
}

export default App;
