import Keycloak from "keycloak-js";

export const keycloak = new Keycloak({
    url: "http://192.168.1.110:8080/auth/",
    realm: "master",
    clientId: "postman",
});