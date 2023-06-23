import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {apiGetMyUser} from "../../api/UserApi";
import {useRole} from "../../auth/Role";
import {defaultUserNew} from "../../empty";
import {UserNew} from "../../newInterfaces";
import {ProfileAdminMenu} from "./ProfileAdminMenu";
import {ProfileBalance} from "./ProfileBalance";
import {ProfileOrders} from "./ProfileOrders";
import {ProfileOrganization} from "./ProfileOrganization";
import {ProfileUserProfile} from "./ProfileUserProfile";

export function Profile() {
    const role = useRole();
    const [user, setUser] = useState<UserNew>(defaultUserNew);

    useEffect(() => {
        apiGetMyUser().then((r: AxiosResponse<UserNew>) => {
            setUser(r.data);
        }).then((e) => {
            console.error(e);
        });
    }, []);

    return (
        <div className="row justify-content-center m-2">
            <div className="col rounded shadow m-2">
                <ProfileUserProfile user={user}/>
            </div>
            <div className="col rounded shadow m-2">
                <ProfileOrders/>
            </div>
            <div className="row">
                <div className="col rounded shadow m-2">
                    <ProfileBalance user={user}/>
                </div>
                <div className="col rounded shadow m-2">
                    <ProfileOrganization/>
                </div>
                {role.isAdmin ? <div className="col rounded shadow m-2"><ProfileAdminMenu/></div> : <div></div>}
            </div>
        </div>
    )
}