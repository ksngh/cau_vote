document.addEventListener("DOMContentLoaded", async function () {
    const urlParts = window.location.pathname.split('/');
    const studentId = urlParts[urlParts.length - 1]; // URL에서 studentId 추출

    if (!studentId) {
        console.error("[ERROR] studentId가 URL에 없습니다.");
        return;
    }

    try {

        const response = await fetch(`/v2/api/admin/student/${studentId}`);

        if (!response.ok) {
            throw new Error(`학생 정보를 불러오지 못했습니다: ${response.statusText}`);
        }

        const studentInfo = await response.json();
        const studentData = studentInfo.data;

        // 📌 데이터 매핑 (각 ID에 값 넣기)
        document.getElementById("studentId").value = studentData.universityId;
        document.getElementById("name").value = studentData.name;
        document.getElementById("majority").value = studentData.majority;
        document.getElementById("createdAt").value = formatDate(studentData.createdAt);
        document.getElementById("overdueFine").value = `${studentData.overdueFine} 원`;

        // 📌 회원 유형 체크
        if (studentData.memberType==="NEW") {
            document.querySelector("input[name='memberType'][value='NEW']").checked = true;
        } else {
            document.querySelector("input[name='memberType'][value='EXISTING']").checked = true;
        }

    } catch (error) {
        console.error("[ERROR] 학생 정보를 불러오는 중 오류 발생:", error);
    }
});

// 📌 날짜 포맷 변환 함수 (YYYY-MM-DD HH:mm 형식)
function formatDate(dateString) {
    if (!dateString) return "정보 없음";
    const date = new Date(dateString);
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
}
