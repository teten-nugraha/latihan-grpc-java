package id.ten.grpc.server;

import id.proto.greet.GreetServiceGrpc;
import id.proto.greet.LongGreetRequest;
import id.proto.greet.LongGreetResponse;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {

        StreamObserver<LongGreetRequest> streamObserver  =new StreamObserver<LongGreetRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetRequest longGreetRequest) {
                // client send messages
                result += "Hello "+ longGreetRequest.getGreeting().getFirstName()+" !;";
            }

            @Override
            public void onError(Throwable throwable) {
                //client send error
            }

            @Override
            public void onCompleted() {
                // client is done
                responseObserver.onNext(
                        LongGreetResponse.newBuilder()
                                .setResult(result)
                                .build()
                );
                responseObserver.onCompleted();
            }
        };

        return streamObserver;
    }
}
