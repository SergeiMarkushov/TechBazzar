import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const users = axiosCustom('http://localhost:5555/auth/api/v1/users', getTokenKeyCloak());
export const apiGetUsers = () => users.get('/all');

export const apiGetMyUser = () => users.get('');
export const apiGetMyUserById = (id: number) => users.get(`/${id}`);
export const apiGetUserRoles = (email: string) => users.get(`/get_roles/${email}`);
export const apiUpBalance = (id: number, value: number) => users.get(`/up_balance/${id}/${value}`);
export const apiBunUser = (id: number) => users.get(`/bun/${id}`);
export const apiSetRoleUser = (email: string, role: string) => users.post(`/set_role`, {
    email: email,
    role: role
});
export const apiGetMyUserForAuth = (token: string) => axiosCustom('http://localhost:5555/auth/api/v1/users', token).get('');