import {useSearch} from "../context/Search";
import React, {useState} from "react";
import {primary} from "../Colors";

export function SearchTab() {
    const search = useSearch();
    const [searchValue, setSearchValue] = useState(search.search);

    function handleClick() {
        if (searchValue.length > 1) {
            search.changeSearch(searchValue);
        }
    }

    function changeSearch(event: React.FormEvent<HTMLInputElement>) {
        if (event.currentTarget.value.length > 1) {
            setSearchValue(event.currentTarget.value);
        } else {
            search.changeSearch("");
        }
    }

    const onkeydown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === "Enter") {
            handleClick();
        }
    }

    return (
        <div className="d-flex w-100">
            <div className="input-group">
                <input style={{borderColor: primary}} onKeyDown={onkeydown} className="form-control"
                       onChange={(event) => changeSearch(event)} type="search" placeholder="Search"
                       aria-label="Search"/>
                <button style={{backgroundColor: primary}} className="btn text-white" onClick={() => handleClick()}
                        type="submit">Поиск
                </button>
            </div>
        </div>
    )
}