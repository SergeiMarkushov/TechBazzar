import {useAuth} from "../../auth/Auth";
import {redirect} from "react-router-dom";

export function Logout() {
    let auth = useAuth();
    auth.signout(() => redirect("/catalog"))
    return <div></div>;
}