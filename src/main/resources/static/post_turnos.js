window.addEventListener('load', function () {
    const url = '/turnos';
    const urlPacientes = '/pacientes/pacientesDTO';
    const urlOdontologos = '/odontologos';
    const form = document.querySelector('.turno-form');
    const selectPacientes = document.getElementById('paciente-select');
    const selectOdontologos = document.getElementById('odontologo-select');

    Promise.all([
        fetch(urlPacientes),
        fetch(urlOdontologos)
    ])
    .then(responses => {
        return Promise.all(responses.map(response => {
            if (response.ok) return response.json();
            throw new Error('Network response was not ok');
        }));
    })
    .then(data => {
        const [pacientes, odontologos] = data;

        pacientes.forEach(paciente => {
            const {id, nombre, apellido} = paciente;
            const optionPaciente = document.createElement('option');
            optionPaciente.setAttribute('id', id);
            optionPaciente.setAttribute('value', `${nombre} ${apellido}`);
            optionPaciente.textContent = `${nombre} ${apellido}`;
            selectPacientes.appendChild(optionPaciente);
        })

        odontologos.forEach(odontologo => {
            const {id, nombre, apellido} = odontologo;
            const optionOdontologo = document.createElement('option');
            optionOdontologo.setAttribute('id', id);
            optionOdontologo.setAttribute('value', `${nombre} ${apellido}`);
            optionOdontologo.textContent = `${nombre} ${apellido}`;
            selectOdontologos.appendChild(optionOdontologo);

        })

        form.addEventListener('submit', (e) => {
            e.preventDefault();
            const fecha = document.getElementById('turno-date').value;
            const hora = document.getElementById('turno-time').value;

            const data = {
                paciente: {
                    id: Number(selectPacientes.options[selectPacientes.selectedIndex].id)
                },
                odontologo: {
                    id: Number(selectOdontologos.options[selectOdontologos.selectedIndex].id)
                },
                fecha: `${fecha}T${hora}:00`
            }

            console.log(data);

            const settings = {
                method: "POST",
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            }

            fetch(url, settings)
                .then(response => { if (response.ok) return response.json() })
                .then(data => {
                    console.log(data);
                    resetForm();
                })

        })
    })


    function resetForm() {
        document.getElementById('turno-date').value = "";
        document.getElementById('turno-time').value = "";
        selectPacientes.selectedIndex = 0;
        selectOdontologos.selectedIndex = 0;
    }




})