window.addEventListener('load', function () {
    (function () {
        const url = "/odontologos";
        const settings = {
            method: "GET"
        }

        fetch(url, settings)
            .then(response => { if (response.ok) return response.json() })
            .then(data => {
                const table = document.getElementById('odontologo-table');
                const tableBody = document.querySelector('.odontologo-table-body');

                data.forEach(odontologo => {
                    const row = document.createElement('tr');
                    const cellId = document.createElement('td');
                    const cellMatricula = document.createElement('td');
                    const cellNombre = document.createElement('td');
                    const cellApellido = document.createElement('td');
                    const cellBtns = document.createElement('td');
                    const btnsContainer = document.createElement('div');
                    const btnDelete = document.createElement('button');
                    const btnUpdate = document.createElement('button');

                    cellId.textContent = odontologo.id;
                    cellMatricula.textContent = odontologo.matricula;
                    cellNombre.textContent = odontologo.nombre;
                    cellApellido.textContent = odontologo.apellido;
                    btnDelete.textContent = "Elminar";
                    btnUpdate.textContent = "Actualizar";

                    btnDelete.setAttribute('id', odontologo.id);
                    btnDelete.setAttribute('class', 'eliminar odontologo-eliminar');

                    btnUpdate.setAttribute('id', odontologo.id);
                    btnUpdate.setAttribute('class', 'actualizar odontologo-actualizar');

                    btnsContainer.setAttribute('class', 'btn-container');

                    cellMatricula.setAttribute('class', 'matricula');
                    cellApellido.setAttribute('class', 'apellido');
                    cellNombre.setAttribute('class', 'nombre');

                    btnsContainer.appendChild(btnUpdate);
                    btnsContainer.appendChild(btnDelete);

                    cellBtns.appendChild(btnsContainer);

                    row.appendChild(cellId);
                    row.appendChild(cellMatricula);
                    row.appendChild(cellNombre);
                    row.appendChild(cellApellido);
                    row.appendChild(cellBtns);

                    tableBody.appendChild(row);
                    table.appendChild(tableBody);


                })
                addDeleteEventListeners();
                updateOdontologos();
            })
    })();

    function addDeleteEventListeners() {
        const btns = document.querySelectorAll('.odontologo-eliminar');

        btns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.target.id;
                const url = `odontologos/${id}`
                console.log(id);

                const settings = {
                    method: "DELETE"
                }

                fetch(url, settings)
                    .then(response => {
                        if (response.ok) {
                            console.log(`Odontologo con id ${id} eliminado`);
                            console.log(e.target);

                            const row = e.target.closest('tr');
                            console.log(row);
                            row.parentNode.removeChild(row);
                        } else {
                            console.error(`No se pudo eliminar el odontologo con id ${id}`);
                        }
                    })
                    .catch(err => console.error(err))
            });
        });
    }

    function updateOdontologos() {
        const btns = document.querySelectorAll('.odontologo-actualizar');
        const formUpdate = document.querySelector('.actualizar-odontologo');
        const url = "/odontologos";

        formUpdate.addEventListener('submit', (e) => {
            const id = formUpdate.getAttribute('data-id');

            const data = {
                id: Number(id),
                matricula: document.getElementById('odontologo-update-matricula').value,
                nombre: document.getElementById('odontologo-update-nombre').value,
                apellido: document.getElementById('odontologo-update-apellido').value
            }

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
                    resetForm();
                    formUpdate.classList.add('actualizar-odontologo-hide');
                })
                .catch(error => console.error('Error:', error));
        })


        btns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                formUpdate.classList.remove('actualizar-odontologo-hide');
                const id = e.target.id;

                formUpdate.setAttribute('data-id', id);

                const row = e.target.closest('tr');
                document.getElementById('odontologo-update-matricula').value = row.querySelector('.matricula').textContent;
                document.getElementById('odontologo-update-nombre').value = row.querySelector('.nombre').textContent;
                document.getElementById('odontologo-update-apellido').value = row.querySelector('.apellido').textContent;
            })
        })



    }

    function resetForm() {
        document.getElementById('odontologo-update-matricula').value = "";
        document.getElementById('odontologo-update-nombre').value = "";
        document.getElementById('odontologo-update-apellido').value = "";
    }



})