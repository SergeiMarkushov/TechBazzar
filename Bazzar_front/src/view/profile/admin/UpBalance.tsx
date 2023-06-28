import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from "@mui/material";
import React from 'react';
import {useState} from "react";
import {primary} from "../../../Colors";
import {User} from "../../../newInterfaces";

interface UpBalanceProps {
    user: User
}

export function UpBalance({user}: UpBalanceProps) {
    const [open, setOpen] = useState(false);
    const [value, setValue] = useState(0);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClick = () => {
        if (value > 0) {
            /*apiUpBalance(user.id, value).then(() => {
                setValue(0);
                setOpen(false);
            });*/
        }
    }

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <div>
            <button className="btn btn-sm text-white"
                    style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Пополнить
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Пополнить баланс</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Текущий баланс - {user.balance}
                    </DialogContentText>
                    <TextField
                        margin="dense"
                        id="name"
                        label="Пополнить на"
                        type="number"
                        fullWidth
                        onChange={(e) => setValue(Number(e.target.value))}
                        variant="standard"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClick}>Пополнить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}