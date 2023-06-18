import React, {useEffect, useState} from "react";
import {primary} from "../../../Colors";
import {useAuth} from "../../../auth/Auth";
import {MessageComponent} from "./MessageComponent";

interface LiveChatProps {
    open: boolean,
}

export interface Message {
    id: number,
    author: string,
    text: string
}

const emptyMessage: Message = {
    id: 0,
    author: "",
    text: ""
}

export function LiveChat({open}: LiveChatProps) {
    const [messages, setMessages] = useState<Array<Message>>(Array.of(emptyMessage));
    const [inputMessage, setInputMessage] = useState("");
    const auth = useAuth();

    useEffect(() => {
        if (!auth.isAuth) {
            return;
        }
    }, [auth.isAuth]);

    useEffect(() => {
        //fetch messages
        if (open && auth.isAuth) {
            setMessages([{id: 1, author: auth.user?.email ?? "", text: "Hello"}, {id: 2, author: "support", text: "Hi"}]);
        }
    }, [auth, open]);

    const handleSendMessage = () => {
        //send message
        setMessages([...messages, {id: 3, author: auth.user?.email ?? "", text: inputMessage}]);
        setInputMessage("");
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setInputMessage(event.target.value);
    }

    const scrollToBottom = () => {
        const chatMessages = document.getElementsByClassName("chat-messages")[0];
        chatMessages.scrollTop = chatMessages.scrollHeight - chatMessages.clientHeight;
    }

    return (
        <div>
            {open &&
                <div className="rounded shadow"
                     style={{position: "fixed", bottom: "150px", right: "20px", maxWidth: "20rem", maxHeight: "20rem"}}>
                    <div className="p-2 text-white rounded-top" style={{backgroundColor: primary}}>
                        Чат с поддержкой
                    </div>
                    <div className="chat-messages p-2 m-2 bg-light rounded border-1 border-dark d-flex row"
                         style={{overflowY: 'scroll', maxHeight: "12rem"}}>
                        {messages.map((message) => (
                            <MessageComponent scrollToBottom={scrollToBottom} key={message.id}
                                              message={message}></MessageComponent>
                        ))}
                    </div>
                    <div className="d-flex">
                        <div className="input-group m-2">
                            <input className="form-control" placeholder="Ввод" type="text"
                                   style={{borderColor: primary}}
                                   value={inputMessage} onChange={handleInputChange}/>
                            <button style={{backgroundColor: primary}} id="basic-addon1"
                                    className="input-group-text text-white btn btn-sm"
                                    onClick={handleSendMessage}>Отправить
                            </button>
                        </div>
                    </div>
                </div>
            }
        </div>
    )
}