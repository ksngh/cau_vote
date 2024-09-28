getMyCardInfo()

function getMyCardInfo() {

    fetch('api/vote/mypage', {
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
            for (let i = 0; i < data.length; i++) {
                generateMyCard(data[i]);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}

function generateMyCard(data) {


    const myCurrentDate = new Date();
    const mySubmitDate = new Date(data.submitDate);

    let dividerClass = '';
    let isBlurred = false; // 블러 처리 여부
    let hideButtons = false; // 버튼 숨김 여부
    let hidePeople =false;
    let voteInfo ='';

    if (myCurrentDate > mySubmitDate) {
        dividerClass = 'finishedVote';
        isBlurred = true; // 마감된 투표는 블러 처리
        hideButtons = true; // 버튼 숨김
        voteInfo = "마감된 투표 입니다."
    } else {
        dividerClass = 'ongoingVote';
        voteInfo = "투표 마감일 : " + formatDate(mySubmitDate);
    }

    // 카드 요소 생성
    const card = document.createElement('li');
    card.className = 'card' + (isBlurred ? ' blurred' : '');
    card.setAttribute('data-id', data.uuid); // 고유 ID 추가
    card.setAttribute('onclick', 'toggleContent(this)');

    // 카드 내용 구성
    card.innerHTML = `
        <h3 style="display: inline;">${data.title}</h3>
        <p>${voteInfo}</p>
        <div class="content">
            <p>${data.content}</p>
            <button onclick=myCancel("${data.uuid}") style="${hideButtons ? 'display:none;' : ''}">취소</button>
            <button onclick=openVoteModal("${data.uuid}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
        </div>
    `;

    // 생성된 카드를 컨테이너에 추가
    document.getElementById(dividerClass).appendChild(card);
}

function myCancel(id){
    fetch(`api/student/vote/choice/${id}`,{
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            return response.json(); // JSON 데이터 반환
        } else {
            throw new Error('취소에 실패했습니다: ' + response.statusText);
        }
    }).then(data => {
        alert("투표가 취소되었습니다.");
        window.location.reload();
    }).catch(error => {
        console.error('Error:', error);
        alert('서버와의 연결에 문제가 발생했습니다.');
    });
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // 월은 0부터 시작하므로 1을 더함
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0'); // 2자리로 포맷

    return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
}
