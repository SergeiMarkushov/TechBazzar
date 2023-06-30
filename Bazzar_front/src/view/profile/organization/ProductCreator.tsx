import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useState} from "react";
import {useParams} from "react-router-dom";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateCharacteristics} from "../../../api/CharacteristicApi";
import {apiCreateProduct} from "../../../api/ProductApi";
import {emptyProductCreateNew2, emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductForCreate, Product} from "../../../newInterfaces";
import {ProductCreateForm} from "./ProductCreateForm";

export function ProductCreator() {
    const [file, setFile] = useState<File | null>(null)
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)
    const {title} = useParams();

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

                            setSuccess(false)
                            if (file !== null) {
                                const formData = new FormData();
                                formData.append("productDto", JSON.stringify(data));
                                formData.append("product_picture", file);
                                apiCreateProduct(formData).then((product: AxiosResponse<Product>) => {
                                    setSuccess(true)
                                    setError("")
                                    if (product.data.id !== undefined && characteristicsDto !== undefined) {
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
                            }
                        }}>
                    <ProductCreateForm onChoseFile={onChoseFile} product={emptyProductNew} titleOrg={title}
                                       error={error}
                                       textIfSuccess={"Продукт отправлен на модерацию"} success={success}/>
                </Formik>
            </div>
        </div>
    )
}