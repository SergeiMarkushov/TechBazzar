import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../../Colors";


interface CreateOrderButtonProps {
    link: string,
    title: string,
    svg: JSX.Element,
    isActive?: boolean
}

export function CreateOrganizationButton(props: CreateOrderButtonProps) {
    return (
        <Link style={{color: primary}} to={props.link} className="text-decoration-none rounded shadow m-2">
            <div className="d-flex align-items-center">
                <div className="m-2">
                    {props.svg}
                </div>
                {
                    props.title !== "" &&
                    <div className="pe-2">
                        {props.title}
                    </div>
                }
                {
                    props.isActive != undefined ? (
                        props.isActive ?
                            <div className="badge bg-success me-2">Активна</div>
                            :
                            <div className="badge bg-danger me-2">Неактивна</div>
                    ) : <div/>
                }
            </div>
        </Link>
    )
}