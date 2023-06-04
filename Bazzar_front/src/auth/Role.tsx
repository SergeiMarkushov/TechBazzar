import React from "react";
import {Navigate} from "react-router-dom";
import {useAuth} from "./Auth";
import {ADMIN} from "./Roles";

export interface RoleContextType {
    isAdmin: boolean,
    isSuperAdmin: boolean,
}

let RoleContext = React.createContext<RoleContextType>(null!);

export function RoleProvider({children}: { children: React.ReactNode }) {
    let isAdmin = false;
    let isSuperAdmin = false;
    let auth = useAuth();

    if (auth.user !== null) {
        auth.roles.forEach(role => {
            console.log("render")
            if (role === ADMIN) {
                isAdmin = true;
            }
        })
    } else {
        isAdmin = false;
        isSuperAdmin = false;
    }
    let value = {isAdmin, isSuperAdmin};

    return <RoleContext.Provider value={value}>{children}</RoleContext.Provider>;
}

export function useRole() {
    return React.useContext(RoleContext);
}

export function RequireRoleADMIN({children}: { children: JSX.Element }) {
    let role = useRole();

    if (!role.isAdmin) {
        return <Navigate to={"/home"} replace/>;
    }

    return children;
}