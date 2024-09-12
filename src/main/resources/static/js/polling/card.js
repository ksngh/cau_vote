function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}

document.querySelectorAll('input[type="radio"]').forEach((radio) => {
    radio.addEventListener('click', (event) => {
        event.stopPropagation(); // 클릭 이벤트가 부모 요소로 전파되는 것을 막음
    });
});

function vote(button) {
    alert("참석에 투표하였습니다!");
};

function generateCard(button) {
    // 카드 생성에 필요한 데이터
    const title = "24학년도 2학기 24/09/08 훈련"; // 예시 제목
    const content = "준비물 : 실내용 운동화, 기능성 티셔츠<br>시간 : 17:30 ~ 21:30 <br>시작 10분 전에 와서 준비 완료하기"; // 예시 내용

    // 카드 요소 생성
    const card = document.createElement('div');
    card.className = 'card';
    card.setAttribute('onclick', 'toggleContent(this)');

    // 카드 내용 구성
    card.innerHTML = `
        <h3 style="display: inline;">${title}</h3>
        <p style="color: red; display: inline;"><strong> 마감</strong></p>
        <div class="content">
            <p>${content}</p>
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