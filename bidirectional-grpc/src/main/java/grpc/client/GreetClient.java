package grpc.client;

import id.proto.greet.GreetEveryoneRequest;
import id.proto.greet.GreetEveryoneResponse;
import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetClient {
    private static final int PORT = 50051;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("Hello gRPC BiDi");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");
        GreetServiceGrpc.GreetServiceStub client = GreetServiceGrpc.newStub(channel);


        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GreetEveryoneRequest> requestObserver =  client.greetEveryone(new StreamObserver<GreetEveryoneResponse>() {
            @Override
            public void onNext(GreetEveryoneResponse value) {
                System.out.println("Response from the server : "+value.getResult());
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

        Arrays.asList("Teten","Mark","Jhon","Patricia").forEach(
                name -> {
                    System.out.println("sending :"+name);
                    requestObserver.onNext(GreetEveryoneRequest.newBuilder()
                            .setGreeting(Greeting.newBuilder()
                                    .setFirstName(name))
                            .build());

                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

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
