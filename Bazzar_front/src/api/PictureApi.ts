import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";


const productPic = axiosCustom('http://localhost:5555/core/api/v1/products', getTokenKeyCloak());
const picture = axiosCustom('http://localhost:5555/picture/api/v1/picture', getTokenKeyCloak());

export const apiSaveProductPic = (file: FormData) => picture.post(``, file, {
    headers: {
        'Content-Type': 'multipart/form-data',
    },
});

export const apiGetProductPic = (id: number) => productPic.get(`/picture/${id}`);
export const apiGetPicByProductId = (id: number) => productPic.get(`/picture-by-product/${id}`);