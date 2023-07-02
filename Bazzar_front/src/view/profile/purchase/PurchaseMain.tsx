import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {apiGetMyPurchased} from "../../../api/PurchasedApi";
import {useError} from "../../../auth/ErrorProvider";
import {PurchaseHistory} from "../../../newInterfaces";
import {PurchaseCard} from "./PurchaseCard";

export function PurchaseMain() {
    const [purchase, setPurchase] = useState<Array<PurchaseHistory>>()
    const error = useError();

    useEffect(() => {
        apiGetMyPurchased().then((r: AxiosResponse<Array<PurchaseHistory>>) => {
            setPurchase(r.data)
            error.setErrors("", true, false, "");
        }).catch((e) => {
            error.setErrors(e.response.data.message, false, false, "");
        })
    }, [])

    return (
        <div>
            {
                purchase && purchase?.length === 0 && <small>У вас нет покупок</small>
            }
            <div className="d-flex flex-column justify-content-center">
                {
                    purchase?.map((p => <PurchaseCard key={p.id} purchase={p}/>))
                }
            </div>
        </div>
    )
}