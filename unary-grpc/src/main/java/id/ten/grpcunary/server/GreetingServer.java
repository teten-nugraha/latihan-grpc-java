package id.ten.grpcunary.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    private static final int PORT = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("gRPC Unary Server running in port "+PORT);

        final Server server = ServerBuilder.forPort(PORT)
                .addService(new GreetServiceImpl())
                .addService(new ReverseServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}

