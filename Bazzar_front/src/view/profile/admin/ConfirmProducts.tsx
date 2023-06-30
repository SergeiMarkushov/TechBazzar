import {AxiosError, AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {primary} from "../../../Colors";
import {ErrorComponent} from "../../../ErrorComponent";
import {apiConfirmProduct, apiGetProductsNotConfirmed} from "../../../api/ProductApi";
import {emptyProductNew} from "../../../empty";
import {ErrorMessage, Product} from "../../../newInterfaces";
import {CircularLoading} from "../../CircularLoading";
import {CatalogCard} from "../../catalog/CatalogCard";

export function ConfirmProducts() {
    const [load, setLoad] = useState(false);
    const [loadProduct, setLoadProduct] = useState(false);
    const [product, setProduct] = useState(emptyProductNew);
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)

    useEffect(() => {
        if (!load) {
            apiGetProductsNotConfirmed().then((products: AxiosResponse<Product>) => {
                setProduct(products.data);
                setLoadProduct(true);
            }).catch((e: AxiosError<ErrorMessage>) => {
                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                if (data !== undefined) {
                    setError(data.data.message);
                }
            });
            setLoad(true);
        }
    }, [load]);

    const confirmHandler = () => {
        apiConfirmProduct(product.title).then(() => {
            setLoad(false);
            setLoadProduct(false);
        }).catch(() => {
            setError("Ошибка подтверждения")
            setSuccess(false)
        });
    }

    const reloadHandler = () => {
        setLoad(false);
        setLoadProduct(false);
    }

    return (
        <div className="d-flex justify-content-center">
            <div>
                <ErrorComponent error={error} success={success} showSuccess={false} textIfSuccess={""}/>
                {loadProduct ?
                    < div className="d-flex row justify-content-center p-0 m-0 g-0">
                        < Button style={{backgroundColor: primary}} onClick={reloadHandler}>Reload</Button>
                        <div className="d-flex justify-content-center p-0 mb-2">
                            <CatalogCard product={product} deleteHandler={undefined}
                                         isChanging={false} key={product.id}></CatalogCard>
                        </div>
                        <Button style={{backgroundColor: primary}} onClick={confirmHandler}>Confirm</Button>
                    </div>

                    : error === "" ? <CircularLoading/> : <div></div>
                }
            </div>
        </div>
    )
}