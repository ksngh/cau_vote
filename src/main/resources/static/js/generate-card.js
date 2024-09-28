getCardInfo();

function getCardInfo() {

    fetch('api/vote', {
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
                generateCard(data[i]);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}

function generateCard(data) {


    const currentDate = new Date();
    const submitDate = new Date(data.submitDate);
    const startDate = new Date(data.startDate);

    let dividerClass = '';
    let isBlurred = false; // 블러 처리 여부
    let hideButtons = false; // 버튼 숨김 여부
    let hidePeople =false;
    let voteInfo ='';

    if (currentDate > submitDate) {
        dividerClass = 'finishedVote';
        isBlurred = true; // 마감된 투표는 블러 처리
        hideButtons = true; // 버튼 숨김
        voteInfo = "마감된 투표 입니다."
    } else if (currentDate < startDate) {
        dividerClass = 'upcomingVote';
        isBlurred = true; // 예정된 투표는 블러 처리
        hideButtons = true; // 버튼 숨김
        hidePeople = true;
        voteInfo = "투표 예정일 : " + formatDate(startDate);
    } else {
        dividerClass = 'ongoingVote';
        voteInfo = "투표 마감일 : " + formatDate(submitDate);
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
        <div class="content" id="card-content">
            <p>${data.content}</p>
            <button onclick=vote("${data.uuid}") style="${hideButtons ? 'display:none;' : ''}">참석</button>
            <button onclick=cancel("${data.uuid}") style="${hideButtons ? 'display:none;' : ''}">취소</button>
            <button onclick=openVoteModal("${data.uuid}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
        </div>
    `;

    fetch('/api/student')
        .then(response => response.json())
        .then(userData => {
            if (userData && userData.role === "ROLE_ADMIN") {
                const cardContent = card.querySelector('#card-content');
                cardContent.insertAdjacentHTML("beforeend", `
                    <button onclick="updateCard('${data.uuid}')">수정</button>
                    <button onclick="deleteCard('${data.uuid}')">삭제</button>
                `);
            }
        })
        .catch(error => console.error('Error fetching user status:', error));

    // 생성된 카드를 컨테이너에 추가
    document.getElementById(dividerClass).appendChild(card);
}

function vote(id) {
    fetch(`api/student/vote/${id}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            return response.json(); // JSON 데이터 반환
        } else {
            throw new Error('투표 생성에 실패했습니다: ' + response.statusText);
        }
    }).then(data => {
            alert(data.message);
    }).catch(error => {
        console.error('Error:', error);
        alert('로그인 후 이용해주세요.');
    });
}

function cancel(id){
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

function updateCard(id){
    window.location.href = `/admin/posting/${id}`
}

function deleteCard(id){

    fetch(`api/vote/${id}`,{
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            return response.json(); // JSON 데이터 반환
        } else {
            throw new Error('취소에 실패했습니다: ' + response.statusText);
        }
    }).then(data => {
        alert("투표가 삭제되었습니다.");
        location.reload();
    }).catch(error => {
        console.error('Error:', error);
        alert('서버와의 연결에 문제가 발생했습니다.');
    });
}