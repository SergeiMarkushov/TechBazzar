import React from 'react';
import {OrderItemNew, OrderNew} from "../../../newInterfaces";
import { Review } from "./Review";

export interface OrderCardProps {
    product: OrderItemNew;
    order: OrderNew;
}

export function OrderCard(props: OrderCardProps) {
    return (

        <div className="card p-2 m-2 rounded shadow-sm">
            <div className="d-flex justify-content-between">
                <div className="card-title m-2">{props.product.quantity} product</div>
                {props.order.status ? <Review/> : ""}
            </div>
            <div className="container">
                <div className="row">
                    <div className="col">
                        {/*TODO replace this*/}
                        <img src="https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png"
                             className="img-fluid rounded" alt="..."
                             style={{maxWidth: "70px", maxHeight: "70px", minWidth: "70px", minHeight: "70px"}}/>
                    </div>
                    <div className="col">
                        <small className="card-text">{props.product.productTitle}</small>
                    </div>
                    <div className="col">
                        <small className="">{props.product.price} ₽</small>
                        <br/>
                        <small className="">{props.product.pricePerProduct}₽/one</small>
                    </div>
                </div>
            </div>
        </div>

    )
}