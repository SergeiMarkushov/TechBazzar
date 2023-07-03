import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import {apiGetLogo} from "../../../api/OrganizationApi";
import {ProductCard} from "./ProductPage";

export function ProductCompanyCard(props: ProductCard) {
    const [logo, setLogo] = useState<string>("");

    useEffect(() => {
        if (props.product.organizationTitle !== '') {
            apiGetLogo(props.product.organizationTitle).then((response) => {
                const file = new Blob([response.data], {type: response.headers['content-type']});
                const fileURL = URL.createObjectURL(file);
                setLogo(fileURL);
            }).catch((error) => {
                // eslint-disable-next-line no-console
                console.error('Error:', error);
            });
        }
    }, [props.product.organizationTitle]);

    return (
        <div className="card">
            <div className="ms-3 d-flex justify-content-start align-content-center align-items-center">
                <div className="me-3">
                    <img src={logo} className="rounded-circle border-1 shadow-sm"
                         style={{maxWidth: "5rem", maxHeight: "5rem"}} alt="..."/>
                </div>
                <div className="d-flex row">
                    <small className="card-title text-black-50">Продавец</small>
                    <Link to={`/catalog/${props.product.organizationTitle}`} className="card-text text-black text-decoration-none">{props.product.organizationTitle}</Link>
                </div>
            </div>
        </div>
    )
}