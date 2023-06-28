import React, {Dispatch, useEffect, useState} from "react";
import {primary} from "../../Colors";
import {defaultFilter} from "../../empty";
import {Filter} from "../../newInterfaces";

interface CatalogFilterProps {
    filter: Filter,
    filterHandler: (filter: Filter) => void
}

export function CatalogFilter(props: CatalogFilterProps) {
    const [minPrice, setMinPrice] = useState(defaultFilter.minPrice)
    const [maxPrice, setMaxPrice] = useState(defaultFilter.maxPrice)
    const [isOpen, setIsOpen] = useState(false);

    const handleButtonClick = () => {
        setIsOpen(!isOpen);
    };

    useEffect(() => {
            props.filterHandler({
                minPrice: minPrice,
                maxPrice: maxPrice,
            });

        }, [minPrice, maxPrice]
    );

    return (
        <div className="text-center d-flex flex-column ms-2"
             style={{position: 'relative', display: 'inline-block', zIndex: '9999'}}>
            <button style={{backgroundColor: primary}} className="btn btn-sm shadow-sm text-white rounded" onClick={handleButtonClick}>Цена</button>
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
                        <span style={{backgroundColor: primary}} className="input-group-text text-white">От</span>
                        <input style={{borderColor: primary}} type="number" value={minPrice} className="form-control" min={0}
                               onChange={(event) => filterChanged(event.currentTarget.valueAsNumber, defaultFilter.minPrice, setMinPrice)}/>
                    </div>
                    <div className="input-group input-group-sm m-1">
                        <span style={{backgroundColor: primary}} className="input-group-text text-white">До</span>
                        <input style={{borderColor: primary}} type="number" value={maxPrice} className="form-control" min={0}
                               onChange={(event) => filterChanged(event.currentTarget.valueAsNumber, defaultFilter.maxPrice, setMaxPrice)}/>
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