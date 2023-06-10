import React from 'react';
import {useState} from "react";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import {Organization} from "../../../newInterfaces";
import {primary} from "../../../Colors";
import {apiOrganizationBun, apiOrganizationConfirm} from "../../../api/OrganizationApi";

interface AdminMenuOrganizationProps {
    org: Organization
}

export function AdminMenuOrganization({org}: AdminMenuOrganizationProps) {
    const [open, setOpen] = useState(false);
    const [check, setCheck] = useState(org.active);


    const handleClickOpen = () => {
        setOpen(true);
        console.log(org.active)
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleClick = () => {
        if (check) {
            if (!org.active) {
                apiOrganizationConfirm(org.title).then((resp) => {
                    window.location.reload();
                });
            }
        } else {
            if (org.active) {
                apiOrganizationBun(org.id).then((resp) => {
                    window.location.reload();
                });
            }
        }
    }

    return (
        <div>
            <button className="btn btn-sm text-white"
                    style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Активность
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Активность компании</DialogTitle>
                <DialogContent>
                    <div className="form-check form-switch">
                        <input className="form-check-input" onChange={(event) => setCheck(event.target.checked)}
                               type="checkbox" defaultChecked={org.active}/>
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