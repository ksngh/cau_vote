// 모달 요소와 버튼 요소
const modal = document.getElementById("myModal");
const btn = document.getElementById("openModal");
const span = document.getElementsByClassName("close")[0];

// 버튼 클릭 시 모달 열기
btn.onclick = function () {
    modal.style.display = "block";
}

// 닫기 버튼 클릭 시 모달 닫기
span.onclick = function () {
    modal.style.display = "none";
}

// 모달 외부 클릭 시 모달 닫기
window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}