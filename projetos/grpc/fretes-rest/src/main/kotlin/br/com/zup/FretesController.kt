package br.com.zup

import br.com.zup.FretesServiceGrpc.FretesServiceBlockingStub
import com.google.rpc.Status
import io.grpc.Status.Code
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.*
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException
import javax.inject.Inject

@Controller
class FretesController(@field:Inject val grpcClient: FretesServiceBlockingStub) {

    @Get("/api/fretes")
    fun calcula(@QueryValue cep: String): FreteResponse {

        val request = CalculaFreteRequest.newBuilder()
            .setCep(cep)
            .build()

        try {
            val response = grpcClient.calculaFrete(request)
            return FreteResponse(cep = response.cep, valor = response.valor)
        } catch (e: StatusRuntimeException) {
            throw when (e.status.code) {
                Code.INVALID_ARGUMENT -> HttpStatusException(BAD_REQUEST, e.status.description)
                //erro inesperado
                Code.PERMISSION_DENIED -> {
                    val proto = StatusProto.fromThrowable(e) ?: throw HttpStatusException(FORBIDDEN, e.status.description)
                    val details : ErrorDetails = proto.detailsList[0].unpack(ErrorDetails::class.java)
                    HttpStatusException(FORBIDDEN, "${e.status.description}. Erro ${details.code}: ${details.message}")
                }
                else -> HttpStatusException(INTERNAL_SERVER_ERROR, e.message)
            }
        }
    }

}

data class FreteResponse(
    val cep: String,
    val valor: Double
)