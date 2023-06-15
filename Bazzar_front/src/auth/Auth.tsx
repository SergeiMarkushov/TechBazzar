import React from "react";
import {Navigate, useLocation} from "react-router-dom";
import {UserNew} from "../newInterfaces";
import {getRoles} from "../util/TokenUtil";
import {getUserNew} from "../util/UserUtil";
import {AuthPasswordProvider} from "./AuthProvider";

export interface AuthContextType {
    user: UserNew | null;
    roles: string[];
    isAuth: boolean;
    error: string;
    signin: (user: string, password: string, callback: VoidFunction, errorCallBack: VoidFunction) => void;
    signout: (callback: VoidFunction) => void;
}


const AuthContext = React.createContext<AuthContextType>(null as unknown as AuthContextType);

export function AuthProvider({children}: { children: React.ReactNode }) {
    const [user, setUser] = React.useState<UserNew | null>(AuthPasswordProvider.user);
    const [roles, setRoles] = React.useState<string[]>(AuthPasswordProvider.roles);
    const [isAuth, setIsAuth] = React.useState<boolean>(AuthPasswordProvider.isAuthenticated);
    const [error, setError] = React.useState<string>(AuthPasswordProvider.AuthError);

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