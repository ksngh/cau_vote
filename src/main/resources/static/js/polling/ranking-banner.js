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
    const displayTime = 5;  // 각 항목이 화면에 유지되는 시간 (초)
    const transitionTime = 1; // 항목이 올라오고 사라지는 시간 (초)
    const totalTime = (transitionTime * 2) + displayTime;  // 각 항목의 전체 애니메이션 시간 (초)

    items.forEach((item, index) => {
        item.style.animationDuration = `${totalTime}s`;  // 각 항목의 전체 애니메이션 시간 설정
        item.style.animationDelay = `${totalTime * index}s`;  // 이전 항목이 끝난 후 시작되도록 지연 시간 설정
    });
}