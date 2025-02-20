const gearTypeOptions = {
    "SABRE": ["MASK", "SWORD", "GLOVE", "METAL"],
    "FLUERET": ["MASK", "SWORD", "GLOVE", "METAL"],
    "COMMON": ["UNIFORM_TOP", "UNIFORM_BOTTOM", "BODY_WIRE", "MASK_WIRE", "OTHERS"]
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
    "OTHERS": "기타"
};

const fencingTypeTranslations = {
    "SABRE": "사브르",
    "FLUERET": "플뢰레",
    "COMMON": "공용"
};

let cursorId = null; // 마지막으로 가져온 cursorId
const size = 15; // 한 번에 불러올 개수
let isLoading = false; // 현재 데이터 로딩 중 여부
let hasNext = true; // 다음 페이지 여부

async function fetchRentalGears() {
    if (isLoading || !hasNext) return; // 이미 로딩 중이거나 데이터가 없으면 중단
    isLoading = true;

    try {
        const response = await fetch(`/v2/api/rental-gear?cursor=${cursorId || ''}&size=${size}`);

        if (!response.ok) {
            throw new Error('데이터를 불러오지 못했습니다.');
        }

        const rentalGearList = await response.json();
        const rentGearDetails = rentalGearList.data.content
        renderTable(rentGearDetails); // 데이터 삽입
        hasNext = rentalGearList.hasNext; // 다음 페이지 여부 업데이트
        cursorId = rentGearDetails.length > 0 ? rentGearDetails[rentGearDetails.length - 1].gearId : null;
    } catch (error) {
        console.error('오류 발생:', error);
        document.getElementById('rental-gear-list').innerHTML =
            `<tr><td colspan="5" class="empty-message">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>`;
    } finally {
        isLoading = false;
    }
}

// 테이블에 데이터를 동적으로 추가하는 함수
function renderTable(rentalGears) {
    const tableBody = document.getElementById('rental-gear-list');

    // 첫 로딩 메시지 제거
    document.getElementById('loading-row')?.remove();

    if (rentalGears.length === 0 && !cursorId) {
        tableBody.innerHTML =
            `<tr><td colspan="5" class="empty-message">대여 중인 장비가 없습니다.</td></tr>`;
        return;
    }

    rentalGears.forEach(gear => {
        const fencingTypeKor = fencingTypeTranslations[gear.fencingType] || gear.fencingType;
        const gearTypeKor = gearTypeTranslations[gear.gearType] || gear.gearType;
        const rentalDate = gear.rentalDate ? new Date(gear.rentalDate).toLocaleDateString() : '-';

        const row = `
                    <tr>
                        <td>${fencingTypeKor}</td>
                        <td>${gear.num}</td>
                        <td>${gearTypeKor}</td>
                        <td>${gear.studentName}</td>
                        <td>${rentalDate}</td>
                    </tr>
                `;
        tableBody.innerHTML += row;
    });
}

// 스크롤 이벤트 감지하여 데이터 추가 로드
window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        fetchRentalGears(); // 스크롤이 거의 끝에 도달하면 새로운 데이터 로드
    }
});

// 페이지 로드 시 첫 데이터 요청
document.addEventListener('DOMContentLoaded', fetchRentalGears);