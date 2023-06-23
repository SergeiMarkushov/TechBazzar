import {AxiosError, AxiosResponse} from "axios";
import {apiAuthWithPasswordNew} from "../api/AuthApi";
import {apiGetMyUserForAuth} from "../api/UserApi";
import {AuthResponseNew, UserNew} from "../newInterfaces";
import {deleteToken, getRoles, isTokenExist, setToken} from "../util/TokenUtil";
import {deleteUser, getUserNew, setUserNew} from "../util/UserUtil";


export const AuthPasswordProvider = {
    isAuthenticated: isTokenExist(),
    user: getUserNew(),
    roles: getRoles(),
    AuthError: "",
    signin(login: string, password: string, callback: VoidFunction, errorCallback: VoidFunction) {
        apiAuthWithPasswordNew(login, password).then((cred: AxiosResponse<AuthResponseNew>) => {
            setToken(cred.data.token);
            apiGetMyUserForAuth(cred.data.token).then((user: AxiosResponse<UserNew>) => {
                setUserNew(user.data);
                AuthPasswordProvider.isAuthenticated = true;
                callback();
                window.location.reload();
            }).catch(() => {
                this.AuthError = "Ошибка при получении данных пользователя";
                errorCallback();
            })
        }).catch((error: AxiosError) => {
            if (error.response?.status === 401) {
                this.AuthError = "Неверный логин или пароль";
            }
            errorCallback();
        })
    },
    signout(callback: VoidFunction) {
        deleteToken();
        deleteUser();
        AuthPasswordProvider.isAuthenticated = false;
        callback();
        window.location.reload();
    },
};