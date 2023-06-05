import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {getAdminMenuSvg} from "../../Svg";

export function ProfileAdminMenu() {
    return (
        <Link style={{color: primary}} to="/function/menu" className="text-decoration-none">
            <div className="card border-0" style={{maxWidth: "17rem"}}>
                <div className="card border-0" style={{maxWidth: "15rem"}}>
                    <div className="d-flex align-items-center">
                        <div className="m-2">
                            {getAdminMenuSvg()}
                        </div>
                        <div className="">
                            <div className="card-body">
                                <h5 className="card-title">Admin menu</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Link>
    )
}