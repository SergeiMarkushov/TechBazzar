import {useKeycloak} from "@react-keycloak/web";
import React, {useEffect} from 'react';
import {Button} from "react-bootstrap";
import {primary} from "../Colors";
import {getHeaderAuthCloseSvg, getHeaderAuthOpenSvg} from "../Svg";
import {removeTokenKeyCloak, setTokenKeyCloak} from "../util/KeyCloakToken";

export interface HeaderLinkAuthProps {
    isHeader?: boolean,
}

export function HeaderLinkAuth(props: HeaderLinkAuthProps) {
    const {keycloak, initialized} = useKeycloak();

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
            if (keycloak.token) {
                setTokenKeyCloak(keycloak.token);
            }
        }
    }, [initialized, keycloak.authenticated, keycloak.token])

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
            if (keycloak.token) {
                setTokenKeyCloak(keycloak.token);
            }
        }
    }, [keycloak.onAuthRefreshSuccess])

    useEffect(() => {
        if (initialized && !keycloak.authenticated) {
            removeTokenKeyCloak();
        }
    }, [keycloak.onAuthLogout])

    function signInViaKeycloak() {
        keycloak.login();
    }

    function signOutViaKeycloak() {
        keycloak.logout();
    }


    return (
        <div>
            {props.isHeader ?
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
                </button> :
                <Button onClick={() => {
                    if (initialized && keycloak.authenticated) {
                        signOutViaKeycloak();
                    } else {
                        signInViaKeycloak();
                    }
                }} style={{backgroundColor: primary, width: "15em"}}>
                    <div>
                        <small hidden={!keycloak.authenticated}><b>Выйти</b></small>
                        <small hidden={keycloak.authenticated}><b>Войти</b></small>
                    </div>
                </Button>
            }
        </div>
    )
}