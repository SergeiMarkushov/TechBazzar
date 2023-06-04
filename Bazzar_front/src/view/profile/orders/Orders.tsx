import {useEffect, useState} from "react";
import {OrderTab} from "./OrderTab";
import {apiGetUserOrders} from "../../../api/OrderApi";
import {emptyOrderNew} from "../../../empty";
import {AxiosResponse} from "axios";
import {OrderNew} from "../../../newInterfaces";

export function Orders() {
    const [Orders, setOrders] = useState({
        orders: Array.of(emptyOrderNew),
    })
    const [load, setLoad] = useState(false);

    useEffect(() => {
            if (!load) {
                console.log("useEffect")
                apiGetUserOrders().then((orders: AxiosResponse<Array<OrderNew>>) => {
                    console.log(orders.data)
                    setOrders({orders: orders.data});
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
            {Orders.orders.map((order) => <OrderTab onReloadOrder={onReloadOrder} key={order.id} order={order}/>)}
        </div>
    )
}