const gearTypeOptions = {
    "SABRE": ["MASK", "SWORD", "GLOVE", "METAL"],
    "FLUERET": ["MASK", "SWORD", "GLOVE", "METAL"],
    "COMMON": ["UNIFORM_TOP", "UNIFORM_BOTTOM", "BODY_WIRE", "MASK_WIRE","CHEST_PROTECTOR","INNER_PROTECTOR"]
};

// 📌 한글 변환 매핑
const gearTypeTranslations = {
    "MASK": "마스크",
    "SWORD": "검",
    "GLOVE": "장갑",
    "METAL": "메탈 자켓",
    "UNIFORM_TOP": "도복 상의",
    "UNIFORM_BOTTOM": "도복 하의",
    "BODY_WIRE": "바디 와이어",
    "MASK_WIRE": "마스크 와이어",
    "CHEST_PROTECTOR": "가슴 보호대",
    "INNER_PROTECTOR": "속 프로텍터",
    // "OTHERS": "기타"
};

// 📌 종목 변경 시, 장비 목록 업데이트
document.getElementById("fencingType").addEventListener("change", function () {
    const fencingType = this.value;
    const gearTypeSelect = document.getElementById("gearType");

    // 기존 옵션 초기화
    gearTypeSelect.innerHTML = "";

    // 선택한 종목에 맞는 Gear Type 추가
    if (gearTypeOptions[fencingType]) {
        gearTypeOptions[fencingType].forEach((gear, index) => {
            const option = document.createElement("option");
            option.value = gear;
            option.textContent = gearTypeTranslations[gear] || gear; // 한글 변환 적용
            if (index === 0) option.selected = true; // 첫 번째 아이템을 기본 선택
            gearTypeSelect.appendChild(option);
        });
    }
});
document.getElementById("registerBtn").addEventListener("click", function () {
    const gearNum = document.getElementById("gearNum").value;
    const fencingType = document.getElementById("fencingType").value;
    const gearType = document.getElementById("gearType").value;

    if (!gearNum) {
        alert("장비 번호를 입력하세요.");
        return;
    }

    const requestBody = {
        num: gearNum,
        fencingType: fencingType,
        gearType: gearType
    };

    fetch("/v2/api/gear", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // 정상 응답인 경우 JSON 데이터를 반환
            } else {
                return response.json().then(errResponse => {
                    throw new Error(errResponse.errorList[0]); // 첫 번째 에러 메시지 반환
                })
            }
        })
        .then(result => {
            alert('장비가 생성되었습니다.');
            window.location.href = "/gear/new";
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message); // 에러 메시지 alert
        });
});