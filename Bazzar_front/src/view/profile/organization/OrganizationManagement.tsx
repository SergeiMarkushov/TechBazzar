import React, {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import {primary} from "../../../Colors";
import {apiGetLogo, apiGetOrganization} from "../../../api/OrganizationApi";
import {Organization} from "../../../newInterfaces";
import {Catalog} from "../../catalog/Catalog";

export function OrganizationManagement() {
    const [organization, setOrganization] = useState({} as Organization);
    const {title} = useParams();

    useEffect(() => {
        if (title === undefined) return;
        apiGetOrganization(title).then((response) => {
            setOrganization(response.data);
        }).catch((error) => {
            // eslint-disable-next-line no-console
            console.log(error);
        });
    }, []);

    const [logo, setLogo] = useState<string>("");

    useEffect(() => {
        if (organization.title === undefined) return;
        apiGetLogo(organization.title).then((response) => {
            const file = new Blob([response.data], {type: response.headers['content-type']});
            const fileURL = URL.createObjectURL(file);
            setLogo(fileURL);
        }).catch((error) => {
            // eslint-disable-next-line no-console
            console.error('Error:', error);
        });
    }, [organization.title]);

    return (
        <div className="mb-2">
            <div className="card text-center mb-2">
                <div className="card-header">Ваша организация</div>
                <div className="d-flex justify-content-center m-2">
                    <img style={{maxWidth: "7rem", maxHeight: "7rem"}} src={logo} alt={organization.title}/>
                </div>
                <div className="card-body">
                    <h5 className="card-title">Название - {organization.title}</h5>
                    <p className="card-text">Описание - {organization.description}</p>
                    {organization.active &&
                        <Link className="btn btn-sm text-white" style={{backgroundColor: primary}} to="addProduct">Добавить
                            продукт</Link>
                    }
                </div>
                <div className="card-footer text-muted">
                    {organization.active ? "Ваша компания подтвержденна" : "Ваша компания отпралена на проверку"}
                </div>
            </div>
            <div className="mb-2">
                <Catalog isChanging={true} companyTitle={organization.title}></Catalog>
            </div>
        </div>
    )
}