import {Characteristic} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const characteristic = axiosCustom('http://localhost:5555/core/api/v1/characteristics', getTokenKeyCloak());

export const apiCreateCharacteristics = (productId: number, characteristics: Array<Characteristic>) => characteristic.post(`${productId}/characteristics`, characteristics);
export const apiDeleteCharacteristics = (id: number) => characteristic.delete(`${id}`);