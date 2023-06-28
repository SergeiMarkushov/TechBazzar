import Rating from "@mui/material/Rating/Rating";
import React from 'react';
import {Review} from "../../../newInterfaces";

export interface CommentCardProps {
    comment: Review;
}

export function CommentCard(props: CommentCardProps) {

    return (
        <div className="card border-0 border-bottom" style={{width: "50%"}}>
            <div className="card-body">
                <div className="d-flex justify-content-between">
                    <b className="card-title">{props.comment.username}</b>
                        <div>
                            <Rating name="half-rating-read" className="ms-2" size="small" value={props.comment.mark}
                                    precision={0.5} readOnly/>
                        </div>
                </div>
                <span className="card-title">отзыв:</span>
                <p className="card-text">{props.comment.reviewText}</p>
            </div>
        </div>
    );
}