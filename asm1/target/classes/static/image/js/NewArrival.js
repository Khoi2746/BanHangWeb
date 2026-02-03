
        function openSearch() {
            document.getElementById("searchOverlay").classList.add("active");
            document.getElementById("bigSearchInput").focus();
        }
        function closeSearch() {
            document.getElementById("searchOverlay").classList.remove("active");
        }
        document.addEventListener("DOMContentLoaded", function () {
            const smallSearchInput = document.querySelector(".search-container input");
            if (smallSearchInput) {
                smallSearchInput.addEventListener("click", openSearch);
            }
        });

        const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
const videos = document.querySelectorAll('.bg-video');
let currentIndex = 0;
//SIDE BAR FILTERS
document.addEventListener("DOMContentLoaded", function() {
        
        // 1. Xử lý nút Hide Filters / Show Filters
        const btnHideFilter = document.getElementById('btnHideFilter');
        const sidebar = document.getElementById('mainSidebar');
        
        if (btnHideFilter && sidebar) {
            btnHideFilter.addEventListener('click', function() {
                // Thêm/Bỏ class 'hidden' cho sidebar
                sidebar.classList.toggle('hidden');
                
                // Đổi text và icon của nút bấm
                if (sidebar.classList.contains('hidden')) {
                    this.innerHTML = 'Show Filters <i class="fa-solid fa-sliders"></i>';
                } else {
                    this.innerHTML = 'Hide Filters <i class="fa-solid fa-sliders"></i>';
                }
            });
        }

        // 2. Xử lý Accordion (Mở lên mở xuống từng mục filter)
        const filterHeaders = document.querySelectorAll('.filter-header');
        
        filterHeaders.forEach(header => {
            header.addEventListener('click', function() {
                // Tìm nội dung (div filter-content) ngay bên dưới header
                const content = this.nextElementSibling;
                const arrow = this.querySelector('.arrow');
                
                // Toggle class để đóng/mở
                content.classList.toggle('collapsed');
                
                // Xoay mũi tên
                if (arrow) arrow.classList.toggle('rotated');
            });
        });
    });