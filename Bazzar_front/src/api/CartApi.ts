import {axiosCustom} from "./AxiosConfig";
import {getToken} from "../util/TokenUtil";
import {CartItemNew, ProductNew} from "../newInterfaces";

const cart = axiosCustom('http://localhost:5555/cart/api/v1/cart', getToken());

export const apiAddItemToCart = (id: number | undefined, isAuth: boolean) => addItemToCart(id, isAuth);

const addItemToCart = (id: number | undefined, isAuth: boolean) => {
    return cart.get(`/add/${id}`);
}
export const apiUpdateQuantity = (product: CartItemNew, quantity: number, isAuth: boolean) => updateQuantity(product, quantity, isAuth);

const updateQuantity = (product: CartItemNew, quantity: number, isAuth: boolean) => {
    return cart.get("/change_quantity", {
        params: {
            productId: product.productId,
            delta: quantity.toString()
        }
    });
}

export const apiClearCart = (isAuth: boolean) => clearCart(isAuth);

const clearCart = (isAuth: boolean) => {
    return cart.get(`/clear`);
}

export const apiGetCart = (isAuth: boolean) => getCart(isAuth);

const getCart = (isAuth: boolean) => {
    return cart.get("");
}

export const apiRemoveItem = (product: CartItemNew, isAuth: boolean) => removeItem(product, isAuth);

const removeItem = (product: CartItemNew, isAuth: boolean) => {
    return cart.get(`/remove/${product.productId}`);
}

export function apiIsExistInCart(product: ProductNew): boolean {
    return false;
}