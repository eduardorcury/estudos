package br.com.zup

import br.com.zup.FretesServiceGrpc.FretesServiceImplBase
import com.google.protobuf.Any
import com.google.rpc.Code
import io.grpc.Status
import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FretesGrpcServer : FretesServiceImplBase() {

    private val logger = LoggerFactory.getLogger(FretesGrpcServer::class.java)

    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {

        logger.info("Calculando frete para request: $request")

        val cep = request?.cep
        if (cep == null || cep.isBlank()) {
            INVALID_ARGUMENT.withDescription("CEP deve ser informado")
                .asRuntimeException()
                .let { responseObserver?.onError(it) }
            return
        }

        if (!cep.matches("[0-9]{5}-[0-9]{3}".toRegex())) {
            INVALID_ARGUMENT.withDescription("CEP inválido")
                .augmentDescription("Formato esperado deve ser 99999-999")
                .asRuntimeException()
                .let { responseObserver?.onError(it) }
            return
        }

        if (cep.endsWith("333")) {
            com.google.rpc.Status.newBuilder()
                .setCode(Code.PERMISSION_DENIED.number)
                .setMessage("Usuário não pode acessar o recurso")
                .addDetails(Any.pack(ErrorDetails.newBuilder()
                    .setCode(401)
                    .setMessage("Token expirado")
                    .build()))
                .build()
                .let { statusProto -> StatusProto.toStatusRuntimeException(statusProto) }
                .let { statusRuntimeException -> responseObserver?.onError(statusRuntimeException) }
        }

        var frete = 0.0
        try {
            frete = Random.nextDouble(from = 0.0, until = 140.0)
            if (frete > 100.0) {
                throw IllegalStateException("Erro inesperado")
            }
        } catch (e: Exception) {
            responseObserver?.onError(Status.INTERNAL
                .withDescription(e.message)
                .withCause(e)
                .asRuntimeException())
        }

        CalculaFreteResponse.newBuilder()
            .setCep(request.cep)
            .setValor(frete)
            .build()
            .also { response -> logger.info("Frete calculado: $response") }
            .let(responseObserver!!::onNext)
            .let { responseObserver.onCompleted() }

    }

}