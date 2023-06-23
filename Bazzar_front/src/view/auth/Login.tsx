import {useKeycloak} from "@react-keycloak/web";
import {Field, Form, Formik} from "formik";
import React, {useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import {ErrorComponent} from "../../ErrorComponent";
import {apiKeycloakAdmin, apiKeycloakUser} from "../../api/AuthApi";
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
    const keycloak = useKeycloak();
    const [isAdmin, setIsAdmin] = useState<boolean>(false);
    const [isUser, setIsUser] = useState<boolean>(false);

    const from = "/catalog";

    function signInViaKeycloak() {
        keycloak.keycloak.login()
            .then(() => {
                console.log("success");
            })
            .catch(() => {
                setError("Неверный логин или пароль");
                setSuccess(false);
            });
    }

    function signOutViaKeycloak() {
        keycloak.keycloak.logout()
            .then(() => {
                console.log("success");
            })
            .catch(() => {
                setError("Неверный логин или пароль");
                setSuccess(false);
            });
    }

    function signInViaBack(values: loginInit) {
        auth.signin(values.login, values.password, () => {
            setSuccess(true);
            navigate(from, {replace: true});
        }, () => {
            setError(auth.error);
            setSuccess(false)
        });
    }

    return (
        <div>
            <span
                className={`${keycloak.keycloak.authenticated ? "bg-success" : "bg-danger"} me-2 p-2 rounded text-white`}>KeyCloak</span>
            <span className={`${isAdmin ? "bg-success" : "bg-danger"} me-2 p-2 rounded text-white`}>Admin</span>
            <span className={`${isUser ? "bg-success" : "bg-danger"} me-2 p-2 rounded text-white`}>User</span>
            <Formik initialValues={{
                login: "",
                password: "",
            }}
                    onSubmit={(values: loginInit) => {
                        signInViaBack(values);
                    }}>

                <Form>
                    <ErrorComponent error={error} success={success} showSuccess={true} textIfSuccess={"Поздравляю"}/>
                    <div className="w-100 d-flex justify-content-center">
                        <div className="w-50">
                            <div className="mb-3 row">
                                <Field as="label" htmlFor="login" className="col-sm-2 col-form-label">E-mail</Field>
                                <div className="col-sm-10">
                                    <Field as="input" type="text" autoComplete="username" placeholder="index@mail.ru"
                                           name="login"
                                           className="form-control shadow-sm" id="login"
                                           required={true}/>
                                </div>
                            </div>
                            <div className="mb-3 row">
                                <Field as="label" htmlFor="password"
                                       className="col-sm-2 col-form-label">Пароль</Field>
                                <div className="col-sm-10">
                                    <Field as="input" name="password" autoComplete="current-password" type="password"
                                           className="form-control shadow-sm"
                                           id="password"
                                           required={true}/>
                                </div>
                            </div>
                            <div className="d-flex justify-content-between">
                                <button type="submit" className="btn btn-primary mb-3">Войти</button>
                                <Link to="/signUp" className="btn btn-primary mb-3">Зарегистрироваться</Link>
                            </div>
                            <div className="d-flex justify-content-between">
                                <button type="button"
                                        onClick={() => keycloak.keycloak.authenticated ? signOutViaKeycloak() : signInViaKeycloak()}
                                        className="btn btn-primary mb-3">{keycloak.keycloak.authenticated ? "Выйти из KeyCloak" : "Войти через KeyCloak"}
                                </button>
                            </div>
                        </div>
                    </div>
                </Form>
            </Formik>
            {
                keycloak.keycloak.authenticated &&
                <div>
                    <button className="btn btn-primary me-2" type="button"
                            onClick={() => {
                                apiKeycloakAdmin(keycloak?.keycloak?.token ?? "")
                                    .then((value) => {
                                        console.log(value);
                                        setIsAdmin(true);
                                    }).catch(() => setIsAdmin(false));
                            }}>tryAdmin
                    </button>
                    <button className="btn btn-primary" type="button"
                            onClick={() => {
                                apiKeycloakUser(keycloak?.keycloak?.token ?? "")
                                    .then((value) => {
                                        console.log(value);
                                        setIsUser(true)
                                    }).catch(() => setIsUser(false));

                            }}>tryUser
                    </button>
                </div>
            }
        </div>
    );
}