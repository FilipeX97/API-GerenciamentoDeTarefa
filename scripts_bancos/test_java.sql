/* Criação das Tabelas */

CREATE TABLE departamento (
	id SERIAL PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL
);

CREATE TABLE pessoas (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	idDepartamento INT NOT NULL
);

CREATE TABLE tarefa (
	id INT PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL,
	descricao VARCHAR(150) NOT NULL,
	prazo DATE NOT NULL,
	idDepartamento INT NOT NULL,
	duracao INT NULL,
	idPessoa INT NULL,
	finalizado BOOLEAN NOT NULL,
	FOREIGN KEY (idDepartamento)
      REFERENCES departamento (id),
	FOREIGN KEY (idPessoa)
      REFERENCES pessoas (id)
);

/* Inserção dos Dados */

INSERT INTO departamento (titulo) 
VALUES ('Financeiro'), ('Comercial'), ('Desenvolvimento');

INSERT INTO pessoas (nome, idDepartamento)
VALUES ('Camila', 1), ('Pedro', 2), ('Fabiano', 1), 
('Raquel', 3), ('Patricia', 3), ('Joaquim', 1);

INSERT INTO tarefa (id, titulo, descricao, prazo, 
	idDepartamento, duracao, idPessoa, finalizado)
VALUES 
	(1001, 'Validar NF Janeiro', 'Validar notas recebidas no mês de Janeiro', TO_DATE('15/02/2022', 'DD/MM/YYYY'), 1, 14, 1, TRUE),
	(1002, 'Bug 352', 'Corrigir bug 352 na versão 1.25', TO_DATE('10/05/2022', 'DD/MM/YYYY'), 3, 25,NULL, FALSE),
	(1003, 'Liberação da versão 1.24', 'Disponibilizar pacote para testes', TO_DATE('02/02/2022', 'DD/MM/YYYY'), 3, 2, 3, FALSE),
	(1004, 'Reunião A', 'Reunião com cliente A para apresentação do produto', TO_DATE('05/02/2022', 'DD/MM/YYYY'), 2, 5, NULL, FALSE),
	(1005, 'Reunião final', 'Fechamento contrato', TO_DATE('28/03/2022', 'DD/MM/YYYY'), 2, 6, NULL, FALSE),
	(1006, 'Pagamento 01/2022', 'Realizar pagamento dos fornecedores', TO_DATE('31/01/2022', 'DD/MM/YYYY'), 1, 6, 1, TRUE),
	(1007, 'Bug 401', 'Corrigir bug 401 na versão 1.20', TO_DATE('01/02/2022', 'DD/MM/YYYY'), 3, 2, 4, TRUE),
	(1008, 'Bug 399', 'Corrigir bug 399 na versão 1.20', TO_DATE('28/01/2022', 'DD/MM/YYYY'), 3, 6, 5, TRUE),
	(1009, 'Reunião B', 'Reunião com cliente B para apresentação do produto', TO_DATE('31/01/2022', 'DD/MM/YYYY'), 2, 5, 2, TRUE),
	(1010, 'Validar NF Fevereiro', 'Validar notas recebidas no mês de Fevereiro', TO_DATE('15/03/2022', 'DD/MM/YYYY'), 1, 14, 6, FALSE);

/* Tarefas dos Selects */

SELECT 
    d.titulo AS "Nome do Departamento",
    SUM(CASE WHEN t.finalizado THEN 1 ELSE 0 END) AS "Tarefas Finalizadas",
    SUM(CASE WHEN NOT t.finalizado THEN 1 ELSE 0 END) AS "Farefas Não Finalizadas"
FROM departamento d LEFT JOIN tarefa t ON d.id = t.idDepartamento
GROUP BY d.titulo;

SELECT p.nome
FROM pessoas p LEFT JOIN tarefa t ON p.id = t.idPessoa
WHERE
	t.prazo >= TO_DATE('01/01/2022', 'DD/MM/YYYY') 
	AND t.prazo <= TO_DATE('31/01/2022', 'DD/MM/YYYY')
GROUP BY p.nome
ORDER BY SUM(t.duracao) DESC LIMIT 1;
	
SELECT 
	t.titulo AS "Título da Tarefa", t.prazo,
	COALESCE('Encaminhado para ' || p.nome, 'Pendente') AS "Pessoa Alocada na Tarefa",
    COALESCE(SUM(t.duracao), 0) AS "Total de Horas Gastas"
FROM tarefa t LEFT JOIN pessoas p ON t.idPessoa = p.id
GROUP BY t.titulo, t.prazo, p.nome
ORDER BY t.prazo DESC;
	




