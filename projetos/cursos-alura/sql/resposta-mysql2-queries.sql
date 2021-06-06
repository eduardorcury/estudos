SELECT * FROM avaliacoes WHERE titulo = 'HTTP';

SELECT AL.id, AL.nome FROM alunos AL
	INNER JOIN respostas R
    ON AL.id = R.id_aluno
    INNER JOIN avaliacoes AV 
	ON R.id_avaliacao = AV.id
    WHERE AV.titulo = 'HTTP';
    
SELECT AV.id, COUNT(*) FROM avaliacoes AV 
	INNER JOIN respostas R 
	ON AV.id = R.id_avaliacao
    GROUP BY AV.id;
    
SELECT * FROM avaliacoes AV 
	INNER JOIN respostas R 
	ON AV.id = R.id_avaliacao;

SELECT R.id_avaliacao, AVG(C.nota) FROM respostas R 
	INNER JOIN correcoes C
    ON C.id_resposta = R.id
    GROUP BY R.id_avaliacao;

	

