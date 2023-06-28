import {CircularProgress} from "@mui/material";
import {useKeycloak} from "@react-keycloak/web";
import {Navigate, useLocation} from "react-router-dom";

export function RequireAuthKeycloak({children}: { children: JSX.Element }) {
    const {keycloak, initialized} = useKeycloak();
    const location = useLocation();

    if (!initialized) {
        return <div className="d-flex justify-content-center">
            <CircularProgress/>
        </div>;
    } else {
        if (!keycloak.authenticated) {
            return <Navigate to="/login" state={{from: location}} replace/>;
        } else {
            return children;
        }
    }
}