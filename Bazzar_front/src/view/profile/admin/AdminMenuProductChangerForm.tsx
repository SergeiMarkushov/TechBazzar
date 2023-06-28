import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateCharacteristics, apiDeleteCharacteristics} from "../../../api/CharacteristicApi";
import {apiGetProductByIdNew, apiUpdateProduct} from "../../../api/ProductApi";
import {emptyProductNew} from "../../../empty";
import {Characteristic, ErrorMessage, ProductForCreate, Product, ProductForCreate2} from "../../../newInterfaces";
import {ProductChangeForm} from "../organization/ProductChangeForm";

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
                    .then((pr: AxiosResponse<Product>) => {
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
                                    setSuccess(true)
                                    setError("")
                                    if (product.id && characteristicsDto) {
                                        apiCreateCharacteristics(product.id, characteristicsDto).then(() => {
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