// ==========================================
// 1. CÁC HÀM GLOBAL (Được gọi từ HTML onclick)
// ==========================================

function openSearch() {
    document.getElementById("searchOverlay").classList.add("active");
    // Focus vào ô input to để người dùng gõ được luôn
    setTimeout(() => {
        const bigInput = document.getElementById("bigSearchInput");
        if(bigInput) bigInput.focus();
    }, 100); 
}

function closeSearch() {
    document.getElementById("searchOverlay").classList.remove("active");
}

// Hàm này được gọi từ các chấm tròn (dots) trong HTML
function currentSlide(index) {
    showSlide(index);
}


// ==========================================
// 2. KHỞI TẠO KHI DOM LOAD XONG
// ==========================================
document.addEventListener("DOMContentLoaded", function () {

    // --- A. XỬ LÝ SỰ KIỆN MỞ SEARCH TỪ MENU NHỎ ---
    const smallSearchInput = document.querySelector(".search-container input");
    if (smallSearchInput) {
        smallSearchInput.addEventListener("click", openSearch);
    }


    // --- B. XỬ LÝ TÌM KIẾM (SEARCH) ---
    const bigSearchInput = document.getElementById('bigSearchInput');
    if (bigSearchInput) {
        // Dùng 'keydown' để bắt phím Enter nhạy hơn
        bigSearchInput.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault(); 
                
                const keyword = bigSearchInput.value.trim();
                
                if (keyword.length > 0) {
                    // Chuyển hướng sang trang tìm kiếm
                    const url = `/search?keyword=${encodeURIComponent(keyword)}`;
                    window.location.href = url;
                }
            }
        });
    }


    // --- C. XỬ LÝ SLIDER VIDEO ---
    // Khởi tạo slider ngay khi DOM load xong
    initSlider();
});


// ==========================================
// 3. LOGIC SLIDER (Tách hàm cho gọn)
// ==========================================

const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
const videos = document.querySelectorAll('.bg-video');
let currentIndex = 0;

function showSlide(index) {
    if (!slides.length) return; // Nếu không có slide thì thoát

    // 1. Dừng tất cả video & xóa active
    slides.forEach((slide, i) => {
        slide.classList.remove('active');
        if(dots[i]) dots[i].classList.remove('active');
        if(videos[i]) {
            videos[i].pause();
            videos[i].currentTime = 0;
        }
    });

    // 2. Cập nhật index vòng tròn
    if (index >= slides.length) currentIndex = 0;
    else if (index < 0) currentIndex = slides.length - 1;
    else currentIndex = index;

    // 3. Kích hoạt slide mới
    slides[currentIndex].classList.add('active');
    if(dots[currentIndex]) dots[currentIndex].classList.add('active');

    // 4. Play video
    const activeVideo = videos[currentIndex];
    if (activeVideo) {
        activeVideo.play().catch(error => {
            console.log("Auto-play prevented:", error);
        });
    }
}

function initSlider() {
    if (!slides.length) return;

    // Lắng nghe video kết thúc -> Next slide
    videos.forEach((video) => {
        video.addEventListener('ended', () => {
            showSlide(currentIndex + 1);
        });
    });

    // Nút Next/Prev
    const nextBtn = document.getElementById('nextBtn');
    const prevBtn = document.getElementById('prevBtn');
    if(nextBtn) nextBtn.addEventListener('click', () => showSlide(currentIndex + 1));
    if(prevBtn) prevBtn.addEventListener('click', () => showSlide(currentIndex - 1));

    // Nút Play/Pause
    const playPauseBtn = document.getElementById('playPauseBtn');
    if (playPauseBtn) {
        playPauseBtn.addEventListener('click', () => {
            const activeVideo = videos[currentIndex];
            const icon = playPauseBtn.querySelector('i');
            
            if (activeVideo.paused) {
                activeVideo.play();
                icon.classList.replace('fa-play', 'fa-pause');
            } else {
                activeVideo.pause();
                icon.classList.replace('fa-pause', 'fa-play');
            }
        });
    }

    // Chạy slide đầu tiên
    showSlide(0);
}