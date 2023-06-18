import React from 'react';
import {ProductCard} from "./ProductPage";

export function ProductPageDescriptionCard(props: ProductCard) {
    return (
        <div className="card border-0">
            <div className="card-body">
                <h5 className="card-title">Description</h5>
                <p className="card-text">{props.product.description}</p>
            </div>
        </div>
    )
}