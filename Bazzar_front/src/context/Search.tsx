import React, {useMemo} from "react";

export interface SearchContextType {
    search: string,
    changeSearch: (search: string) => void;
}

let SearchContext = React.createContext<SearchContextType>(null!);

export function SearchProvider({children}: { children: React.ReactNode }) {
    let [search, setSearch] = React.useState<string>("");
    let changeSearch = (search: string) => {
        setSearch(search);
    }
    let value = {search, changeSearch};
    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
}

export function useSearch() {
    return React.useContext(SearchContext);
}