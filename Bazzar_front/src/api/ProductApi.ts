import {axiosCustom} from "./AxiosConfig";
import {getToken} from "../util/TokenUtil";
import {FindRequest, ProductCreateNew} from "../newInterfaces";

const products = axiosCustom('http://localhost:5555/core/api/v1/products', getToken());

export const apiGetProductsNew = (findRequest: FindRequest ) => products('', {
    method: 'get',
    params: findRequest,
});

export const apiGetProductByIdNew = (id: number) => products.get(`/${id}`);
export const apiDeleteProductById = (id: number) => products.delete(id.toString());
export const apiGetProductsNotConfirmed = () => products.get("/not_confirmed");
export const apiConfirmProduct = (title: string) => products.get("/confirm/" + title);

export const apiCreateOrUpdateProductNew = (product: ProductCreateNew) => products.post('', product);