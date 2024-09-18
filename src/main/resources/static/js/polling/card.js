function toggleContent(card) {
    const content = card.querySelector('.content');
    content.style.display = (content.style.display === 'block') ? 'none' : 'block';
}