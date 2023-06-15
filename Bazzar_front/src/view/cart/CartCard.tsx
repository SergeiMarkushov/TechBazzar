import {AxiosError} from "axios";
import React, {useEffect, useState} from 'react';
import {primary} from "../../Colors";
import {apiRemoveItem, apiUpdateQuantity} from "../../api/CartApi";
import {CartItemNew} from "../../newInterfaces";

export interface ProductCart {
    product: CartItemNew;
    onReloadCart: () => void;
}

export function CartCard(props: ProductCart) {
    const [quantity, setQuantity] = useState(props.product.quantity);
    const [isQuantityMore, setQuantityMore] = useState(false);

    useEffect(() => {
        if (quantity > 9) {
            setQuantityMore(true);
        }
    }, [quantity])

    const handleChange = (event: any) => {
        const value = quantityValidate(event.currentTarget.value);
        if (value < 10) {
            updateQuantity(value);
        } else {
            setQuantityMore(true);
        }
    }

    const customHandleChange = (event: any) => {
        const value = quantityValidate(event.currentTarget.value);
        updateQuantity(value);
    }

    const updateQuantity = (value: number) => {
        apiUpdateQuantity(props.product, value).then(() => {
            setQuantity(value);
            props.onReloadCart()
        }).catch((reason: AxiosError) => {
            console.log(reason)
        });
    }

    return (
        <div className="card border-0 border-bottom mb-3" style={{maxWidth: "none"}}>
            <div className="d-flex">
                <div className="w-25 align-self-center">
                    {/*TODO: add image*/}
                    <img alt={props.product.productTitle}
                         src="https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png"
                         className="rounded"
                         style={{maxWidth: "100px", maxHeight: "100px", minWidth: "100px", minHeight: "100px"}}
                    />
                </div>
                <div className="d-flex w-100">
                    <div className="align-self-center flex-grow-1" style={{textAlign: "start", width: "100%"}}>
                        <div className="card-body">
                            <h5 className="card-title">{props.product.productTitle}</h5>
                            <p className="card-text"><small
                                className="text-muted">{props.product.pricePerProduct} ₽/ one</small></p>
                            <p className="card-text"><small
                                className="text-muted">{props.product.price} ₽/ total</small></p>
                            <button type="button" onClick={() => {
                                apiRemoveItem(props.product).then(resp => resp.status === 200 ? props.onReloadCart() : false);
                            }} className="btn btn-sm text-white" style={{backgroundColor: primary}}>Delete
                            </button>
                        </div>
                    </div>
                    <div className="align-self-center flex-grow-0 m-lg-5">
                        <select disabled={isQuantityMore} hidden={isQuantityMore} style={{width: "5rem"}}
                                className="form-select"
                                value={quantity} onChange={handleChange}>
                            <option value={1}>1</option>
                            <option value={2}>2</option>
                            <option value={3}>3</option>
                            <option value={4}>4</option>
                            <option value={5}>5</option>
                            <option value={6}>6</option>
                            <option value={7}>7</option>
                            <option value={8}>8</option>
                            <option value={9}>9</option>
                            <option value={10}>10+</option>
                        </select>
                        <input disabled={!isQuantityMore} value={quantity} onInput={customHandleChange}
                               hidden={!isQuantityMore} style={{width: "5rem"}}
                               className="form-select"/>
                    </div>
                </div>
            </div>
        </div>
    );
}

function quantityValidate(value: number): number {
    if (value < 1) {
        return 1;
    }
    return value;
}