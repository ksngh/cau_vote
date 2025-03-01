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

        const roleSelect = document.getElementById("role");
        roleSelect.value = studentData.role; // 불러온 값과 동일한 option 선택
        // 📌 데이터 매핑 (각 ID에 값 넣기)
        document.getElementById("universityId").value = studentData.universityId;
        document.getElementById("name").value = studentData.name;
        document.getElementById("email").value = studentData.email;
        // document.getElementById("role").value = studentData.role;
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

function getStudentIdFromUrl() {
    const urlParts = window.location.pathname.split('/'); // URL을 '/' 기준으로 분할
    return urlParts[urlParts.length - 1]; // 마지막 요소가 studentId
}


function payLateFee() {
    const studentId = getStudentIdFromUrl(); // URL에서 studentId 가져오기

    if (!studentId || isNaN(studentId)) {
        alert("유효한 학생 ID를 찾을 수 없습니다.");
        return;
    }

    fetch(`/v2/api/admin/student/${studentId}/late-fee`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                alert("연체료가 정상적으로 처리되었습니다.");
                window.location.reload(); // 페이지 새로고침
            } else {
                return response.json().then(err => {
                    throw new Error(err.message || "연체료 처리 중 오류 발생");
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
}

function updateStudent(){
    const studentId = getStudentIdFromUrl(); // URL에서 studentId 가져오기

    if (!studentId || isNaN(studentId)) {
        alert("유효한 학생 ID를 찾을 수 없습니다.");
        return;
    }

    const studentData = {
        universityId: document.getElementById("universityId").value,
        role: document.getElementById("role").value,
        name: document.getElementById("name").value,
        majority: document.getElementById("majority").value,
        memberType: document.querySelector('input[name="memberType"]:checked')?.value || null
    };


    fetch(`/v2/api/admin/student/${studentId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(studentData)
    })
        .then(response => {
            if (response.ok) {
                alert("부원 정보가 정상적으로 수정되었습니다.");
                window.location.reload(); // 페이지 새로고침
            } else {
                return response.json().then(err => {
                    throw new Error(err.message || "부원 정보 처리 중 오류 발생");
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
}

function deleteStudent(){
    const studentId = getStudentIdFromUrl(); // URL에서 studentId 가져오기
    if (!confirm("부원 정보를 삭제하시겠습니까?")) return;
    if (!studentId || isNaN(studentId)) {
        alert("유효한 학생 ID를 찾을 수 없습니다.");
        return;
    }

    fetch(`/v2/api/admin/student/${studentId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                alert("부원 정보가 정상적으로 삭제되었습니다.");
                window.location.href="/admin/student"; // 페이지 새로고침
            } else {
                return response.json().then(err => {
                    throw new Error(err.message || "부원 삭제 처리 중 오류 발생");
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
}