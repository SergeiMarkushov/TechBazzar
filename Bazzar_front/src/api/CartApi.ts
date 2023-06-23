import {CartItemNew} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";



const cart = axiosCustom('http://localhost:5555/cart/api/v1/cart', getTokenKeyCloak());

export const apiAddItemToCart = (id: number | undefined) => addItemToCart(id);

const addItemToCart = (id: number | undefined) => {
    return cart.get(`/add/${id}`);
}
export const apiUpdateQuantity = (product: CartItemNew, quantity: number) => updateQuantity(product, quantity);

const updateQuantity = (product: CartItemNew, quantity: number) => {
    return cart.get("/change_quantity", {
        params: {
            productId: product.productId,
            delta: quantity.toString()
        }
    });
}

export const apiClearCart = () => clearCart();

const clearCart = () => {
    return cart.get(`/clear`);
}

export const apiGetCart = () => getCart();

const getCart = () => {
    return cart.get("");
}

export const apiRemoveItem = (product: CartItemNew) => removeItem(product);

const removeItem = (product: CartItemNew) => {
    return cart.get(`/remove/${product.productId}`);
}