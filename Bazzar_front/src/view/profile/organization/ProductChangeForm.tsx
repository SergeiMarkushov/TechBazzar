import {Field, FieldArray, Form} from "formik";
import React, {useEffect, useState} from 'react';
import {MAX_FILE_SIZE} from "../../../CONST";
import {Product} from "../../../newInterfaces";

interface ProductChangeFormProps {
    product: Product,
    titleOrg: string | undefined,
    onChoseFile: (event: React.ChangeEvent<HTMLInputElement>) => void
}

export function ProductChangeForm(props: ProductChangeFormProps) {
    const [characteristics, setCharacteristics] = useState(props.product.characteristicsDto)

    useEffect(() => {
        setCharacteristics(props.product.characteristicsDto)
    }, [props.product.characteristicsDto])

    const addCharacteristic = () => {
        setCharacteristics([...characteristics, {id: null, name: "", product: null}])
    }

    return (
        <div>
            <Form className="row g-3">
                <div className="col-md-3">
                    <Field as="label" htmlFor="id" className="form-label">ID</Field>
                    <Field as="input" name="id" disabled={true} value={props.product.id} type="text"
                           className="form-control shadow-sm" id="id"
                           required={false}/>
                </div>
                <div className="col-md-9">
                    <Field as="label" htmlFor="title" className="form-label">Заголовок продукта</Field>
                    <Field as="input" name="title" type="text" className="form-control shadow-sm" id="title"
                           required={true}/>
                </div>
                <div className="col-12">
                    <Field as="label" htmlFor="description" className="form-label">Описание</Field>
                    <Field as="textarea" name="description" placeholder="Краткое описание вашего продукта"
                           className="form-control shadow-sm" id="description"
                           required={true}/>
                </div>
                <div className="col-md-12">
                    <Field as="label" htmlFor="organizationTitle" className="form-label">Название
                        организации</Field>
                    <small className="form-text text-muted"> (Вы должны иметь доступ к этой организации)</small>
                    <Field as="input" name="organizationTitle"
                           placeholder="Название организации от имени которой предоставляется продукт" type="text"
                           value={props.titleOrg ?? props.product.organizationTitle ?? ""}
                           disabled={props.titleOrg ?? props.product.organizationTitle}
                           className="form-control shadow-sm"
                           id="organizationTitle"
                           required={true}/>
                </div>
                <div className="col-md-6">
                    <Field as="label" htmlFor="price" className="form-label">Цена</Field>
                    <Field as="input" name="price" type="number" className="form-control shadow-sm" id="price"
                           required={true}/>
                </div>
                <div className="col-md-6">
                    <Field as="label" htmlFor="quantity" className="form-label">Колличество</Field>
                    <Field as="input" name="quantity" type="number" className="form-control shadow-sm" id="quantity"
                           required={true}/>
                </div>
                <div className="col-12">
                    <Field as="label" htmlFor="companyImage" className="form-label">Логотип</Field>
                    <small className="form-text text-muted"> (Максимальный размер
                        файла: {MAX_FILE_SIZE / 1024} КБ)</small>
                    <Field as="file" type="file" name="companyImage" className="form-control shadow-sm"
                           id="companyImage"
                           required={false}>
                        <input type="file" onChange={props.onChoseFile} id="companyImage" name="companyImage"/>
                    </Field>
                </div>
                <FieldArray name={"characteristicsDto"}
                            validateOnChange={true}
                            render={() => (
                                <div className="row g-3">
                                    {
                                        characteristics.map((value, index) => {
                                            return (
                                                <div className="col-md-3" key={index}>
                                                    <Field as="label" htmlFor={"characteristicsDto" + index}
                                                           className="form-label">Характеристика {index + 1}</Field>
                                                    <Field as="input" name={`characteristicsDto.${index}.name`}
                                                           type="text"
                                                           className="form-control shadow-sm"
                                                           id={`characteristicsDto.${index}.name`}
                                                           required={index === 0}/>
                                                </div>
                                            )
                                        })
                                    }
                                </div>
                            )}/>
                <div className="col-12">
                    <button type={"button"} className="btn btn-sm btn-primary"
                            onClick={addCharacteristic}>Добавить характеристику
                    </button>
                </div>
                {/*<div className="col-12">
                <Field as="label" htmlFor="image" className="form-label">Image URL</Field>
                <Field as="input" name="image" type="text" className="form-control" id="image"
                       required={true}/>
            </div>*/}
                <div className="col-12">
                    <button type="submit" className="btn btn-primary">save</button>
                </div>
            </Form>
        </div>
    )
}