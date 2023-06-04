import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter} from "react-router-dom";
import {Header} from "./view/Header";
import {AuthProvider} from "./auth/Auth";
import {RoleProvider} from "./auth/Role";
import {SearchProvider} from "./context/Search";
import AddIcon from '@mui/icons-material/Add';
import {Fab} from '@mui/material';
import {getDeveloperSvg, getOrderSvg} from "./Svg";
import { FloatingButton } from './view/FloatingButton';

function App() {
    return (
        <div style={{backgroundColor: "white"}}>
            <BrowserRouter>
                <AuthProvider>
                    <RoleProvider>
                        <SearchProvider>
                            <Header/>
                            <FloatingButton/>
                        </SearchProvider>
                    </RoleProvider>
                </AuthProvider>
            </BrowserRouter>
        </div>

    )
}

export default App;
