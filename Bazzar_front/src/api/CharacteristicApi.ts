import {Characteristic} from "../newInterfaces";
import {getToken} from "../util/TokenUtil";
import {axiosCustom} from "./AxiosConfig";

const characteristic = axiosCustom('http://localhost:5555/core/api/v1/characteristics', getToken());

export const apiCreateCharacteristics = (productId: number, characteristics: Array<Characteristic>) => characteristic.post(`${productId}/characteristics`, characteristics);