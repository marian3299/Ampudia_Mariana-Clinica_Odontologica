window.addEventListener('load', function () {
    (function () {
        const url = '/turnos';
        const settings = {
            method: "GET"
        }

        fetch(url, settings)
            .then(response => { if (response.ok) return response.json() })
            .then(data => {
                const table = document.getElementById('turno-table');
                const tableBody = document.querySelector('.turno-table-body');

                data.forEach(turno => {
                    const {id, paciente, odontologo, fecha} = turno;
                    let dateTimeString = fecha;
                    let [date,time] = dateTimeString.split('T');

                    const row = document.createElement('tr');
                    const cellId = document.createElement('td');
                    const cellPaciente = document.createElement('td');
                    const cellOdontologo = document.createElement('td');
                    const cellFecha = document.createElement('td');
                    const cellHora = document.createElement('td');
                    const cellBtns = document.createElement('td');
                    const btnsContainer = document.createElement('div');
                    const btnDelete = document.createElement('button');
                    const btnUpdate = document.createElement('button');

                    cellId.textContent = id;
                    cellPaciente.textContent = `${paciente.nombre} ${paciente.apellido}`;
                    cellOdontologo.textContent = `${odontologo.nombre} ${odontologo.apellido}`;
                    cellFecha.textContent = date;
                    cellHora.textContent = time;
                    btnDelete.textContent = "Elminar";
                    btnUpdate.textContent = "Actualizar";

                    btnDelete.setAttribute('id', id);
                    btnDelete.setAttribute('class', 'eliminar turno-eliminar');

                    btnUpdate.setAttribute('id', id);
                    btnUpdate.setAttribute('class', 'actualizar turno-actualizar');

                    btnsContainer.setAttribute('class', 'btn-container');

                    btnsContainer.appendChild(btnUpdate);
                    btnsContainer.appendChild(btnDelete);

                    cellBtns.appendChild(btnsContainer);

                    row.appendChild(cellId);
                    row.appendChild(cellPaciente);
                    row.appendChild(cellOdontologo);
                    row.appendChild(cellFecha);
                    row.appendChild(cellHora);
                    row.appendChild(cellBtns);


                    tableBody.appendChild(row);
                    table.appendChild(tableBody);
                })
                deleteTurno();
                updateTurno();
            })
    })();

    function deleteTurno(){
        const btns = document.querySelectorAll('.turno-eliminar');

        btns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.target.id;
                const url = `/turnos/${id}`;

                const settings = {
                    method: "DELETE"
                }

                fetch(url, settings)
                    .then(response => {
                        if (response.ok) {
                            console.log(`Turno con id ${id} eliminado`);
                            console.log(e.target);

                            const row = e.target.closest('tr');
                            console.log(row);
                            row.parentNode.removeChild(row);
                        } else {
                            console.error(`No se pudo eliminar el turno con id ${id}`);
                        }
                    })
                    .catch(err => console.error(err))
            })
        })
    }

    function updateTurno() {
        const btns = document.querySelectorAll('.turno-actualizar');
        const formUpdate = document.querySelector('.actualizar-turno');
        const selectPacientes = document.getElementById('paciente-update-select');
        const selectOdontologos = document.getElementById('odontologo-update-select');
        const url = "/turnos";
        const urlPacientes = '/pacientes';
        const urlOdontologos = '/odontologos';



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
                    const { id, nombre, apellido } = paciente;
                    const optionPaciente = document.createElement('option');
                    optionPaciente.setAttribute('id', id);
                    optionPaciente.setAttribute('value', `${nombre} ${apellido}`);
                    optionPaciente.textContent = `${nombre} ${apellido}`;
                    selectPacientes.appendChild(optionPaciente);
                })

                odontologos.forEach(odontologo => {
                    const { id, nombre, apellido } = odontologo;
                    const optionOdontologo = document.createElement('option');
                    optionOdontologo.setAttribute('id', id);
                    optionOdontologo.setAttribute('value', `${nombre} ${apellido}`);
                    optionOdontologo.textContent = `${nombre} ${apellido}`;
                    selectOdontologos.appendChild(optionOdontologo);

                })

                formUpdate.addEventListener('submit', (e) => {
                    const id = formUpdate.getAttribute('data-id');
                    const fecha = document.getElementById('turno-update-date').value;
                    const hora = document.getElementById('turno-update-time').value;

                    const data = {
                        id: id,
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
                        method: "PUT",
                        body: JSON.stringify(data),
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }

                    fetch(url, settings)
                        .then(response => {
                            if (response.ok) return response.text();
                            throw new Error('Network response was not ok');
                        })
                        .then(text => {
                            console.log(text);
                            formUpdate.classList.add('actualizar-turno-hide');
                        })
                        .catch(error => console.error('Error:', error));

                })
            })

        btns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                formUpdate.classList.remove('actualizar-turno-hide');
                const id = e.target.id;

                formUpdate.setAttribute('data-id', id);

                fetch(url + `/${id}`)
                .then(response => {
                    if (response.ok) return response.json();
                    throw new Error('Network response was not ok');
                })
                .then(data => {
                    document.getElementById('turno-update-date').value = data.fecha.split('T')[0];
                    document.getElementById('turno-update-time').value = data.fecha.split('T')[1].slice(0, 5);

                    for (let i = 0; i < selectPacientes.options.length; i++) {
                        if (selectPacientes.options[i].id == data.paciente.id) {
                            selectPacientes.selectedIndex = i;
                            break;
                        }
                    }

                    for (let i = 0; i < selectOdontologos.options.length; i++) {
                        if (selectOdontologos.options[i].id == data.odontologo.id) {
                            selectOdontologos.selectedIndex = i;
                            break;
                        }
                    }
                })
                .catch(error => console.error('Error:', error));


            })
        })
    }


})