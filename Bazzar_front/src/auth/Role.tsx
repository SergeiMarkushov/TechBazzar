import {useKeycloak} from "@react-keycloak/web";
import React from "react";
import {Navigate} from "react-router-dom";
import {setTokenKeyCloak} from "../util/KeyCloakToken";
import {ADMIN} from "./Roles";

export interface RoleContextType {
    isAdmin: boolean,
}

const RoleContext = React.createContext<RoleContextType>("" as unknown as RoleContextType);

export function RoleProvider({children}: { children: React.ReactNode }) {
    let isAdmin = false;
    const {keycloak, initialized} = useKeycloak();
    if (keycloak.authenticated) {
        keycloak?.realmAccess?.roles.forEach(role => {
            if (role === ADMIN) {
                isAdmin = true;
            }
        })
    } else {
        isAdmin = false;
    }

    if (keycloak.authenticated && initialized && keycloak.token) {
        setTokenKeyCloak(keycloak?.token);
    }

    const value = {isAdmin};

    return <RoleContext.Provider value={value}>{children}</RoleContext.Provider>;
}

export function useRole() {
    return React.useContext(RoleContext);
}

export function RequireRoleADMIN({children}: { children: JSX.Element }) {
    const role = useRole();

    if (!role.isAdmin) {
        return <Navigate to={"/home"} replace/>;
    }

    return children;
}