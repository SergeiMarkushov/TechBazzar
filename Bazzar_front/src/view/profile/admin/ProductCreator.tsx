import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useState} from "react";
import {useParams} from "react-router-dom";
import {apiCreateCharacteristics, apiCreateOrUpdateProductNew} from "../../../api/ProductApi";
import {emptyProductCreateNew2, emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductCreateNew2, ProductNew} from "../../../newInterfaces";
import {ProductCreateForm} from "./ProductCreateForm";

export function ProductCreator() {
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)
    const {title} = useParams();
    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={emptyProductCreateNew2}
                        onSubmit={(values: ProductCreateNew2) => {
                            const data: ProductCreateNew2 = {
                                title: values.title,
                                description: values.description,
                                price: values.price,
                                quantity: values.quantity,
                                organizationTitle: title ?? values.organizationTitle,
                                characteristicsDto: Array.of()
                            }
                            const characteristicsDto: Array<Characteristic> = Array.from(values.characteristicsDto.map((value) => {
                                return {id: null, name: value, product: null}
                            }))
                            console.log(data)
                            setSuccess(false)
                            apiCreateOrUpdateProductNew(data).then((product: AxiosResponse<ProductNew>) => {
                                setSuccess(true)
                                setError("")
                                if (product.data.id !== undefined) {
                                    apiCreateCharacteristics(product.data.id, characteristicsDto).then(() => {
                                        console.log("success characteristic")
                                        setSuccess(true)
                                        setError("")
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
                    <ProductCreateForm product={emptyProductNew} titleOrg={title} error={error}
                                       textIfSuccess={"Продукт отправлен на модерацию"} success={success}/>
                </Formik>
            </div>
        </div>
    )
}