package id.ten.grpcunary.server;

import id.proto.greet.GreetRequest;
import id.proto.greet.GreetResponse;
import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;


public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // extract the fields we need
        Greeting greeting  =request.getGreeting();
        String firstName = greeting.getFirstName();

        // create the response
        String result = "Kumaha damang "+firstName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // send the response
        responseObserver.onNext(response);

        // complete rpc call
        responseObserver.onCompleted();
    }

}
