let cursorId = null; // 마지막으로 가져온 cursorId
const size = 15; // 한 번에 불러올 개수
let isLoading = false; // 현재 데이터 로딩 중 여부
let hasNext = true; // 다음 페이지 여부

document.addEventListener("DOMContentLoaded", function () {
    fetchStudentInfo(); // 첫 번째 데이터 로드

    window.addEventListener("scroll", handleScroll); // 스크롤 이벤트 리스너 추가
});

// 📌 스크롤 이벤트 핸들러
function handleScroll() {
    if (isLoading || !hasNext) return;

    const { scrollTop, scrollHeight, clientHeight } = document.documentElement;

    if (scrollTop + clientHeight >= scrollHeight - 100) {
        fetchStudentInfo();
    }
}

// 📌 학생 정보를 백엔드에서 가져오는 함수
function fetchStudentInfo() {
    if (isLoading || !hasNext) return;

    isLoading = true; // 로딩 중 상태 설정

    let url = `/v2/api/admin/student?size=${size}`;
    if (cursorId) {
        url += `&cursorId=${cursorId}`;
    }


    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("네트워크 응답이 올바르지 않습니다.");
            }
            return response.json();
        })
        .then(info => {

            const details = info.data;

            // 예외 처리: 데이터가 없을 경우
            if (!details || !details.content || details.content.length === 0) {
                hasNext = false;
                return;
            }

            updateStudentTable(details.content);

            // 📌 hasNext 업데이트 로직 수정
            hasNext = details.content.length === size;

            // 📌 cursorId 업데이트
            cursorId = details.content[details.content.length - 1].id;
        })
        .catch(error => {
            console.error("[ERROR] 학생 정보를 불러오는 중 오류 발생:", error);
        })
        .finally(() => {
            isLoading = false; // 요청 완료 후 상태 초기화
        });
}

// 📌 테이블에 학생 정보 추가
function updateStudentTable(students) {
    const tableBody = document.getElementById("student-info-list");

    students.forEach(student => {
        const row = document.createElement("tr");

        let role = "";
        if (student.role[0] === "ADMIN") {
            role = "임원진";
        } else if (student.role[0] === "USER") {
            role = "부원";
        } else if (student.role[0] === "PENDING_USER") {
            role = "임시 부원";
        }

        row.innerHTML = `
            <td>${student.name}</td>
            <td>${student.majority}</td>
            <td>${student.universityId}</td>
            <td>${student.overdueFine}</td>
            <td>${role}</td>
        `;

        // 행 클릭 시 해당 학생 상세 페이지로 이동
        row.addEventListener("click", function () {
            window.location.href = `/admin/student/${student.id}`;
        });

        tableBody.appendChild(row);
    });

}
