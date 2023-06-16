import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateCharacteristics} from "../../../api/CharacteristicApi";
import {apiGetProductByIdNew, apiUpdateProduct} from "../../../api/ProductApi";
import {emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductCreateNew2, ProductNew} from "../../../newInterfaces";
import {ProductChangeForm} from "./ProductChangeForm";

export function AdminMenuProductChangerForm() {
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)
    const [product, setProduct] = useState(emptyProductNew);
    const [isLoad, setLoad] = useState(false);
    const {id} = useParams();
    const [file, setFile] = useState<File | null>(null)

    useEffect(() => {
            if (id !== undefined && !isLoad) {
                apiGetProductByIdNew(Number(id))
                    .then((pr: AxiosResponse<ProductNew>) => {
                        if (pr.data !== undefined) {
                            setProduct(pr.data)
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
    );

    const onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const file = event.target.files[0];
            if (file.size > MAX_FILE_SIZE) {
                event.target.value = "";
                setError("Картинка должна быть меньше 200 кб");
            } else {
                setFile(file);
                setError("");
            }
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={product}
                        enableReinitialize={true}
                        onSubmit={(values: ProductNew) => {
                            const data: ProductCreateNew2 = {
                                id: values.id,
                                title: values.title,
                                description: values.description,
                                price: values.price,
                                quantity: values.quantity,
                                organizationTitle: values.organizationTitle,
                                characteristicsDto: Array.of(),
                                pictureId: 1,
                            }
                            const characteristicsDto: Array<Characteristic> =
                                values.characteristicsDto.filter((value) => value.name !== "").map((value) => {
                                    return {id: null, name: value.name, product: null}
                                });
                            const formData = new FormData();
                            formData.append("productDto", JSON.stringify(data));
                            if (file !== null) {
                                formData.append("product_picture", file);
                            }
                            apiUpdateProduct(formData).then((product: AxiosResponse<ProductNew>) => {
                                setSuccess(true)
                                setError("")
                                if (product.data.id !== undefined) {
                                    apiCreateCharacteristics(product.data.id, characteristicsDto).then(() => {
                                        setSuccess(true)
                                        setError("")
                                    }).catch(() => {
                                        setError("Не удалось создать характеристики");
                                        setSuccess(false);
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
                        <ProductChangeForm titleOrg={undefined} product={values} onChoseFile={onChoseFile}
                                           textIfSuccess={"Продукт успешно изменён"} error={error}
                                           success={success}/>
                    )}
                </Formik>
            </div>
        </div>
    )
}