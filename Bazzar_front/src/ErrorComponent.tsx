import React from 'react';
import {Button} from "react-bootstrap";
import {getChatWithSupportsOpenedSvg} from "./Svg";
import {useError} from "./auth/ErrorProvider";

export function ErrorComponent() {
    const error = useError();

    return (
        <div style={{position: "absolute", bottom: "20px", left: "50%", transform: "translateX(-50%)", maxWidth: "1200px"}}>
            {
                error.show &&
                <div className="mt-2">
                    <div>
                        {
                            error.error !== "" && <div className="alert alert-danger" role="alert">
                                <div className="d-flex justify-content-between align-items-center">
                                    <span className="me-3">{error.error}</span>
                                    <Button onClick={() => error.setShow(false)}
                                            className="btn btn-sm btn-danger">{getChatWithSupportsOpenedSvg(20, 20)}</Button>
                                </div>
                            </div>
                        }
                        {
                            error.showSuccess && error.success &&
                            <div className="alert alert-success" role="alert">
                                <div className="d-flex justify-content-between align-items-center">
                                    <span className="me-3">{error.textIfSuccess}</span>
                                    <Button onClick={() => error.setShow(false)}
                                            className="btn btn-sm btn-success">{getChatWithSupportsOpenedSvg(20, 20)}</Button>
                                </div>
                            </div>
                        }
                    </div>
                </div>
            }
        </div>
    )
}