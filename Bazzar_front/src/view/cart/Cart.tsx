import {CartCard} from "./CartCard";
import {CartBuyForm} from "./CartBuyForm";
import {useEffect, useState} from "react";
import {apiClearCart, apiGetCart} from "../../api/CartApi";
import {CartFunctionMenu} from "./CartFunctionMenu";
import {useAuth} from "../../auth/Auth";
import {emptyCart} from "../../empty";
import {AxiosResponse} from "axios";
import {CartNew} from "../../newInterfaces";

export function Cart() {
    const [isDone, setIsDone] = useState(false)
    const [cart, setCart] = useState(emptyCart)
    const [isEmpty, setEmpty] = useState(true)
    let auth = useAuth();

    useEffect(() => {
        if (!isDone) {
            console.log("useEffect")
            apiGetCart(auth.isAuth).then((data: AxiosResponse<CartNew>) => {
            console.log(data.data)
                    if (data.data === null || (data.data.items.length === 0)) {
                        setEmpty(true)
                    } else {
                        setCart(data.data);
                        setEmpty(false)
                    }
                    setIsDone(true);
                }
            )
        }
    }, [isDone])

    function onReloadContext() {
        setIsDone(false);
    }

    function onClearCart() {
        apiClearCart(auth.isAuth).then(() => {
            setCart(emptyCart);
            setEmpty(true);
            onReloadContext();
        });
    }

    return (<div className="container text-center">
            <h1 hidden={isEmpty}>Cart</h1>
            <h1 hidden={!isEmpty}>Cart is empty</h1>
            <div hidden={isEmpty}>
                <div className="row align-items-start">
                    <div className="col">
                        <div className="mb-3">
                            <CartFunctionMenu onClearCart={onClearCart}></CartFunctionMenu>
                        </div>
                        {
                            cart.items.map(product =>
                                <CartCard onReloadCart={onReloadContext} product={product} key={product.productId}/>)
                        }
                    </div>
                    <div className="col-3">
                        <CartBuyForm onReloadCart={onClearCart} cart={cart}/>
                    </div>
                </div>
            </div>
        </div>
    )
}