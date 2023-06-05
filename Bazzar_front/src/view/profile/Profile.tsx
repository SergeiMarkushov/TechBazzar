import {ProfileBalance} from "./ProfileBalance";
import {ProfileOrders} from "./ProfileOrders";
import {ProfileUserProfile} from "./ProfileUserProfile";
import {useRole} from "../../auth/Role";
import {ProfileAdminMenu} from "./ProfileAdminMenu";
import {ProfileOrganization} from "./ProfileOrganization";
import {useEffect, useState} from "react";
import {UserNew} from "../../newInterfaces";
import {defaultUserNew} from "../../empty";
import {apiGetMyUser} from "../../api/UserApi";
import {AxiosResponse} from "axios";

export function Profile() {
    let role = useRole();
    let [isLoad, setLoad] = useState(false);
    let [user, setUser] = useState<UserNew>(defaultUserNew);

    useEffect(() => {
        if (!isLoad) {
            apiGetMyUser().then((r: AxiosResponse<UserNew>) => {
                    setLoad(true);
                    setUser(r.data);
            });
        }
    });

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