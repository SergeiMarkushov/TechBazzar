import React, {useEffect, useState} from "react";

export interface ErrorContextType {
    error: string,
    success: boolean,
    showSuccess: boolean,
    textIfSuccess: string,
    setErrors: (error: string, success: boolean, showSuccess: boolean, textIfSuccess: string) => void,
    show: boolean
    setShow: (show: boolean) => void,
}

const ErrorContext = React.createContext<ErrorContextType>("" as unknown as ErrorContextType);

export function ErrorProvider({children}: { children: React.ReactNode }) {
    const [error, setError] = useState<string>("");
    const [success, setSuccess] = useState<boolean>(false);
    const [showSuccess, setShowSuccess] = useState<boolean>(false);
    const [textIfSuccess, setTextIfSuccess] = useState<string>("");
    const [show, setShow] = useState<boolean>(false);

    useEffect(() => {
        if (!show) {
            setError("")
            setSuccess(false)
            setShowSuccess(false)
            setTextIfSuccess("")
        }
    }, [show])

    function setErrors(error: string, success: boolean, showSuccess: boolean, textIfSuccess: string) {
        setError(error);
        setSuccess(success);
        setShowSuccess(showSuccess);
        setTextIfSuccess(textIfSuccess);
    }

    const value = {
        error,
        success,
        showSuccess,
        textIfSuccess,
        setErrors,
        show,
        setShow
    };

    return <ErrorContext.Provider value={value}>{children}</ErrorContext.Provider>;
}

export function useError() {
    return React.useContext(ErrorContext);
}