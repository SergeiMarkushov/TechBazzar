import React from 'react';
import {primary} from "../../Colors";
import {apiClearCart} from "../../api/CartApi";

interface CartFunctionMenuProps {
    onClearCart: () => void;
}

export function CartFunctionMenu(props: CartFunctionMenuProps) {
    return (
        <div className="card border-0">
            <div className="d-flex justify-content-start m-1">
                <div className="btn-group-sm" role="group" aria-label="Small button group">
                    <button style={{backgroundColor: primary}} type="button" onClick={() => apiClearCart().then(() => props.onClearCart())} className="btn btn-sm text-white">clear</button>
                </div>
            </div>
        </div>
    )
}