import {deleteToken, getRoles, isTokenExist, setToken} from "../util/TokenUtil";
import {deleteUser, getUserNew, setUserNew} from "../util/UserUtil";
import {apiAuthWithPasswordNew} from "../api/AuthApi";
import {AxiosError, AxiosResponse} from "axios";
import {AuthResponseNew, ErrorMessage, UserNew} from "../newInterfaces";
import {apiGetMyUserForAuth} from "../api/UserApi";


export const AuthPasswordProvider = {
    isAuthenticated: isTokenExist(),
    user: getUserNew(),
    roles: getRoles(),
    AuthError: "",
    signin(login: string, password: string, callback: VoidFunction, errorCallback: VoidFunction) {
        apiAuthWithPasswordNew(login, password).then((cred: AxiosResponse<AuthResponseNew>) => {
            console.log("Login successful");
            setToken(cred.data.token);
            apiGetMyUserForAuth(cred.data.token).then((user: AxiosResponse<UserNew>) => {
                console.log(user.data)
                setUserNew(user.data);
                AuthPasswordProvider.isAuthenticated = true;
                callback();
            }).catch((error) => {
                console.log(error);
            })
        }).catch((error: AxiosError) => {
            if (error.response?.status === 401) {
                this.AuthError = "Неверный логин или пароль";
                errorCallback();
            }
        })
    },
    signout(callback: VoidFunction) {
        deleteToken();
        deleteUser();
        AuthPasswordProvider.isAuthenticated = false;
        callback();
    },
};