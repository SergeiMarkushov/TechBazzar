import Keycloak from "keycloak-js";

export const keycloak = new Keycloak({
    url: "http://95.165.90.118:20701/auth/",
    realm: "master",
    clientId: "postman",
});