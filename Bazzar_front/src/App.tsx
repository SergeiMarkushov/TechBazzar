import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter} from "react-router-dom";
import {AuthProvider} from "./auth/Auth";
import {keycloak} from "./auth/Keycloak";
import {RoleProvider} from "./auth/Role";
import {SearchProvider} from "./context/Search";
import {FloatingButtonWithDevelopers} from './view/FloatingButtonWithDevelopers';
import {Header} from "./view/Header";
import {FloatingButtonWithChat} from './view/profile/chat/FloatingButtonWithChat';
// eslint-disable-next-line import/order
import {ReactKeycloakProvider} from "@react-keycloak/web";

function App() {
    return (
        <div style={{backgroundColor: "white"}}>

                <ReactKeycloakProvider authClient={keycloak}>
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
                </ReactKeycloakProvider>

        </div>

    )
}

export default App;
