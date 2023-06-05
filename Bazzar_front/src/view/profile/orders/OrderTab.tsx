import {OrderTabCard} from "./OrderTabCard";
import {Link} from "react-router-dom";
import {OrderNew} from "../../../newInterfaces";
import {useState} from "react";
import {PayForm} from "./PayForm";
import {DeleteOrderForm} from "./DeleteOrderForm";

export interface OrderProps {
    order: OrderNew,
    onReloadOrder: () => void
}

export function OrderTab(props: OrderProps) {
    const [status, setStatus] = useState(props.order.status);

    return (
        <div className="card mb-3">
            <div className="card-header d-flex justify-content-between">
                <div>
                    <span>Order #{props.order.id} </span>
                    <span>from {props.order.createdAt}</span>
                    <div className="d-flex justify-content-between">
                        {!status ? <PayForm setStatus={setStatus} order={props.order}
                                            onReloadOrder={props.onReloadOrder}/> : null}
                        {!status ? <DeleteOrderForm setStatus={setStatus} order={props.order}
                                                    onReloadOrder={props.onReloadOrder}/> : null}
                    </div>
                </div>
                <div>Total price {props.order.totalPrice} ₽</div>
            </div>
            <div className="d-flex justify-content-between">
                <div className="m-2">
                    <small className="p-0 g-0">Status <div
                        className="alert alert-primary p-0 g-0">{status ? "Получен и оплачен" : "Ожидает оплаты"}</div>
                    </small>
                    <Link to={`/profile/orders/order/${props.order.id}`} state={{order: props.order}}
                          className="link-info">Details</Link>
                </div>
                <div>
                    {props.order.items.map(product => <OrderTabCard key={product.id} product={product}/>)}
                </div>

            </div>
        </div>
    )
}