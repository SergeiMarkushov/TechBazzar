import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {apiGetPicByProductId, apiGetProductPic} from "../../../api/PictureApi";
import {OrderItem, Picture} from "../../../newInterfaces";

export interface OrderCardProps {
    product: OrderItem;
}

export function OrderTabCard(props: OrderCardProps) {
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
        <img src={pic}
             className="rounded float-end m-2"
             style={{width: "50px", height: "50px"}}
             alt={props.product.productTitle}/>
    )
}