function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}

document.querySelectorAll('input[type="radio"]').forEach((radio) => {
    radio.addEventListener('click', (event) => {
        event.stopPropagation(); // 클릭 이벤트가 부모 요소로 전파되는 것을 막음
    });
});



function openCard(card) {
    // 카드의 ID 가져오기
    const id = card.getAttribute('data-id');
    const content = document.getElementById(`content-${id}`);

    // 카드 내용 토글
    if (content.style.display === "none" || content.style.display === "") {
        content.style.display = "block";
    } else {
        content.style.display = "none";
    }
}