import {AxiosResponse} from "axios";
import React from 'react';
import {useEffect, useState} from "react";
import {apiGetAllOrganization} from "../../../api/OrganizationApi";
import {emptyOrganization} from "../../../empty";
import {Organization} from "../../../newInterfaces";
import {AdminOrganizationCard} from "./AdminOrganizationCard";

export function AdminOrganizations() {
    const [organizations, setOrganizations] = useState(Array.of(emptyOrganization));
    const [isLoad, setLoad] = useState(false);

    useEffect(() => {
            if (!isLoad) {
                apiGetAllOrganization().then((org: AxiosResponse<Array<Organization>>) => {
                    setOrganizations(org.data);
                    setLoad(true);
                })
            }
        }, [isLoad]
    );

    return (
        <div className="d-flex flex-wrap justify-content-center">
            {
                organizations.map(org => <AdminOrganizationCard key={org.id} org={org}></AdminOrganizationCard>)
            }
        </div>
    )
}