import React, {useEffect} from 'react';
import {useLocation} from "react-router-dom";
import {Order} from "../../../newInterfaces";
import {OrderCard} from "./OrderCard";

export interface OrderProps {
    order: Order;
}

export function OrderInfo() {
    const location = useLocation();
    const state: Order = location.state.order;

    return (
        <div className="card mb-3">
            <div className="card-header">
                <div className="d-flex justify-content-between">
                    <div>Order #{state.id} from {state.createdAt}</div>
                    <div>Total price {state.totalPrice} ₽</div>
                </div>
                <div className="p-0 g-0">
                    <small className="p-0 g-0">Status <label
                        className="alert alert-primary p-0 g-0">{state.status ? "Получен и оплачен" : "Ожидает оплаты"}</label></small>
                </div>
            </div>
            <div className="d-flex flex-row flex-wrap justify-content-center">
                {state.items.map(product => <OrderCard key={product.id} product={product} order={state}/>)}
            </div>
        </div>
    )
}