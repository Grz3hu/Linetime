import React, { useEffect, useState } from 'react'
import axios from "axios";
import { Link } from "react-router-dom"

export default function BrowseTimelines() {
    const [timelines, setTimelines] = useState([]);

    useEffect(() => {
        loadTimelines();
    }, []);

    const loadTimelines = async () => {
        const result = await axios.get("http://localhost:8080/timeline/all");
        setTimelines(result.data);
    };
    
    const deleteTimeline = async (id) => {
        await axios.delete(`http://localhost:8080/timeline/${id}`);
        loadTimelines();
    };



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
                            timelines.map((timeline, index) => (
                                <tr>
                                    <th scope="row" key={index}>{index + 1}</th>
                                    <td>{timeline.ownerName}</td>
                                    <td>{timeline.title}</td>
                                    <td>
                                        <Link className='btn btn-primary mx-2' to={`/user/${timeline.ownerId}`}>Owner details</Link>
                                        <Link className='btn btn-outline-primary mx-2' to={`/timeline/${timeline.id}`}>View Timeline</Link>
                                        <button className="btn btn-danger mx-2" onClick={() => deleteTimeline(timeline.id)}>Delete</button>
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
