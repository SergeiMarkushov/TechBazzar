import {primary} from "../../../Colors";
import {Link} from "react-router-dom";
import {getMoneySvg} from "../../../Svg";

interface ProfileBalanceProps {
	link: string,
	title: string,
	svg: JSX.Element
}

export function AdminMenuCard(props: ProfileBalanceProps) {
	return (
		<Link style={{color: primary}} to={props.link} className="text-decoration-none">
			<div className="card border-0" style={{maxWidth: "15rem"}}>
				<div className="card border-0" style={{maxWidth: "15rem"}}>
					<div className="d-flex align-items-center">
						<div className="m-2">
							{props.svg}
						</div>
						<div className="">
							<div className="card-body">
								<h5 className="card-title">{props.title}</h5>
							</div>
						</div>
					</div>
				</div>
			</div>
		</Link>
	)
}