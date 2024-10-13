let socket;  // WebSocket 객체
let inactivityTimeout = 600000; // 10분(600,000 ms)
let inactivityTimer; // 타이머 변수

// WebSocket 연결 설정 함수
function openWebSocket() {
    if (!socket || socket.readyState === WebSocket.CLOSED) {
        socket = new WebSocket("ws://localhost:8080/ws/student/vote");

        socket.onopen = function(event) {
            console.log("WebSocket 연결이 열렸습니다.");
        };

        socket.onmessage = function(event) {
            console.log("서버로부터 메시지 수신:", event.data);
            // 서버에서 수신한 데이터를 화면에 표시하거나 다른 작업 수행
        };

        socket.onclose = function(event) {
            console.log("WebSocket 연결이 닫혔습니다.");
            // 연결이 끊기면 자동 재연결
            inactivityTimer = setTimeout(openWebSocket, 3000); // 3초 후 재연결 시도
        };

        socket.onerror = function(error) {
            console.error("WebSocket 오류:", error);
        };
    }
}

// WebSocket 연결 종료 함수
function closeWebSocket() {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.close();
        console.log("사용자 비활성화로 WebSocket 연결을 종료했습니다.");
    }
}

// 비활성 타이머 리셋 함수
function resetInactivityTimer() {
    clearTimeout(inactivityTimer); // 기존 타이머 취소
    openWebSocket(); // 활동이 감지되면 WebSocket 연결 (연결이 이미 되어있다면 무시)

    // 10분 후 비활성 타이머를 초기화하여 WebSocket 연결 종료
    inactivityTimer = setTimeout(() => {
        closeWebSocket();
    }, inactivityTimeout);
}

// 사용자 활동 감지 이벤트 리스너 설정
window.addEventListener("mousemove", resetInactivityTimer);
window.addEventListener("keypress", resetInactivityTimer);
window.addEventListener("click", resetInactivityTimer);

// 모바일 터치 이벤트 리스너 추가
window.addEventListener("touchstart", resetInactivityTimer);
window.addEventListener("touchend", resetInactivityTimer);
window.addEventListener("scroll", resetInactivityTimer);

// 페이지가 로드될 때 처음으로 WebSocket 연결 및 타이머 시작
resetInactivityTimer();
