import {ProductCard} from "./ProductPage";
import {apiGetLogo} from "../../../api/OrganizationApi";
import {useEffect, useState} from "react";

export function ProductCompanyCard(props: ProductCard) {
    let [logo, setLogo] = useState<string>("");
    let [load, setLoad] = useState<boolean>(false);

    useEffect(() => {
        if (props.product.organizationTitle !== '' && !load) {
            setLoad(true);
            console.log(props.product.organizationTitle);
            apiGetLogo(props.product.organizationTitle).then((response) => {
                const file = new Blob([response.data], {type: response.headers['content-type']});
                const fileURL = URL.createObjectURL(file);
                setLogo(fileURL);
            }).catch((error) => {
                console.error('Error:', error);
            });
        }
    }, [load, props.product.organizationTitle]);

    return (
        <div className="card">
            <div className="ms-3 d-flex justify-content-start align-content-center align-items-center">
                <div className="me-3">
                    <img src={logo} className="rounded-circle border-1 shadow-sm" style={{maxWidth: "5rem", maxHeight: "5rem"}} alt="..."/>
                </div>
                <div className="d-flex row">
                    <small className="card-title text-black-50">Продавец</small>
                    <b className="card-text">{props.product.organizationTitle}</b>
                </div>
            </div>
        </div>
    )
}