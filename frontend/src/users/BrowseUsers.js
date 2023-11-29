import React, { useEffect, useState } from 'react'
import axios from "axios";
import { Link } from "react-router-dom"

export default function BrowseUsers() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        const result = await axios.get(`${process.env.REACT_APP_API_ENDPOINT}/user/all`);
        
        setUsers(result.data);
    };


    const deleteUser = async (id) => {
        await axios.delete(`${process.env.REACT_APP_API_ENDPOINT}/user/${id}`);
        loadUsers();
    };

    return (
        <div className='container'>
            <h2 className="text-center m-4">Browse Users</h2>
            <div className='py-4 table-responsive-sm'>
                <table className="table table-sm border shadow">
                    <thead>
                        <tr>
                            <th scope="col">S.N</th>
                            <th scope="col">Name</th>
                            <th scope="col">Username</th>
                            <th scope="col">Email</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            users.map((user, index) => (
                                <tr>
                                    <th scope="row" key={index}>{index + 1}</th>
                                    <td>{user.name}</td>
                                    <td>{user.username}</td>
                                    <td>{user.email}</td>
                                    <td>
                                        <div class="d-grid gap-2 d-md-block">
                                            <Link className='btn btn-primary mx-2 btn-sm' to={`/user/${user.id}`}>View</Link>
                                            <Link className='btn btn-outline-primary mx-2 btn-sm' to={`/edituser/${user.id}`}>Edit</Link>
                                            <button className='btn btn-danger mx-2 btn-sm' onClick={() => deleteUser(user.id)}>Delete</button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>

            </div>
        </div>
    );
}
