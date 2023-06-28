import axios from "axios";

export const axiosCustom = (baseUrl: string, token: string) => axios.create({
    baseURL: baseUrl,
    timeout: 50000,
    headers: {
        "Authorization": `Bearer ${token}`,
    },

});