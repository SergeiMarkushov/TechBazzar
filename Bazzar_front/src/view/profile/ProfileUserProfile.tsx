import {Link} from "react-router-dom";
import {useAuth} from "../../auth/Auth";
import {primary} from "../../Colors";
import {UserNew} from "../../newInterfaces";
import {getProfileSvg} from "../../Svg";

interface ProfileUserProfileProps {
    user: UserNew
}

export function ProfileUserProfile(props: ProfileUserProfileProps) {
    let auth = useAuth();
    return (
        <Link style={{color: primary}} to="/profile/userProfile" className="text-decoration-none">
            <div className="card border-0">
                <div className="card border-0">
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getProfileSvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">{props.user.username}</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="">
                    <div className="card-body">
                        <small className="card-title text-sm-start">e-mail: {props.user.email}</small>
                        <br/>
                    </div>
                </div>
            </div>
        </Link>
    )
}