import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {apiGetProductPic} from "../../../api/PictureApi";
import {OrderItemNew, OrderNew, Picture} from "../../../newInterfaces";
import {Review} from "./Review";

export interface OrderCardProps {
    product: OrderItemNew;
    order: OrderNew;
}

export function OrderCard(props: OrderCardProps) {
    const [pic, setPic] = useState<string>("");

    useEffect(() => {
        apiGetProductPic(1).then((response: AxiosResponse<Picture>) => {
            const base64String = response.data.bytes;
            const contentType = response.data.contentType;
            const dataURL = `data:${contentType};base64,${base64String}`;
            setPic(dataURL);
        }).catch((error) => {
            // eslint-disable-next-line no-console
            console.error('Error:', error);
        });
        return () => URL.revokeObjectURL(pic);
    }, [props.product.orderId]);

    return (

        <div className="card p-2 m-2 rounded shadow-sm">
            <div className="d-flex justify-content-between">
                <div className="card-title m-2">{props.product.quantity} product</div>
                {props.order.status ? <Review/> : ""}
            </div>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <img src={pic}
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