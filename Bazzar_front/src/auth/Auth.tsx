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
    error: string;
    signin: (user: string, password: string, callback: VoidFunction, errorCallBack: VoidFunction) => void;
    signout: (callback: VoidFunction) => void;
}


const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({children}: { children: React.ReactNode }) {
    const [user, setUser] = React.useState<any>(AuthPasswordProvider.user);
    const [roles, setRoles] = React.useState<any>(AuthPasswordProvider.roles);
    const [isAuth, setIsAuth] = React.useState<any>(AuthPasswordProvider.isAuthenticated);
    const [error, setError] = React.useState<any>(AuthPasswordProvider.AuthError);

    const signin = (user: string, password: string, callback: VoidFunction, errorCallBack: VoidFunction) => {
        return AuthPasswordProvider.signin(user, password, () => {
            setUser(getUserNew());
            setRoles(getRoles());
            setIsAuth(true);
            callback();
        }, () => {
            setError(AuthPasswordProvider.AuthError);
            errorCallBack();
        });
    };

    const signout = (callback: VoidFunction) => {
        return AuthPasswordProvider.signout(() => {
            setUser(null);
            setRoles([]);
            setIsAuth(false);
            callback();
        });
    };

    const value = {user, roles, isAuth, error, signin, signout};

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    return React.useContext(AuthContext);
}

export function RequireAuth({children}: { children: JSX.Element }) {
    const auth = useAuth();
    const location = useLocation();

    if (!auth.isAuth || auth.user === null) {
        return <Navigate to="/login" state={{from: location}} replace/>;
    }

    return children;
}