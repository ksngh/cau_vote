
document.addEventListener("DOMContentLoaded", function () {
    fetch(window.location.href)
        .then(response => {
            if (!response.ok) { // 401 또는 403 에러 감지
                return response.json().then(errorData => {
                    console.error("🚨 에러 발생:", errorData);
                    if (errorData.status === 401 || errorData.status === 403) {
                        alert("인증이 필요합니다. 로그인 페이지로 이동합니다.");
                        window.location.href = "/"; // 🔥 자동 리다이렉트
                    }
                });
            }
        })
        .catch(error => console.error("🚨 요청 중 오류 발생:", error));
});

