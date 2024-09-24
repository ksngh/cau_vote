document.getElementById('hamburger').addEventListener('click', function() {
    const menu = document.getElementById('menu');
    menu.classList.toggle('active'); // 메뉴의 활성화 상태를 토글
});