import React from 'react';
import {
    getAdminMenuProductChangeSvg,
    getAdminMenuProductConfirmSvg,
    getAdminMenuUserSvg, getOrganizationSvg
} from "../../../Svg";
import {AdminMenuCard} from "./AdminMenuCard";

export function AdminMenu() {
    return (
        <div className="row justify-content-center m-2">
            <div className="col rounded shadow m-2">
                <AdminMenuCard link="/function/menu/allUsers" title="users" svg={getAdminMenuUserSvg()}/>
            </div>
            <div className="col rounded shadow m-2">
                <AdminMenuCard link="/function/menu/productChanger" title="product redactor" svg={getAdminMenuProductChangeSvg()}/>
            </div>
            <div className="col rounded shadow m-2">
                <AdminMenuCard link="/function/menu/confirmProduct" title="confirm products" svg={getAdminMenuProductConfirmSvg()}/>
            </div>
            <div className="col rounded shadow m-2">
                <AdminMenuCard link="/function/menu/organizations" title="organizations" svg={getOrganizationSvg()}/>
            </div>
        </div>
    )
}