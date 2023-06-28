import React, {useEffect, useState} from 'react';
import {apiGetUsers} from "../../../api/UserApi";
import {defaultUserNew} from "../../../empty";
import {AdminMenuAllUsersCard} from "./AdminMenuAllUsersCard";

export function AdminMenuAllUsers() {
    const [users, setUsers] = useState(Array.of(defaultUserNew));

    useEffect(() => {
            apiGetUsers().then(users => {
                setUsers(users.data);
            })
        }, []);
    return (
        <div className="d-flex flex-wrap justify-content-center">
            {
                users.map(user => <AdminMenuAllUsersCard key={user.id} user={user}></AdminMenuAllUsersCard>)
            }
        </div>
    )
}