// 모달 요소
const modal = document.getElementById("loginModal");
const loginBtn = document.getElementById("loginBtn");
const closeBtn = document.getElementById("closeBtn");

// 로그인 버튼 클릭 시 모달 열기
loginBtn.onclick = function () {
    modal.style.display = "block";
}

// 모달 닫기 버튼 클릭 시 모달 닫기
closeBtn.onclick = function () {
    modal.style.display = "none";
}

// 모달 외부 클릭 시 모달 닫기
window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

function checkUserStatus() {
    fetch('/api/student')
        .then(response => response.json())
        .then(data => {
            if (data.name) {
                // 로그인한 경우 햄버거 버튼 표시
                const nav = document.getElementById('nav-header');

                nav.insertAdjacentHTML('beforeend', `
                    <button class="menu-trigger">
                        <span></span>
                        <span></span>
                        <span></span>
                    </button>
                `);

                const menuTrigger = nav.querySelector('.menu-trigger');
                menuTrigger.addEventListener('click', function (event) {
                    event.preventDefault(); // 링크의 기본 동작 방지
                    const menu = document.getElementById('menu');

                    if (data.role === "ROLE_USER") {
                        menu.innerHTML = ` 
                            <li><a href="/mypage">나의 투표 현황</a></li>
                            <li><a href="/logout">로그아웃</a></li>`
                    } else if (data.role === "ROLE_ADMIN") {
                        menu.innerHTML = ` 
                            <li><a href="/admin/posting">투표 생성하기</a></li>
                            <li><a href="/logout">로그아웃</a></li>`
                    }
                    const menuContainer = document.querySelector('.menu');
                    menuContainer.classList.toggle('active'); // 메뉴의 활성화 상태 토글
                });


                // 햄버거 버튼을 nav에 추가
                loginBtn.style.display = 'none';
            } else {
                // 로그인하지 않은 경우 로그인 버튼 표시
                loginBtn.style.display = 'block';
            }
        })
        .catch(error => console.error('Error fetching user status:', error));
}

document.addEventListener('DOMContentLoaded', (event) => {
    checkUserStatus();
});
