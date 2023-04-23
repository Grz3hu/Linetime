//ADD EVENT 
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function AddEvent() {
  let navigate = useNavigate();

  const { id } = useParams();

  const [timeline, setTimeline] = useState({
    date: "",
    cardTitle: "",
    cardSubtitle: "",
    cardDetailedText: ""
  });

  const { date, cardTitle, cardSubtitle, cardDetailedText } = timeline;

  const onInputChange = (e) => {
    setTimeline({ ...timeline, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadUser();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/timeline/${id}`, timeline);
    navigate("/");
  };

  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/user/${id}`);
    setTimeline(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Add Timeline</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Date" className="form-label">
                Name
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
              <label htmlFor="mode" className="form-label">
                Choose mode
              </label>
              <select 
                className="form-select" 
                placeholder="Choose timeline mode"
                name="mode"
                onChange={(e) => onInputChange(e)}
                aria-label="Default select example"
                >
                <option value="HORIZONTAL">Horizontal</option>
                <option value="VERTICAL">Vertical</option>
                <option value="VERTICAL_ALTERNATING">Vertical alternating</option>
              </select>

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