import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {apiGetPicByProductId, apiGetProductPic} from "../../../api/PictureApi";
import {OrderItem, Order, Picture} from "../../../newInterfaces";
import {ReviewComponent} from "./ReviewComponent";

export interface OrderCardProps {
    product: OrderItem;
    order: Order;
}

export function OrderCard(props: OrderCardProps) {
    const [pic, setPic] = useState<string>("");

    useEffect(() => {
        if (props.product.productId !== 0) {
            apiGetPicByProductId(props.product.productId).then((response: AxiosResponse<Picture>) => {
                const base64String = response.data.bytes;
                const contentType = response.data.contentType;
                const dataURL = `data:${contentType};base64,${base64String}`;
                setPic(dataURL);
            }).catch((error) => {
                // eslint-disable-next-line no-console
                console.error('Error:', error);
            });
        }
        return () => URL.revokeObjectURL(pic);
    }, [props.product.orderId]);

    return (

        <div className="card p-2 m-2 rounded shadow-sm">
            <div className="d-flex justify-content-between">
                <div className="card-title m-2">{props.product.quantity} товар</div>
                {props.order.status ? <ReviewComponent product={props.product}/> : ""}
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