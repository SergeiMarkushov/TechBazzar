import {axiosCustom, axiosCustomTokenOff} from "./AxiosConfig";

const auth = axiosCustomTokenOff('http://localhost:5555/auth/api/v1');

export const apiAuthWithPasswordNew = (login: string, password: string) => auth.post("/auth", {
    username: login,
    password: password,
})

export const apiSignUpWithPasswordNew = (email: string, username: string, password: string) =>
    auth.post("/registration", {email: email, username: username, password: password})

export const apiKeycloakAdmin = (token: string) =>
    axiosCustom('http://localhost:8186/', token).get("admin")

export const apiKeycloakUser = (token: string) =>
    axiosCustom('http://localhost:8186/', token).get("user")