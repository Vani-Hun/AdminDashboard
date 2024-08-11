import { useState, useEffect } from 'react';
import { fetchStatsData } from '../utils/api';

const useStatsData = (dateRange) => {
    const [data, setData] = useState({ labels: [], datasets: [] });
    const [keyMetrics, setKeyMetrics] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            setError(null);

            try {
                const result = await fetchStatsData(dateRange);
                const labels = result.map(item => item.date);
                const counts = result.map(item => item.count);

                setData({
                    labels,
                    datasets: [
                        {
                            label: 'Number of Videos Created',
                            data: counts,
                            backgroundColor: 'rgba(75,192,192,1)',
                            borderColor: 'rgba(75,192,192,1)',
                            borderWidth: 1,
                        },
                    ],
                });
                setKeyMetrics(result);
            } catch (error) {
                setError(error.message);
            } finally {
                setIsLoading(false);
            }
        };

        if (dateRange.start && dateRange.end) {
            fetchData();
        }
    }, [dateRange]);

    return { data, keyMetrics, isLoading, error };
};

export default useStatsData;