package id.ten.grpc.client;

import id.proto.greet.GreetManyTimesRequest;
import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetClient {
    private static final int PORT = 50051;

    public static void main(String[] args) {
        System.out.println("Hello gRPC Client  -Server Stream");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        GreetManyTimesRequest greetManyTimesRequest =
                GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Teten"))
                .build();

        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
                });


        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
