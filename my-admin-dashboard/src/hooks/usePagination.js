import { useState, useCallback } from 'react';

const usePagination = (totalItems, itemsPerPage) => {
    const [currentPage, setCurrentPage] = useState(1);
    const totalPages = Math.ceil(totalItems / itemsPerPage);

    const handlePageChange = useCallback((page) => {
        setCurrentPage(page);
    }, []);

    return { currentPage, totalPages, handlePageChange };
};

export default usePagination;