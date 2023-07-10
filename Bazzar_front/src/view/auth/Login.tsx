import {HeaderLinkAuth} from "../HeaderLinkAuth";

export function Login() {
    return (
        <div className="d-flex justify-content-center flex-grow-1" style={{height: "70vh"}}>
            <div className="d-flex flex-column justify-content-center">
                <div className="d-flex flex-column align-items-center">
                    <h5>Войдите или зарегистрируйтесь.</h5>
                    <HeaderLinkAuth isHeader={false}/>
                </div>
            </div>
        </div>
    )
}