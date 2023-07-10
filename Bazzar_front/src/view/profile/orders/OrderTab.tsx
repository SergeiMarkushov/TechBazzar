import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import {Order} from "../../../newInterfaces";
import {DeleteOrderForm} from "./DeleteOrderForm";
import {OrderTabCard} from "./OrderTabCard";
import {PayForm} from "./PayForm";

export interface OrderProps {
    order: Order,
    onReloadOrder: () => void
}

export function OrderTab(props: OrderProps) {
    const [status, setStatus] = useState(props.order.status);

    useEffect(() => {
        setStatus(props.order.status);
    }, [props.order.status]);

    return (
        <div className="card mb-3">
            <div className="card-header d-flex justify-content-between">
                <div>
                    <span className="me-2">Заказ #{props.order.id}</span>
                    <span>от {props.order.createdAt}</span>
                    <div className="d-flex">
                        {!status ? <PayForm setStatus={setStatus} order={props.order}
                                            onReloadOrder={props.onReloadOrder}/> : <div></div>}
                        <DeleteOrderForm setStatus={setStatus} order={props.order}
                                         onReloadOrder={props.onReloadOrder}/>
                    </div>
                </div>
                <div>Цена за всё {props.order.totalPrice} ₽</div>
            </div>
            <div className="d-flex justify-content-between">
                <div className="m-2">
                    <small className="p-0 g-0">Статус <div
                        className="alert alert-primary p-0 g-0">{status ? "Получен и оплачен" : "Ожидает оплаты"}</div>
                    </small>
                    <Link to={`/profile/orders/order/${props.order.id}`} state={{order: props.order}}
                          className="link-info">Подробнее</Link>
                </div>
                <div>
                    {props.order.items.map(product => <OrderTabCard key={product.id} product={product}/>)}
                </div>

            </div>
        </div>
    )
}