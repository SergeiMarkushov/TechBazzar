import React, {useEffect, useState} from "react";
import {ErrorMessage, ProductNew} from "../../../newInterfaces";
import {emptyProductNew} from "../../../empty";
import {apiConfirmProduct, apiGetProductsNotConfirmed} from "../../../api/ProductApi";
import {AxiosError, AxiosResponse} from "axios";
import {CatalogCard} from "../../catalog/CatalogCard";
import {CircularLoading} from "../../CircularLoading";
import {Button} from "react-bootstrap";
import {primary} from "../../../Colors";
import {ErrorComponent} from "../../../ErrorComponent";

export function ConfirmProducts() {
    const [load, setLoad] = useState(false);
    const [loadProduct, setLoadProduct] = useState(false);
    const [product, setProduct] = useState(emptyProductNew);
    const [error, setError] = useState<any>("")
    const [success, setSuccess] = useState<boolean>(false)

    useEffect(() => {
        if (!load) {
            console.log("useEffect")
            apiGetProductsNotConfirmed().then((products: AxiosResponse<ProductNew>) => {
                console.log(products.data)
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
        apiConfirmProduct(product.title).then((r) => {
            console.log(r.data)
            setLoad(false);
            setLoadProduct(false);
        }).catch(e => {
            console.log(e)
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
                        <div className="d-flex justify-content-center p-0">
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