import {Dialog, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import React from 'react';
import {useState} from "react";
import {UserNew} from "../../../newInterfaces";
import {ChangeRoles} from "./ChangeRoles";
import {UpBalance} from "./UpBalance";
import {UserActivity} from "./UserActivity";

interface AdminMenuUserMenuProps {
    user: UserNew
}

export function AdminMenuUserMenu({user}: AdminMenuUserMenuProps) {
    const [open, setOpen] = useState(false);


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <div>
            <button className="btn btn-sm btn-success" onClick={handleClickOpen}>Меню</button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Меню пользователя</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <div className="d-flex justify-content-between mb-3">
                            Баланс - {<UpBalance user={user}/>}
                        </div>
                    </DialogContentText>
                    <DialogContentText>
                        <div className="d-flex justify-content-between mb-3">
                            Роли - {<ChangeRoles user={user}/>}
                        </div>
                    </DialogContentText>
                    <DialogContentText>
                        <div className="d-flex justify-content-between mb-3">
                            Активность - {<UserActivity user={user}/>}
                        </div>
                    </DialogContentText>
                </DialogContent>
            </Dialog>
        </div>
    )
}