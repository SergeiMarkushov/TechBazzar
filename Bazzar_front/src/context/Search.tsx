import React, {useCallback, useMemo, useState} from "react";

export interface SearchContextType {
    search: string,
    changeSearch: (search: string) => void;
}

const SearchContext = React.createContext<SearchContextType>("" as unknown as SearchContextType);

export function SearchProvider({children}: { children: React.ReactNode }) {
    const [search, setSearch] = useState<string>("");
    const changeSearch = useCallback((newSearch: string) => {
        setSearch(newSearch);
    }, []);

    const value = useMemo(() => ({search, changeSearch}), [search, changeSearch]);
    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
}

export function useSearch() {
    return React.useContext(SearchContext);
}