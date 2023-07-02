import {useKeycloak} from "@react-keycloak/web";
import {AxiosError, AxiosResponse} from "axios";
import {Formik} from "formik";
import React, {useState} from "react";
import {MAX_FILE_SIZE} from "../../../CONST";
import {apiCreateOrganization} from "../../../api/OrganizationApi";
import {useError} from "../../../auth/ErrorProvider";
import {emptyOrganizationCreate} from "../../../empty";
import {ErrorMessage, OrganizationCreate} from "../../../newInterfaces";
import {OrganizationCreateForm} from "./OrganizationCreateForm";

export function CreateOrganization() {
    const [file, setFile] = useState<File | null>(null)
    const {keycloak, initialized} = useKeycloak();
    const [email, setEmail] = React.useState<string>(keycloak?.tokenParsed?.email ?? "");
    const error = useError();

    const onChoseFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const file = event.target.files[0];
            if (file.size > MAX_FILE_SIZE) {
                event.target.value = "";
                error.setErrors("Картинка должна быть меньше 5 мб", false, false, "");
                error.setShow(true)
            } else {
                setFile(file);
            }
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="container-fluid m-2"
                 style={{maxWidth: "50rem"}}>
                <Formik initialValues={emptyOrganizationCreate}
                        onSubmit={(values: OrganizationCreate) => {
                            const formData = new FormData()
                            formData.append("owner", email)
                            formData.append("name", values.name)
                            formData.append("description", values.description)
                            if (file !== null)
                                formData.append("companyImage", file)
                            apiCreateOrganization(formData).then(() => {
                                error.setErrors("", true, true, "Организация отправлена на модерацию");
                                error.setShow(true)
                            }).catch((e: AxiosError<ErrorMessage>) => {
                                const data: AxiosResponse<ErrorMessage> | undefined = e.response;
                                if (data !== undefined) {
                                    error.setErrors(data.data.message, false, false, "");
                                    error.setShow(true)
                                }
                            })
                        }}>
                    <OrganizationCreateForm onChoseFile={onChoseFile}/>
                </Formik>
            </div>
        </div>
    )
}