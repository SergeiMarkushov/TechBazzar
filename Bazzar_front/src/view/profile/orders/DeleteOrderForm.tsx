import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import {apiGetMyUser, apiGetMyUserById} from "../../../api/UserApi";
import {AxiosResponse} from "axios";
import {OrderNew, UserNew} from "../../../newInterfaces";
import {useAuth} from "../../../auth/Auth";
import {apiOrderRefund, apiOrderPayment} from "../../../api/OrderApi";

interface PayFormProps {
    order: OrderNew
    onReloadOrder: () => void
    setStatus: (status: boolean) => void
}

export function DeleteOrderForm(props: PayFormProps) {
    let auth = useAuth();

    const [open, setOpen] = useState(false);


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const deleteHandle = () => {
        apiOrderRefund(props.order.id).then((resp) => {
            if (resp.statusText === "OK") {
                handleClose();
                props.onReloadOrder();
            }
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