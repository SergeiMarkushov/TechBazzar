import React from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getOrderSvg} from "../../Svg";

export function ProfileOrders() {
    return (
        <Link style={{color: primary}} to="/profile/orders" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "15rem"}}>
                <div className="d-flex align-items-center">
                    <div className="m-2">
                        {getOrderSvg()}
                    </div>
                    <div className="">
                        <div className="card-body">
                            <h5 className="card-title">Заказы</h5>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}