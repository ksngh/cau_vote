function formatLocalDateTime(dateString) {
    const date = new Date(dateString);
    date.setMinutes(date.getMinutes() - date.getTimezoneOffset()); // 로컬 시간대 적용
    return date.toISOString().slice(0, 19); // "YYYY-MM-DDTHH:mm:ss" 포맷
}


document.addEventListener("DOMContentLoaded", function () {
    function makeVote() {

        // 폼 데이터 가져오기
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;
        const limitPeople = parseInt(document.getElementById('limitPeople').value);
        const startDateInput = document.getElementById('startDate').value;
        const endDateInput = document.getElementById('endDate').value;

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
        if (!endDateInput) {
            alert('마감 날짜를 입력해 주세요.');
            document.getElementById('endDate').focus();
            return;
        }

        const startDate = formatLocalDateTime(startDateInput);
        const endDate = formatLocalDateTime(endDateInput);

        if (startDate >= endDate) {
            alert('시작 날짜는 마감 날짜보다 빨라야 합니다.');
            document.getElementById('startDate').focus();
            return;
        }

        // 요청 데이터 준비
        const voteData = {
            title: title,
            content: content,
            limitPeople: limitPeople,
            startDate: startDate,
            endDate: endDate
        };

        fetch('/v2/api/board', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(voteData)
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // 정상 응답인 경우 JSON 데이터를 반환
                } else {
                    return response.json().then(errResponse => {
                        throw new Error(errResponse.errorList[0]); // 첫 번째 에러 메시지 반환
                    })
                }
            })
            .then(result => {
                alert('투표가 생성되었습니다.');
                window.location.href = "/";
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message); // 에러 메시지 alert
            });
    }

    const button = document.getElementById("generate-poll");
    button.addEventListener("click", makeVote);
})

