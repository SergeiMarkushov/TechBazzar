import {useAuth} from "../auth/Auth";
import {styled} from "@mui/material/styles";
import Badge, {BadgeProps} from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton/IconButton";
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
    let auth = useAuth();
    return (
        <div className="d-flex justify-content-center align-items-center flex-column">
            <div>
                <IconButton aria-label="cart" style={{maxWidth: "16px", maxHeight: "16px"}}>
                    {/*TODO: replace badgeContent with notification size*/}
                    <StyledBadge style={{maxWidth: "16px", maxHeight: "16px"}} badgeContent={4} color="info">
                        {getHeaderProfileSvg()}
                    </StyledBadge>
                </IconButton>
            </div>
            <div>
                <small hidden={!auth.isAuth}><b>{auth.user === null ? '' : auth.user.username}</b></small>
                <small hidden={auth.isAuth}><b>Guest</b></small>
            </div>
        </div>
    )
}