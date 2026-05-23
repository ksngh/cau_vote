// semester 문자열을 숫자로 변환하는 함수
function parseSemester(semesterStr) {
    const match = semesterStr.match(/(\d+)(?:학)?년도\s*(\d+)학기/);
    if (!match) return { year: 0, term: 0 };
    return { year: parseInt(match[1]), term: parseInt(match[2]) };
}

// 🏆 랭킹 데이터 화면에 표시 (버튼 중복 방지 포함)
function displayRankings(groupedData, sortedSemesters, append = false, showMoreLink = true) {
    const container = document.getElementById("rankings-container");

    if (!append) {
        container.innerHTML = ""; // 처음 로딩 시 초기화
    }

    sortedSemesters.forEach(semester => {
        const semesterTitle = document.createElement("h3");
        semesterTitle.textContent = semester;
        container.appendChild(semesterTitle);

        let rank = 1;
        let previousCount = null;

        groupedData[semester].forEach((item, index) => {
            if (previousCount !== item.count) {
                rank = index + 1;
            }

            const rankText = document.createElement("p");
            rankText.classList.add("rank");

            let medal = '';
            if (rank === 1) medal = '🥇';
            else if (rank === 2) medal = '🥈';
            else if (rank === 3) medal = '🥉';

            const majorName = item.majority || "미확인 학과";
            const studentName = item.name || "이름 없음";

            rankText.innerText = `${medal} ${majorName} ${studentName} : ${item.count}회`;
            container.appendChild(rankText);

            previousCount = item.count;
        });
    });

    // ✅ showMoreLink가 true일 때만 더보기 버튼 추가
    if (showMoreLink) {
        const moreLink = document.createElement("div");
        moreLink.textContent = "이전학기 더보기";
        moreLink.style.color = "#888";
        moreLink.style.fontSize = "14px";
        moreLink.style.textAlign = "center";
        moreLink.style.cursor = "pointer";
        moreLink.style.padding = "10px 0";

        moreLink.onclick = () => {
            moreLink.remove(); // 클릭 후 버튼 삭제
            getBeforeAll();    // 이전 학기 데이터 불러오기
        };
        container.appendChild(moreLink);
    }
}

// 📌 현재학기 데이터 로딩
function getRankings() {
    fetch('/v2/api/attendance/ranking')
        .then(response => response.json())
        .then(ranking => {
            const data = ranking.data;

            const groupedData = {};
            data.forEach(item => {
                if (!groupedData[item.semester]) {
                    groupedData[item.semester] = [];
                }
                groupedData[item.semester].push(item);
            });

            const sortedSemesters = Object.keys(groupedData).sort((a, b) => {
                const semA = parseSemester(a);
                const semB = parseSemester(b);
                return semB.year - semA.year || semB.term - semA.term;
            });

            sortedSemesters.forEach(sem => {
                groupedData[sem].sort((a, b) => b.count - a.count);
                groupedData[sem] = groupedData[sem].slice(0, 10);
            });

            // ✅ 최초 로딩: 더보기 버튼 보이기 (true)
            displayRankings(groupedData, sortedSemesters, false, true);
        })
        .catch(error => console.error('Error fetching rankings:', error));
}

// 🔥 이전학기 데이터 로딩 (더보기 버튼을 없애고 추가만 하기)
function getBeforeAll() {
    fetch('/v2/api/attendance/before-all')
        .then(response => response.json())
        .then(ranking => {
            const data = ranking.data;

            const groupedData = {};
            data.forEach(item => {
                if (!groupedData[item.semester]) {
                    groupedData[item.semester] = [];
                }
                groupedData[item.semester].push(item);
            });

            const sortedSemesters = Object.keys(groupedData).sort((a, b) => {
                const semA = parseSemester(a);
                const semB = parseSemester(b);
                return semB.year - semA.year || semB.term - semA.term;
            });

            sortedSemesters.forEach(sem => {
                groupedData[sem].sort((a, b) => b.count - a.count);
                groupedData[sem] = groupedData[sem].slice(0, 10);
            });

            // ✅ 추가 데이터 로딩: 더보기 버튼 제거 (false)
            displayRankings(groupedData, sortedSemesters, true, false);
        })
        .catch(error => console.error('Error fetching previous rankings:', error));
}

// 🚀 초기 페이지 로딩
document.addEventListener("DOMContentLoaded", () => {
    getRankings();
    document.querySelector(".tab-container").style.display = "none";
});
