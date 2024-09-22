const adminLoginButton = document.getElementById('admin-login'); // 로그인 버튼 가져오기

adminLoginButton.addEventListener('click', function (event) {
    event.preventDefault(); // 기본 행동 방지 (예: 폼 제출)

    const username = document.getElementById('username').value; // 사용자 이름 가져오기
    const password = document.getElementById('password').value; // 비밀번호 가져오기

    fetch('/admin/login', { // 서버에 로그인 요청
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: username, password: password }), // JSON 형식으로 데이터 전송
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message); // 에러 메시지 던지기
                });
            }
            return response.json(); // 성공 시 JSON 데이터 반환
        })
        .then(data => {
            alert("관리자 계정에 로그인 하였습니다."); // 성공 메시지
            window.location.href = '/'; // 성공 시 리다이렉트
        })
        .catch(error => {
            alert("아이디 또는 비밀번호가 틀렸습니다."); // 실패 메시지
        });
});