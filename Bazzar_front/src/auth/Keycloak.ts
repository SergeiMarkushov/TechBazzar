import Keycloak from "keycloak-js";

export const keycloak = new Keycloak({
    url: "http://localhost:8080/auth/",
    realm: "Tech Bazzar",
    clientId: "FrontNodeJS",
});