import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateCharacteristics, apiDeleteCharacteristics} from "../../../api/CharacteristicApi";
import {apiGetProductByIdNew, apiUpdateProduct} from "../../../api/ProductApi";
import {useError} from "../../../auth/ErrorProvider";
import {emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, Product, ProductForCreate2} from "../../../newInterfaces";
import {ProductChangeForm} from "../organization/ProductChangeForm";

export function AdminMenuProductChangerForm() {
    const [product, setProduct] = useState(emptyProductNew);
    const [isLoad, setLoad] = useState(false);
    const {id} = useParams();
    const [file, setFile] = useState<File | null>(null)
    const error = useError();

    useEffect(() => {
            if (id !== undefined && !isLoad) {
                apiGetProductByIdNew(Number(id))
                    .then((pr: AxiosResponse<Product>) => {
                        if (pr.data !== undefined) {
                            setProduct(pr.data)
                            setLoad(true);
                        }
                    }).catch(() => {
                    error.setErrors("Упс... Что-то пошло не так, попробуйте позже", false, false, "");
                    error.setShow(true)
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
                <Formik initialValues={product}
                        enableReinitialize={true}
                        onSubmit={(values: Product) => {
                            const characteristicsDto: Array<Characteristic> | undefined = checkIfChangeCharacteristics(product, values);
                            const data: ProductForCreate2 | undefined = checkIfUpdateAndCreateProduct(product, values, characteristicsDto);

                            const formData = new FormData();
                            formData.append("productDto", JSON.stringify(data));

                            if (file !== null) {
                                formData.append("product_picture", file);
                            }

                            if (data !== undefined) {
                                apiUpdateProduct(formData).then(() => {
                                    error.setErrors("", true, true, "Продукт успешно изменён");
                                    error.setShow(true)
                                    if (product.id && characteristicsDto) {
                                        apiCreateCharacteristics(product.id, characteristicsDto).then(() => {
                                            error.setErrors("", true, true, "Продукт и характеристики успешно изменены");
                                            error.setShow(true)
                                        }).catch(() => {
                                            error.setErrors("Не удалось создать характеристики", false, false, "");
                                            error.setShow(true)
                                        })
                                    }
                                }).catch((e: AxiosError<ErrorMessage>) => {
                                    const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                    if (data !== undefined) {
                                        error.setErrors(data.data.message, false, false, "");
                                        error.setShow(true)
                                    }
                                })
                            }


                        }}>
                    {({values}) => (
                        <ProductChangeForm titleOrg={undefined} product={values} onChoseFile={onChoseFile}/>
                    )}
                </Formik>
            </div>
        </div>
    )
}

function checkIfUpdateAndCreateProduct(source: Product, changed: Product, characteristicsDto: Array<Characteristic> | undefined): ProductForCreate2 | undefined {
    const product: ProductForCreate2 = {
        id: source.id,
        title: source.title === changed.title ? undefined : changed.title,
        description: source.description === changed.description ? undefined : changed.description,
        price: source.price === changed.price ? undefined : changed.price,
        quantity: source.quantity === changed.quantity ? undefined : changed.quantity,
        characteristicsDto: characteristicsDto
    };

    if (product.title === undefined && product.description === undefined && product.price === undefined && product.quantity === undefined && product.characteristicsDto === undefined) {
        return undefined;
    }
    return product;
}

function checkIfChangeCharacteristics(product: Product, values: Product): Array<Characteristic> | undefined {
    const characteristicsDto: Array<Characteristic> = [];
    if (values.characteristicsDto !== undefined) {
        values.characteristicsDto.forEach((characteristic: Characteristic) => {
            if (characteristic.name !== "") {
                characteristicsDto.push(characteristic);
            } else {
                if (characteristic.id && characteristic.id > 0) {
                    apiDeleteCharacteristics(characteristic.id).then(() => {
                        console.log("Характеристика удалена успешно")
                    }).catch(() => {
                        console.log("Не удалось удалить характеристику")
                    })
                }
            }
        })
    }
    console.log(characteristicsDto)
    return characteristicsDto.length === 0 ? undefined : characteristicsDto;
}