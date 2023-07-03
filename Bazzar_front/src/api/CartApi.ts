import {CartItem} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";



const cart = axiosCustom('http://localhost:5555/cart/api/v1/cart', getTokenKeyCloak());

export const apiAddItemToCart = (id: number | undefined) => cart.get(`/add/${id}`);
export const apiUpdateQuantity = (product: CartItem, quantity: number) => cart.get("/change_quantity", {
    params: {
        productId: product.productId,
        delta: quantity.toString()
    }
});
export const apiClearCart = () => cart.get(`/clear`);
export const apiGetCart = () => cart.get("");
export const apiGetCartSize = () => cart.get("/size");
export const apiRemoveItem = (product: CartItem) => cart.get(`/remove/${product.productId}`);