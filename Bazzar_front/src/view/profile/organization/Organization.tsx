import {CreateOrganizationButton} from "./CreateOrganizationButton";
import {getPlusSvg} from "../../../Svg";


export function Organization() {
    return (
        <div className="d-flex align-items-start justify-content-start">
            <div>
                <div className="col rounded shadow m-2">
                    <CreateOrganizationButton link={"create"} title={""} svg={getPlusSvg()}/>
                </div>
            </div>
        </div>
    )
}