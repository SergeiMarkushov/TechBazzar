import Keycloak from "keycloak-js";

export const keycloak = new Keycloak({
    url: "http://45.147.177.192:8080/auth/",
    realm: "Tech Bazzar",
    clientId: "FrontNodeJS",
});