import {useKeycloak} from "@react-keycloak/web";
import React from 'react';
import {redirect} from "react-router-dom";
import {useAuth} from "../../auth/Auth";

export function Logout() {
    const auth = useAuth();
    const {keycloak} = useKeycloak();
    if (auth.user !== null) {
        auth.signout(() => redirect("/catalog"))
    }

    return <div></div>;
}