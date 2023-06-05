import React, {useState} from "react";
import {apiCreateOrder} from "../../api/OrderApi";
import {useAuth} from "../../auth/Auth";
import {CartNew} from "../../newInterfaces";

interface CartBuyFormProps {
    cart: CartNew,
    onReloadCart: () => void,
}

export function CartBuyForm(props: CartBuyFormProps) {
    const [isBlock, setBlock] = useState(false);
    let auth = useAuth();

    function buy() {
        if (auth.isAuth) {
            setBlock(true);
            apiCreateOrder().then((resp) => {
                if (resp.status === 201) {
                    props.onReloadCart();
                }
            })
        }
    }

    return (
        <div className="card text-center shadow-sm" style={{width: "10rem;"}}>
            <div className="card-body">
                <h5 className="card-title">Total price</h5>
                <p className="card-text">{props.cart.totalPrice}</p>
                <button disabled={isBlock || !auth.isAuth} onClick={() => buy()} className="btn btn-success">Buy</button>
            </div>
        </div>
    );
}