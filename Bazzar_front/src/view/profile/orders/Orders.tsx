import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {apiGetUserOrders} from "../../../api/OrderApi";
import {emptyOrderNew} from "../../../empty";
import {OrderNew} from "../../../newInterfaces";
import {OrderTab} from "./OrderTab";

export function Orders() {
    const [Orders, setOrders] = useState(Array.of(emptyOrderNew))
    const [load, setLoad] = useState(false);

    useEffect(() => {
            if (!load) {
                apiGetUserOrders().then((orders: AxiosResponse<Array<OrderNew>>) => {
                    setOrders(orders.data);
                })
                setLoad(true);
            }
        }, [load]
    );

    function onReloadOrder() {
        setLoad(false);
    }

    return (
        <div className="container-fluid">
            {Orders.map((order) => <OrderTab onReloadOrder={onReloadOrder} key={order.id} order={order}/>)}
        </div>
    )
}