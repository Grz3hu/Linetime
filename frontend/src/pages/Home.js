import Login from '../users/Login';
import BrowseTimelines from '../timelines/BrowseTimelines';

    function isLoggedIn() {
        return localStorage.getItem("token") != null;
    }

export default function Home() {
    if (isLoggedIn())
         return <BrowseTimelines/>;
    else
        return <Login/>;
}
