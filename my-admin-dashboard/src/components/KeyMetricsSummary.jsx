import React, { useMemo } from 'react';
import PropTypes from 'prop-types';

const KeyMetricsSummary = ({ data }) => {
    const {
        totalVideos,
        totalLikes,
        averageLikes,
        totalComments,
        averageComments
    } = useMemo(() => {
        const totalVideos = data.reduce((total, item) => total + item.count, 0);
        const totalLikes = data.reduce((total, item) => total + item.total_likes, 0);
        const averageLikes = totalVideos ? totalLikes / totalVideos : 0;
        const totalComments = data.reduce((total, item) => total + item.total_comments, 0);
        const averageComments = totalVideos ? totalComments / totalVideos : 0;
        return { totalVideos, totalLikes, averageLikes, totalComments, averageComments };
    }, [data]);

    return (
        <div className="key-metrics-summary">
            <h2>Key Metrics Summary</h2>
            <div className="metrics-row">
                <p>Total Videos: {totalVideos}</p>
            </div>
            <div className="metrics-row">
                <p>Total Likes: {totalLikes}</p>
                <p>&amp;&amp;</p>
                <p>Average Likes per Video: {averageLikes.toFixed(2)}</p>
            </div>
            <div className="metrics-row">
                <p>Total Comments: {totalComments}</p>
                <p>&amp;&amp;</p>
                <p>Average Comments per Video: {averageComments.toFixed(2)}</p>
            </div>
        </div>
    );
};

KeyMetricsSummary.propTypes = {
    data: PropTypes.arrayOf(PropTypes.shape({
        count: PropTypes.number,
        total_likes: PropTypes.number,
        total_comments: PropTypes.number,
    })).isRequired,
};

export default React.memo(KeyMetricsSummary);