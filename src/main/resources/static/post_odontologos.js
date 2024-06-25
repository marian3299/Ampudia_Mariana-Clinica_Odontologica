window.addEventListener('load', function(){
    const url = "/odontologos";
    const form = document.querySelector('.odontologo-form');

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        console.log('crear odontologo');

        const data = {
            matricula: document.getElementById('odontologo-matricula').value,
            nombre: document.getElementById('odontologo-nombre').value,
            apellido: document.getElementById('odontologo-apellido').value
        }

       const settings = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
       }

       fetch(url,settings)
        .then(response => { if (response.ok) return response.json() })
        .then(data => {
            console.log(data);
            resetForm();
        })


    })

    function resetForm(){
        document.getElementById('odontologo-matricula').value = "";
        document.getElementById('odontologo-nombre').value = "";
        document.getElementById('odontologo-apellido').value = "";
    }


})