import React from 'react';
import {useEffect, useState} from "react";
import {apiGetUsers} from "../../../api/UserApi";
import {AdminMenuAllUsersCard} from "./AdminMenuAllUsersCard";
import {defaultUserNew} from "../../../empty";

export function AdminMenuAllUsers() {
    const [users, setUsers] = useState(Array.of(defaultUserNew));
    const [isLoad, setLoad] = useState(false);

    useEffect(() => {
            if (!isLoad) {
                apiGetUsers().then(users => {
                    setUsers(users.data);
                    setLoad(true);
                })
            }
        }, [isLoad]
    );
    return (
        <div className="d-flex flex-wrap justify-content-center">
                {
                    users.map(user => <AdminMenuAllUsersCard key={user.id} user={user}></AdminMenuAllUsersCard>)
                }
        </div>
    )
}