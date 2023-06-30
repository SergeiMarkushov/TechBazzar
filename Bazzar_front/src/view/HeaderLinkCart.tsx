import Badge, {BadgeProps} from '@mui/material/Badge';
import IconButton from '@mui/material/IconButton';
import {styled} from '@mui/material/styles';
import React from 'react';
import {getHeaderCartSvg} from "../Svg";
import {useNotify} from "../context/Notify";

const StyledBadge = styled(Badge)<BadgeProps>(({theme}) => ({
    '& .MuiBadge-badge': {
        right: -3,
        top: 13,
        border: `2px solid ${theme.palette.background.paper}`,
        padding: '0 4px',
    },
}));

export function HeaderLinkCart() {
    const notify = useNotify();

    return (
        <div className="d-flex justify-content-center align-items-center flex-column">
            <div>
                <IconButton aria-label="cart" style={{maxWidth: "16px", maxHeight: "16px"}}>
                    <StyledBadge style={{maxWidth: "16px", maxHeight: "16px"}} badgeContent={notify.cartSize}
                                 color="info">
                        {getHeaderCartSvg()}
                    </StyledBadge>
                </IconButton>
            </div>
            <div>
                <small><b>Корзина</b></small>
            </div>
        </div>
    )
}