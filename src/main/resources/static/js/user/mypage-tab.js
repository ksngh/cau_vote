function redirectToPage(page) {
    if (page === 'train') {
        window.location.href = "/mypage/vote"; // 훈련 신청 페이지로 이동
    } else if (page === 'rent') {
        window.location.href = "/mypage/gear"; // 장비 대여 페이지로 이동
    }
}

// 현재 페이지에 따라 active 클래스 추가
document.addEventListener("DOMContentLoaded", function () {
    const currentPage = window.location.pathname;

    // "/" 경로는 훈련 신청 페이지로 간주
    if (currentPage === "/mypage/vote") {
        document.getElementById("trainTab").classList.add("active");
    } else if (currentPage.includes("/gear")) {
        document.getElementById("rentTab").classList.add("active");
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const trainTab = document.getElementById("trainTab");
    const rentTab = document.getElementById("rentTab");

    // 텍스트 변경
    trainTab.textContent = "나의 신청 내역";
    rentTab.textContent = "나의 대여 내역";

    // 글자 크기 80%로 조정
    trainTab.style.fontSize = "80%";
    rentTab.style.fontSize = "80%";
});

