import Rating from "@mui/material/Rating/Rating";
import React, {useEffect, useState} from 'react';
import {apiGetProductMark} from "../../../api/ReviewApi";
import {Product} from "../../../newInterfaces";

interface FunctionalPanelOnCatalogCardProps {
    product: Product;
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export function FunctionalPanelOnCatalogCard(props: FunctionalPanelOnCatalogCardProps) {
    const [mark, setMark] = useState(0);

    useEffect(() => {
        if (props.product.id) {
            apiGetProductMark(props.product.id).then((response) => {
                setMark(response.data);
            }).catch(() => {
                setMark(0);
            });
        }
    }, [props.product.id]);

    return (
        <div className="d-flex mb-2">
            <div className="d-flex align-items-center me-3" style={{maxHeight: "50px"}}>
                <Rating name="half-rating-read" size="small" value={mark}
                        precision={0.5} readOnly/>
                <label className="ms-1">{mark}</label>
            </div>
        </div>
    )
}