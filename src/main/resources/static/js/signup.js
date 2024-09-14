document.addEventListener("DOMContentLoaded", function() {
    // signup 함수 정의
    function signup() {
        const studentId = document.getElementById("studentId").value;
        const name = document.getElementById("name").value;
        const majority = document.getElementById("majority").value;
        const memberType = document.querySelector('input[name="memberType"]:checked').value;

        // studentData 객체 생성
        const studentData = {
            studentId: studentId,
            name: name,
            majority: majority,
            memberType: memberType
        };

        // API 요청
        fetch("/api/student", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(studentData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                window.location.href = "/poll"; // 원하는 URL로 이동
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    // 버튼 클릭 시 signup 함수 호출
    const button = document.getElementById("signup-button");
    button.addEventListener("click", signup);
});