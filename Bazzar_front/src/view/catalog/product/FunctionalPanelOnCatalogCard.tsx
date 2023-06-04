import Rating from "@mui/material/Rating/Rating";
import {useEffect, useState} from "react";
import {ProductNew} from "../../../newInterfaces";

interface FunctionalPanelOnCatalogCardProps {
    product: ProductNew;
}

export function FunctionalPanelOnCatalogCard(props: FunctionalPanelOnCatalogCardProps) {
    const [Bought, setBought] = useState(0)
    const [mark, setMark] = useState(0);

    useEffect(() => {
        /*setMark(props.product.review != null && props.product.review.mark != null ? props.product.review.mark : 0)*/
    });

    /*useEffect(() => {
            apiGetCountOfBought(props.productId).then((count) => {
                setBought(count.data);
            })
        }, [Bought, props.productId]
    );*/

    return (
        <div className="d-flex mb-2">
            <div className="me-3" style={{maxHeight: "50px"}}>
                <label className="">bought {Bought}</label>
            </div>
            <div className="d-flex align-items-center me-3" style={{maxHeight: "50px"}}>
                <Rating name="half-rating-read" size="small" value={mark}
                        precision={0.5} readOnly/>
                <label className="ms-1">{mark}</label>
            </div>
        </div>
    )
}