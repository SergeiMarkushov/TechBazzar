import {axiosCustom} from "./AxiosConfig";
import {getToken} from "../util/TokenUtil";
import {OrganizationCreate} from "../newInterfaces";

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
export const apiCreateOrganization = (org: FormData) => organization.post("", org, {});
export const apiOrganizationConfirm = (title: string) => organization.get(`/confirm/${title}`);
export const apiOrganizationBun = (id: number) => organization.get(`/bun/${id}`);
