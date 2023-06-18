import React from 'react';
import {useLocation} from "react-router-dom";
import {ProductCard} from "./ProductPage";

export function ProductPageTitleCard(props: ProductCard) {
    const location = useLocation();
    const picture = location.state?.pic;

    return (
        <div className="card mb-3 border-0">
            <div className="row g-0">
                <div className="col-md-4">
                    <img alt={props.product.title} src={picture} className="img-fluid rounded-start"/>
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