getFirstRankings();

function getFirstRankings() {
    fetch('/v2/api/attendance/most')
        .then(response => response.json())
        .then(ranking => {
            rankingData = ranking.data;
            rankingData.forEach(ranking => generateRankingBanner(ranking));
        })
        .catch(error => console.error('Error fetching rankings:', error));
}

function generateRankingBanner(ranking) {
    const banner = document.getElementById('first-grade-banner');
    const text = `
        <div class="ranking-item">${ranking.majority} ${ranking.name} : ${ranking.count}회</div>
    `;
    banner.insertAdjacentHTML('beforeend', text);  // span 안에 삽입

    const items = document.querySelectorAll('.ranking-item');
    const displayTime = 5;  // 각 항목이 화면에 표시되는 시간 (초)

    let currentIndex = 0;

    function showNextItem() {
        // 이전 항목 숨기기
        items.forEach((item, index) => {
            item.style.opacity = '0';
            item.style.animation = 'none'; // 이전 애니메이션 리셋
        });

        // 현재 항목 애니메이션 시작
        items[currentIndex].style.animation = `slideUp ${displayTime}s`;

        // 다음 항목으로 인덱스 업데이트
        currentIndex = (currentIndex + 1) % items.length;
    }

// 주기적으로 showNextItem 함수 실행
    setInterval(showNextItem, 5000);

// 초기 항목 표시
    showNextItem();
}