import React, {useState} from "react";
import {Button, Dialog, DialogActions, DialogTitle} from "@mui/material";
import {OrderNew} from "../../../newInterfaces";
import {apiOrderRefund} from "../../../api/OrderApi";

interface PayFormProps {
    order: OrderNew
    onReloadOrder: () => void
    setStatus: (status: boolean) => void
}

export function DeleteOrderForm(props: PayFormProps) {
    const [open, setOpen] = useState(false);


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const deleteHandle = () => {
        apiOrderRefund(props.order.id).then((resp) => {
                handleClose();
                props.onReloadOrder();
        })
    };

    return (
        <div>
            <button className="btn btn-sm btn-danger" onClick={handleClickOpen}>Отмена</button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Отмена заказа</DialogTitle>
                <DialogActions>
                    <Button onClick={deleteHandle}>Отменить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}