import {Button, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import React, {useState} from 'react';
import {primary} from "../../../Colors";
import {apiOrganizationBun, apiOrganizationConfirm} from "../../../api/OrganizationApi";
import {Organization} from "../../../newInterfaces";

interface AdminMenuOrganizationProps {
    org: Organization
}

export function AdminMenuOrganization({org}: AdminMenuOrganizationProps) {
    const [open, setOpen] = useState(false);
    const [check, setCheck] = useState(org.active);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleClick = () => {
        if (check) {
            if (!org.active) {
                apiOrganizationConfirm(org.title).then(() => {
                    window.location.reload();
                });
            }
        } else {
            if (org.active) {
                apiOrganizationBun(org.id).then(() => {
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