import {getDeveloperSvg} from "../Svg";
import {Dialog, DialogContent, DialogTitle} from "@mui/material";
import React, {useState} from "react";
import {primary} from "../Colors";
import {Button} from "react-bootstrap";
import {DeveloperDescription} from "./DeveloperDescription";

export function FloatingButtonWithDevelopers() {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button className="rounded-circle p-0 shadow-sm"
                    style={{
                        backgroundColor: primary,
                        position: "fixed",
                        bottom: "20px",
                        right: "20px",
                        width: "50px",
                        height: "50px"
                    }}
                    onClick={handleClickOpen}>
                {getDeveloperSvg()}
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Контакты</DialogTitle>
                <DialogContent>
                    <DeveloperDescription name="Бородинов Юрий" telegramLink="tg://resolve?domain=Aditon"/>
                    <DeveloperDescription name="Бехтер Николай" telegramLink="tg://resolve?domain=N_Bekhter"/>
                    <DeveloperDescription name="Шеховцов Сергей" telegramLink="tg://resolve?domain=sergey_shekhov"/>
                    <DeveloperDescription name="Маркушов Сергей" telegramLink="tg://resolve?domain=Serega_Markus"/>
                    <DeveloperDescription name="Федоренко Вадим" telegramLink="tg://resolve?domain=KekNyaV"/>
                </DialogContent>
            </Dialog>
        </div>
    )
}