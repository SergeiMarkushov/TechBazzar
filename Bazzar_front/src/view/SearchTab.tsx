import {useSearch} from "../context/Search";
import {useState} from "react";
import {primary} from "../Colors";

export function SearchTab() {
    let search = useSearch();
    let [searchValue, setSearchValue] = useState(search.search);

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

    return (
        <div className="d-flex w-100">
            <div className="input-group">
                <input style={{borderColor: primary}} className="form-control" onChange={(event) => changeSearch(event)} type="search" placeholder="Search"
                       aria-label="Search"/>
                <button style={{backgroundColor: primary}} className="btn text-white" onClick={() => handleClick()} type="submit">Search</button>
            </div>
        </div>
    )
}