import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {apiGetProductByIdNew} from "../../../api/ProductApi";
import {useError} from "../../../auth/ErrorProvider";
import {emptyProductNew} from "../../../empty";
import {Product} from "../../../newInterfaces";
import {BreadCrumbForProductPage} from "./BreadCrumbForProductPage";
import {FunctionalPanelOnCatalogCard} from "./FunctionalPanelOnCatalogCard";
import {ProductCharacteristic} from "./ProductCharacteristic";
import {ProductCompanyCard} from "./ProductCompanyCard";
import {ProductPageBuy} from "./ProductPageBuy";
import {ProductPageCommentsCard} from "./ProductPageCommentsCard";
import {ProductPageDescriptionCard} from "./ProductPageDescriptionCard";
import {ProductPageTitleCard} from "./ProductPageTitleCard";

export interface ProductCard {
    product: Product;
}

export function ProductPage() {
    const [product, setProduct] = useState(emptyProductNew)
    const {id} = useParams();
    const error = useError();

    useEffect(() => {
            if (id !== undefined) {
                apiGetProductByIdNew(Number(id)).then((product) => {
                    setProduct(product.data);
                    error.setErrors("", true, false, "");
                }).catch(() => {
                    error.setErrors("Упс... Что то пошло не так. Попробуйте обновить страницу", false, false, "");
                    error.setShow(true)
                });
            }
        }, [id]
    );

    return (
        <div>
            {error.success &&
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
                    <ProductCharacteristic product={product}/>
                    <ProductPageDescriptionCard product={product}/>
                    <ProductPageCommentsCard product={product}/>
                </div>
            }
        </div>
    )
}