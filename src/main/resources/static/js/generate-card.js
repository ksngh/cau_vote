let isRequestInProgress = false;
getCardInfo();

async function getCardInfo() {
    if (isRequestInProgress) return; // 이미 요청 중인 경우 종료
    isRequestInProgress = true;

    try {
        const response = await fetch('api/vote', {
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
        dataWithCounts.forEach(item => generateCard(item));

    } catch (error) {
        console.error('Error:', error);
    } finally {
        isRequestInProgress = false; // 요청 완료 후 상태 초기화
    }
}

function generateCard(data) {
    const currentDate = new Date();
    const submitDate = new Date(data.submitDate);
    const startDate = new Date(data.startDate);

    let dividerClass = '';
    let isBlurred = false;
    let hideButtons = false;
    let hidePeople = false;
    let voteInfo = '';
    const attendanceNum = "참여 인원 : " + data.joinNum + "/" + data.limitPeople;

    if (currentDate > submitDate) {
        dividerClass = 'finishedVote';
        isBlurred = true;
        hideButtons = true;
        voteInfo = "마감된 투표 입니다.";
    } else if (currentDate < startDate) {
        dividerClass = 'upcomingVote';
        isBlurred = true;
        hideButtons = true;
        hidePeople = true;
        voteInfo = "투표 예정일 : " + formatDate(startDate);
    } else {
        dividerClass = 'ongoingVote';
        voteInfo = "투표 마감일 : " + formatDate(submitDate);
    }

    const card = document.createElement('li');
    card.className = 'card' + (isBlurred ? ' blurred' : '');
    card.setAttribute('data-id', data.uuid);
    card.setAttribute('onclick', 'toggleContent(this)');

    fetch('/api/student')
        .then(response => response.json())
        .then(userData => {
            if (userData && userData.role === "ROLE_ADMIN") {
                card.innerHTML = `
                    <h3 style="display: inline;">${data.title}</h3>
                    <p>${voteInfo}</p>
                    <p id="attendance-number">${attendanceNum}</p>
                    <div class="content" id="card-content">
                        <p class="vote">${data.content}</p>
                        <button onclick=openVoteModal("${data.uuid}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
                        <button onclick=updateCard('${data.uuid}')>수정</button>
                        <button onclick=deleteCard('${data.uuid}')>삭제</button>
                    </div>
                `;
            } else {
                card.innerHTML = `
                    <h3 style="display: inline;">${data.title}</h3>
                    <p>${voteInfo}</p>
                    <p id="attendance-number">${attendanceNum}</p>
                    <div class="content" id="card-content">
                        <p class="vote">${data.content}</p>
                            <div id="category-selection" style="${hideButtons ? 'display:none;' : ''} margin-bottom:15px" onclick="event.stopPropagation();">
                                <label style="margin: 5px">
                                    <input type="radio" name="category" value="SABRE" checked>사브르
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="category" value="FLUERET">플러레
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="category" value="EPEE">에페
                                </label>
                            </div>
                        <button onclick=sendVote("${data.uuid}") style="${hideButtons ? 'display:none;' : ''}">참석</button>
                        <button onclick=cancel("${data.uuid}") style="${hideButtons ? 'display:none;' : ''}">취소</button>
                        <button onclick=openVoteModal("${data.uuid}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
                    </div>
                `;
            }
        })
        .catch(error => console.error('Error fetching user status:', error));

    document.getElementById(dividerClass).appendChild(card);
}

async function fetchVoteCount(id) {
    try {
        const response = await fetch(`api/student/vote/count/${id}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        return data.countStudentVote;
    } catch (error) {
        console.error('Error fetching vote count:', error);
    }
}

function vote(id) {
    fetch(`api/student/vote/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('투표 생성에 실패했습니다: ' + response.statusText);
        }
    }).then(data => {
        alert(data.message);
        window.location.reload();
    }).catch(error => {
        console.error('Error:', error);
        alert('로그인 후 이용해주세요.');
    });
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
}

function updateCard(id) {
    window.location.href = `/admin/posting/${id}`
}

function deleteCard(id) {
    fetch(`api/vote/${id}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            return response.json();
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

function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}