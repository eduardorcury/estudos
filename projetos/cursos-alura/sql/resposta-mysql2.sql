CREATE DATABASE resposta_sql;

USE resposta_sql;

CREATE TABLE ALUNOS(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30),
    email VARCHAR(30),
    idade TINYINT
);

CREATE TABLE AVALIACOES(
	id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(50),
    descricao VARCHAR(300)
);

CREATE TABLE RESPOSTAS(
	id INT PRIMARY KEY AUTO_INCREMENT,
    resposta VARCHAR(3000),
    id_aluno INT,
    id_avaliacao INT,
    FOREIGN KEY (id_aluno) REFERENCES ALUNOS(id),
    FOREIGN KEY (id_avaliacao) REFERENCES AVALIACOES(id)
);

CREATE TABLE CORRECOES(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nota TINYINT,
    id_resposta INT,
    FOREIGN KEY (id_resposta) REFERENCES RESPOSTAS(id)
);

INSERT INTO ALUNOS (nome, email, idade) VALUES
	('Maria', 'maria@gmail.com', 22),
    ('João', 'João@gmail.com', 40),
    ('Roberto', 'Roberto@gmail.com', 33),
    ('Eduardo', 'Eduardo@gmail.com', 23),
    ('Daniel', 'daniel@gmail.com', 40);
    
INSERT INTO AVALIACOES (titulo, descricao) VALUES
	('Servlets', 'Aula de Servlets'),
    ('HTTP', 'Aula de HTTP'),
    ('MYSQL 1', 'Aula de MYSQL 1'),
    ('MYSQL 2', 'Aula de MYSQL 2');
    
INSERT INTO RESPOSTAS(resposta, id_aluno, id_avaliacao) VALUES
	('Reposta Servlet 1', 1, 1),
    ('Reposta Servlet 2', 2, 1),
    ('Reposta HTTP 1', 4, 2),
    ('Reposta MYSQL 1', 3, 3),
    ('Reposta HTTP 2', 1, 2),
    ('Reposta MYSQL 2', 3, 4);
    
INSERT INTO CORRECOES(nota, id_resposta) VALUES
	(8, 1),
    (3, 2),
    (5, 3),
    (10, 4),
    (6, 5),
    (7, 6);
    