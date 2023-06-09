import React, {useState} from "react";
import {Button} from "react-bootstrap";
import {LiveChat} from "./LiveChat";
import {getChatWithSupportsClosedSvg, getChatWithSupportsOpenedSvg} from "../../../Svg";

export function FloatingButtonWithChat() {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button className="chat-button-open rounded-circle p-0 shadow-sm"
                    style={{
                        backgroundColor: "green",
                        position: "fixed",
                        bottom: "80px",
                        right: "20px",
                        width: "50px",
                        height: "50px"
                    }}
                    onClick={open ? handleClose : handleClickOpen}>
                {!open ? getChatWithSupportsClosedSvg(25, 25) : getChatWithSupportsOpenedSvg(25, 25)}
            </Button>
            <LiveChat open={open} handleClose={handleClose}/>
        </div>
    )
}