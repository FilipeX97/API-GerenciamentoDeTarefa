use test_java;

db.createCollection("pessoas");
db.createCollection("tarefas");
db.createCollection("departamentos");

db.pessoas.insert([
    { "idPessoa": 1, "nome": "Camila", "idDepartamento": 1 },
    { "idPessoa": 2, "nome": "Pedro", "idDepartamento": 2 },
    { "idPessoa": 3, "nome": "Fabiano", "idDepartamento": 3 },
    { "idPessoa": 4, "nome": "Raquel", "idDepartamento": 3 },
    { "idPessoa": 5, "nome": "Patricia", "idDepartamento": 3 },
    { "idPessoa": 6, "nome": "Joaquim", "idDepartamento": 1 }
]);

db.departamentos.insert([
    { "idDepartamento": 1, "titulo": "Financeiro" },
    { "idDepartamento": 2, "titulo": "Comercial" },
    { "idDepartamento": 3, "titulo": "Desenvolvimento" }
]);

db.tarefa.insert([
    {
        "idTarefa": 1001,
        "titulo": "Validar NF Janeiro",
        "descricao": "Validar notas recebidas no mês de Janeiro",
        "prazo": "2022-02-15",
        "idDepartamento": 1,
        "duracao": 14,
        "idPessoa": 1,
        "finalizado": true
    },
    {
        "idTarefa": 1002,
        "titulo": "Bug 352",
        "descricao": "Corrigir bug 352 na versão 1.25",
        "prazo": "2022-05-10",
        "idDepartamento": 3,
        "duracao": 25,
        "idPessoa": null,
        "finalizado": false
    },
    {
        "idTarefa": 1003,
        "titulo": "Liberação da versão 1.24",
        "descricao": "Disponibilizar pacote para testes",
        "prazo": "2022-02-02",
        "idDepartamento": 3,
        "duracao": 2,
        "idPessoa": 3,
        "finalizado": false
    },
    {
        "idTarefa": 1004,
        "titulo": "Reunião A",
        "descricao": "Reunião com cliente A para apresentação do produto",
        "prazo": "2022-02-05",
        "idDepartamento": 2,
        "duracao": 5,
        "idPessoa": null,
        "finalizado": false
    },
    {
        "idTarefa": 1005,
        "titulo": "Reunião final",
        "descricao": "Fechamento contrato",
        "prazo": "2022-03-28",
        "idDepartamento": 2,
        "duracao": 6,
        "idPessoa": null,
        "finalizado": false
    },
    {
        "idTarefa": 1006,
        "titulo": "Pagamento 01/2022",
        "descricao": "Realizar pagamento dos fornecedores",
        "prazo": "2022-01-31",
        "idDepartamento": 1,
        "duracao": 6,
        "idPessoa": 1,
        "finalizado": true
    },
    {
        "idTarefa": 1007,
        "titulo": "Bug 401",
        "descricao": "Corrigir bug 401 na versão 1.20",
        "prazo": "2022-02-01",
        "idDepartamento": 3,
        "duracao": 2,
        "idPessoa": 4,
        "finalizado": true
    },
    {
        "idTarefa": 1008,
        "titulo": "Bug 399",
        "descricao": "Corrigir bug 399 na versão 1.20",
        "prazo": "2022-01-28",
        "idDepartamento": 3,
        "duracao": 6,
        "idPessoa": 5,
        "finalizado": true
    },
    {
        "idTarefa": 1009,
        "titulo": "Reunião B",
        "descricao": "Reunião com cliente B para apresentação do produto",
        "prazo": "2022-01-31",
        "idDepartamento": 2,
        "duracao": 5,
        "idPessoa": 2,
        "finalizado": true
    },
    {
        "idTarefa": 1010,
        "titulo": "Validar NF Fevereiro",
        "descricao": "Validar notas recebidas no mês de Fevereiro",
        "prazo": "2022-03-15",
        "idDepartamento": 1,
        "duracao": 14,
        "idPessoa": 6,
        "finalizado": false
    }
]);