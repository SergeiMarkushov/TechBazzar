import {ProductCard} from "./ProductPage";
import {apiAddItemToCart} from "../../../api/CartApi";
import {useAuth} from "../../../auth/Auth";
import React, {useEffect, useState} from "react";
import {primary} from "../../../Colors";

export function ProductPageBuy(props: ProductCard) {
    let auth = useAuth();
    let [discount, setDiscount] = useState(0);

    useEffect(() => {
        /*setDiscount(props.product.discount !== null && props.product.discount.dis != null ? props.product.discount.dis : props.product.price);*/
    });

    return (
        <div className="card shadow-sm" style={{width: "14rem"}}>
            <div className="card-body row">
                <h5 className="card-title">Price</h5>
                <div className="mb-2">
                    <b className="card-text text-danger pb-3 me-2">{discount === 0 ? props.product.price : discount} ₽</b>
                    <small className="card-subtitlerounded-1 mb-3" style={{
                        color: "grey",
                        textDecoration: "line-through"
                    }}>{props.product.price} ₽</small>
                </div>
                <small className="mb-2 text-black">Quantity: {props.product.quantity}</small>
                <a href="#" className="btn btn-primary"
                   style={{backgroundColor: primary}}
                   onClick={() => apiAddItemToCart(props.product.id, auth.isAuth).then()}>To cart</a>
            </div>
        </div>
    )
}