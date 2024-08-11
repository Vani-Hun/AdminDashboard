import React from 'react';
import { Bar } from 'react-chartjs-2';
import PropTypes from 'prop-types';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, PointElement, LineElement, ArcElement } from 'chart.js'; ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, PointElement, LineElement, ArcElement);
const BarChart = ({ data }) => {
    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Statistics',
            },
        },
        scales: {
            x: {
                type: 'category',
                title: {
                    display: true,
                    text: 'Date',
                },
            },
            y: {
                type: 'linear',
                title: {
                    display: true,
                    text: 'Count',
                },
            },
        },
    };

    return <Bar data={data} options={options} />;
};

BarChart.propTypes = {
    data: PropTypes.shape({
        labels: PropTypes.arrayOf(PropTypes.string),
        datasets: PropTypes.arrayOf(PropTypes.shape({
            label: PropTypes.string,
            data: PropTypes.arrayOf(PropTypes.number),
            backgroundColor: PropTypes.string,
            borderColor: PropTypes.string,
            borderWidth: PropTypes.number,
        })),
    }).isRequired,
};

export default React.memo(BarChart);