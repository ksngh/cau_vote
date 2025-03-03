const gearTypeOptions = {
    "SABRE": ["MASK", "SWORD", "GLOVE", "METAL"],
    "FLUERET": ["MASK", "SWORD", "GLOVE", "METAL"],
    "COMMON": ["UNIFORM_TOP", "UNIFORM_BOTTOM", "BODY_WIRE", "MASK_WIRE","CHEST_PROTECTOR","INNER_PROTECTOR", "OTHERS"]
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
    "OTHERS": "기타"
};
const fencingTypeTranslations = {
    "SABRE": "사브르",
    "FLUERET": "플뢰레",
    "COMMON": "공용"
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

document.getElementById("searchBtn").addEventListener("click", function () {
    const fencingType = document.getElementById("fencingType").value;
    const gearType = document.getElementById("gearType").value;

    let apiUrl = "/v2/api/gear?";
    if (fencingType) apiUrl += `fencingType=${fencingType}&`;
    if (gearType) apiUrl += `gearType=${gearType}&`;
    apiUrl = apiUrl.slice(0, -1); // 마지막 & 제거

    // 1️⃣ 장비 목록 불러오기
    fetch(apiUrl)
        .then(response => response.json())
        .then(result => {
            let gearList = result.data;

            // 2️⃣ "IN_USE" 상태인 장비만 대여 정보 요청 (Promise.all 사용)
            const rentalPromises = gearList.map(gear => {
                if (gear.gearStatus === "IN_USE") {
                    return fetchRentalInfo(gear.id).then(rentalInfo => ({ ...gear, ...rentalInfo }));
                }
                return Promise.resolve(gear); // "IN_USE"가 아니면 원본 그대로 반환
            });

            return Promise.all(rentalPromises);
        })
        .then(gearsWithRentalInfo => {

            // 3️⃣ UI 업데이트 (모든 장비를 표시)
            document.querySelector(".gear-list").innerHTML = ""; // 기존 목록 초기화
            gearsWithRentalInfo.forEach(gear => gearCard(gear));
        })
        .catch(error => {
            console.error(error)
        });
});

// 📌 대여 정보 가져오는 함수
function fetchRentalInfo(gearId) {
    return fetch(`/v2/api/gear/${gearId}/rental`)
        .then(response => response.json())
        .then(rentalData => rentalData.data) // { renter: "김성호", expectedReturnDate: "2025-02-26" }
        .catch(error => {
            console.error(`Error fetching rental info for gear ${gearId}:`, error);
            return { renter: "알 수 없음", expectedReturnDate: "미정" };
        });
}

// 📌 장비 카드 생성 함수
function gearCard(data) {
    let hideRental = false;
    let hideReturn = false;
    let status = '';

    if (data.gearStatus === "AVAILABLE") {
        status = `<p class="gear-status available">대여 가능</p>`;
        hideReturn = true;
    } else if (data.gearStatus === "IN_USE") {
        status = `<p class="gear-status in-use">대여 중 <span class="black-text">(대여자 : ${data.studentName})</span></p>
          <p class="gear-status">예상 반납일: ${formatDate(new Date(data.dueDate))}</p>`;
        hideRental = true;
    } else {
        status = `<p class="gear-status unavailable">대여 불가</p>`;
        hideRental = true;
        hideReturn = true;
    }

    const card = document.createElement('div');
    card.classList.add("gear-card");
    card.setAttribute('data-id', data.id);
    const translatedGearType = gearTypeTranslations[data.gearType] || data.gearType;
    const translatedFencingType = fencingTypeTranslations[data.fencingType] || data.fencingType;
    // 📌 사용자 권한 확인
    fetch('/v2/api/auth')
        .then(response => response.json())
        .then(authInfo => {
            const roles = new Set(authInfo.data.role);

            if (roles.has("ADMIN")) {
                card.innerHTML = `
                    <div class="gear-info">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <h3 class="gear-num" style="margin: 0;">${data.num}</h3>
                            <p style="margin: 0;">${translatedFencingType} ${translatedGearType}</p>
                        </div>
                        ${status}
                    </div>
                    <div class="button-container">
                        <button class="rent-button" onclick="rentGear('${data.id}')" ${hideRental ? 'disabled' : ''}>대여</button>
                        <button class="return-button" onclick="returnGear('${data.id}')" ${hideReturn ? 'disabled' : ''}>반납</button>
                        <button class="delete-button" onclick="deleteGear('${data.id}')">&times;</button>
                    </div>
                    `;
            } else if(roles.has("USER")){
                card.innerHTML = `
                    <div class="gear-info">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <h3 class="gear-num" style="margin: 0;">${data.num}</h3>
                            <p style="margin: 0;">${translatedFencingType} ${translatedGearType}</p>
                        </div>
                        ${status}
                    </div>
                    <div class="button-container">
                        <button class="rent-button" onclick="rentGear('${data.id}')" ${hideRental ? 'disabled' : ''}>대여</button>
                        <button class="return-button" onclick="returnGear('${data.id}')" ${hideReturn ? 'disabled' : ''}>반납</button>
                    </div>
                `;
            } else{
                card.innerHTML = `
                    <div class="gear-info">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <h3 class="gear-num" style="margin: 0;">${data.num}</h3>
                            <p style="margin: 0;">${translatedFencingType} ${translatedGearType}</p>
                        </div>
                        ${status}
                    </div>
                `;
            }
        })
        .catch(error => console.error('Error fetching user status:', error));

    document.querySelector(".gear-list").appendChild(card);
}


function returnGear(gearId){
    fetch(`/v2/api/gear/${gearId}/return`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            return response.json(); // 정상 응답인 경우 JSON 데이터를 반환
        } else {
            return response.json().then(errResponse => {
                throw new Error(errResponse.description); // 첫 번째 에러 메시지 반환
            })
        }
    })
        .then(result => {
            alert('장비가 반납되었습니다.');
            window.location.href="/gear"
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message); // 에러 메시지 alert
        });
}

function rentGear(gearId){
    fetch(`/v2/api/gear/${gearId}/rental`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            return response.json(); // 정상 응답인 경우 JSON 데이터를 반환
        } else {
            return response.json().then(errResponse => {
                throw new Error(errResponse.description); // 첫 번째 에러 메시지 반환
            })
        }
    })
        .then(result => {
            alert('장비를 대여하였습니다.');
            window.location.href="/gear"
        })
        .catch(error => {
            alert(error.message); // 에러 메시지 alert
        });
}

async function deleteGear(gearId){
    if (!confirm("장비를 삭제하시겠습니까?")) return;
    try {
        const response = await fetch(`/v2/api/gear/${gearId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("삭제 실패");
        alert("장비가 삭제되었습니다.");
        location.reload();
    } catch (error) {
        console.error("삭제 중 오류 발생:", error);
        alert("현재 대여중인 장비입니다.");
    }
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${year}년 ${month}월 ${day}일`;
}

