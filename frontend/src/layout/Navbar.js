import React, { useEffect, useState } from 'react'
import axios from "axios";
import logo from "../images/logo.png"
import { Link, useNavigate } from "react-router-dom";


export default function Navbar() {
    let navigate = useNavigate();

    const [isadmin, setIsAdmin] = useState({admin: false});

    useEffect(() => {
        isAdmin();
    }, []);

    const isAdmin = async () => {
        const result = await axios.get("http://localhost:8080/api/auth/isadmin");
        setIsAdmin(result.data);
        console.log(isadmin)
    };

    function Logout(){
        localStorage.removeItem("token");
        navigate("/login");
    }
    
    function isLoggedIn(){
        return localStorage.getItem("token")!=null;
    }
    
    function getAddEventPath(){
        return "/add_event/" + window.location.pathname.split("/").pop();
    }
    
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/"><img src={logo} width='140vw' alt='Linetime'/></a>

                    <div>
                        {isLoggedIn() && window.location.pathname.includes("/timeline/") ? <Link className='btn btn-outline-light me-2' to={getAddEventPath()}>Add event</Link> :''}
                        {isLoggedIn() ? <Link className='btn btn-outline-light me-2' to="/add_timeline">Add timeline</Link> :''}
                        <Link className='btn btn-outline-light me-2' to="/browse_timelines">Browse timelines</Link>
                        {isLoggedIn() && isadmin.admin ? <Link className='btn btn-outline-light me-2' to="/users">Browse Users</Link> : ''}
                        {!isLoggedIn() ? <Link className='btn btn-outline-light me-2' to="/login">Login</Link> :''}
                        {!isLoggedIn() ? <Link className='btn btn-outline-light me-2' to="/register">Register</Link>:''}
                        {isLoggedIn() ? <button className='btn btn-outline-light' onClick={Logout}>Logout</button> :''}
                    </div>
                </div>
            </nav>
        </div>
    )
}