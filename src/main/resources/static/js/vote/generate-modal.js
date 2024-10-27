function getModalInfo(id) {
    fetch(`api/student/vote/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response => {
        if (response.ok) {
            return response.json(); // JSON 데이터 반환
        } else {
            throw new Error('투표 생성에 실패했습니다: ' + response.statusText);
        }
    })
        .then(data => {
            generateModal(data);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}

function generateModal(data) {
    // 이전 모달 내용 제거
    const existingModal = document.querySelector('.modal');
    if (existingModal) {
        existingModal.remove();
    }

    const modal = document.createElement('div');
    modal.className = 'modal';

    modal.innerHTML = `
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>참여 인원</h2>
            <ul id="voteList"></ul> <!-- 리스트를 추가할 곳 -->
        </div>
    `;

    const voteList = modal.querySelector('#voteList');

    fetch('/api/student')
        .then(response => response.json())
        .then(userData => {
            if (userData && userData.role === "ROLE_ADMIN") {

                data.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `${item.studentId} ${item.majority} ${item.name} / ${item.category}`; // 데이터에서 이름 가져오기
                    voteList.appendChild(listItem);
                });

            } else{

                // data를 통해 리스트 항목 생성
                data.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `${item.majority} ${item.name} / ${item.category}`; // 데이터에서 이름 가져오기
                    voteList.appendChild(listItem);
                });

            }
        })
        .catch(error => console.error('Error fetching user status:', error));


    // 모달을 페이지에 추가
    document.body.appendChild(modal);
    modal.style.display = "block"; // 모달 표시

    // 모달 닫기 기능
    const closeButton = modal.querySelector('.close');
    closeButton.onclick = function () {
        modal.style.display = "none";
        modal.remove(); // 모달 제거
    }

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
            modal.remove(); // 모달 제거
        }
    }
}

// 카드 생성 시 모달 열기
function openVoteModal(uuid) {
    event.stopPropagation();
    getModalInfo(uuid); // UUID를 사용하여 데이터 요청
}
