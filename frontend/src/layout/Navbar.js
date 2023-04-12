import React from "react"
import { Link } from "react-router-dom"
import logo from "../images/logo.png"

export default function Navbar() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/"><img src={logo} width='140vw' /></a>

                    <div>
                        <Link className='btn btn-outline-light me-2' to="/login">Login</Link>
                        <Link className='btn btn-outline-light' to="/adduser">Register</Link>
                    </div>
                </div>
            </nav>
        </div>
    )
}