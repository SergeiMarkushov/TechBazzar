import axios from "axios";


export const axiosCustom = (baseUrl: string, token: string) => axios.create({
    baseURL: baseUrl,
    timeout: 5000,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        "Authorization": `Bearer ${token}`,
    },

});

export const axiosCustomTokenOff = (baseUrl: string) => axios.create({
    baseURL: baseUrl,
    timeout: 5000,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
    },
});