import React from "react"
import { Link } from "react-router-dom"
import logo from "../images/logo.png"

export default function Navbar() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/"><img src={logo} width='140vw' alt='Linetime'/></a>

                    <div>
                        <Link className='btn btn-outline-light me-2' to="/add_event">Add event</Link>
                        <Link className='btn btn-outline-light me-2' to="/add_timeline">Add timeline</Link>
                        <Link className='btn btn-outline-light me-2' to="/browse_timelines">Browse timelines</Link>
                        <Link className='btn btn-outline-light me-2' to="/users">Browse Users</Link>
                        <Link className='btn btn-outline-light me-2' to="/login">Login</Link>
                        <Link className='btn btn-outline-light me-2' to="/register">Register</Link>
                        <button className='btn btn-outline-light'>Logout</button>
                    </div>
                </div>
            </nav>
        </div>
    )
}