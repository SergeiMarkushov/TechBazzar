import {deleteToken, getRoles, isTokenExist, setToken} from "../util/TokenUtil";
import {deleteUser, getUserNew, setUserNew} from "../util/UserUtil";
import {apiAuthWithPasswordNew} from "../api/AuthApi";
import {AxiosResponse} from "axios";
import {AuthResponseNew, UserNew} from "../newInterfaces";
import {apiGetMyUserForAuth} from "../api/UserApi";


export const AuthPasswordProvider = {
    isAuthenticated: isTokenExist(),
    user: getUserNew(),
    roles: getRoles(),
    signin(login: string, password: string, callback: VoidFunction) {

        apiAuthWithPasswordNew(login, password).then((cred: AxiosResponse<AuthResponseNew>) => {
            if (cred.status === 200) {
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
            }
        }).catch((error) => {
            console.log(error);
        })
    },
    signout(callback: VoidFunction) {
        deleteToken();
        deleteUser();
        AuthPasswordProvider.isAuthenticated = false;
        callback();
    },
};