import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useState} from "react";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateOrganization} from "../../../api/OrganizationApi";
import {useAuth} from "../../../auth/Auth";
import {emptyOrganizationCreate} from "../../../empty";
import {ErrorMessage, OrganizationCreate} from "../../../newInterfaces";
import {OrganizationCreateForm} from "./OrganizationCreateForm";

export function CreateOrganization() {
    const [file, setFile] = useState<File | null>(null)
    const [error, setError] = useState<string>("")
    const [success, setSuccess] = useState<boolean>(false)
    const auth = useAuth()

    const onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const file = event.target.files[0];
            if (file.size > MAX_FILE_SIZE) {
                event.target.value = "";
                setError("Картинка должна быть меньше 200 кб");
            } else {
                setFile(file);
                setError("");
            }
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
                            formData.append("owner", auth.user?.email ?? "")
                            formData.append("name", values.name)
                            formData.append("description", values.description)
                            if (file !== null)
                                formData.append("companyImage", file)
                            apiCreateOrganization(formData).then(() => {
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