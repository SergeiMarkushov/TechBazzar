import {Formik} from "formik";
import {ProductForm} from "../../ProductForm";
import {useEffect, useState} from "react";
import {AxiosError, AxiosResponse} from "axios";
import {useParams} from "react-router-dom";
import {emptyProductNew} from "../../../empty";
import {apiCreateOrUpdateProductNew, apiGetProductByIdNew} from "../../../api/ProductApi";
import {ErrorMessage, ProductNew} from "../../../newInterfaces";

export function AdminMenuProductChangerForm() {
    let [error, setError] = useState<any>("")
    let [success, setSuccess] = useState<boolean>(false)
    const [product, setProduct] = useState(emptyProductNew);
    const [isLoad, setLoad] = useState(false);
    let {id} = useParams();

    useEffect(() => {
            if (id !== undefined && !isLoad) {
                apiGetProductByIdNew(Number(id))
                    .then((pr: AxiosResponse<ProductNew>) => {
                        if (pr.data !== undefined) {
                            setProduct(pr.data)
                            setLoad(true);
                        }
                    })
            }
        }, [product]
    );

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={product}
                        enableReinitialize={true}
                        onSubmit={(values: ProductNew) => {
                            setSuccess(false)
                            apiCreateOrUpdateProductNew(values).then(r => {
                                setSuccess(true)
                            }).catch((e: AxiosError<ErrorMessage>) => {
                                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                if (data !== undefined) {
                                    setError(data.data.message);
                                }
                            })
                        }}>
                    <ProductForm product={product} error={error} success={success}/>
                </Formik>
            </div>
        </div>
    )
}