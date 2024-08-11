import { useEffect } from 'react';

const useScript = (url, onload) => {
    console.log("onload:", onload)
    console.log("url:", url)
    useEffect(() => {
        // Tạo một phần tử script mới
        const script = document.createElement('script');

        // Đặt đường dẫn của script là URL được cung cấp
        script.src = url;

        // Đặt sự kiện onload của script thành hàm gọi lại được cung cấp
        script.onload = onload;

        // Thêm script vào phần head của tài liệu để bắt đầu tải
        document.head.appendChild(script);

        // Hàm dọn dẹp để loại bỏ script khi thành phần bị gỡ bỏ hoặc các phụ thuộc thay đổi
        return () => {
            document.head.removeChild(script);
        };
    }, [url, onload]); // Mảng phụ thuộc: chạy lại hiệu ứng nếu URL hoặc hàm onload thay đổi
};

export default useScript;
