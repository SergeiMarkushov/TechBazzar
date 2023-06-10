import React from 'react';
import {CommentCard} from "./CommentCard";
import {ProductNew} from "../../../newInterfaces";
import {useState} from "react";

interface ProductPageCommentsCardProps {
    product: ProductNew;
}

export function ProductPageCommentsCard(props: ProductPageCommentsCardProps) {
    const [mark] = useState(props.product.review != null && props.product.review.mark != null ? props.product.review.mark : 0);
    return (
        <div className="card border-0">
            <div className="card-body">
                <h5 className="card-title">Reviews</h5>
                {/*{comment.map((comment) => <CommentCard key={comment.id} comment={comment}/>)}*/}
                { props.product.review != null &&
                    <CommentCard comment={props.product.review}/>
                }
            </div>
        </div>
    )
}