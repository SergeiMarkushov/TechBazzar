import {ReviewDto} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const reviewApi = axiosCustom('http://localhost:5555/core/api/v1/reviews', getTokenKeyCloak());
const productApi = axiosCustom('http://localhost:5555/core/api/v1/products', getTokenKeyCloak());

export const apiAddReview = (review: ReviewDto) => reviewApi.post("", review);
export const apiGetReviews = (productId: number) => reviewApi.get(`find-by-productId/${productId}`);
export const apiGetProductMark = (productId: number) => productApi.get(`${productId}/average-rating`);
export const apiGetReviewCount = (productId: number) => reviewApi.get(`count/${productId}`);