import { useState, useEffect, useCallback } from 'react';
import { fetchDatabase, fetchSearchData } from '../utils/api';

const useTableData = (itemsPerPage) => {
    const [tableData, setTableData] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [totalPages, setTotalPages] = useState(0);

    const fetchData = useCallback(async (database, page, searchValue) => {
        setIsLoading(true);
        try {
            const data = searchValue
                ? await fetchSearchData(database, itemsPerPage, page, searchValue)
                : await fetchDatabase(database, itemsPerPage, page);
            setTableData(data.content);
            setTotalPages(data.totalPages);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'An error occurred');
        } finally {
            setIsLoading(false);
        }
    }, [itemsPerPage]);

    return { tableData, isLoading, error, totalPages, fetchData };
};

export default useTableData;