import {ReviewNew} from "../../../newInterfaces";
import Rating from "@mui/material/Rating/Rating";

export interface CommentCardProps {
    comment: ReviewNew;
}

export function CommentCard(props: CommentCardProps) {

    return (
        <div className="card border-0 border-bottom">
            <div className="card-body">
                <div className="d-flex justify-content-between">
                    <b className="card-title">{props.comment.username}</b>
                        <div>
                            <Rating name="half-rating-read" className="ms-2" size="small" value={props.comment.mark}
                                    precision={0.5} readOnly/>
                        </div>
                </div>
                <label className="card-title">review:</label>
                <p className="card-text">{props.comment.reviewText}</p>
            </div>
        </div>
    );
}