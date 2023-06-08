import {Formik} from "formik";
import {emptyOrganizationCreate} from "../../../empty";
import {ErrorMessage, OrganizationCreate} from "../../../newInterfaces";
import {OrganizationCreateForm} from "../admin/OrganizationCreateForm";
import {apiCreateOrganization} from "../../../api/OrganizationApi";
import React, {useState} from "react";
import {AxiosError, AxiosResponse} from "axios";
import {useAuth} from "../../../auth/Auth";

export function CreateOrganization() {
    const [file, setFile] = useState<File | null>(null)
    const [error, setError] = useState<any>("")
    const [success, setSuccess] = useState<boolean>(false)
    const auth = useAuth()

    const onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            setFile(event.target.files[0])
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={emptyOrganizationCreate}
                        onSubmit={(values: OrganizationCreate) => {
                            setSuccess(false)
                            const formData = new FormData()
                            formData.append("owner", auth.user.email)
                            formData.append("name", values.name)
                            formData.append("description", values.description)
                            if (file !== null)
                                formData.append("companyImage", file)
                            apiCreateOrganization(formData).then(r => {
                                setSuccess(true)
                            }).catch((e: AxiosError<ErrorMessage>) => {
                                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                if (data !== undefined) {
                                    setError(data.data.message);
                                }
                            })
                        }}>
                    <OrganizationCreateForm onChoseFile={onChoseFile} error={error} success={success}/>
                </Formik>
            </div>
        </div>
    )
}