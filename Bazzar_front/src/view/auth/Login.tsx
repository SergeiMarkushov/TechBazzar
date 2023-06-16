import {Field, Form, Formik} from "formik";
import React from 'react';
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {ErrorComponent} from "../../ErrorComponent";
import {useAuth} from "../../auth/Auth";

interface loginInit {
    login: string,
    password: string,
}

export function Login() {
    const navigate = useNavigate();
    const auth = useAuth();
    const [error, setError] = useState<string>("");
    const [success, setSuccess] = useState<boolean>(false);
    
    const from = "/catalog";

    return (
        <Formik initialValues={{
            login: "",
            password: "",
        }}
                onSubmit={(values: loginInit) => {
                    auth.signin(values.login, values.password, () => {
                        setSuccess(true);
                        navigate(from, {replace: true});
                    }, () => {
                        setError(auth.error);
                        setSuccess(false)
                    });
                }}>

            <Form>
                <ErrorComponent error={error} success={success} showSuccess={true} textIfSuccess={"Поздравляю"}/>
                <div className="w-100 d-flex justify-content-center">
                    <div className="w-50">
                        <div className="mb-3 row">
                            <Field as="label" htmlFor="login" className="col-sm-2 col-form-label">E-mail</Field>
                            <div className="col-sm-10">
                                <Field as="input" type="text" autoComplete="username" placeholder="index@mail.ru" name="login"
                                       className="form-control shadow-sm" id="login"
                                       required={true}/>
                            </div>
                        </div>
                        <div className="mb-3 row">
                            <Field as="label" htmlFor="password" className="col-sm-2 col-form-label">Password</Field>
                            <div className="col-sm-10">
                                <Field as="input" name="password" autoComplete="current-password"  type="password" className="form-control shadow-sm"
                                       id="password"
                                       required={true}/>
                            </div>
                        </div>
                        <div className="d-flex justify-content-between">
                            <button type="submit" className="btn btn-primary mb-3">Login</button>
                            <Link to="/signUp" className="btn btn-primary mb-3">SignUp</Link>
                        </div>
                    </div>
                </div>
            </Form>
        </Formik>
    );
}