import React, {useEffect, useState} from 'react';
import {useLocation, useParams} from "react-router-dom";
import {emptyOrderNew} from "../../../empty";
import {Order} from "../../../newInterfaces";
import {OrderCard} from "./OrderCard";

export interface OrderProps {
    order: Order;
}

export function OrderInfo() {
    const location = useLocation();
    const [order, setOrder] = useState<Order>(emptyOrderNew)

    useEffect(() => {
        console.log("lkek")
        if (location.state?.order) {
            localStorage.setItem("order", JSON.stringify(location.state.order));
            setOrder(location.state.order)
            console.log(order)
        } else {
            const order1: Order = JSON.parse(localStorage.getItem("order") as string)
            console.log(order1)
            setOrder(order1);
        }
    }, []);

    return (
        <div className="card mb-3">
            <div className="card-header">
                <div className="d-flex justify-content-between">
                    <div>Заказ #{order.id} from {order.createdAt}</div>
                    <div>Цена за всё {order.totalPrice} ₽</div>
                </div>
                <div className="p-0 g-0">
                    <small className="p-0 g-0">Статус <label
                        className="alert alert-primary p-0 g-0">{order.status ? "Получен и оплачен" : "Ожидает оплаты"}</label></small>
                </div>
            </div>
            <div className="d-flex flex-row flex-wrap justify-content-center">
                {order.items.map(product => <OrderCard key={product.id} product={product} order={order}/>)}
            </div>
        </div>
    )
}