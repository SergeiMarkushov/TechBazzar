import {getToken} from "../util/TokenUtil";
import {axiosCustom} from "./AxiosConfig";

const productPic = axiosCustom('http://localhost:5555/core/api/v1/products', getToken());

export const apiSaveProductPic = (file: FormData) => productPic.post(`/save-pic-return-id`, file, {
    headers: {
        'Content-Type': 'multipart/form-data',
    },
});

export const apiGetProductPic = (id: number) => productPic.get(`/find-pic-dto/${id}`,
    {
    });