package grpc.server;

import id.proto.greet.GreetEveryoneRequest;
import id.proto.greet.GreetEveryoneResponse;
import id.proto.greet.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public StreamObserver<GreetEveryoneRequest> greetEveryone(StreamObserver<GreetEveryoneResponse> responseObserver) {
        StreamObserver<GreetEveryoneRequest> streamObserver  =new StreamObserver<GreetEveryoneRequest>() {


            @Override
            public void onNext(GreetEveryoneRequest value) {
                // client send messages
                String result = "Hello "+value.getGreeting().getFirstName();
                GreetEveryoneResponse response  =GreetEveryoneResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                //client send error
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

        return streamObserver;
    }
}
