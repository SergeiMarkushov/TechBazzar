import {Formik} from "formik";
import {ProductForm} from "../../ProductForm";
import {emptyProductCreateNew} from "../../../empty";
import {ErrorMessage, ProductCreateNew} from "../../../newInterfaces";
import {apiCreateOrUpdateProductNew} from "../../../api/ProductApi";
import {AxiosError, AxiosResponse} from "axios";
import {useState} from "react";

export function ProductCreator() {
    let [error, setError] = useState<any>("")
    let [success, setSuccess] = useState<boolean>(false)
    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={emptyProductCreateNew}
                        onSubmit={(values: ProductCreateNew) => {
                            setSuccess(false)
                            apiCreateOrUpdateProductNew(values).then(r => {
                                setSuccess(true)
                                setError("")
                            }).catch((e: AxiosError<ErrorMessage>) => {
                                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                if (data !== undefined) {
                                    setError(data.data.message);
                                }
                            })
                        }}>
                    <ProductForm product={emptyProductCreateNew} error={error} success={success}/>
                </Formik>
            </div>
        </div>
    )
}