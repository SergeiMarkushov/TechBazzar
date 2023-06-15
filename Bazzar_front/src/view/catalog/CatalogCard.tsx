import React, {useState} from "react";
import {Link} from "react-router-dom";
import {getMessageSvg, getStarSvg} from "../../Svg";
import {ProductNew} from "../../newInterfaces";
import {ChangeProductButton} from "./ChangeProductButton";

export interface ProductCard {
    product: ProductNew,
    deleteHandler?: (id: number) => void,
    changeHandler?: (id: number) => void,
    isChanging: boolean,
}

export function CatalogCard(props: ProductCard) {
    const [isShown, setIsShown] = useState(false);
    const [discount] = useState(0);
    const [mark] = useState(0);

    return (
        <div style={{width: "15em", borderStyle: "none", textDecoration: "none", color: "black"}}>

            <Link style={{width: "15em", borderStyle: "none", textDecoration: "none", color: "black"}}
                  to={`/product/${props.product.title}/${props.product.id}`}
                  className={`card pt-2 row m-1 ${isShown ? "shadow-sm bg-light" : ""}`}
                  onMouseEnter={() => {
                      setIsShown(true)
                  }}
                  onMouseLeave={() => {
                      setIsShown(false)
                  }}>
                <div className="row flex-grow-1 align-self-center">
                    <div className="align-self-center" style={{maxHeight: "200px"}}>
                        {/*TODO add image*/}
                        <img
                            src="https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png"
                            style={{maxHeight: "inherit"}} className="card-img-top"
                            alt={props.product.title}/>
                    </div>
                </div>
                <div className="card-body flex-grow-0">
                    <div>
                        <b className="card-subtitlerounded-1 text-black me-2 mb-3">
                            {discount === 0 ? props.product.price : discount} ₽</b>
                        <small className="card-subtitlerounded-1 mb-3" style={{
                            color: "grey",
                            textDecoration: "line-through"
                        }}>{props.product.price} ₽</small>
                    </div>
                    <small className="">{props.product.title}</small>
                    <div>
                        <div className="d-flex flex-row justify-content-between">
                        <span className="text-center">
                            {getStarSvg(16, 16)}
                            <span>  {mark}</span>
                        </span>
                            <span className="text-center">
                            {getMessageSvg(16, 16)}
                                {/*<span>  {props.product.review.count}</span>*/}
                                {/*TODO add count*/}
                                <span>  1</span>
                        </span>
                        </div>
                    </div>
                </div>
            </Link>
            {
                props.isChanging ? <div className="d-flex mt-1 justify-content-between">
                    <ChangeProductButton product={props.product} deleteHandler={props.deleteHandler}
                                         changeHandler={props.changeHandler}></ChangeProductButton>
                </div> : null
            }
        </div>
    )
}