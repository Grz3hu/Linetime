import { Chrono } from "react-chrono";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Timeline() {
    const [timeline, setTimeline] = useState({});

    const { id } = useParams();

    useEffect(() => {
        loadTimeline();
    }, []);

    const loadTimeline = async () => {
        const result = await axios.get(`${process.env.REACT_APP_API_ENDPOINT}/timeline/${id}`);
        setTimeline(result.data);
    };

    return (
        <div>
            <h2 className="text-center m-4">{timeline.title}</h2>
            <Chrono items={timeline.events} mode={timeline.mode} allowDynamicUpdate={true} />
        </div>
    );
}
