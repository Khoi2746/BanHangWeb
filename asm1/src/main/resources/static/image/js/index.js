
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

// Hàm hiển thị slide theo index
function showSlide(index) {
    // 1. Dừng tất cả video hiện tại và xóa class active
    slides.forEach((slide, i) => {
        slide.classList.remove('active');
        dots[i].classList.remove('active');
        videos[i].pause();
        videos[i].currentTime = 0; // Reset về đầu video
    });

    // 2. Cập nhật index vòng lặp
    if (index >= slides.length) currentIndex = 0;
    else if (index < 0) currentIndex = slides.length - 1;
    else currentIndex = index;

    // 3. Kích hoạt slide và video mới
    slides[currentIndex].classList.add('active');
    dots[currentIndex].classList.add('active');
    
    // Tự động chạy video của slide hiện tại
    const activeVideo = videos[currentIndex];
    activeVideo.play().catch(error => {
        console.log("Auto-play was prevented. Waiting for user interaction.");
    });
}

// Lắng nghe sự kiện video kết thúc để tự động chuyển slide
videos.forEach((video, index) => {
    video.addEventListener('ended', () => {
        showSlide(currentIndex + 1);
    });
});

// Nút Next/Prev
document.getElementById('nextBtn').addEventListener('click', () => showSlide(currentIndex + 1));
document.getElementById('prevBtn').addEventListener('click', () => showSlide(currentIndex - 1));

// Click vào Dot
function currentSlide(index) {
    showSlide(index);
}

// Xử lý Play/Pause
const playPauseBtn = document.getElementById('playPauseBtn');
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

// Chạy slide đầu tiên khi trang load xong
window.onload = () => {
    showSlide(0);
};

