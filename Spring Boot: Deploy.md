# Deploy & Docker

## Variáveis de ambiente

- No arquivo `application-prod.yaml`:
	
	```yaml
	spring:  
		 datasource: 
			  driverClassName: org.h2.Driver  
			  url: ${FORUM_DATABASE_URL}  
			  username: ${FORUM_DATABASE_USERNAME}  
			  password: ${FORUM_DATABASE_PASSWORD}

	forum:  
		 jwt: 
		      secret: ${FORUM_JWT_SECRET}  
			  expiration: 86400000
	```

- Na linha de comando:

	1. PowerShell (lixo):
	
	```bash
	java -jar "-DFORUM_DATABASE_URL=jdbc:h2:mem:alura-forum"
			  "D-FORUM_DATABASE_USERNAME=sa"
			  "D-FORUM_DATABASE_PASSWORD="
			  "D-FORUM_JWT_SECRET=secret"
			  "-Dspring.profiles.active=prod" <NomeDoJar>
	```

	2. Terminal decente:

	```bash
	export FORUM_DATABASE_URL=DATABASE_URL
	export FORUM_DATABASE_USERNAME=sa
	export FORUM_DATABASE_PASSWORD=sa
	export FORUM_JWT_SECRET=secret
	java -jar -Dspring.profiles.active=prod <NomeDoJar>
	```

## Docker

- Criar arquivo `Dockerfile`:

	```dockerfile
	FROM openjdk:11.0.10-jre-slim  
	ARG JAR_FILE=target/*.jar  
	COPY ${JAR_FILE} app.jar  
	ENTRYPOINT ["java", "-jar", "/app.jar"]
	```

- Criando imagem:

	```bash
	sudo docker build -t alura/forum .
	```

- Executando imagem:

	```bash
	sudo docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE='prod'
								 -e FORUM_DATABASE_URL='jdbc:h2:mem:alura-forum'
								 -e FORUM_DATABASE_USERNAME='sa'
								 -e FORUM_DATABASE_PASSWORD=''
								 -e FORUM_JWT_SECRET='secret' alura/forum
	```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MDE1NDEyMjJdfQ==
-->