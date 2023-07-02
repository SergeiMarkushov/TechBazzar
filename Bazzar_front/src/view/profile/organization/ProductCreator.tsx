import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useState} from "react";
import {useParams} from "react-router-dom";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateCharacteristics} from "../../../api/CharacteristicApi";
import {apiCreateProduct} from "../../../api/ProductApi";
import {useError} from "../../../auth/ErrorProvider";
import {emptyProductCreateNew2, emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductForCreate, Product} from "../../../newInterfaces";
import {ProductCreateForm} from "./ProductCreateForm";

export function ProductCreator() {
    const [file, setFile] = useState<File | null>(null)
    const error = useError();
    const {title} = useParams();

    const onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const file = event.target.files[0];
            if (file.size > MAX_FILE_SIZE) {
                event.target.value = "";
                error.setErrors("Картинка должна быть меньше 5 мб", false, false, "");
                error.setShow(true)
            } else {
                setFile(file);
            }
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={emptyProductCreateNew2}
                        onSubmit={(values: ProductForCreate) => {
                            const data: ProductForCreate = {
                                title: values.title,
                                description: values.description,
                                price: values.price,
                                quantity: values.quantity,
                                organizationTitle: title ?? values.organizationTitle,
                            }

                            const characteristicsDto: Array<Characteristic> | undefined = values.characteristicsDto
                                ? values.characteristicsDto.map(value => ({id: null, name: value, product: null}))
                                : undefined;

                            if (file !== null) {
                                const formData = new FormData();
                                formData.append("productDto", JSON.stringify(data));
                                formData.append("product_picture", file);
                                apiCreateProduct(formData).then((product: AxiosResponse<Product>) => {
                                    error.setErrors("", true, true, "Продукт отправлен на модерацию");
                                    error.setShow(true);
                                    if (product.data.id !== undefined && characteristicsDto !== undefined) {
                                        apiCreateCharacteristics(product.data.id, characteristicsDto).then(() => {
                                            error.setErrors("", true, true, "Продукт отправлен на модерацию, характеристики созданны");
                                            error.setShow(true);
                                        }).catch(() => {
                                            error.setErrors("Не удалось создать характеристики", false, false, "");
                                            error.setShow(true)
                                        })
                                    }

                                }).catch((e: AxiosError<ErrorMessage>) => {
                                    const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                    if (data !== undefined) {
                                        error.setErrors(data.data.message, false, false, "");
                                        error.setShow(true);
                                    }
                                })
                            }
                        }}>
                    <ProductCreateForm onChoseFile={onChoseFile} product={emptyProductNew} titleOrg={title}/>
                </Formik>
            </div>
        </div>
    )
}