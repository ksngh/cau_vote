const fencingTypeTranslations = {
    "SABRE": "사브르",
    "FLUERET": "플뢰레",
    "COMMON": "공용"
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
    "MASK_WIRE": "마스크 와이어"
    // "OTHERS": "기타"
};

getMyGear();
fetchLateFee();

function getMyGear() {
    // 1️⃣ 장비 목록 불러오기
    fetch(`/v2/api/student/gear`)
        .then(response => response.json())
        .then(result => {
            let gearList = result.data.content;

            // 2️⃣ "IN_USE" 상태인 장비만 대여 정보 요청 (Promise.all 사용)
            const rentalPromises = gearList.map(gear => {
                return fetchRentalInfo(gear.id).then(rentalInfo => ({...gear, ...rentalInfo}));
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
}

// 📌 대여 정보 가져오는 함수
function fetchRentalInfo(gearId) {
    return fetch(`/v2/api/gear/${gearId}/rental`)
        .then(response => response.json())
        .then(rentalData => rentalData.data) // { renter: "김성호", expectedReturnDate: "2025-02-26" }
        .catch(error => {
            console.error(`Error fetching rental info for gear ${gearId}:`, error);
            return {renter: "알 수 없음", expectedReturnDate: "미정"};
        });
}

// 📌 장비 카드 생성 함수
function gearCard(data) {
    let hideRental = false;
    let hideReturn = false;
    let status = '';


    status = `<p class="gear-status in-use">대여 중 <span class="black-text">(대여자 : ${data.studentName})</span></p>
          <p class="gear-status">예상 반납일: ${formatDate(new Date(data.dueDate))}</p>`;
    hideRental = true;


    const card = document.createElement('div');
    card.classList.add("gear-card");
    card.setAttribute('data-id', data.id);

    const translatedGearType = gearTypeTranslations[data.gearType] || data.gearType;
    const translatedFencingType = fencingTypeTranslations[data.fencingType] || data.fencingType;

    card.innerHTML = `
                    <div class="gear-info">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <h3 class="gear-num" style="margin: 0;">${data.num}</h3>
                            <p style="margin: 0;">${translatedFencingType} ${translatedGearType}</p>
                        </div>
                        ${status}
                    </div>
                    </div>
                    <div class="button-container">
                        <button class="rent-button" onclick="rentGear('${data.id}')" ${hideRental ? 'disabled' : ''}>대여</button>
                        <button class="return-button" onclick="returnGear('${data.id}')" ${hideReturn ? 'disabled' : ''}>반납</button>
                    </div>
                    `;


    document.querySelector(".gear-list").appendChild(card);
}


function returnGear(gearId) {
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
                throw new Error(errResponse.errorList[0]); // 첫 번째 에러 메시지 반환
            })
        }
    })
        .then(result => {
            alert('장비가 반납되었습니다.');
            window.location.href = "/mypage/gear"
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message); // 에러 메시지 alert
        });
}


function formatDate(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${year}년 ${month}월 ${day}일`;
}

function fetchLateFee() {
    fetch("/v2/api/student/late-fee") // 백엔드 API 호출
        .then(response => {
            if (!response.ok) {
                throw new Error("서버 응답 오류");
            }
            return response.json();
        })
        .then(info => {
            displayLateFees(info.data.lateFee); // 화면에 데이터 표시
        })
        .catch(error => console.error("연체료 데이터를 가져오는 중 오류 발생:", error));
}

function displayLateFees(lateFee) {
    const lateFeeContainer = document.getElementById("lateFeeContainer");
    lateFeeContainer.textContent = `연체료 : ${lateFee}원`;
}
