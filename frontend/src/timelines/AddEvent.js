//ADD EVENT 
import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function AddEvent() {
  let navigate = useNavigate();

  const { id } = useParams();

  const [t_event, setT_Event] = useState({
    timelineId: id,
    date: "",
    cardTitle: "",
    cardSubtitle: "",
    cardDetailedText: ""
  });

  const { date, cardTitle, cardSubtitle, cardDetailedText } = t_event;

  const onInputChange = (e) => {
    setT_Event({ ...t_event, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`${process.env.REACT_APP_API_ENDPOINT}/event/create`, t_event);
    navigate("/");
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Add Event</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Date" className="form-label">
                Date
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter date"
                name="date"
                value={date}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Surname" className="form-label">
                Card title
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter card title"
                name="cardTitle"
                value={cardTitle}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="text" className="form-label">
                Card subtitle
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter card subtitle"
                name="cardSubtitle"
                value={cardSubtitle}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="text" className="form-label">
                Card detailed text
              </label>
              <textarea 
                className="form-control" 
                placeholder="Enter detailed text"
                name="cardDetailedText"
                value={cardDetailedText}
                onChange={(e) => onInputChange(e)}
                rows="5" id="comment">
              </textarea>
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
