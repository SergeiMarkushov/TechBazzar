import React, {useEffect, useState} from "react";
import {OrderTab} from "./OrderTab";
import {apiGetUserOrders} from "../../../api/OrderApi";
import {emptyOrderNew} from "../../../empty";
import {AxiosResponse} from "axios";
import {OrderNew} from "../../../newInterfaces";

export function Orders() {
    const [Orders, setOrders] = useState(Array.of(emptyOrderNew))
    const [load, setLoad] = useState(false);

    useEffect(() => {
            if (!load) {
                apiGetUserOrders().then((orders: AxiosResponse<Array<OrderNew>>) => {
                    console.log(orders.data)
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