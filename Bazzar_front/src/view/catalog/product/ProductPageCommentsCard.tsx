import React, {useEffect} from 'react';
import {apiGetReviews} from "../../../api/ReviewApi";
import {emptyReviewNew} from "../../../empty";
import {Product} from "../../../newInterfaces";
import {CommentCard} from "./CommentCard";

interface ProductPageCommentsCardProps {
    product: Product;
}

export function ProductPageCommentsCard(props: ProductPageCommentsCardProps) {
    const [comment, setComment] = React.useState(Array.of(emptyReviewNew));

    useEffect(() => {
        if (props.product.id) {
            apiGetReviews(props.product.id).then((response) => {
                setComment(response.data);
            });
        }
    }, []);

    return (
        <div className="card border-0">
            <div className="card-body">
                <h5 className="card-title">Отзывы</h5>
                <div className="">
                    {comment.length === 0 || comment[0].id === 0 && <span className="text-center">No reviews</span> ||
                        comment.map((comment) => <CommentCard key={comment.id} comment={comment}/>)}
                </div>
            </div>
        </div>
    )
}