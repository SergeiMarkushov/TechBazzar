import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {getMessageSvg, getStarSvg} from "../../Svg";
import {apiGetProductPic} from "../../api/PictureApi";
import {apiGetProductMark, apiGetReviewCount} from "../../api/ReviewApi";
import {Picture, Product} from "../../newInterfaces";
import {ChangeProductButton} from "./ChangeProductButton";

export interface ProductCard {
    product: Product,
    deleteHandler?: (id: number) => void,
    changeHandler?: (id: number) => void,
    isChanging: boolean,
}

export function CatalogCard(props: ProductCard) {
    const [isShown, setIsShown] = useState(false);
    const [discount] = useState(0);
    const [mark, setMark] = useState(0);
    const [pic, setPic] = useState<string>("");
    const[count, setCount] = useState<number>(0);

    useEffect(() => {
        if (props.product.id) {
            apiGetProductPic(props.product.pictureId).then((response: AxiosResponse<Picture>) => {
                const base64String = response.data.bytes;
                const contentType = response.data.contentType;
                const dataURL = `data:${contentType};base64,${base64String}`;
                setPic(dataURL);
            }).catch((error) => {
                // eslint-disable-next-line no-console
                console.error('Error:', error);
            });

            apiGetProductMark(props.product.id).then((response) => {
                setMark(response.data);
            }).catch(() => {
                setMark(0);
            });

            apiGetReviewCount(props.product.id).then((response) => {
                setCount(response.data);
            }).catch(() => {
                setCount(0);
            });
        }
        return () => URL.revokeObjectURL(pic);
    }, [props.product.title]);

    return (
        <div>
            <Link style={{width: "15em", height: "20em", borderStyle: "none", textDecoration: "none", color: "black"}}
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
                        <img
                            src={pic}
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
                                <span>  {count}</span>
                        </span>
                        </div>
                    </div>
                </div>
            </Link>
            {
                props.isChanging ? <div className="mt-1 mb-2">
                    <ChangeProductButton product={props.product} deleteHandler={props.deleteHandler}
                                         changeHandler={props.changeHandler}></ChangeProductButton>
                </div> : null
            }
        </div>
    )
}