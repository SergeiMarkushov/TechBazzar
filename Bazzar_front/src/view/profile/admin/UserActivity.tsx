import {Button, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import React, {useState} from "react";
import {primary} from "../../../Colors";
import {apiBunUser} from "../../../api/UserApi";
import {User} from "../../../newInterfaces";

interface UserActivityProps {
    user: User
}

export function UserActivity({user}: UserActivityProps) {
    const [open, setOpen] = useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClick = () => {
        apiBunUser(user.id).then(() => {
            setOpen(false);
            window.location.reload();
        });
    }

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <button className="btn btn-sm text-white"
                    style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Активность
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Активность пользователя</DialogTitle>
                <DialogContent>
                    <div className="form-check form-switch">
                        <input className="form-check-input" type="checkbox" defaultChecked={user.active}/>
                        <span className="form-check-label">активность</span>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClick}>Сохранить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}