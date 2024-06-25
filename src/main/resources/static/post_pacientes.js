window.addEventListener('load', function(){
    const url = "/pacientes";
    const form = document.querySelector('.paciente-form');

    form.addEventListener('submit', (e) =>{
        e.preventDefault();

        const data = {
            nombre: document.getElementById('paciente-nombre').value,
            apellido: document.getElementById('paciente-apellido').value,
            cedula: document.getElementById('paciente-cedula').value,
            fechaIngreso: document.getElementById('paciente-fechaIngreso').value,
            domicilio: {
                calle: document.getElementById('paciente-calle').value,
                numero: document.getElementById('paciente-numero').value,
                localidad: document.getElementById('paciente-localidad').value,
                provincia: document.getElementById('paciente-provincia').value,
            },
        }

       const settings = {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
       }

       fetch(url,settings)
        .then(response => { if (response.ok) return response.json() })
        .then(data => {
            console.log(data);
            resetForm();
        })

    })

    function resetForm(){
        document.getElementById('paciente-nombre').value = "";
        document.getElementById('paciente-apellido').value = "";
        document.getElementById('paciente-cedula').value = "";
        document.getElementById('paciente-fechaIngreso').value = "";
        document.getElementById('paciente-calle').value = "";
        document.getElementById('paciente-numero').value = "";
        document.getElementById('paciente-localidad').value = "";
        document.getElementById('paciente-provincia').value = "";
    }


})