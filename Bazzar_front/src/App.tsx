import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter} from "react-router-dom";
import {AuthProvider} from "./auth/Auth";
import {RoleProvider} from "./auth/Role";
import {SearchProvider} from "./context/Search";
import { FloatingButtonWithDevelopers } from './view/FloatingButtonWithDevelopers';
import {Header} from "./view/Header";
import { FloatingButtonWithChat } from './view/profile/chat/FloatingButtonWithChat';

function App() {
    return (
        <div style={{backgroundColor: "white"}}>
            <BrowserRouter>
                <AuthProvider>
                    <RoleProvider>
                        <SearchProvider>
                            <Header/>
                            <FloatingButtonWithDevelopers/>
                            <FloatingButtonWithChat/>
                        </SearchProvider>
                    </RoleProvider>
                </AuthProvider>
            </BrowserRouter>
        </div>

    )
}

export default App;
