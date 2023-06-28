import React, {useEffect, useState} from "react";
import {primary} from "../../../Colors";
import {apiAddItemToCart} from "../../../api/CartApi";
import {useNotify} from "../../../context/Notify";
import {ProductCard} from "./ProductPage";

export function ProductPageBuy(props: ProductCard) {
    const [discount] = useState(0);
    const notify = useNotify();

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
                <button className="btn btn-primary"
                   style={{backgroundColor: primary}}
                   onClick={() => apiAddItemToCart(props.product.id).then(() => notify.changeCartSize())}>To cart</button>
            </div>
        </div>
    )
}