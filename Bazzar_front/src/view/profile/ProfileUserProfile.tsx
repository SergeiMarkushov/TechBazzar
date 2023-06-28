import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getProfileSvg} from "../../Svg";
import {User} from "../../newInterfaces";

interface ProfileUserProfileProps {
    user: User
}

export function ProfileUserProfile(props: ProfileUserProfileProps) {
    return (
        <Link style={{color: primary}} to="/profile/userProfile" className="text-decoration-none">
            <div className="card border-0">
                <div className="card border-0">
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getProfileSvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">{props.user.username}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}