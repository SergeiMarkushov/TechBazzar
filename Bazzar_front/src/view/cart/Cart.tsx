import {AxiosError, AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {apiClearCart, apiGetCart} from "../../api/CartApi";
import {emptyCart} from "../../empty";
import {CartNew} from "../../newInterfaces";
import {CartBuyForm} from "./CartBuyForm";
import {CartCard} from "./CartCard";
import {CartFunctionMenu} from "./CartFunctionMenu";

export function Cart() {
    const [isDone, setIsDone] = useState(false)
    const [cart, setCart] = useState(emptyCart)
    const [isEmpty, setEmpty] = useState(true)

    useEffect(() => {
        apiGetCart().then((data: AxiosResponse<CartNew>) => {
                if (data.data === null || (data.data.items.length === 0)) {
                    setEmpty(true)
                } else {
                    setCart(data.data);
                    setEmpty(false)
                }
                setIsDone(true);
            }
        ).catch((reason: AxiosError) => {
            // eslint-disable-next-line no-console
            console.error(reason)
        });
    }, [isDone]);

    function onReloadContext() {
        setIsDone(false);
    }

    function onClearCart() {
        apiClearCart().then(() => {
            setCart(emptyCart);
            setEmpty(true);
            onReloadContext();
        }).catch((reason: AxiosError) => {
            // eslint-disable-next-line no-console
            console.error(reason)
        });
    }

    return (<div className="container text-center">
            <h1 hidden={isEmpty}>Корзина</h1>
            <h1 hidden={!isEmpty}>Корзина пуста</h1>
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