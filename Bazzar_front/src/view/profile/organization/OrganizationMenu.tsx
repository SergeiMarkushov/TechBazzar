import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {getOrganizationSvg, getPlusSvg} from "../../../Svg";
import {apiGetAllMyOrganization} from "../../../api/OrganizationApi";
import {Organization} from "../../../newInterfaces";
import {CreateOrganizationButton} from "./CreateOrganizationButton";


export function OrganizationMenu() {
    const [organizations, setOrganizations] = useState([] as Array<Organization>);

    useEffect(() => {
        apiGetAllMyOrganization().then((response: AxiosResponse<Array<Organization>>) => {
            setOrganizations(response.data);
        }).catch((error) => {
            // eslint-disable-next-line no-console
            console.error(error);
        });
    }, []);

    return (
        <div className="d-flex align-items-start justify-content-start">
            <div>
                <div className="d-flex flex-wrap">
                    {
                        organizations.map((organization: Organization) => {
                            return (
                                <CreateOrganizationButton key={organization.id} isActive={organization.active}
                                                          link={organization.title}
                                                          title={organization.title} svg={getOrganizationSvg()}/>
                            )
                        })
                    }
                    <CreateOrganizationButton link={"create"} title={""} svg={getPlusSvg()}/>
                </div>
            </div>
        </div>
    )
}