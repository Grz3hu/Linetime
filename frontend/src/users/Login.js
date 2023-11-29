import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { SetAuthToken } from "../auth/SetAuthToken";

export default function Login() {
  let navigate = useNavigate();

  const [user, setUser] = useState({
    usernameOrEmail: "",
    password: "",
  });

  const {usernameOrEmail, password } = user;

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`${process.env.REACT_APP_API_ENDPOINT}/api/auth/signin`, user)
    .then(response => {
        const token = response.data.token;

        localStorage.setItem("token", token);
        
        SetAuthToken(token);
    
        navigate("/");
      })
      .catch(err => console.log(err));
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Login</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Email" className="form-label">
                Username / E-mail
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your e-mail or username"
                name="usernameOrEmail"
                value={usernameOrEmail}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Password" className="form-label">
                Password
              </label>
              <input
                type={"password"}
                className="form-control"
                placeholder="Enter your password"
                name="password"
                value={password}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Login
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
