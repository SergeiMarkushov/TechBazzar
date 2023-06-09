import React from 'react';
import {Message} from "./LiveChat";
import {useAuth} from "../../../auth/Auth";
import {primary} from "../../../Colors";
import {useEffect} from "react";

interface MessageProps {
    message: Message,
    scrollToBottom: () => void
}

export function MessageComponent({message, scrollToBottom}: MessageProps) {
    const auth = useAuth();
    const isAuthor = message.author === auth.user.email;

    useEffect(() => {
        scrollToBottom();
    }, [message]);

    return (
        <div className={`mb-2 d-flex ${isAuthor ? "justify-content-end" : "justify-content-start"}`}>
            <small className={`ps-3 pe-3 p-1 rounded ${isAuthor ? "text-white" : "text-black"}`}
                   style={{
                       backgroundColor: isAuthor ? primary : "lightgray",
                       maxWidth: "10rem",
                       wordWrap: "break-word"
                   }}>{message.text}</small>
        </div>
    )
}