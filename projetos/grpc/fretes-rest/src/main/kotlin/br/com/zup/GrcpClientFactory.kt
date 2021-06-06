package br.com.zup

import br.com.zup.FretesServiceGrpc.FretesServiceBlockingStub
import br.com.zup.FretesServiceGrpc.newBlockingStub
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrcpClientFactory {

    @Singleton
    fun fretesClientStub(@GrpcChannel("fretes") channel: ManagedChannel):
            FretesServiceBlockingStub? = newBlockingStub(channel)


}