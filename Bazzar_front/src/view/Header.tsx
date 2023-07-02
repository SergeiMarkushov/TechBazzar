import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import {primary} from "../Colors";
import {ErrorComponent} from "../ErrorComponent";
import {RequireAuthKeycloak} from "../auth/KeycloakProvider";
import {RequireRoleADMIN} from "../auth/Role";
import {HeaderLinkAuth} from "./HeaderLinkAuth";
import {HeaderLinkCart} from "./HeaderLinkCart";
import {HeaderLinkProfile} from "./HeaderLinkProfile";
import {Home} from "./Home";
import {SearchTab} from "./SearchTab";
import {CartComponent} from "./cart/CartComponent";
import {Catalog} from "./catalog/Catalog";
import {ProductPage} from "./catalog/product/ProductPage";
import {Profile} from "./profile/Profile";
import {UserProfile} from "./profile/UserProfile";
import {AdminMenu} from "./profile/admin/AdminMenu";
import {AdminMenuAllUsers} from "./profile/admin/AdminMenuAllUsers";
import {AdminMenuChangeUser} from "./profile/admin/AdminMenuChangeUser";
import {AdminMenuProductChanger} from "./profile/admin/AdminMenuProductChanger";
import {AdminMenuProductChangerForm} from "./profile/admin/AdminMenuProductChangerForm";
import {AdminOrganizations} from "./profile/admin/AdminOrganizations";
import {ConfirmProducts} from "./profile/admin/ConfirmProducts";

import {Balance} from "./profile/balance/Balance";
import {OrderInfo} from "./profile/orders/OrderInfo";
import {Orders} from "./profile/orders/Orders";
import {CreateOrganization} from "./profile/organization/CreateOrganization";
import {OrganizationManagement} from "./profile/organization/OrganizationManagement";
import {OrganizationMenu} from "./profile/organization/OrganizationMenu";
import {ProductCreator} from "./profile/organization/ProductCreator";
import {PurchaseMain} from "./profile/purchase/PurchaseMain";

export function Header() {
    return (
        <div className="bg-white">
            <div style={{width: "auto"}} className="bg-light shadow-sm rounded-bottom">
                <nav style={{maxWidth: "1200px", marginLeft: "auto", marginRight: "auto"}}
                     className="navbar navbar-expand-sm bg-light mb-3">
                    <div className="container-fluid">
                        <div className="collapse navbar-collapse" id="navbarNav">
                            <div className="navbar-nav">
                                <Link style={{color: primary}} className="nav-link fw-bold" to="/home">
                                    TECHBAZZAR
                                </Link>
                                <Link className="nav-link" to="/catalog">
                                    Каталог
                                </Link>
                            </div>
                        </div>
                        <SearchTab/>
                        <div className="navbar-nav">
                            <Link className="nav-link" to="/cart">
                                <HeaderLinkCart/>
                            </Link>
                            <Link className="nav-link" to="/profile">
                                <HeaderLinkProfile/>
                            </Link>
                            <span className="nav-link">
                                <HeaderLinkAuth/>
                            </span>
                        </div>
                    </div>
                </nav>
            </div>
            <div style={{maxWidth: "1200px", marginLeft: "auto", marginRight: "auto", backgroundColor: "white"}}>
                <Routes>

                    {/*public rotes*/}
                    <Route>
                        <Route path="/home" element={<Home/>}/>
                        <Route path="/catalog" element={<Catalog/>}/>
                        <Route path="/catalog/:companyParam" element={<Catalog/>}/>
                        <Route path="/product/:name/:id" element={<ProductPage/>}/>
                        <Route path="/" element={<Catalog/>}/>
                    </Route>

                    {/*auth and has any private roles rotes*/}
                    <Route>
                        <Route path="/function/menu" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminMenu/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>
                        }/>
                        <Route path="/function/menu/productChanger" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminMenuProductChanger/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                        <Route path="/function/menu/productChanger/:id" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminMenuProductChangerForm/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                        <Route path="/function/menu/allUsers" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminMenuAllUsers/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                        <Route path="/function/menu/allUsers/:id" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminMenuChangeUser/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                        <Route path="/function/menu/confirmProduct" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <ConfirmProducts/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                        <Route path="/function/menu/organizations" element={
                            <RequireAuthKeycloak>
                                <RequireRoleADMIN>
                                    <AdminOrganizations/>
                                </RequireRoleADMIN>
                            </RequireAuthKeycloak>}/>
                    </Route>

                    {/*auth rotes*/}
                    <Route>
                        <Route path="/cart" element={<RequireAuthKeycloak><CartComponent/></RequireAuthKeycloak>}/>
                        <Route path="/profile" element={<RequireAuthKeycloak><Profile/></RequireAuthKeycloak>}/>
                        <Route path="/profile/orders" element={<RequireAuthKeycloak><Orders/></RequireAuthKeycloak>}/>
                        <Route path="/profile/purchase" element={<RequireAuthKeycloak><PurchaseMain/></RequireAuthKeycloak>}/>
                        <Route path="/profile/orders/order/:id" element={<RequireAuthKeycloak><OrderInfo/></RequireAuthKeycloak>}/>
                        <Route path="/profile/userProfile" element={<RequireAuthKeycloak><UserProfile/></RequireAuthKeycloak>}/>
                        <Route path="/profile/balance" element={<RequireAuthKeycloak><Balance/></RequireAuthKeycloak>}/>
                        <Route path="/profile/organization" element={<RequireAuthKeycloak><OrganizationMenu/></RequireAuthKeycloak>}/>
                        <Route path="/profile/organization/:title"
                               element={<RequireAuthKeycloak><OrganizationManagement/></RequireAuthKeycloak>}/>
                        <Route path="/profile/organization/create"
                               element={<RequireAuthKeycloak><CreateOrganization/></RequireAuthKeycloak>}/>
                        <Route path="/profile/organization/:title/addProduct"
                               element={<RequireAuthKeycloak><ProductCreator/></RequireAuthKeycloak>}/>
                    </Route>
                </Routes>
            </div>
        </div>
    )
}