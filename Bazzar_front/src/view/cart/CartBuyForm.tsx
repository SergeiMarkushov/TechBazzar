import {useKeycloak} from "@react-keycloak/web";
import React, {useState} from "react";
import {apiCreateOrder} from "../../api/OrderApi";
import {Cart} from "../../newInterfaces";

interface CartBuyFormProps {
    cart: Cart,
    onReloadCart: () => void,
}

export function CartBuyForm(props: CartBuyFormProps) {
    const [isBlock, setBlock] = useState(false);
    const {keycloak} = useKeycloak();

    function buy() {
        if (keycloak?.authenticated) {
            setBlock(true);
            apiCreateOrder().then(() => {
                props.onReloadCart();
            }).catch((err) => {
                console.log(err);
            })
        }
    }

    return (
        <div className="card text-center shadow-sm" style={{width: "10rem;"}}>
            <div className="card-body">
                <h5 className="card-title">Total price</h5>
                <p className="card-text">{props.cart.totalPrice}</p>
                <button disabled={isBlock || !keycloak.authenticated} onClick={() => buy()} className="btn btn-success">Buy
                </button>
            </div>
        </div>
    );
}