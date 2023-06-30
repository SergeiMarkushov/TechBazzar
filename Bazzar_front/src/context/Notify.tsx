import React, {useCallback, useEffect, useMemo, useState} from "react";
import {apiGetCartSize} from "../api/CartApi";

export interface NotifyContextType {
    cartSize: number,
    changeCartSize: () => void;
}

const NotifyContext = React.createContext<NotifyContextType>("" as unknown as NotifyContextType);

export function NotifyProvider({children}: { children: React.ReactNode }) {
    const [cartSize, setCartSize] = useState<number>(0);
    const changeCartSize = useCallback(() => {
        apiGetCartSize().then(resp => {
            setCartSize(resp.data);
        }).catch(() => {
            setCartSize(0);
        });
    }, []);

    useEffect(() => {
        changeCartSize();
    }, [changeCartSize]);

    const value = useMemo(() => ({cartSize, changeCartSize}), [cartSize, changeCartSize]);
    return <NotifyContext.Provider value={value}>{children}</NotifyContext.Provider>;
}

export function useNotify() {
    return React.useContext(NotifyContext);
}