window.addEventListener('load', function(){
    const btnLogout = document.getElementById('btnLogout');

    btnLogout.addEventListener('click', (e) => {
        window.location.href = 'http://localhost:8080/logout';
    })
})