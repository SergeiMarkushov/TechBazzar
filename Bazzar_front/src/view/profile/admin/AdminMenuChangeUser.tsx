import React from 'react';
import {useParams} from "react-router-dom";
import {Field, Form, Formik} from "formik";
import {useEffect, useState} from "react";
import {defaultUserNew} from "../../../empty";
import {apiGetMyUserById} from "../../../api/UserApi";
import {UserNew} from "../../../newInterfaces";


export function AdminMenuChangeUser() {
    const {id} = useParams();
    const [isLoad, setLoad] = useState(false);
    const [user, setUser] = useState(defaultUserNew);
    let usersome: UserNew = defaultUserNew;

    useEffect(() => {
        if (id !== undefined && !isLoad) {
            apiGetMyUserById(Number(id)).then((resp) => {
                setUser(resp.data);
                usersome = resp.data;
                setLoad(true);
            });
        }
    });

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={user}
                        enableReinitialize={true}
                        onSubmit={(values: UserNew) => {
                            console.log(values)
                        }}>
                    {props => (
                        <Form className="row g-3">
                            <div className="col-md-3">
                                <Field as="label" htmlFor="id" className="form-label">id</Field>
                                <Field as="input" name="id" disabled={true} value={props.values.id} type="text"
                                       className="form-control" id="id"
                                       required={false}/>
                            </div>
                            <div className="col-md-9">
                                <Field as="label" htmlFor="username" className="form-label">username</Field>
                                <Field as="input" name="username" type="text" value={props.values.username} className="form-control" id="username"
                                       required={true}/>
                            </div>
                            <div className="col-12">
                                <Field as="label" htmlFor="email" className="form-label">email</Field>
                                <Field as="input" type="email" name="email" value={props.values.email} className="form-control" id="email"
                                       required={true}/>
                            </div>
                            <div className="col-md-6">
                                <Field as="label" htmlFor="balance" className="form-label">balance</Field>
                                <Field as="input" name="balance" type="number" value={props.values.balance} className="form-control" id="balance"
                                       required={true}/>
                            </div>
                            <div className="col-md-6">
                                <Field as="label" htmlFor="active" className="form-label">active</Field>
                                <Field as="input" name="active" type="boolean" value={props.values.active} className="form-control" id="active"
                                       required={true}/>
                            </div>
                            <div className="col-12">
                                <button type="submit" className="btn btn-primary">save</button>
                            </div>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    )
}