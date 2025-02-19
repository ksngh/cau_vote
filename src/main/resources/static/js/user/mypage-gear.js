getMyGear();

function getMyGear(){
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

    // 📌 사용자 권한 확인
    fetch('/v2/api/auth')
        .then(response => response.json())
        .then(authInfo => {
            const roles = new Set(authInfo.data.role);

            card.innerHTML = `
                    <div class="gear-info">
                        <h3 class="gear-num">${data.num} </h3>
                        ${status}
                    </div>
                    <div class="button-container">
                        <button class="rent-button" onclick="rentGear('${data.id}')" ${hideRental ? 'disabled' : ''}>대여</button>
                        <button class="return-button" onclick="returnGear('${data.id}')" ${hideReturn ? 'disabled' : ''}>반납</button>
                    </div>
                    `;
        })
        .catch(error => console.error('Error fetching user status:', error));

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

