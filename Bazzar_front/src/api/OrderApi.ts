import {getToken} from "../util/TokenUtil";
import {axiosCustom} from "./AxiosConfig";

const orders = axiosCustom('http://localhost:5555/core/api/v1/orders', getToken());

export const apiGetUserOrders = () => orders.get("");

export const apiCreateOrder = () => orders.post("");
export const apiOrderPayment = (orderId: number) => orders.get(`/payment/${orderId}`);
export const apiOrderRefund = (orderId: number) => orders.get(`/refund/${orderId}`);