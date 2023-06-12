import {Characteristic, FindRequest, ProductCreateNew} from "../newInterfaces";
import {getToken} from "../util/TokenUtil";
import {axiosCustom} from "./AxiosConfig";

const products = axiosCustom('http://localhost:5555/core/api/v1/products', getToken());
const characteristic = axiosCustom('http://localhost:5555/core/api/v1/characteristics', getToken());

export const apiGetProductsNew = (findRequest: FindRequest ) => products('', {
    method: 'get',
    params: findRequest,
});

export const apiGetProductByIdNew = (id: number) => products.get(`/${id}`);
export const apiDeleteProductById = (id: number) => products.delete(id.toString());
export const apiGetProductsNotConfirmed = () => products.get("/not_confirmed");
export const apiConfirmProduct = (title: string) => products.get("/confirm/" + title);

export const apiCreateOrUpdateProductNew = (product: ProductCreateNew) => products.post('', product);
export const apiUpdateProduct = (id: number) => products.get(`/update/${id}`);
export const apiCleanCache = () => products.get(`/evict`);

export const apiCreateCharacteristics = (productId: number, characteristics: Array<Characteristic>) => characteristic.post(`${productId}/characteristics`, characteristics);