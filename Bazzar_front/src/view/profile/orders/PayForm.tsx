import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import {apiGetMyUser, apiGetMyUserById} from "../../../api/UserApi";
import {AxiosResponse} from "axios";
import {OrderNew, UserNew} from "../../../newInterfaces";
import {useAuth} from "../../../auth/Auth";
import {apiOrderPayment} from "../../../api/OrderApi";

interface PayFormProps {
    order: OrderNew
    onReloadOrder: () => void
    setStatus: (status: boolean) => void
}

export function PayForm(props: PayFormProps) {
    const [balance, setBalance] = useState(0);
    let [load, setLoad] = useState(false);
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
            if (resp.statusText === "OK") {
                handleClose();
                props.onReloadOrder();
                props.setStatus(true);
            }
        })
    };

    return (
        <div>
            <button className="btn btn-sm btn-success" style={{zIndex: "99999", position: "absolute"}}
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