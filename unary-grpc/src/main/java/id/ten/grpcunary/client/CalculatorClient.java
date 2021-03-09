package id.ten.grpcunary.client;

import id.proto.calculator.CalculatorServiceGrpc;
import id.proto.calculator.SumRequest;
import id.proto.calculator.SumResponse;
import id.proto.reverse.Reverse;
import id.proto.reverse.ReverseRequest;
import id.proto.reverse.ReverseResponse;
import id.proto.reverse.ReverseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    private static final String HOST = "localhost";
    private static final int PORT = 50051;

    public static void main(String[] args) {
        ManagedChannel channel  = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest sumRequest  =SumRequest.newBuilder()
                .setFirstNumber(1)
                .setSecondNumber(2)
                .build();

        SumResponse sumResponse = stub.sum(sumRequest);

        System.out.println("Hasil nya adalah "+ sumResponse.getSumResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
