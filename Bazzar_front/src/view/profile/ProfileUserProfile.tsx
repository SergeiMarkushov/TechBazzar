import {useKeycloak} from "@react-keycloak/web";
import React from 'react';
import {primary} from "../../Colors";
import {getProfileSvg} from "../../Svg";
import {User} from "../../newInterfaces";

interface ProfileUserProfileProps {
    user: User
}

export function ProfileUserProfile(props: ProfileUserProfileProps) {
    const {keycloak} = useKeycloak();
    return (
        <div style={{color: primary}} /*to="/profile/userProfile"*/ className="text-decoration-none">
            <div className="card border-0">
                <div className="card border-0">
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getProfileSvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">Почта - {props.user.username}</h5>
                                <h5 className="card-title">Имя - {keycloak.tokenParsed?.given_name}</h5>
                                <h5 className="card-title">Фамилия - {keycloak.tokenParsed?.family_name}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}