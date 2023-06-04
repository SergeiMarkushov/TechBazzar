import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {UserNew} from "../../newInterfaces";
import {getMoneySvg} from "../../Svg";

interface ProfileBalanceProps {
    user: UserNew
}

export function ProfileBalance(props: ProfileBalanceProps) {
    return (
        <Link style={{color: primary}} to="/profile/balance" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "17rem"}}>
                <div className="card border-0" style={{maxWidth: "15rem"}}>
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getMoneySvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">Balance: {props.user.balance}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}