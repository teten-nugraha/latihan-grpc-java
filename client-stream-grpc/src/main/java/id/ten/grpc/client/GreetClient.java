package id.ten.grpc.client;

import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.Greeting;
import id.proto.greet.LongGreetRequest;
import id.proto.greet.LongGreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetClient {
    private static final int PORT = 50051;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("Hello gRPC Client stream - Server");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");
        GreetServiceGrpc.GreetServiceStub client = GreetServiceGrpc.newStub(channel);


        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver =  client.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                // get response from the server
                System.out.println("Received response");
                System.out.println(value.getResult());

                // on next will be called once
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                // the server is done sending data
                // onComplete will be called right after onNext
                System.out.println("Server has completed sending data");
                latch.countDown();
            }
        });

        System.out.println("Sending data 1");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Marc").build())
                .build());

        System.out.println("Sending data 2");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Jhon").build())
                .build());

        System.out.println("Sending data 3");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Dany").build())
                .build());

        // tell the server that client done sending data
        requestObserver.onCompleted();

        try{
            latch.await(3L, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
