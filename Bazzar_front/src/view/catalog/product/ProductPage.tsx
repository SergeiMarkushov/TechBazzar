import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {ErrorComponent} from "../../../ErrorComponent";
import {apiGetProductByIdNew} from "../../../api/ProductApi";
import {emptyProductNew} from "../../../empty";
import {ProductNew} from "../../../newInterfaces";
import {BreadCrumbForProductPage} from "./BreadCrumbForProductPage";
import {FunctionalPanelOnCatalogCard} from "./FunctionalPanelOnCatalogCard";
import {ProductCharacteristic} from "./ProductCharacteristic";
import {ProductCompanyCard} from "./ProductCompanyCard";
import {ProductPageBuy} from "./ProductPageBuy";
import {ProductPageCommentsCard} from "./ProductPageCommentsCard";
import {ProductPageDescriptionCard} from "./ProductPageDescriptionCard";
import {ProductPageTitleCard} from "./ProductPageTitleCard";

export interface ProductCard {
    product: ProductNew;
}

export function ProductPage() {
    const [product, setProduct] = useState(emptyProductNew)
    const [load, setLoad] = useState({
        isLoad: false,
    });
    const {id} = useParams();
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)

    useEffect(() => {
            if (!load.isLoad && id !== undefined) {
                setLoad({isLoad: true});
                apiGetProductByIdNew(Number(id)).then((product) => {
                    setProduct(product.data);
                    setSuccess(true);
                }).catch(() => {
                    setSuccess(false);
                    setError("Упс... Что то пошло не так. Попробуйте позже")
                });
            }
        }, [id, load.isLoad]
    );

    return (
        <div>
            {success ?
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
                : <ErrorComponent error={error} success={success} showSuccess={false} textIfSuccess={""}/>
            }
        </div>
    )
}