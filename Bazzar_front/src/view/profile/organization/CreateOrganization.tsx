import {Formik} from "formik";
import {emptyOrganizationCreate} from "../../../empty";
import {ErrorMessage, OrganizationCreate} from "../../../newInterfaces";
import {OrganizationCreateForm} from "../admin/OrganizationCreateForm";
import {apiCreateOrganization} from "../../../api/OrganizationApi";
import React, {useState} from "react";
import {AxiosError, AxiosResponse} from "axios";

export function CreateOrganization() {
    let [file, setFile] = useState<File | null>(null)
    let [error, setError] = useState<any>("")
    let [success, setSuccess] = useState<boolean>(false)

    let onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
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
                            let formData = new FormData()
                            formData.append("owner", values.owner)
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