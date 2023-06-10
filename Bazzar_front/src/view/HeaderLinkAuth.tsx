import React from 'react';
import {useAuth} from "../auth/Auth";
import {getHeaderAuthCloseSvg, getHeaderAuthOpenSvg} from "../Svg";

export function HeaderLinkAuth() {
    const auth = useAuth();
    return (
        <div className="d-flex justify-content-center align-items-center flex-column">
            <div hidden={!auth.isAuth}>
                {/*open*/}
                {getHeaderAuthOpenSvg()}
            </div>
            <div hidden={auth.isAuth}>
                {/*close*/}
                {getHeaderAuthCloseSvg()}
            </div>
            <div>
                <small hidden={!auth.isAuth}><b>Выйти</b></small>
                <small hidden={auth.isAuth}><b>Войти</b></small>
            </div>
        </div>
    )
}