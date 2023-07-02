import Badge, {BadgeProps} from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton/IconButton";
import {styled} from "@mui/material/styles";
import {useKeycloak} from "@react-keycloak/web";
import React from 'react';
import {getHeaderProfileSvg} from "../Svg";

const StyledBadge = styled(Badge)<BadgeProps>(({theme}) => ({
    '& .MuiBadge-badge': {
        right: -3,
        top: 13,
        border: `2px solid ${theme.palette.background.paper}`,
        padding: '0 4px',
    },
}));

export function HeaderLinkProfile() {
    const {keycloak} = useKeycloak();
    return (
        <div className="d-flex justify-content-center align-items-center flex-column">
            <div>
                <IconButton aria-label="cart" style={{maxWidth: "16px", maxHeight: "16px"}}>
                    {/*TODO: replace badgeContent with notification size*/}
                    <StyledBadge style={{maxWidth: "16px", maxHeight: "16px"}} badgeContent={0} color="info">
                        {getHeaderProfileSvg()}
                    </StyledBadge>
                </IconButton>
            </div>
            <div>
                <small hidden={!keycloak.authenticated}><b>{keycloak.tokenParsed?.given_name ?? "Unknown"}</b></small>
                <small hidden={keycloak.authenticated}><b>Гость</b></small>
            </div>
        </div>
    )
}