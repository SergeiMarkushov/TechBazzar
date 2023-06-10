import React from 'react';
import {Field, Form} from "formik";
import {ProductCreateNew} from "../newInterfaces";
import {ErrorComponent} from "../ErrorComponent";

interface ProductFormProps {
    product: ProductCreateNew,
    error: any,
    success: any,
    textIfSuccess: string
}

export function ProductForm(props: ProductFormProps) {
    return (
        <div>
            <ErrorComponent error={props.error} success={props.success} showSuccess={true}
                            textIfSuccess={props.textIfSuccess}/>
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