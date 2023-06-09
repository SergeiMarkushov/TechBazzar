import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getOrganizationSvg} from "../../Svg";

export function ProfileOrganization() {

    return (
        <Link style={{color: primary}} to="/profile/organization" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "17rem"}}>
                <div className="card border-0" style={{maxWidth: "15rem"}}>
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getOrganizationSvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">Organization</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="">
                    <div className="card-body">
                        {/*TODO replace this*/}
                        <small className="card-title text-sm-start">Your org: none</small>
                    </div>
                </div>
            </div>
        </Link>
    )
}