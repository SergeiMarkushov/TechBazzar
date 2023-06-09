import React, {useEffect, useState} from 'react';
import {Dialog, DialogContent, DialogTitle} from "@mui/material";
import {primary} from "../../../Colors";
import {Role, UserNew} from "../../../newInterfaces";
import {apiGetUserRoles, apiSetRoleUser} from "../../../api/UserApi";
import {existingRoles} from "../../../auth/Roles";
import {AxiosError, AxiosResponse} from "axios";

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
        apiSetRoleUser(user.email, someRole).then((response: AxiosResponse) => {
            setRoles(roles.concat(someRole))
        }).catch((error: AxiosError) => {
            console.log(error)
        })
    }

    useEffect(() => {
        if (!load) {
            apiGetUserRoles(user.email).then((roles: AxiosResponse<Array<Role>>) => {
                setRoles(roles.data.map(role => role.name));
                isLoad(true);
            })
        }
    }, [load]);

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