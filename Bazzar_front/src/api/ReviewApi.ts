import {ReviewDto} from "../newInterfaces";
import {getTokenKeyCloak} from "../util/KeyCloakToken";
import {axiosCustom} from "./AxiosConfig";

const reviewApi = axiosCustom('http://localhost:5555/core/api/v1/reviews', getTokenKeyCloak());

export const apiAddReview = (review: ReviewDto) => reviewApi.post("", review);