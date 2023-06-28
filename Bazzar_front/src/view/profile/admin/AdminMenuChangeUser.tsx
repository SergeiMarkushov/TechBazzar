import {AxiosResponse} from "axios";
import {Field, Form, Formik} from "formik";
import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {apiGetUserById} from "../../../api/UserApi";
import {defaultUserNew} from "../../../empty";
import {User} from "../../../newInterfaces";


export function AdminMenuChangeUser() {
    const {id} = useParams();
    const [user, setUser] = useState(defaultUserNew);

    useEffect(() => {
        if (id !== undefined) {
            apiGetUserById(Number(id)).then((resp:AxiosResponse<User>) => {
                setUser(resp.data);
            });
        }
    }, []);

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={user}
                        enableReinitialize={true}
                        onSubmit={() => {
                            return;
                        }}>
                    {stateFormik => (
                        <Form className="row g-3">
                            <div className="col-md-3">
                                <Field as="label" htmlFor="id" className="form-label">id</Field>
                                <Field as="input" name="id" disabled={true} value={stateFormik.values.id} type="text"
                                       className="form-control" id="id"
                                       required={false}/>
                            </div>
                            <div className="col-md-9">
                                <Field as="label" htmlFor="username" className="form-label">username</Field>
                                <Field as="input" name="username" type="text" value={stateFormik.values.username} className="form-control" id="username"
                                       required={true}/>
                            </div>
                            <div className="col-md-6">
                                <Field as="label" htmlFor="balance" className="form-label">balance</Field>
                                <Field as="input" name="balance" type="number" value={stateFormik.values.balance} className="form-control" id="balance"
                                       required={true}/>
                            </div>
                            <div className="col-md-6">
                                <Field as="label" htmlFor="active" className="form-label">active</Field>
                                <Field as="input" name="active" type="boolean" value={stateFormik.values.active} className="form-control" id="active"
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