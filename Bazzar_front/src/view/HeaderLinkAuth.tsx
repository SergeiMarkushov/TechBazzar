import {useKeycloak} from "@react-keycloak/web";
import React from 'react';
import {getHeaderAuthCloseSvg, getHeaderAuthOpenSvg} from "../Svg";

export function HeaderLinkAuth() {
    const {keycloak, initialized} = useKeycloak();

    function signInViaKeycloak() {
        keycloak.login()
    }

    function signOutViaKeycloak() {
        keycloak.logout()
    }


    return (
        <button
            className="d-flex justify-content-center align-items-center text-secondary bg-transparent border-0 flex-column"
            onClick={() => {
                if (initialized && keycloak.authenticated) {
                    signOutViaKeycloak();
                } else {
                    signInViaKeycloak();
                }
            }}
        >
            <div hidden={!keycloak.authenticated}>
                {/*open*/}
                {getHeaderAuthOpenSvg()}
            </div>
            <div hidden={keycloak.authenticated}>
                {/*close*/}
                {getHeaderAuthCloseSvg()}
            </div>
            <div>
                <small hidden={!keycloak.authenticated}><b>Выйти</b></small>
                <small hidden={keycloak.authenticated}><b>Войти</b></small>
            </div>
        </button>
    )
}