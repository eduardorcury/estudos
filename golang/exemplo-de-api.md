---
title: "Exemplo de API"
layout: single
sidebar:
  nav: main
---

1. Arquivo main

```go
package http

import (
	"log"
	"net/http"
)

func main() {
	userRepository := NewUserRepository()
	service := NewUserService(userRepository)
	mux := http.NewServeMux()

	httpHandler := HttpHandler{
		Service: service,
	}

	mux.HandleFunc("POST /users", httpHandler.HandleNewUser)

	server := &http.Server{
		Addr:    ":8080",
		Handler: mux,
	}

	if err := server.ListenAndServe(); err != nil {
		log.Printf("HTTP server error: %v", err)
	}
}
```

2. Domínio

```go
package http

import "context"

type User struct {
	ID      string
	Email   string
	Name    string
	Address Address
}

type Address struct {
	Street string
	City   string
}

type UserRepository interface {
	createNewUser(ctx context.Context, user *User) (*User, error)
}

type UserService interface {
	CreateUser(ctx context.Context, user *User) (*User, error)
}
```

3. Repositório

```go
package http

import "context"

type userRepository struct {
	users map[string]*User
}

func NewUserRepository() *userRepository {
	return &userRepository{
		users: make(map[string]*User),
	}
}

func (r *userRepository) createNewUser(ctx context.Context, user *User) (*User, error) {
	r.users[user.ID] = user
	return user, nil
}
```

4. Service

```go
package http

import (
	"context"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
)

type userService struct {
	userRepository UserRepository
}

func NewUserService(userRepository UserRepository) *userService {
	return &userService{
		userRepository: userRepository,
	}
}

func (s *userService) CreateUser(ctx context.Context, user *User) (*User, error) {
	url := fmt.Sprintf("http://user-service/%s/addresses", user.ID)

	resp, err := http.Get(url)
	if err != nil {
		return nil, fmt.Errorf("failed to get address from API: %v", err)
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, fmt.Errorf("failed to read the response: %v", err)
	}

	var addressResponse Address
	if err := json.Unmarshal(body, &addressResponse); err != nil {
		return nil, fmt.Errorf("failed to parse response: %v", err)
	}

	user = &User{
		ID:      user.ID,
		Email:   user.Email,
		Name:    user.Name,
		Address: addressResponse,
	}

	return s.userRepository.createNewUser(ctx, user)
}
```

5. Handler

```go
package http

import (
	"encoding/json"
	"log"
	"net/http"
)

type HttpHandler struct {
	Service UserService
}

type userRequest struct {
	UserID string `json:"userID"`
	Email  string `json:"email"`
	Name   string `json:"name"`
}

func (s *HttpHandler) HandleNewUser(w http.ResponseWriter, r *http.Request) {
	var reqBody userRequest
	if err := json.NewDecoder(r.Body).Decode(&reqBody); err != nil {
		http.Error(w, "failed to parse JSON data", http.StatusBadRequest)
		return
	}

	user := &User{
		ID:    reqBody.UserID,
		Email: reqBody.Email,
		Name:  reqBody.Name,
	}

	ctx := r.Context()

	t, err := s.Service.CreateUser(ctx, user)
	if err != nil {
		log.Println(err)
	}

	writeJSON(w, http.StatusOK, t)
}

func writeJSON(w http.ResponseWriter, status int, data any) error {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	return json.NewEncoder(w).Encode(data)
}
```
