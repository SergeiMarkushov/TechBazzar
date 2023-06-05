import {axiosCustom} from "./AxiosConfig";
import {getToken} from "../util/TokenUtil";

const purchase = axiosCustom('http://localhost:5555/core/api/v1/history', getToken());

export const apiGetUserPurchased = () => purchase.get("");