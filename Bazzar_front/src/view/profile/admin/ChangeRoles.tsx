import {Dialog, DialogContent, DialogTitle} from "@mui/material";
import {AxiosResponse} from "axios";
import React, {useEffect, useState} from 'react';
import {primary} from "../../../Colors";
import {apiGetUserRoles, apiSetRoleUser} from "../../../api/UserApi";
import {existingRoles} from "../../../auth/Roles";
import {Role, UserNew} from "../../../newInterfaces";

interface ChangeRolesProps {
    user: UserNew
}

export function ChangeRoles({user}: ChangeRolesProps) {
    const [open, setOpen] = useState(false);
    const [load, isLoad] = useState(false);
    const [roles, setRoles] = useState(Array.of(""));
    const handleClickOpen = () => {
        setOpen(true);
    };

    function changeRoles(someRole: string) {
        apiSetRoleUser(user.email, someRole).then(() => {
            setRoles(roles.concat(someRole))
        }).catch(() => {
            /*TODO check it*/
            setRoles(roles.filter(role => role !== someRole))
        })
    }

    useEffect(() => {
        if (!load) {
            apiGetUserRoles(user.email).then((roles: AxiosResponse<Array<Role>>) => {
                setRoles(roles.data.map(role => role.name));
                isLoad(true);
            })
        }
    }, [load, user.email]);

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <div>
            <button className="btn btn-sm text-white"
                    style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Сменить
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Сменить роли</DialogTitle>
                <DialogContent>
                    {
                        existingRoles.map(role => {
                            return (
                                <div className="form-check form-switch" key={role}>
                                    <input className="form-check-input" onChange={(event) =>
                                        event.target.checked ? changeRoles(role) : null}
                                           type="checkbox" id={role}
                                           defaultChecked={isPresent(role, roles)}/>
                                    <label className="form-check-label" htmlFor={role}>{role}</label>
                                </div>
                            )
                        })
                    }
                </DialogContent>
            </Dialog>
        </div>
    )
}

function isPresent(role: string, roles: Array<string>) {
    for (const element of roles) {
        if (element === role) {
            return true;
        }
    }
    return false;
}