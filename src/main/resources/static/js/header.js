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
    fetch('/v2/api/auth')
        .then(response => response.json())
        .then(authInfo => {
            const roles = new Set(authInfo.data.role)
            if (roles) {
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

                    if (roles.has("USER")) {
                        menu.innerHTML = ` 
                            <div class="menu-header">
                                <button class="close-menu" id="close-menu">&times;</button>
                            </div>
                            <li><a href="/mypage">나의 투표 현황</a></li>
                            <li><a href="/logout">로그아웃</a></li>`
                    } else if (roles.has("ADMIN")) {
                        menu.innerHTML = `
                            <div class="menu-header">
                                <button class="close-menu" id="close-menu">&times;</button>
                            </div>  
                            <li><a href="/admin/posting">투표 생성하기</a></li>
                            <li><a href="/logout">로그아웃</a></li>`
                    }
                    const menuContainer = document.querySelector('.menu');
                    menuContainer.classList.toggle('active'); // 메뉴의 활성화 상태 토글

                    const overlay = document.getElementById('overlay');
                    overlay.classList.toggle('active');

                    const closeMenuButton = document.getElementById('close-menu');
                    closeMenuButton.addEventListener('click', function () {
                        menuContainer.classList.remove('active'); // 메뉴 비활성화
                        overlay.classList.remove('active'); // 오버레이 비활성화
                    });

                    document.addEventListener('click', function (event) {
                        if (menuContainer.classList.contains('active') && !menuContainer.contains(event.target) && !menuTrigger.contains(event.target)) {
                            menuContainer.classList.remove('active'); // 메뉴 비활성화
                            overlay.classList.remove('active'); // 오버레이 비활성화
                        }
                    });
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


