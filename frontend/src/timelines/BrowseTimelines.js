import React, { useEffect, useState } from 'react'
import axios from "axios";
import { Link } from "react-router-dom"

export default function Home() {
    return (
        <div className='container'>
          <h2 className="text-center m-4">Browse Timelines</h2>
            <div className='py-4'>
                <table className="table border shadow">
                    <thead>
                        <tr>
                            <th scope="col">S.N</th>
                            <th scope="col">Owner</th>
                            <th scope="col">Title</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            <tr>
                                <th scope="row">1</th>
                                <td>John Snow</td>
                                <td>Test timeline</td>
                                <td>
                                    <Link className='btn btn-primary mx-2' to='/user/1'>User details</Link>
                                    <Link className='btn btn-outline-primary mx-2' to='/timeline/1' >View Timeline</Link>
                                    <button className="btn btn-danger mx-2" >Delete</button>
                                </td>
                            </tr>
                            // users.map((user, index) => (
                            //     <tr>
                            //         <th scope="row" key={index}>{index + 1}</th>
                            //         <td>{user.name}</td>
                            //         <td>{user.surname}</td>
                            //         <td>{user.email}</td>
                            //         <td>
                            //             <Link className='btn btn-primary mx-2' to={`/user/${user.id}`}>View</Link>
                            //             <Link className='btn btn-outline-primary mx-2' to={`/edituser/${user.id}`}>Edit</Link>
                            //             <button className="btn btn-danger mx-2" onClick={() => deleteUser(user.id)}>Delete</button>
                            //         </td>
                            //     </tr>
                            // ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}
