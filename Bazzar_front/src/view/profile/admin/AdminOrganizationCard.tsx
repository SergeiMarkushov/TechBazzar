import React from 'react';
import {Organization} from "../../../newInterfaces";
import {AdminMenuOrganization} from "./AdminMenuOrganization";

interface AdminOrganizationCardProps {
	org: Organization
}

export function AdminOrganizationCard({org}: AdminOrganizationCardProps) {
	return (
		<div className="rounded-2 shadow m-3 d-flex" style={{width: "13rem"}}>
			<div className="m-3 d-flex">
				<div className="d-flex row justify-content-between align-content-between">
					<div>
						<p className="card-title">id - {org.id}</p>
						<p className="card-subtitle mb-2 text-muted">title - {org.title}</p>
						<p className="card-subtitle mb-2 text-muted">description - {org.description}</p>
						<p className="card-subtitle mb-2 text-muted">owner - {org.owner}</p>
						<p className="card-subtitle mb-2 text-muted">is active
							- {org.active ? "active" : "not active"}</p>
					</div>
					<div className="d-flex justify-content-between mb-1">
						{/*<div>
							<Link to={`/function/menu/allUsers/${props.user.id}`} className="btn-sm btn text-white"
								  style={{backgroundColor: primary}}>Изменить</Link>
						</div>*/}
						<AdminMenuOrganization org={org}/>
					</div>
				</div>
			</div>
		</div>
	)
}