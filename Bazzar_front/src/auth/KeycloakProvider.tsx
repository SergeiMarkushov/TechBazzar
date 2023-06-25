import {useKeycloak} from "@react-keycloak/web";
import {Navigate, useLocation} from "react-router-dom";

export function RequireAuthKeycloak({children}: { children: JSX.Element }) {
    const {keycloak} = useKeycloak();
    const location = useLocation();

    const isLoggedIn = keycloak.authenticated;

    if (isLoggedIn) {
        return children;
    } else {
        return <Navigate to="/login" state={{from: location}} replace/>;
    }
}