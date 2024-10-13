let isRequestInProgress = false;
getMyCardInfo();

async function getMyCardInfo() {

    try {
        const response = await fetch('api/vote/mypage', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('투표 데이터를 가져오는데 실패했습니다: ' + response.statusText);
        }

        const data = await response.json();

        // 각 아이템에 대해 fetchVoteCount를 병렬로 호출하고, 순서 유지
        const dataWithCounts = await Promise.all(
            data.map(async (item) => {
                const joinNum = await fetchVoteCount(item.uuid);
                return { ...item, joinNum }; // joinNum을 포함한 새로운 객체 반환
            })
        );

        // 정렬된 데이터에 대해 generateCard 호출
        dataWithCounts.forEach(item => generateMyCard(item));

    } catch (error) {
        console.error('Error:', error);
    } finally {
        isRequestInProgress = false; // 요청 완료 후 상태 초기화
    }
}

function generateMyCard(data) {


    const myCurrentDate = new Date();
    const mySubmitDate = new Date(data.submitDate);

    let dividerClass = '';
    let isBlurred = false; // 블러 처리 여부
    let hideButtons = false; // 버튼 숨김 여부
    let hidePeople =false;
    let voteInfo ='';

    const attendanceNum = "참여 인원 : " + data.joinNum + "/" + data.limitPeople;

    if (myCurrentDate > mySubmitDate || data.joinNum >= data.limitPeople) {
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
        <p>${attendanceNum}</p>
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

async function fetchVoteCount(id) {
    try {
        const response = await fetch(`api/student/vote/count/${id}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json(); // JSON 형식으로 변환
        return data.countStudentVote; // StudentVoteCountResponseDTO 객체 반환
    } catch (error) {
        console.error('Error fetching vote count:', error);
    }
}
function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}