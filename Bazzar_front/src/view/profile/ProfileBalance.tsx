import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getMoneySvg} from "../../Svg";
import {User} from "../../newInterfaces";

interface ProfileBalanceProps {
    user: User
}

export function ProfileBalance(props: ProfileBalanceProps) {
    return (
        <Link style={{color: primary}} to="/profile/balance" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "17rem"}}>
                <div className="card border-0" style={{maxWidth: "15rem"}}>
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getMoneySvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">Баланс: {props.user.balance}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}