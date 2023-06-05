import {ProductPageTitleCard} from "./ProductPageTitleCard";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {ProductPageDescriptionCard} from "./ProductPageDescriptionCard";
import {ProductPageBuy} from "./ProductPageBuy";
import {FunctionalPanelOnCatalogCard} from "./FunctionalPanelOnCatalogCard";
import { ProductPageCommentsCard } from "./ProductPageCommentsCard";
import { BreadCrumbForProductPage } from "./BreadCrumbForProductPage";
import {ProductNew} from "../../../newInterfaces";
import {emptyProductNew} from "../../../empty";
import {apiGetProductByIdNew} from "../../../api/ProductApi";
import {ProductCompanyCard} from "./ProductCompanyCard";

export interface ProductCard {
    product: ProductNew;
}

export function ProductPage() {
    const [product, setProduct] = useState(emptyProductNew)
    const [load, setLoad] = useState({
        isLoad: false,
    });
    let {id} = useParams();

    useEffect(() => {
            if (!load.isLoad && id !== undefined) {
                apiGetProductByIdNew(Number(id)).then((product) => {
                    setProduct(product.data);
                })
                setLoad({isLoad: true});
            }
        }, [id, load.isLoad]
    );

    return (
        <div className="m-2">
            <div className="row align-items-start">
                <BreadCrumbForProductPage/>
                <FunctionalPanelOnCatalogCard product={product}/>
                <div className="col flex-grow-1">
                    <ProductPageTitleCard product={product}/>
                </div>
                <div className="col flex-grow-0">
                    <ProductPageBuy product={product}/>
                </div>
            </div>
            <ProductCompanyCard product={product}/>
            <ProductPageDescriptionCard product={product}/>
            <ProductPageCommentsCard product={product}/>
        </div>
    )
}