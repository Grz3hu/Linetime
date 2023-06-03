import React, { useEffect, useState } from 'react'
import axios from "axios";
import logo from "../images/logo.png"
import { Link, useNavigate } from "react-router-dom";


export default function Navbar() {
    let navigate = useNavigate();

    const [isadmin, setIsAdmin] = useState({ admin: false });

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
                        <ul className="navbar-nav me-auto">
                            <li className="nav-item">
                                {isLoggedIn() && window.location.pathname.includes("/timeline/") ? <Link className='btn btn-info btn-outline-light mx-5 w-75' to={getAddEventPath()}>Add event</Link> : ''}
                            </li>
                            <li className="nav-item">
                                {isLoggedIn() ? <Link className='btn btn-outline-light mx-5 w-75' to="/add_timeline">Add timeline</Link> : ''}
                            </li>
                            <li className="nav-item">
                                <Link className='btn btn-outline-light mx-5 w-75' to="/browse_timelines">Browse timelines</Link>
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
    
    return (
        <nav className="navbar navbar-expand-lg bg-light">
            <div className="container-fluid">
                <a className="navbar-brand" href="#">Navbar</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <a className="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Link</a>
                        </li>
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Dropdown
                            </a>
                            <ul className="dropdown-menu">
                                <li><a className="dropdown-item" href="#">Action</a></li>
                                <li><a className="dropdown-item" href="#">Another action</a></li>
                                <li><hr className="dropdown-divider" /></li>
                                <li><a className="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link disabled">Disabled</a>
                        </li>
                    </ul>
                    <form className="d-flex" role="search">
                        <input className="form-control mx-5 w-75" type="search" placeholder="Search" aria-label="Search" />
                            <button className="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
    )
}