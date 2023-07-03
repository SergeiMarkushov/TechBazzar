import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const purchase = axiosCustom('http://localhost:5555/core/api/v1/history', getTokenKeyCloak());
export const apiGetMyPurchased = () => purchase.get("");
export const apiGetCountPurchase = () => purchase.get("/count");