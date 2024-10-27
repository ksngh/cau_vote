document.addEventListener("DOMContentLoaded", function() {
    function makeVote() {

        // 폼 데이터 가져오기
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;
        const limitPeople = parseInt(document.getElementById('limitPeople').value);
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

// 초를 포함한 올바른 형식으로 변환
        const startDate = new Date(startDateInput + ":00").toISOString();
        const submitDate = new Date(submitDateInput + ":00").toISOString();

        if (startDate >= submitDate) {
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
            submitDate: submitDate
        };

        fetch('/api/vote', {
            method: 'POST',
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
                alert('투표가 생성되었습니다.');
                window.location.href = "../../../..";
            })
            .catch(error => {
                console.error('Error:', error);
                alert('서버와의 연결에 문제가 발생했습니다.');
            });
    }
    const button = document.getElementById("generate-poll");
    button.addEventListener("click", makeVote);
})

