import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter} from "react-router-dom";
import {ErrorComponent} from "./ErrorComponent";
import {ErrorProvider} from "./auth/ErrorProvider";
import {keycloak} from "./auth/Keycloak";
import {RoleProvider} from "./auth/Role";
import {NotifyProvider} from "./context/Notify";
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
                <ErrorProvider>
                    <BrowserRouter>
                        <RoleProvider>
                            <SearchProvider>
                                <NotifyProvider>
                                    <Header/>
                                    <FloatingButtonWithDevelopers/>
                                    <FloatingButtonWithChat/>
                                    <ErrorComponent/>
                                </NotifyProvider>
                            </SearchProvider>
                        </RoleProvider>
                    </BrowserRouter>
                </ErrorProvider>
            </ReactKeycloakProvider>

        </div>

    )
}

export default App;
