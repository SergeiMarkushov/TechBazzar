import {Dialog, DialogActions, DialogTitle} from "@mui/material";
import React, {useState} from "react";
import {Link} from "react-router-dom";
import {primary} from "../../Colors";
import {ProductNew} from "../../newInterfaces";

interface ChangeProductButtonProps {
    product: ProductNew,
    deleteHandler?: (id: number) => void
    changeHandler?: (id: number) => void
}

export function ChangeProductButton(props: ChangeProductButtonProps) {
    const [open, setOpen] = useState(false);


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <button className="btn btn-sm text-white" style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Изменить
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Редактирование продукта</DialogTitle>
                <DialogActions>
                    <div className="d-flex justify-content-between">
                        <button className="btn btn-danger me-3"
                                onClick={() => props.deleteHandler !== undefined && props.product.id !== undefined ? props.deleteHandler(props.product.id) : false}>delete
                        </button>
                        <Link className="btn btn-primary "
                              to={`/function/menu/productChanger/${props.product.id}`}>change</Link>
                    </div>
                </DialogActions>
            </Dialog>
        </div>
    )
}