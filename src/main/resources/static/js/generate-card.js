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

    // 카드 요소 생성
    const card = document.createElement('li');
    card.className = 'card';
    card.setAttribute('data-id', data.uuid); // 고유 ID 추가
    card.setAttribute('onclick', 'toggleContent(this)');

    // 카드 내용 구성
    card.innerHTML = `
        <h3 style="display: inline;">${data.title}</h3>
        <p>${data.submitDate}</p>
        <p style="color: red; display: inline;"><strong> 마감</strong></p>
        <div class="content">
            <p>${data.content}</p>
            <button onclick=vote("${data.uuid}")>참석</button>
            <button onclick=cancel("${data.uuid}")>취소</button>
            <button onclick=openVoteModal("${data.uuid}")>투표한 사람들</button>
        </div>
    `;

    // 생성된 카드를 컨테이너에 추가
    document.getElementById('voteContainer').appendChild(card);
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
        alert("투표가 취소되었습니다!");
    }).catch(error => {
        console.error('Error:', error);
        alert('서버와의 연결에 문제가 발생했습니다.');
    });
}
