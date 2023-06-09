import React, {useState} from "react";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from "@mui/material";
import {primary} from "../../../Colors";
import {UserNew} from "../../../newInterfaces";
import {apiBunUser} from "../../../api/UserApi";

interface UserActivityProps {
    user: UserNew
}

export function UserActivity({user}: UserActivityProps) {
    const [open, setOpen] = useState(false);
    const [check, setCheck] = useState(user.active);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClick = () => {
        if (check) {
            console.log("true")
        } else {
            if (user.active) {
                apiBunUser(user.id).then(() => {
                    setOpen(false);
                    user.active = false;
                });
            }
        }
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
                        <input className="form-check-input" onChange={(event) => setCheck(event.target.checked)}
                               type="checkbox" defaultChecked={user.active}/>
                        <label className="form-check-label">активность</label>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClick}>Сохранить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}