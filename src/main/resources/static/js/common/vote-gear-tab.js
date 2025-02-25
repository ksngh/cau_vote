function redirectToPage(page) {
    if (page === 'train') {
        window.location.href = "/"; // 훈련 신청 페이지로 이동
    } else if (page === 'rent') {
        window.location.href = "/gear"; // 장비 대여 페이지로 이동
    }
}

// 현재 페이지에 따라 active 클래스 추가
document.addEventListener("DOMContentLoaded", function () {
    const currentPage = window.location.pathname;

    // "/" 경로는 훈련 신청 페이지로 간주
    if (currentPage === "/" || currentPage.includes("/post")) {
        document.getElementById("trainTab").classList.add("active");
    } else if (currentPage.includes("/gear")) {
        document.getElementById("rentTab").classList.add("active");
    }
});