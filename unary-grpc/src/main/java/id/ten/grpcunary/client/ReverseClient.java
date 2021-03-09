package id.ten.grpcunary.client;

import id.proto.reverse.Reverse;
import id.proto.reverse.ReverseRequest;
import id.proto.reverse.ReverseResponse;
import id.proto.reverse.ReverseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ReverseClient {
    private static final String HOST = "localhost";
    private static final int PORT = 50051;

    public static void main(String[] args) {
        ManagedChannel channel  = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");

        ReverseServiceGrpc.ReverseServiceBlockingStub reverseClient = ReverseServiceGrpc.newBlockingStub(channel);

        Reverse reverse = Reverse.newBuilder()
                .setOriginalText("original text")
                .build();

        ReverseRequest reverseRequest= ReverseRequest.newBuilder()
                .setReverse(reverse)
                .build();

        ReverseResponse reverseResponse = reverseClient.reverse(reverseRequest);

        System.out.println(reverseResponse.getReverseText());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
