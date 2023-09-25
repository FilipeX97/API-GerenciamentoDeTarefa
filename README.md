# API-GerenciamentoDeTarefa
API para gerenciamento de tarefas

# Requisições:

## GET

### /pessoas

Listar pessoas trazendo nome, departamento e total horas gastas nas tarefas.

-------------------

### /pessoas/gastos

Buscar pessoas por nome e período, retorna média de horas gastas por tarefa.

Body Request:

`{
  "nome": "Camila",
  "dataInicio": "2021-09-01",
  "dataFim": "2023-09-15"
}`

-------------------

### /tarefas/pendentes

Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos.

-------------------

### /departamentos

Listar departamento e quantidade de pessoas e tarefas.

### POST

### /pessoas

Adicionar uma pessoa.

Body Request:

`{
    "nome": "Miguel",
    "idDepartamento": 1
}`

-------------------

### /tarefas

Adicionar uma tarefa.

Body Request:

`{
  "titulo": "Validar NF Outubro",
  "descricao": "Validar notas recebidas no mês de Outubro",
  "prazo": "2022-02-15",
  "idDepartamento": 2,
  "duracao": null,
  "idPessoa": null,
  "finalizado": false
}`

### PUT

### /pessoas/{id}

Alterar dados de uma pessoa.

Body Request:

`{
    "nome": "Filipe"
}`

-------------------

### /tarefas/alocar/{id}

Alocar uma pessoa na tarefa que tenha o mesmo departamento.

Body Request:

`{
    "idTarefa": 1011
}`

-------------------

### /tarefas/finalizar/{id}

Finalizar a tarefa.

-------------------

### DELETE

### /pessoas/{id}

Remover uma pessoa.




