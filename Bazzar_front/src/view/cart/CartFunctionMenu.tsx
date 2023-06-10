import React from 'react';
import {apiClearCart} from "../../api/CartApi";
import {useAuth} from "../../auth/Auth";
import {primary} from "../../Colors";

interface CartFunctionMenuProps {
    onClearCart: () => void;
}

export function CartFunctionMenu(props: CartFunctionMenuProps) {
    const auth = useAuth();
    return (
        <div className="card border-0">
            <div className="d-flex justify-content-start m-1">
                <div className="btn-group-sm" role="group" aria-label="Small button group">
                    <button style={{backgroundColor: primary}} type="button" onClick={() => apiClearCart(auth.isAuth).then((prom) => props.onClearCart())} className="btn btn-sm text-white">clear</button>
                </div>
            </div>
        </div>
    )
}