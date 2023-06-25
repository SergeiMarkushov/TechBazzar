import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";


const productPic = axiosCustom('http://localhost:5555/core/api/v1/products', getTokenKeyCloak());

export const apiSaveProductPic = (file: FormData) => productPic.post(`/save-pic-return-id`, file, {
    headers: {
        'Content-Type': 'multipart/form-data',
    },
});

export const apiGetProductPic = (id: number) => productPic.get(`/find-pic-dto/${id}`,
    {
    });