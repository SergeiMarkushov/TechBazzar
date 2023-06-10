import React from 'react';
import {useAuth} from "../../auth/Auth";
import {redirect} from "react-router-dom";

export function Logout() {
    const auth = useAuth();
    auth.signout(() => redirect("/catalog"))
    return <div></div>;
}