import React from 'react';
import {Field, Form, Formik} from "formik";
import {useAuth} from "../../auth/Auth";
import {useNavigate} from "react-router-dom";
import {AxiosError, AxiosResponse} from "axios";
import {apiSignUpWithPasswordNew} from "../../api/AuthApi";
import {useState} from "react";
import {ErrorComponent} from "../../ErrorComponent";

interface loginInit {
    email: string,
    username: string,
    password: string,
}

export function Registration() {
    const navigate = useNavigate();
    const auth = useAuth();
    const [error, setError] = useState<any>("")
    const [success, setSuccess] = useState<boolean>(false)

    const from = "/catalog";

    return (
        <Formik initialValues={{
            email: "",
            username: "",
            login: "",
            password: "",
        }}
                onSubmit={(values: loginInit) => {
                    apiSignUpWithPasswordNew(values.email, values.username, values.password).then((value: AxiosResponse) => {
                        setSuccess(true);
                    }).catch((reason: AxiosError) => {
                        setError(reason.response?.data);
                    })
                }}>
            <Form>
                <ErrorComponent error={error} success={success} showSuccess={true}
                                textIfSuccess={"Поздравляю вы зарегистрированы"}/>
                <div className="w-100 d-flex justify-content-center">
                    <div className="w-50">
                        <div className="mb-3 row">
                            <Field as="label" htmlFor="email" className="col-sm-2 col-form-label">email</Field>
                            <div className="col-sm-10">
                                <Field as="input" type="text" name="email" className="form-control shadow-sm" id="email"
                                       required={true}/>
                            </div>
                        </div>
                        <div className="mb-3 row">
                            <Field as="label" htmlFor="username" className="col-sm-2 col-form-label">username</Field>
                            <div className="col-sm-10">
                                <Field as="input" type="text" name="username" className="form-control shadow-sm"
                                       id="username"
                                       required={true}/>
                            </div>
                        </div>
                        <div className="mb-3 row">
                            <Field as="label" htmlFor="password" className="col-sm-2 col-form-label">Password</Field>
                            <div className="col-sm-10">
                                <Field as="input" name="password" type="password" className="form-control shadow-sm"
                                       id="password"
                                       required={true}/>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary mb-3">Registration</button>
                    </div>
                </div>
            </Form>
        </Formik>
    )
}