import {Link as MuiLink} from '@mui/material';
import Breadcrumbs from '@mui/material/Breadcrumbs';
import * as React from 'react';
import {Link} from 'react-router-dom';

export function BreadCrumbForProductPage() {
    return (
        <div role="presentation" className="mb-2">
            <Breadcrumbs aria-label="breadcrumb" separator="›">
                <MuiLink underline="hover" color="inherit">
                    <Link className="text-decoration-none text-black" to="/catalog">products</Link>
                </MuiLink>
                <MuiLink underline="hover" color="inherit">
                    <Link className="text-decoration-none text-black" color="inherit" to="/catalog">vegetables</Link>
                </MuiLink>
                <MuiLink underline="hover" color="inherit" aria-current="page">
                    <Link className="text-decoration-none text-black" color="text.primary" to="/catalog">yams</Link>
                </MuiLink>
            </Breadcrumbs>
        </div>
    )
}