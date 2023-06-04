import React from "react";
import {Navigate, useLocation} from "react-router-dom";
import {AuthPasswordProvider} from "./AuthProvider";
import {UserNew} from "../newInterfaces";
import {getUserNew} from "../util/UserUtil";
import {getRoles} from "../util/TokenUtil";

export interface AuthContextType {
    user: UserNew;
    roles: string[];
    isAuth: boolean;
    signin: (user: string, password: string, callback: VoidFunction) => void;
    signout: (callback: VoidFunction) => void;
}


let AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({children}: { children: React.ReactNode }) {
    let [user, setUser] = React.useState<any>(AuthPasswordProvider.user);
    let [roles, setRoles] = React.useState<any>(AuthPasswordProvider.roles);
    let [isAuth, setIsAuth] = React.useState<any>(AuthPasswordProvider.isAuthenticated);

    /*React.useEffect(() => {
        console.log("Auth Provider");
        if (!isAuth && getGuestId() === null) {
            setGuestId(uuidv4());
        }
    });*/

    let signin = (user: string, password: string, callback: VoidFunction) => {
        return AuthPasswordProvider.signin(user, password, () => {
            setUser(getUserNew());
            setRoles(getRoles())
            setIsAuth(true);
            callback();
        });
    };

    let signout = (callback: VoidFunction) => {
        return AuthPasswordProvider.signout(() => {
            setUser(null);
            setRoles([]);
            setIsAuth(false);
            callback();
        });
    };

    let value = {user, roles, isAuth, signin, signout};

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    return React.useContext(AuthContext);
}

export function RequireAuth({children}: { children: JSX.Element }) {
    let auth = useAuth();
    let location = useLocation();

    if (!auth.isAuth || auth.user === null) {
        return <Navigate to="/login" state={{from: location}} replace/>;
    }

    return children;
}