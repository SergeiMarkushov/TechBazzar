import React from "react";

export interface SearchContextType {
    search: string,
    changeSearch: (search: string) => void;
}

const SearchContext = React.createContext<SearchContextType>(null!);

export function SearchProvider({children}: { children: React.ReactNode }) {
    const [search, setSearch] = React.useState<string>("");
    const changeSearch = (search: string) => {
        setSearch(search);
    }
    const value = {search, changeSearch};
    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
}

export function useSearch() {
    return React.useContext(SearchContext);
}