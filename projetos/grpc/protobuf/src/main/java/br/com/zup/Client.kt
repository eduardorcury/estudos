package br.com.zup

import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)

    FuncionarioRequest.newBuilder()
        .setNome("Eduardo")
        .setCpf("111.111.111-11")
        .setSalario(2000.0)
        .setIdade(22)
        .setAtivo(true)
        .setCargo(Cargo.DEV)
        .addEnderecos(FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Rua X")
            .setCep("00000-000")
            .setComplemento("Ap X"))
        .build()
        .let(client::cadastrar)
        .also(::println)

}