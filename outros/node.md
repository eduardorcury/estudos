---
title: "Backend com NodeJS"
layout: single
sidebar:
  nav: main
permalink: /outros/node/
---

> Next Level Week #4

## Configuração de API

### Dependências

* express

```bash
  npm install express
```

* Tipagem

```bash
  npm install --save-dev @types/express
```

### Métodos

```typescript
import express from 'express';


const app = express();


app.get("/", (request, response) => {
    return response.json({ message: "Hello World - NLW #4" });
});


app.post("/", (request, response) => {
    return response.json({ message: "Dados salvos com sucesso" });
});


app.listen(3333, () => console.log('Servidor rodando!'));
```

## Conexão com Banco de Dados

### Dependências

* ORM

```bash
  npm install typeorm reflect-metadata
```

* Banco de dados

```bash
  npm install sqlite3
```

### Configuração

1. Criar arquivo **ormconfig.json**

```javascript
   {
       "type": "sqlite",
       "database": "./src/database/database.sqlite",
       "migrations": ["./src/database/migrations/**.ts"],
       "entities": ["./src/models/**.ts"],
       "logging": true,
       "cli": {
           "migrationsDir": "./src/database/migrations"
       }
   }
```

1. Criar arquivo **database/index.ts**

```typescript
   import { createConnection } from 'typeorm';

   createConnection();
```

### Migrations

1. Adicionar script no **package.json**

```javascript
   {
       "typeorm": "ts-node-dev node_modules/typeorm/cli.js"
   }
```

1. Criar Migration

```bash
   npm run-script typeorm migration:create -- -n <Nome da Migration>
```

1. Executar Migration

```bash
   npm run-script typeorm migration:run
```

1. Reverter Migration

```bash
   npm run-script typeorm migration:revert
```

### Criação da Tabela

* Código dentro da migration criada:

```typescript
  import {MigrationInterface, QueryRunner, Table} from "typeorm";

  export class CreateUsers1614180967447 implements MigrationInterface {

      public async up(queryRunner: QueryRunner): Promise<void> {
          await queryRunner.createTable(
              new Table({
                  name: "users",
                  columns: [
                      {
                          name: "id",
                          type: "uuid",
                          isPrimary: true
                      },
                      {
                          name: "name",
                          type: "varchar"
                      },
                      {
                          name: "email",
                          type: "varchar"
                      },
                      {
                          name: "created_at",
                          type: "timestamp",
                          default: "now()"
                      }
                  ]
              })
          )
      }

      public async down(queryRunner: QueryRunner): Promise<void> {
          await queryRunner.dropTable("users");
      }

  }
```

## Roteamento e Express

### Arquivo de rotas

```typescript
import { Router } from "express";
import { UserController } from "./controllers/UserController";


const router = Router();
const userController = new UserController();


router.post("/users", userController.create);


export { router };
```

### Arquivo _server.ts_

```typescript
import 'reflect-metadata';
import express from 'express';
import "./database";
import { router } from './routes';


const app = express();


app.use(express.json());
app.use(router);


app.listen(3333, () => console.log('Servidor rodando!'));
```

## Criação da Entidade

### Dependências

* UUID

```bash
  npm install uuid
  npm install @types/uuid
```

### Classe de domínio

* Arquivo **model/user.ts**

```typescript
  import { Column, CreateDateColumn, Entity, PrimaryColumn } from "typeorm";
  import { v4 as uuid } from "uuid";

  @Entity("users")
  class User {

      @PrimaryColumn()
      readonly id: string;

      @Column()
      name: string;

      @Column()
      email: string;

      @CreateDateColumn()
      created_at: Date;

      constructor() {
          if (!this.id) {
              this.id = uuid();
          }
      }

  }

  export { User };
```

### Modificação em _ormconfig.json_

* Adicionar entidades ao arquivo de configuração:

```javascript
  {
      "entities": ["./src/models/**.ts"]
  }
```

## Repositório

### Classe

```typescript
import { EntityRepository, Repository } from "typeorm";
import { User } from "../models/user";


@EntityRepository(User)
class UsersRepository extends Repository<User> {

}


}

export { UsersRepository };
```

## Controlador

### Funções

* [x] Deve possuir métodos que serão usados pela classe de rotas para realizar as funções da API.
* [x] Obtém o repositório e o usa para salvar as entidades a partir do _json_ das requisições.
* [x] Checa regras de negócio, como _e-mail_ repetido, etc.
* [x] Retorna a mensagem apropriada.

### Classe

```typescript
import { Request, Response } from "express";
import { getCustomRepository } from "typeorm";
import { UsersRepository } from "../repositories/usersRepository";


class UserController {


    async create(request: Request, response: Response) {


        const { name, email } = request.body;


        const userRepository = getCustomRepository(UsersRepository);

        const userAlreadyExists = await userRepository.findOne({
            email
        });


        if (userAlreadyExists) {
            return response.status(400).json({
                erro: "Email já cadastrado"
            })
        }


        const user = userRepository.create({
            name, email
        });


        await userRepository.save(user);
        return response.status(201).json(user);


    }

}


}

export { UserController };
```

## Testes Automatizados

### Dependências

* Jest

```bash
  npm install --save-dev jest
```

* Tipagem

```bash
  npm install --save-dev @types/jest
```

* Com Typescript

```bash
  npm install --save-dev ts-jest
```

* Supertest (para criar _requests_)

```bash
  npm install --save-dev supertest @types/supertest
```

### Configuração

* Adicionar script _test_

```javascript
  {
    "scripts": {
      "test": "jest"
    }
  }
```

* Executar comando _init_

```bash
  npm test -- --init
```

### Separação de ambientes

* [x] Criar arquivo _.ts_ que exporta o express
* [x] Modificar _server.ts_ para somente rodar o servidor
* [x] Criar arquivo responsável por criar a conexão com banco
* [x] Inserir variável de ambiente ao executar comando de teste
* [ ] Arquivo _app.ts_

```typescript
  import 'reflect-metadata';
  import express from 'express';
  import createConnection from "./database";
  import { router } from './routes';

  createConnection();
  const app = express();

  app.use(express.json());
  app.use(router);

  export { app };
```

* Arquivo _server.ts_

```typescript
  import { app } from "./app";

  app.listen(3333, () => console.log('Servidor rodando!'));
```

* Arquivo _index.ts_ na pasta _database_

```typescript
  import { Connection, createConnection, getConnectionOptions } from 'typeorm';

  export default async(): Promise<Connection> => {

      const defaultOptions = await getConnectionOptions();

      return createConnection(
          Object.assign(defaultOptions, {
              database: process.env.NODE_ENV === 'test' 
                  ? "./src/database/database.test.sqlite" 
                  : defaultOptions.database
          })
      );

  }
```

* Comando _test_

```javascript
  {
    "scripts": {
      "test": "set NODE_ENV=test&& jest"
    }
  }
```
