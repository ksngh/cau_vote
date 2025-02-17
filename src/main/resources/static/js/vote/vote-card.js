let cursorId = null;  // 처음에는 null
let isLoading = false;
let hasNext = true;   // 다음 페이지가 있는지 여부

// 초기 데이터 로드
getCardInfo();

window.addEventListener('scroll', handleScroll);

function handleScroll() {
    if (isLoading || !hasNext) return; // 요청 중이거나 더 이상 데이터가 없으면 실행 X
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        getCardInfo();
    }
}

async function getCardInfo() {
    if (isLoading) return;
    isLoading = true;

    try {
        const response = await fetch(`/v2/api/board?cursorId=${cursorId || ''}&size=10`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('투표 데이터를 가져오는데 실패했습니다: ' + response.statusText);
        }

        const cardInfo = await response.json();
        const cardInfoContents = cardInfo.data.content;
        // 다음 페이지가 있는지 확인
        hasNext = cardInfoContents.length === 10; // `size=10`이면, 데이터가 10개일 때만 다음 페이지 가능

        if (cardInfoContents.length === 0) return;

        // 각 아이템에 대해 fetchVoteCount를 병렬로 호출하고, 순서 유지
        const dataWithCounts = await Promise.all(
            cardInfoContents.map(async (item) => {
                const joinNum = await fetchVoteCount(item.id);
                return { ...item, joinNum }; // joinNum을 포함한 새로운 객체 반환
            })
        );

        // 정렬된 데이터에 대해 voteCard 호출
        dataWithCounts.forEach(item => voteCard(item));

        // 마지막 데이터의 ID를 `cursorId`로 업데이트
        cursorId = cardInfoContents[cardInfoContents.length - 1].id;

    } catch (error) {
        console.error('Error:', error);
    } finally {
        isLoading = false; // 요청 완료 후 상태 초기화
    }
}

// 카드 UI 생성
function voteCard(data) {

    const endDate = new Date(data.endDate);
    const startDate = new Date(data.startDate);

    let dividerClass = '';
    let isBlurred = false;
    let hideButtons = false;
    let hidePeople = false;
    let voteInfo = '';
    const attendanceNum = "참여 인원 : " + data.joinNum + "/" + data.limitPeople;

    if (data.status === "ACTIVE") {
        dividerClass = 'ongoingVote';
        voteInfo = "투표 마감일 : " + formatDate(endDate);
    } else if (data.status === "PENDING") {
        dividerClass = 'upcomingVote';
        isBlurred = true;
        hideButtons = true;
        hidePeople = true;
        voteInfo = "투표 예정일 : " + formatDate(startDate);
    } else {
        dividerClass = 'finishedVote';
        isBlurred = true;
        hideButtons = true;
        voteInfo = "마감된 투표 입니다.";
    }

    const card = document.createElement('li');
    card.className = 'card' + (isBlurred ? ' blurred' : '');
    card.setAttribute('data-id', data.id);
    card.setAttribute('onclick', 'toggleContent(this)');

    fetch('/v2/api/auth')
        .then(response => response.json())
        .then(authInfo => {
            const roles = new Set(authInfo.data.role)

            if (roles.has("ADMIN")) {
                card.innerHTML = `
                    <h3 style="display: inline;">${data.title}</h3>
                    <p>${voteInfo}</p>
                    <p id="attendance-number">${attendanceNum}</p>
                    <div class="content" id="card-content">
                        <p class="vote">${data.content}</p>
                            <div id="category-selection" style="${hideButtons ? 'display:none;' : ''} margin-bottom:15px" onclick="event.stopPropagation();">
                                <label style="margin: 5px">
                                    <input type="radio" name="fencing-type" value="SABRE" checked>사브르
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="fencing-type" value="FLUERET">플러레
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="fencing-type" value="EPEE">에페
                                </label>
                            </div>
                        <button onclick=sendVote("${data.id}") style="${hideButtons ? 'display:none;' : ''}">참석</button>
                        <button onclick=cancel("${data.id}") style="${hideButtons ? 'display:none;' : ''}">취소</button>
                        <button onclick=openVoteModal("${data.id}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
                        <button onclick=deleteBoard('${data.id}')>삭제</button>
                        <button onclick=updateBoard('${data.id}')>수정</button>
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
                                    <input type="radio" name="fencing-type" value="SABRE" checked>사브르
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="fencing-type" value="FLUERET">플러레
                                </label>
                                <label style="margin: 5px">
                                    <input type="radio" name="fencing-type" value="EPEE">에페
                                </label>
                            </div>
                        <button onclick=sendVote("${data.id}") style="${hideButtons ? 'display:none;' : ''}">참석</button>
                        <button onclick=cancel("${data.id}") style="${hideButtons ? 'display:none;' : ''}">취소</button>
                        <button onclick=openVoteModal("${data.id}") style="${hidePeople ? 'display:none;' : ''}">참여 인원</button>
                    </div>
                `;
            }
        })
        .catch(error => console.error('Error fetching user status:', error));

    document.getElementById(dividerClass).appendChild(card);
}

// 개별 투표의 참여 인원 수 가져오기
async function fetchVoteCount(boardId) {
    try {
        const response = await fetch(`/v2/api/board/${boardId}/count`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const countInfo = await response.json();
        return countInfo.data.count;
    } catch (error) {
        console.error('인원 수를 불러오는데 실패하였습니다.', error);
        return 0; // 기본값 반환
    }
}

// 날짜 포맷 변환
function formatDate(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
}

function updateBoard(id) {
    window.location.href = `/posting/${id}`;
}

async function deleteBoard(boardId) {
    if (!confirm("투표를 삭제하시겠습니까?")) return;
    try {
        const response = await fetch(`v2/api/board/${boardId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("삭제 실패");
        alert("투표가 삭제되었습니다.");
        location.reload();
    } catch (error) {
        console.error("삭제 중 오류 발생:", error);
        alert("삭제 중 오류가 발생했습니다.");
    }
}

// 카드 내용 토글
function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}
