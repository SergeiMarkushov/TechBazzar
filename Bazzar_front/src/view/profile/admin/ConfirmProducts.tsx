import {AxiosError, AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {primary} from "../../../Colors";
import {apiConfirmProduct, apiGetProductsNotConfirmed} from "../../../api/ProductApi";
import {useError} from "../../../auth/ErrorProvider";
import {emptyProductNew} from "../../../empty";
import {ErrorMessage, Product} from "../../../newInterfaces";
import {CatalogCard} from "../../catalog/CatalogCard";

export function ConfirmProducts() {
    const [load, setLoad] = useState(false);
    const [loadProduct, setLoadProduct] = useState(false);
    const [product, setProduct] = useState(emptyProductNew);
    const error = useError();

    useEffect(() => {
        if (!load) {
            apiGetProductsNotConfirmed().then((products: AxiosResponse<Product>) => {
                setProduct(products.data);
                setLoadProduct(true);
            }).catch((e: AxiosError<ErrorMessage>) => {
                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                if (data !== undefined) {
                    error.setErrors(data.data.message, false, false, "");
                    error.setShow(true)
                    setLoadProduct(false)
                }
            });
            setLoad(true);
        }
    }, [load]);

    const confirmHandler = () => {
        apiConfirmProduct(product.title).then(() => {
            setLoad(false);
            setLoadProduct(false);
            error.setErrors("", true, true, "Продукт подтверждён");
            error.setShow(true)
        }).catch(() => {
            error.setErrors("Ошибка подтверждения", false, false, "");
            error.setShow(true)
        });
    }

    const reloadHandler = () => {
        setLoad(false);
        setLoadProduct(false);
    }

    return (
        <div className="d-flex justify-content-center">
            <div>
                {loadProduct &&
                    < div className="d-flex row justify-content-center p-0 m-0 g-0">
                        < Button style={{backgroundColor: primary}} onClick={reloadHandler}>Reload</Button>
                        <div className="d-flex justify-content-center p-0 mb-2">
                            <CatalogCard product={product} deleteHandler={undefined}
                                         isChanging={false} key={product.id}></CatalogCard>
                        </div>
                        <Button style={{backgroundColor: primary}} onClick={confirmHandler}>Confirm</Button>
                    </div>
                }
            </div>
        </div>
    )
}