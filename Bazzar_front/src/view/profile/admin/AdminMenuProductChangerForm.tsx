import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {
    apiCleanCache,
    apiCreateCharacteristics,
    apiCreateOrUpdateProductNew,
    apiGetProductByIdNew,
    apiUpdateProduct
} from "../../../api/ProductApi";
import {emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductNew} from "../../../newInterfaces";
import {ProductChangeForm} from "./ProductChangeForm";

export function AdminMenuProductChangerForm() {
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)
    const [product, setProduct] = useState(emptyProductNew);
    const [isLoad, setLoad] = useState(false);
    const {id} = useParams();

    useEffect(() => {
            if (id !== undefined && !isLoad) {
                apiGetProductByIdNew(Number(id))
                    .then((pr: AxiosResponse<ProductNew>) => {
                        if (pr.data !== undefined) {
                            setProduct(pr.data)
                            console.log(pr.data)
                            setLoad(true);
                        }
                    }).catch(() => {
                    setError("Упс... Что-то пошло не так, попробуйте позже");
                    setSuccess(false);
                });
            }
        }

        ,
        [id, isLoad, product]
    )
    ;

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={product}
                        enableReinitialize={true}
                        onSubmit={(values: ProductNew) => {
                            console.log(values)
                            const characteristicsDto: Array<Characteristic> =
                                values.characteristicsDto.filter((value) => value.name !== "");

                            apiCreateOrUpdateProductNew(values).then(() => {
                                console.log("success product")
                                if (values.id !== undefined) {
                                    apiCleanCache().then(() => {
                                        console.log("success update product")
                                    }).catch((e: AxiosError) => {
                                        console.log("error update product")
                                        console.log(e)
                                    })
                                    apiCreateCharacteristics(values.id, characteristicsDto).then(() => {
                                        setSuccess(true)
                                    }).catch((e: AxiosError) => {
                                        console.log("error characteristic")
                                        console.log(e)
                                    })
                                }
                            }).catch((e: AxiosError<ErrorMessage>) => {
                                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                if (data !== undefined) {
                                    setError(data.data.message);
                                }
                            })
                        }}>
                    {({values}) => (
                        <ProductChangeForm titleOrg={undefined} product={values}
                                           textIfSuccess={"Продукт успешно изменён"} error={error}
                                           success={success}/>
                    )}

                </Formik>
            </div>
        </div>
    )
}