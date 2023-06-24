import {FindRequest, ProductCreateNew} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const products = axiosCustom('http://localhost:5555/core/api/v1/products', getTokenKeyCloak());

export const apiGetProductsNew = (findRequest: FindRequest) => products('', {
    method: 'get',
    params: findRequest,
});

export const apiGetProductByIdNew = (id: number) => products.get(`/${id}`);
export const apiDeleteProductById = (id: number) => products.delete(id.toString());
export const apiGetProductsNotConfirmed = () => products.get("/not_confirmed");
export const apiConfirmProduct = (title: string) => products.get("/confirm/" + title);

export const apiCreateOrUpdateProductNew = (product: ProductCreateNew) => products.post('', product);
export const apiCreateProduct = (formData: FormData) => products.post('', formData, {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
});

export const apiUpdateProduct = (formData: FormData) => products.put('', formData, {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
});
export const apiCleanCache = () => products.get(`/evict`);