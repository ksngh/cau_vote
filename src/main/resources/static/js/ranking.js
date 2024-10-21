// 서버에서 받아온 데이터로 학기별 랭킹을 표시하는 함수
function displaySemesterRankings(semesterRankingMap) {
    const container = document.getElementById("rankings-container");

    // 학기별로 데이터 처리
    Object.keys(semesterRankingMap).forEach(semester => {
        // 학기별 섹션 생성
        const semesterSection = document.createElement("div");
        semesterSection.classList.add("semester-section");

        // 학기 제목 추가
        const semesterTitle = document.createElement("h3");
        semesterTitle.innerText = `-- ${semester} --`;
        semesterSection.appendChild(semesterTitle);

        let rank = 1;
        let previousAttendance = null;

        // 학기별 랭킹 리스트 가져오기
        let rankingList = semesterRankingMap[semester];

        rankingList.forEach((ranking, index) => {
            // 출석 횟수가 다를 경우에만 새로운 순위로 갱신
            if (previousAttendance !== ranking.attendance) {
                rank = index + 1;
            }

            // 순위 정보 추가
            const rankText = document.createElement("p");
            rankText.classList.add("rank");

            let medal;
            if (rank === 1) {
                medal = '🥇';  // 금메달
            } else if (rank === 2) {
                medal = '🥈';  // 은메달
            } else if (rank === 3 || rank === 4) {
                medal = '🥉';  // 동메달
            } else {
                medal = '';  // 4등 이후에는 메달 없음
            }

            rankText.innerText = `${medal} ${rank}등 ${ranking.majorName} ${ranking.studentName} : ${ranking.attendance}회`;
            semesterSection.appendChild(rankText);

            // 현재 출석 횟수를 다음 순위 비교를 위해 저장
            previousAttendance = ranking.attendance;
        });

        // 학기 섹션을 컨테이너에 추가
        container.appendChild(semesterSection);
    });
}

// 서버에서 학기별 랭킹 데이터를 받아오는 함수
function getRankings() {
    fetch('/api/ranking/')
        .then(response => response.json())
        .then(data => {
            displaySemesterRankings(data);
        })
        .catch(error => console.error('Error fetching rankings:', error));
}

// 페이지 로드 시 랭킹 데이터 불러오기
document.addEventListener("DOMContentLoaded", () => {
    getRankings();
});
