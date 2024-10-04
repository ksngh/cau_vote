document.addEventListener("DOMContentLoaded", function() {
    function makeVote() {

        // 폼 데이터 가져오기
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;
        const limitPeople = parseInt(document.getElementById('limitPeople').value);
        const startDateInput = document.getElementById('startDate').value;
        const submitDateInput = document.getElementById('submitDate').value;

// 초를 포함한 올바른 형식으로 변환
        const startDate = new Date(startDateInput + ":00").toISOString();
        const submitDate = new Date(submitDateInput + ":00").toISOString();

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
                window.location.href = "/";
            })
            .catch(error => {
                console.error('Error:', error);
                alert('서버와의 연결에 문제가 발생했습니다.');
            });
    }
    const button = document.getElementById("generate-poll");
    button.addEventListener("click", makeVote);
})

