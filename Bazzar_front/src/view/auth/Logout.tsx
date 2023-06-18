import React from 'react';
import {redirect} from "react-router-dom";
import {useAuth} from "../../auth/Auth";

export function Logout() {
    const auth = useAuth();
    auth.signout(() => redirect("/catalog"))
    return <div></div>;
}