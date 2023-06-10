import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import React, {useEffect, useState} from "react";
import {apiGetMyUser} from "../../../api/UserApi";
import {AxiosResponse} from "axios";
import {OrderNew, UserNew} from "../../../newInterfaces";
import {apiOrderPayment} from "../../../api/OrderApi";

interface PayFormProps {
    order: OrderNew
    onReloadOrder: () => void
    setStatus: (status: boolean) => void
}

export function PayForm(props: PayFormProps) {
    const [balance, setBalance] = useState(0);
    const [load, setLoad] = useState(false);
    const [open, setOpen] = useState(false);

    useEffect(() => {
        if (!load && open) {
            console.log("useEffect")
            apiGetMyUser().then((data: AxiosResponse<UserNew>) => {
                setBalance(data.data.balance);
            });
            setLoad(true);
        }
    });


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const payHandle = () => {
        apiOrderPayment(props.order.id).then((resp) => {
            handleClose();
            props.onReloadOrder();
            props.setStatus(true);
        })
    };

    return (
        <div>
            <button className="btn btn-sm btn-success"
                    onClick={handleClickOpen}>Оплатить
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Оплата</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Ваш баланс - {balance}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={payHandle}>Оплатить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}