rendering();

function updateVote(voteData, id) {
    fetch(`/api/vote/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(voteData)
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // JSON 데이터 반환
            } else {
                throw new Error('투표 생성에 실패했습니다: ' + response.statusText);
            }
        })
        .then(result => {
            alert('투표가 수정되었습니다.');
            window.location.href = "/";
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}

function rendering() {
    const urlPath = window.location.pathname; // 전체 경로 가져오기
    const id = urlPath.split('/').pop();

    fetch(`/api/vote/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // JSON 데이터 반환
            } else {
                throw new Error('투표 생성에 실패했습니다: ' + response.statusText);
            }
        })
        .then(data => {
            const defaultTitle = data.title;
            const defaultContent = data.content;
            const defaultLimitPeople = data.limitPeople;
            const defaultStartDate = formatDateToLocal(data.startDate);
            const defaultSubmitDate = formatDateToLocal(data.submitDate);

            // 투표 수정 내용을 동적으로 생성
            const pollContainer = document.getElementById('poll-container');

            // 동적 HTML 내용
            const htmlContent = `
                <h1>투표 수정</h1>
                <label for="title">제목:</label>
                <input type="text" id="title" name="title" value="${defaultTitle}" required><br><br>

                <label for="content">내용:</label>
                <textarea id="content" name="content" required>${defaultContent}</textarea><br><br>

                <label for="limitPeople">참여 인원 제한:</label>
                <input type="number" id="limitPeople" name="limitPeople" value="${defaultLimitPeople}" required><br><br>

                <label for="startDate">시작 날짜:</label>
                <input type="datetime-local" id="startDate" name="startDate" value="${defaultStartDate}" required><br><br>

                <label for="submitDate">마감 날짜:</label>
                <input type="datetime-local" id="submitDate" name="submitDate" value="${defaultSubmitDate}" required><br><br>
                    <div style="text-align: right; margin-right: 15px">
                <button id="update-poll" type="button">수정</button>
                </div>
            `;

            // pollContainer에 HTML 내용 추가
            pollContainer.innerHTML = htmlContent;

            // 수정 버튼 클릭 이벤트 리스너 추가
            const button = document.getElementById("update-poll");
            button.addEventListener("click", () => {
                const title = document.getElementById('title').value;
                const content = document.getElementById('content').value;
                const limitPeople = document.getElementById('limitPeople').value;
                const startDateInput = document.getElementById('startDate').value;
                const submitDateInput = document.getElementById('submitDate').value;

                // 필드가 비어 있는지 확인
                if (!title) {
                    alert('제목을 입력해 주세요.');
                    document.getElementById('title').focus();
                    return;
                }
                if (!content) {
                    alert('내용을 입력해 주세요.');
                    document.getElementById('content').focus();
                    return;
                }
                if (!limitPeople) {
                    alert('참여 인원 제한을 입력해 주세요.');
                    document.getElementById('limitPeople').focus();
                    return;
                }
                if (!startDateInput) {
                    alert('시작 날짜를 입력해 주세요.');
                    document.getElementById('startDate').focus();
                    return;
                }
                if (!submitDateInput) {
                    alert('마감 날짜를 입력해 주세요.');
                    document.getElementById('submitDate').focus();
                    return;
                }

                const startDate = new Date(startDateInput + ":00").toISOString();
                const submitDate = new Date(submitDateInput + ":00").toISOString();

                if (startDate >= submitDate) {
                    alert('시작 날짜는 마감 날짜보다 빨라야 합니다.');
                    document.getElementById('startDate').focus();
                    return;
                }

                const voteData = {
                    title: title,
                    content: content,
                    limitPeople: limitPeople,
                    startDate: startDate,
                    submitDate: submitDate
                };

                updateVote(voteData, id);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}
function formatDateToLocal(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}