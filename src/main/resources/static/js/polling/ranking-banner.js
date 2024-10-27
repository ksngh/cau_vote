getFirstRankings();

function getFirstRankings() {
    fetch('/api/ranking/first')
        .then(response => response.json())
        .then(data => {
            data.forEach(ranking => generateRankingBanner(ranking));
        })
        .catch(error => console.error('Error fetching rankings:', error));
}

function generateRankingBanner(ranking) {
    const banner = document.getElementById('first-grade-banner');
    const text = `
        <div class="ranking-item">${ranking.majorName} ${ranking.studentName} : ${ranking.attendance}회</div>
    `;
    banner.insertAdjacentHTML('beforeend', text);  // span 안에 삽입

    const items = document.querySelectorAll('.ranking-item');
    const displayTime = 5;  // 각 항목이 화면에 표시되는 시간 (초)

    items.forEach((item, index) => {
        item.style.animationDuration = `${displayTime}s`;  // 각 항목의 총 애니메이션 시간 설정
        item.style.animationDelay = `${displayTime * index}s`;  // 각 항목이 순차적으로 나타나도록 딜레이 설정
    });
}