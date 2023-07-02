import {AxiosResponse} from "axios";
import React, {Dispatch, useEffect, useRef, useState} from "react";
import {primary} from "../../Colors";
import {apiGetOrganizationTitles} from "../../api/OrganizationApi";
import {defaultFilter} from "../../empty";
import {Filter} from "../../newInterfaces";

interface CatalogFilterProps {
    filter: Filter,
    filterHandler: (filter: Filter) => void
}

export function BuyerFilter(props: CatalogFilterProps) {
    const [organizationTitle, setOrganizationTitle] = useState(defaultFilter.organizationTitle)
    const [organizations, setOrganizations] = useState<Array<string>>([] as Array<string>)
    const [isOpen, setIsOpen] = useState(false);
    const myRef = useRef<HTMLDivElement>(null);

    const handleButtonClick = () => {
        setIsOpen(!isOpen);
    };

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (myRef.current && !myRef.current.contains(event.target as Node)) {
                setIsOpen(false)
            }
        };

        document.addEventListener('mousedown', handleClickOutside);

        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    useEffect(() => {
        apiGetOrganizationTitles().then((response: AxiosResponse<Array<string>>) => {
            setOrganizations(response.data)
        }).catch((error) => {
            console.log(error);
        });
    }, [isOpen])

    useEffect(() => {
        props.filterHandler({
            minPrice: props.filter.minPrice,
            maxPrice: props.filter.maxPrice,
            organizationTitle: organizationTitle
        });
    }, [organizationTitle]);

    return (
        <div ref={myRef} className="text-center d-flex flex-column ms-2"
             style={{position: 'relative', display: 'inline-block', zIndex: '9999'}}>
            <button style={{backgroundColor: primary}} className="btn btn-sm shadow-sm text-white rounded"
                    onClick={handleButtonClick}>Продавец
            </button>
            {isOpen && (
                <div className="bg-light rounded shadow-sm" style={{
                    position: 'absolute',
                    top: '100%',
                    left: 0,
                    backgroundColor: '#f9f9f9',
                    padding: '10px',
                    zIndex: '9999',
                    minWidth: "10em"
                }}>
                    <div className="input-group input-group-sm m-1">
                        <select style={{borderColor: primary}} className="form-select" value={organizationTitle}
                                onChange={(event) => setOrganizationTitle(event.currentTarget.value)}>
                            <option value={defaultFilter.organizationTitle}>Все</option>
                            {organizations.map((organization) => {
                                return <option key={organization} value={organization}>{organization}</option>
                            })}
                        </select>
                    </div>
                </div>
            )}
        </div>
    )
}

function filterChanged(newValue: number, oldValue: number, state: Dispatch<React.SetStateAction<number>>) {
    if (isNaN(newValue)) {
        state(oldValue);
    } else {
        state(newValue);
    }
}