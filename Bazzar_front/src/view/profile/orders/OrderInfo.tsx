import {useLocation} from "react-router-dom";
import {OrderCard} from "./OrderCard";
import {OrderNew} from "../../../newInterfaces";

export interface OrderProps {
    order: OrderNew;
}

export function OrderInfo() {
    const location = useLocation();
    const props: OrderNew = location.state.order;

    return (
        <div className="card mb-3">
            <div className="card-header">
                <div className="d-flex justify-content-between">
                    <div>Order #{props.id} from {props.createdAt}</div>
                    <div>Total price {props.totalPrice} ₽</div>
                </div>
                <div className="p-0 g-0">
                    <small className="p-0 g-0">Status <label
                        className="alert alert-primary p-0 g-0">{props.status ? "Получен и оплачен" : "Ожидает оплаты"}</label></small>
                </div>
            </div>
            <div className="d-flex flex-row flex-wrap justify-content-center">
                {props.items.map(product => <OrderCard key={product.id} product={product} order={props}/>)}
            </div>
        </div>
    )
}