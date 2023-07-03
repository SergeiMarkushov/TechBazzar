import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {apiGetUserOrders} from "../../../api/OrderApi";
import {Order} from "../../../newInterfaces";
import {OrderTab} from "./OrderTab";

export function Orders() {
    const [Orders, setOrders] = useState<Array<Order>>()
    const [load, setLoad] = useState(false);

    useEffect(() => {
            if (!load) {
                apiGetUserOrders().then((orders: AxiosResponse<Array<Order>>) => {
                    setOrders(orders.data);
                    setLoad(true);
                })
            }
        }, [load]
    );

    function onReloadOrder() {
        setLoad(false);
    }

    return (
        <div>
            {Orders && Orders.length === 0 && <span className="text-center">У вас нет заказов</span>}
            <div className="container-fluid">
                {Orders?.map((order) => <OrderTab onReloadOrder={onReloadOrder} key={order.id} order={order}/>)}
            </div>
        </div>
    )
}