import React, {useEffect} from 'react';
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getHistoryOrdersSvg} from "../../Svg";
import {apiGetCountPurchase} from "../../api/PurchasedApi";

export function ProfilePurchase() {
    const [count, setCount] = React.useState<number>(0)

    useEffect(() => {
        apiGetCountPurchase().then((r) => {
            setCount(r.data)
        }).catch((e) => {
            setCount(0)
            console.error(e)
        })
    }, [])

    return (
        <Link style={{color: primary}} to="/profile/purchase" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "16rem"}}>
                <div className="d-flex align-items-center">
                    <div className="m-2">
                        {getHistoryOrdersSvg(40, 40)}
                    </div>
                    <div className="">
                        <div className="card-body">
                            <h5 className="card-title">История покупок</h5>
                            <small>Вы купили {count} товаров</small>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}