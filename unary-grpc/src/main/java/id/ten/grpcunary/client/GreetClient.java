package id.ten.grpcunary.client;

import id.proto.greet.GreetRequest;
import id.proto.greet.GreetResponse;
import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetClient {
    private static final int PORT = 50051;

    public static void main(String[] args) {
        System.out.println("Hello gRPC Client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");

        // created a greet service client (blocking - sync)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // created a protocol buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Teten")
                .setLastName("Nugraha")
                .build();

        // do the same for a GreetRequest
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        // call the RPC and get back a GreetResponse (protocol buffer)
        GreetResponse greetResponse = greetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
