import React, { useEffect, useState } from 'react'
import axios from "axios";
import logo from "../images/logo.png"
import { Link, useNavigate } from "react-router-dom";


export default function Navbar() {
    let navigate = useNavigate();

    const [isadmin, setIsAdmin] = useState( false );

    useEffect(() => {
        isAdmin();
    }, []);

    const isAdmin = async () => {
        let data;
        try {
        const result = await axios.get("http://localhost:8080/api/auth/isadmin");
        data = result.data;
        } catch(errr) {
            setIsAdmin(false);
        }
        setIsAdmin(data);
        console.log(isadmin)
    };

    function Logout() {
        localStorage.removeItem("token");
        navigate("/login");
    }

    function isLoggedIn() {
        return localStorage.getItem("token") != null;
    }

    function getAddEventPath() {
        return "/add_event/" + window.location.pathname.split("/").pop();
    }

    return (
        <div>
            <nav className="navbar navbar-expand-xl navbar-dark bg-primary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/"><img src={logo} width='140vw' alt='Linetime' /></a>

                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#colNav">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="colNav">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0 mx-auto">
                            <li className="nav-item">
                                {isLoggedIn() && window.location.pathname.includes("/timeline/") ? <Link className='btn btn-success btn-outline-light mx-5 w-75' to={getAddEventPath()}>Add event</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {isLoggedIn() ? <Link className='btn btn-outline-light mx-5 w-75' to="/add_timeline">Add timeline</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {isLoggedIn() ? <Link className='btn btn-outline-light mx-5 w-75' to="/browse_timelines">Browse timelines</Link>: ''}
                            </li>
                            <li className="nav-item">
                                {isLoggedIn() && isadmin.admin ? <Link className='btn btn-outline-light mx-5 w-75' to="/users">Browse Users</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {!isLoggedIn() ? <Link className='btn btn-outline-light mx-5 w-75' to="/login">Login</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {!isLoggedIn() ? <Link className='btn btn-outline-light mx-5 w-75' to="/register">Register</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {isLoggedIn() ? <button className='btn btn-outline-light mx-5 w-75' onClick={Logout}>Logout</button> : ''}
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    )
}