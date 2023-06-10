import React from 'react';
import {Field, Form} from "formik";
import {ErrorComponent} from "../../../ErrorComponent";
import {useAuth} from "../../../auth/Auth";

interface OrganizationCreateFormProps {
    onChoseFile: (event: React.ChangeEvent<HTMLInputElement>) => void,
    error: any,
    success: boolean
}

export function OrganizationCreateForm({onChoseFile, error, success}: OrganizationCreateFormProps) {
    const auth = useAuth();

    return (
        <div>
            <ErrorComponent error={error} success={success} showSuccess={true}
                            textIfSuccess={"Организация отправлена на модерацию"}/>
            <Form className="row g-3 justify-content-center">
                <div className="col-md-9">
                    <Field as="label" htmlFor="owner" className="form-label">Почта</Field>
                    <Field as="input" name="owner" value={auth.user.email} disabled type="text"
                           className="form-control shadow-sm" id="owner"
                           required={true}/>
                </div>
                <div className="col-md-9">
                    <Field as="label" htmlFor="name" className="form-label">Название организации</Field>
                    <Field as="input" name="name" type="text" className="form-control shadow-sm" id="name"
                           required={true}/>
                </div>
                <div className="col-9">
                    <Field as="label" htmlFor="description" className="form-label">Описание</Field>
                    <Field as="textarea" name="description" resize="auto" placeholder="Краткое описание вашей компании"
                           className="form-control shadow-sm" id="description"
                           required={true}/>
                </div>
                <div className="col-9">
                    <Field as="label" htmlFor="companyImage" className="form-label">Логотип</Field>
                    <Field as="file" type="file" name="companyImage" className="form-control shadow-sm"
                           id="companyImage"
                           required={true}>
                        <input type="file" onChange={onChoseFile} id="companyImage" name="companyImage"/>
                    </Field>
                </div>
                <div className="col-9">
                    <button type="submit" className="btn btn-sm btn-primary">Отправить</button>
                </div>
            </Form>
        </div>
    )
}