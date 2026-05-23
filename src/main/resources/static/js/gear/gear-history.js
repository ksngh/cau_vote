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

const fencingTypeTranslations = {
    "SABRE": "사브르",
    "FLUERET": "플뢰레",
    "COMMON": "공용"
};

let cursorId = null;
const size = 15;
let isLoading = false;
let hasNext = true;

async function fetchRentalGears() {
    if (isLoading || !hasNext) return;
    isLoading = true;


    try {
        const response = await fetch(`/v2/api/rental-gear/history?cursor=${cursorId || ''}&size=${size}`);
        if (!response.ok) {
            throw new Error('데이터를 불러오지 못했습니다.');
        }

        const rentalGearList = await response.json();
        let rentGearDetails = rentalGearList.data.content;

        hasNext = !rentalGearList.data.last;
        if (rentGearDetails.length > 0) {
            cursorId = rentGearDetails[rentGearDetails.length - 1].rentalDate;
        }

        if (!hasNext) {
            window.removeEventListener('scroll', handleScroll);
        }

        renderTable(rentGearDetails);
    } catch (error) {
        console.error('오류 발생:', error);
        if (!cursorId) {
            document.getElementById('rental-gear-list').innerHTML =
                `<tr><td colspan="6" class="empty-message">대여 장비 기록이 없습니다.</td></tr>`;
        }
    } finally {
        isLoading = false;
    }
}


function renderTable(rentalGears) {
    const tableBody = document.getElementById('rental-gear-list');
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
        const returnDate = gear.returnDate ? new Date(gear.returnDate).toLocaleDateString() : '-';

        const row = `
            <tr>
                <td>${fencingTypeKor}</td>
                <td>${gear.num}</td>
                <td>${gearTypeKor}</td>
                <td>${gear.studentName}</td>
                <td>${rentalDate}</td>
                <td>${returnDate}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

function handleScroll() {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        fetchRentalGears();
    }
}

window.addEventListener('scroll', handleScroll);
document.addEventListener('DOMContentLoaded', fetchRentalGears);
