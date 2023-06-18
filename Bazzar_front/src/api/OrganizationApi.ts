import {getToken} from "../util/TokenUtil";
import {axiosCustom} from "./AxiosConfig";

const organization = axiosCustom('http://localhost:5555/organization/api/v1/organizations', getToken());
const logo = axiosCustom('http://localhost:5555/organization/api/v1/logo', getToken());

export const apiGetLogo = (orgTitle: string) => logo.get(`/${orgTitle}`,
    {
        responseType: 'arraybuffer',
        headers: {
            Accept: 'application/octet-stream',
        },
    });

export const apiGetAllOrganization = () => organization.get("");
export const apiGetOrganization = (title: string) => organization.get(`/${title}`);
export const apiGetAllMyOrganization = () => organization.get("/by_owner");
export const apiCreateOrganization = (org: FormData) => organization.post("", org, {});
export const apiOrganizationConfirm = (title: string) => organization.get(`/confirm/${title}`);
export const apiOrganizationBun = (id: number) => organization.get(`/bun/${id}`);
