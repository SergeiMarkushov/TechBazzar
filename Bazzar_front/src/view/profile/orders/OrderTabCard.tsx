import React from 'react';
import {OrderItemNew} from "../../../newInterfaces";

export interface OrderCardProps {
    product: OrderItemNew;
}

export function OrderTabCard(props: OrderCardProps) {
    return (
        /*TODO replace this*/
        <img src="https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png"
             className="rounded float-end m-2"
             style={{width: "50px", height: "50px"}}
         alt={props.product.productTitle}/>
    )
}