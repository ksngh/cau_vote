document.addEventListener("DOMContentLoaded", function() {

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
                generateCard(data);
                //window.location.href = "/poll";
            })
            .catch(error => {
                console.error('Error:', error);
                alert('서버와의 연결에 문제가 발생했습니다.');
            });
    }
    function generateCard(data){

        // 카드 요소 생성
        const card = document.createElement('li');
        card.className = 'card';
        card.setAttribute('onclick', 'toggleContent(this)');

        // 카드 내용 구성
        card.innerHTML = `
        <h3 style="display: inline;">${data.title}</h3>
        <p>${data.submitDate}</p>
        <p style="color: red; display: inline;"><strong> 마감</strong></p>
        <div class="content">
            <p>${data.content}</p>
            <label>
                <input type="radio" name="attendance" value="참석"> 참석
            </label><br>
            <label>
                <input type="radio" name="attendance" value="불참석"> 불참석
            </label><br>
            <label>
                <input type="radio" name="attendance" value="미정"> 미정
            </label><br><br>
            <button onclick="vote(this)">투표</button>
            <button id="openModal">투표한 사람들</button>
        </div>
    `;

        // 생성된 카드를 컨테이너에 추가
        document.getElementById('voteContainer').appendChild(card);
    }
})

function vote(button) {
    alert("참석에 투표하였습니다!");
}


