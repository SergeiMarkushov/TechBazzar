import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import {primary} from "../Colors";
import {RequireAuth, useAuth} from "../auth/Auth";
import {RequireRoleADMIN} from "../auth/Role";
import {HeaderLinkAuth} from "./HeaderLinkAuth";
import {HeaderLinkCart} from "./HeaderLinkCart";
import {HeaderLinkProfile} from "./HeaderLinkProfile";
import {Home} from "./Home";
import {SearchTab} from "./SearchTab";
import {Login} from "./auth/Login";
import {Logout} from "./auth/Logout";
import {Registration} from "./auth/Registration";
import {Cart} from "./cart/Cart";
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
import {ProductCreator} from "./profile/admin/ProductCreator";

import {Balance} from "./profile/balance/Balance";
import {OrderInfo} from "./profile/orders/OrderInfo";
import {Orders} from "./profile/orders/Orders";
import {CreateOrganization} from "./profile/organization/CreateOrganization";
import {OrganizationManagement} from "./profile/organization/OrganizationManagement";
import {OrganizationMenu} from "./profile/organization/OrganizationMenu";

export function Header() {
    const auth = useAuth();

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
                            <Link className="nav-link" to={!auth.isAuth ? "/login" : "/logout"}>
                                <HeaderLinkAuth/>
                            </Link>
                        </div>
                    </div>
                </nav>
            </div>
            <div style={{maxWidth: "1200px", marginLeft: "auto", marginRight: "auto", backgroundColor: "white"}}>

                <Routes>

                    {/*public rotes*/}
                    <Route>
                        <Route path="/home" element={<Home/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/signUp" element={<Registration/>}/>
                        <Route path="/catalog" element={<Catalog/>}/>
                        <Route path="/" element={<Catalog/>}/>
                        <Route path="/cart" element={<Cart/>}/>
                    </Route>

                    {/*auth and has any private roles rotes*/}
                    <Route>
                        <Route path="/function/menu" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminMenu/>
                                </RequireRoleADMIN>
                            </RequireAuth>
                        }/>
                        <Route path="/function/menu/addProduct" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <ProductCreator/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/productChanger" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminMenuProductChanger/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/productChanger/:id" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminMenuProductChangerForm/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/allUsers" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminMenuAllUsers/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/allUsers/:id" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminMenuChangeUser/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/confirmProduct" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <ConfirmProducts/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                        <Route path="/function/menu/organizations" element={
                            <RequireAuth>
                                <RequireRoleADMIN>
                                    <AdminOrganizations/>
                                </RequireRoleADMIN>
                            </RequireAuth>}/>
                    </Route>

                    {/*auth rotes*/}
                    <Route>
                        <Route path="/logout" element={<RequireAuth><Logout/></RequireAuth>}/>
                        <Route path="/profile" element={<RequireAuth><Profile/></RequireAuth>}/>
                        <Route path="/product/:name/:id" element={<ProductPage/>}/>
                        <Route path="/profile/orders" element={<RequireAuth><Orders/></RequireAuth>}/>
                        <Route path="/profile/orders/order/:id" element={<RequireAuth><OrderInfo/></RequireAuth>}/>
                        <Route path="/profile/userProfile" element={<RequireAuth><UserProfile/></RequireAuth>}/>
                        <Route path="/profile/balance" element={<RequireAuth><Balance/></RequireAuth>}/>
                        <Route path="/profile/organization" element={<RequireAuth><OrganizationMenu/></RequireAuth>}/>
                        <Route path="/profile/organization/:title" element={<RequireAuth><OrganizationManagement/></RequireAuth>}/>
                        <Route path="/profile/organization/create" element={<RequireAuth><CreateOrganization/></RequireAuth>}/>
                        <Route path="/profile/organization/:title/addProduct" element={<RequireAuth><ProductCreator/></RequireAuth>}/>
                    </Route>
                </Routes>
            </div>
        </div>
    )
}