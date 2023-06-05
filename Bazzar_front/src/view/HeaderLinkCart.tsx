import Badge, { BadgeProps } from '@mui/material/Badge';
import { styled } from '@mui/material/styles';
import IconButton from '@mui/material/IconButton';
import {getHeaderCartSvg} from "../Svg";

const StyledBadge = styled(Badge)<BadgeProps>(({ theme }) => ({
    '& .MuiBadge-badge': {
        right: -3,
        top: 13,
        border: `2px solid ${theme.palette.background.paper}`,
        padding: '0 4px',
    },
}));

export function HeaderLinkCart() {
    return (
        <div className="d-flex justify-content-center align-items-center flex-column">
            <div>
                <IconButton aria-label="cart" style={{maxWidth: "16px", maxHeight: "16px"}}>
                    {/*TODO: replace badgeContent with cart size*/}
                    <StyledBadge style={{maxWidth: "16px", maxHeight: "16px"}} badgeContent={4} color="info">
                        {getHeaderCartSvg()}
                    </StyledBadge>
                </IconButton>
            </div>
            <div>
                <small><b>Cart</b></small>
            </div>
        </div>
    )
}