window.addEventListener('load', function () {
    (function () {
        const url = "/pacientes";
        const settings = {
            method: "GET"
        }

        fetch(url, settings)
            .then(response => { if (response.ok) return response.json() })
            .then(data => {
                const table = document.getElementById('paciente-table');
                const tableBody = document.querySelector('.paciente-table-body');
                console.log(data)

                data.forEach(paciente => {
                    const { id, nombre, apellido, cedula, fechaIngreso, domicilio } = paciente;
                    const row = document.createElement('tr');
                    const cellId = document.createElement('td');
                    const cellNombre = document.createElement('td');
                    const cellApellido = document.createElement('td');
                    const cellCedula = document.createElement('td');
                    const cellFechaIngreso = document.createElement('td');
                    const cellDomicilio = document.createElement('td');
                    const cellBtns = document.createElement('td');
                    const btnsContainer = document.createElement('div');
                    const btnDelete = document.createElement('button');
                    const btnUpdate = document.createElement('button');

                    cellId.textContent = id;
                    cellNombre.textContent = nombre;
                    cellApellido.textContent = apellido;
                    cellCedula.textContent = cedula;
                    cellFechaIngreso.textContent = fechaIngreso;
                    cellDomicilio.textContent = `${domicilio.calle} ${domicilio.numero}, ${domicilio.localidad}, ${domicilio.provincia}`
                    btnDelete.textContent = "Elminar";
                    btnUpdate.textContent = "Actualizar";

                    btnDelete.setAttribute('id', id);
                    btnDelete.setAttribute('class', 'eliminar paciente-eliminar');

                    btnUpdate.setAttribute('id', id);
                    btnUpdate.setAttribute('class', 'actualizar paciente-actualizar');

                    btnsContainer.setAttribute('class', 'btn-container');

                    btnsContainer.appendChild(btnUpdate);
                    btnsContainer.appendChild(btnDelete);

                    cellBtns.appendChild(btnsContainer);

                    row.appendChild(cellId);
                    row.appendChild(cellNombre);
                    row.appendChild(cellApellido);
                    row.appendChild(cellCedula);
                    row.appendChild(cellFechaIngreso);
                    row.appendChild(cellDomicilio);
                    row.appendChild(cellBtns);


                    tableBody.appendChild(row);
                    table.appendChild(tableBody);

                })

                deletePaciente();
                updatePacientes();
            })
    })();

    function deletePaciente() {
            const btns = document.querySelectorAll('.paciente-eliminar');

            btns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.target.id;
                    const url = `/pacientes/${id}`
                    console.log(id);

                    const settings = {
                        method: "DELETE"
                    }

                    fetch(url, settings)
                        .then(response => {
                            if (response.ok) {
                                console.log(`Paciente con id ${id} eliminado`);
                                console.log(e.target);

                                const row = e.target.closest('tr');
                                console.log(row);
                                row.parentNode.removeChild(row);
                            } else {
                                console.error(`No se pudo eliminar el paciente con id ${id}`);
                            }
                        })
                        .catch(err => console.error(err))
                });
            });
        }

    function updatePacientes() {
            const btns = document.querySelectorAll('.paciente-actualizar');
            const formUpdate = document.querySelector('.paciente-update-form');
            const url = "/pacientes";

            function fillForm(data) {
                const { nombre, apellido, cedula, fechaIngreso, domicilio } = data;
                document.getElementById('paciente-update-nombre').value = nombre;
                document.getElementById('paciente-update-apellido').value = apellido;
                document.getElementById('paciente-update-cedula').value = cedula;
                document.getElementById('paciente-update-fechaIngreso').value = fechaIngreso;
                document.getElementById('paciente-update-calle').value = domicilio.calle;
                document.getElementById('paciente-update-numero').value = domicilio.numero;
                document.getElementById('paciente-update-localidad').value = domicilio.localidad;
                document.getElementById('paciente-update-provincia').value = domicilio.provincia;
            }

            formUpdate.addEventListener('submit', (e) => {
                const id = formUpdate.getAttribute('data-id');

                const data = {
                    id: Number(id),
                    nombre: document.getElementById('paciente-update-nombre').value,
                    apellido: document.getElementById('paciente-update-apellido').value,
                    cedula: document.getElementById('paciente-update-cedula').value,
                    fechaIngreso: document.getElementById('paciente-update-fechaIngreso').value,
                    domicilio: {
                        calle: document.getElementById('paciente-update-calle').value,
                        numero: document.getElementById('paciente-update-numero').value,
                        localidad: document.getElementById('paciente-update-localidad').value,
                        provincia: document.getElementById('paciente-update-provincia').value,
                    },
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
                        formUpdate.classList.add('actualizar-paciente-hide');
                    })
                    .catch(error => console.error('Error:', error));
            })


            btns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    formUpdate.classList.remove('actualizar-paciente-hide');
                    const id = e.target.id;

                    formUpdate.setAttribute('data-id', id);

                    fetch(`${url}/${id}`)
                        .then(response => {
                            if (response.ok) return response.json();
                            throw new Error('Network response was not ok');
                        })
                        .then(data => {
                            console.log(data);
                            fillForm(data);
                        })
                        .catch(error => console.error('Error:', error));
                })
            })

        }


})