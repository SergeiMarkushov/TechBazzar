import {Button, Dialog, DialogActions, DialogTitle} from "@mui/material";
import React, {useState} from "react";
import {apiOrderRefund} from "../../../api/OrderApi";
import {useError} from "../../../auth/ErrorProvider";
import {Order} from "../../../newInterfaces";

interface PayFormProps {
    order: Order
    onReloadOrder: () => void
    setStatus: (status: boolean) => void
}

export function DeleteOrderForm(props: PayFormProps) {
    const [open, setOpen] = useState(false);
    const error = useError();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const refundHandle = () => {
        apiOrderRefund(props.order.id).then(() => {
            handleClose();
            props.onReloadOrder();
        }).catch(() => {
            handleClose()
            error.setErrors("Не могу отменить покупку, возможно время отмены прошло", false, false, "");
            error.setShow(true)
        })
    };

    const deleteHandle = () => {
        // delete order
        console.log("fake delete")
        error.setErrors("Не реализованно", false, true, "Не реализованно");
        error.setShow(true)
        handleClose()
    };

    return (
        <div>
            { (props.order.status && checkData(props.order.createdAt)) || !props.order.status &&
                <div>
                    <button className="btn btn-sm btn-danger" onClick={handleClickOpen}>Отмена</button>
                    <Dialog open={open} onClose={handleClose}>
                        <DialogTitle>Отмена заказа</DialogTitle>
                        <DialogActions>
                            <Button onClick={props.order.status ? refundHandle : deleteHandle}>Отменить</Button>
                        </DialogActions>
                    </Dialog>
                </div>
            }
        </div>
    )
}

function checkData(date: string): boolean {
    const newDate = new Date(date);
    return Date.now() - newDate.getTime() <= 86400000;
}