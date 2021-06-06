package br.com.zup

import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class CarrosEndpoint(
    @Inject val carrosRepository: CarrosRepository
) : CarrosServiceGrpc.CarrosServiceImplBase() {

    override fun adicionar(request: CarrosRequest, responseObserver: StreamObserver<CarrosResponse>) {

        if (carrosRepository.existsByPlaca(request.placa)) {
            responseObserver.onError(Status.ALREADY_EXISTS
                .withDescription("Carro com a placa informada já existe")
                .asRuntimeException())
            return
        }

        val carro = Carro(
            modelo = request.modelo,
            placa = request.placa
        )

        try {
            carrosRepository.save(carro)
        } catch (e: ConstraintViolationException) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                .withDescription("Dados de entrada inválidos")
                .asRuntimeException())
            return
        }
        responseObserver.onNext(CarrosResponse.newBuilder().setId(carro.id!!).build())
        responseObserver.onCompleted()
    }

}