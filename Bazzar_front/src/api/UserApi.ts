import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const users = axiosCustom('http://localhost:5555/user/api/v1/users', getTokenKeyCloak());
export const apiGetUsers = () => users.get('/all');

export const apiGetMyUser = () => users.get('');
export const apiGetUserById = (id: number) => users.get(`/${id}`);
export const apiBunUser = (id: number) => users.get(`/bun/${id}`);
export const apiUpBalance = (email: string, value: number) => users.post("/up_balance", {
    username: email,
    balance: value,
})