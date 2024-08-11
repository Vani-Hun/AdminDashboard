import { useState, useCallback } from 'react';
import debounce from 'lodash/debounce';

const useSearch = (searchFunction) => {
    const [search, setSearch] = useState('');
    const [isSearching, setIsSearching] = useState(false);

    const debouncedSearch = useCallback(
        debounce((value) => {
            searchFunction(value);
            setIsSearching(false);
        }, 300),
        [searchFunction]
    );

    const handleSearch = useCallback((event) => {
        const value = event.target.value;
        setSearch(value);
        setIsSearching(true);
        debouncedSearch(value);
    }, [debouncedSearch]);

    return { search, handleSearch, isSearching };
};

export default useSearch;