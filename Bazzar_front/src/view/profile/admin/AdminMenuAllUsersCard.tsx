import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../../Colors";
import {UserNew} from "../../../newInterfaces";
import {AdminMenuUserMenu} from "./AdminMenuUserMenu";

interface AllUsersCardProps {
    user: UserNew,
}

export function AdminMenuAllUsersCard(props: AllUsersCardProps) {
    return (
        <div className="rounded-2 shadow m-3 d-flex" style={{width: "13rem"}}>
            <div className="m-3 d-flex">
                <div className="d-flex row justify-content-between align-content-between">
                    <div>
                        <p className="card-title">id - {props.user.id}</p>
                        <p className="card-subtitle mb-2 text-muted">username - {props.user.username}</p>
                        <p className="card-subtitle mb-2 text-muted">email - {props.user.email}</p>
                        <p className="card-subtitle mb-2 text-muted">balance - {props.user.balance}</p>
                        <p className="card-subtitle mb-2 text-muted">is active
                            - {props.user.active ? "active" : "not active"}</p>
                    </div>
                    <div className="d-flex justify-content-between mb-1">
                        <div>
                            <Link to={`/function/menu/allUsers/${props.user.id}`} className="btn-sm btn text-white"
                                  style={{backgroundColor: primary}}>Изменить</Link>
                        </div>
                        <AdminMenuUserMenu user={props.user}/>
                    </div>
                </div>
            </div>
        </div>
    )
}