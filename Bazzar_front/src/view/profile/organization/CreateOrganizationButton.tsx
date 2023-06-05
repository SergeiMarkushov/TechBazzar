import {primary} from "../../../Colors";
import {Link} from "react-router-dom";


interface CreateOrderButtonProps {
    link: string,
    title: string,
    svg: JSX.Element
}

export function CreateOrganizationButton(props: CreateOrderButtonProps) {
    return (
        <Link style={{color: primary}} to={props.link} className="text-decoration-none">
            <div className="d-flex align-items-center">
                <div className="m-2">
                    {props.svg}
                </div>
            </div>
        </Link>
    )
}