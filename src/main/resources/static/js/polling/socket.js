let socket;  // SockJS 객체
let stompClient;  // Stomp 클라이언트 객체
const inactivityTimeout = 600000; // 10분 (600,000 ms)
let inactivityTimer; // 타이머 변수

// WebSocket 연결 설정 함수
function openWebSocket() {
    if (!stompClient || !stompClient.connected) {
        socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);
        stompClient.debug = null; // 로그 비활성화
        stompClient.connect({}, onConnected, onError);
    }
}

// WebSocket 연결 성공 시 실행되는 함수
function onConnected(frame) {

    // 투표 결과 메시지 구독
    stompClient.subscribe('/user/topic/vote/result', onVoteResultReceived);

    // 투표 수 업데이트 구독
    stompClient.subscribe(`/topic/vote/count`, onVoteCountUpdated);

    stompClient.subscribe('/user/topic/errors', (errorMessage) => {
        const errorContent = errorMessage.body;
        alert(errorContent); // 사용자에게 에러 메시지 표시
    });
}

// WebSocket 연결 실패 시 실행되는 함수
function onError(error) {
    console.error("WebSocket 오류:", error);
    // 연결이 끊기면 3초 후에 자동으로 재연결 시도
    inactivityTimer = setTimeout(openWebSocket, 3000);
}

// 투표 결과 메시지를 처리하는 함수
function onVoteResultReceived(messageDTO) {
    alert(messageDTO.body);  // 결과 메시지를 사용자에게 알림
}

// 투표 수 업데이트 메시지를 처리하는 함수
function onVoteCountUpdated(numberDTO) {
    const data = JSON.parse(numberDTO.body);
    const card = document.querySelector(`[data-id="${data.votePk}"]`);
    // card 요소에서 id가 "attendance-number"인 요소를 찾아 텍스트 업데이트
    if (card) {
        const attendanceElement = card.querySelector("#attendance-number");
        if (attendanceElement) {
            attendanceElement.innerText = `참여 인원 : ${data.number}/${data.limitPeople}`;
        }
    }
}

// WebSocket 연결 종료 함수
function closeWebSocket() {
    if (stompClient && stompClient.connected) {
        stompClient.disconnect(() => {
            console.log("사용자 비활성화로 WebSocket 연결을 종료했습니다.");
        });
    }
}

// 비활성 타이머 리셋 함수
function resetInactivityTimer() {
    clearTimeout(inactivityTimer); // 기존 타이머 취소
    openWebSocket(); // 활동이 감지되면 WebSocket 연결 (이미 연결된 경우 무시)

    // 10분 후 비활성 타이머를 초기화하여 WebSocket 연결 종료
    inactivityTimer = setTimeout(closeWebSocket, inactivityTimeout);
}

// 투표 요청 전송 함수
function sendVote(voteId) {
    const selectedCategory = document.querySelector('input[name="category"]:checked').value;
    const attendanceData = {
        category: selectedCategory  // 선택된 카테고리 정보 추가
    };

    if (stompClient && stompClient.connected) {
        stompClient.send(`/app/vote/${voteId}`, {}, JSON.stringify(attendanceData));
    }
}

function cancel(voteId) {
    if (stompClient && stompClient.connected) {
        stompClient.send(`/app/vote/cancel/${voteId}`, {}, JSON.stringify({}));
    }
}

// 사용자 활동 감지 이벤트 리스너 설정
function setupUserActivityListeners() {
    const events = ["mousemove", "keypress", "click", "touchstart", "touchend", "scroll"];
    events.forEach(event => window.addEventListener(event, resetInactivityTimer));
}

// 페이지가 로드될 때 WebSocket 연결 및 타이머 시작
window.onload = () => {
    setupUserActivityListeners();
    resetInactivityTimer();
};

