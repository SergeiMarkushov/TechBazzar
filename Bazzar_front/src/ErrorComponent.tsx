import React from 'react';

export interface ErrorProps {
    error: any,
    success: boolean,
    showSuccess: boolean,
    textIfSuccess: string,
}

export function ErrorComponent(props: ErrorProps) {
    return (
        <div>
            {
                props.error !== "" && <div className="alert alert-danger" role="alert">{props.error}</div>
            }
            {
                props.showSuccess && props.success && <div className="alert alert-success" role="alert">{props.textIfSuccess}</div>
            }
        </div>
    )
}