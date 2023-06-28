import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {apiGetProductPic} from "../../../api/PictureApi";
import {Picture} from "../../../newInterfaces";
import {ProductCard} from "./ProductPage";

export function ProductPageTitleCard(props: ProductCard) {
    const [pic, setPic] = useState<string>("");

    useEffect(() => {
        apiGetProductPic(props.product.pictureId).then((response: AxiosResponse<Picture>) => {
            const base64String = response.data.bytes;
            const contentType = response.data.contentType;
            const dataURL = `data:${contentType};base64,${base64String}`;
            setPic(dataURL);
        }).catch((error) => {
            // eslint-disable-next-line no-console
            console.error('Error:', error);
        });
        return () => URL.revokeObjectURL(pic);
    }, [props.product.title]);

    return (
        <div className="card mb-3 border-0">
            <div className="row g-0">
                <div className="col-md-4">
                    <img alt={props.product.title} src={pic} className="img-fluid rounded-start"/>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{props.product.title}</h5>
                    </div>
                </div>
            </div>
        </div>
    )
}