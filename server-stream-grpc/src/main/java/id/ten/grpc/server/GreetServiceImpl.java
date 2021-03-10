package id.ten.grpc.server;

import id.proto.greet.GreetManyTimesRequest;
import id.proto.greet.GreetManyTimesResponse;
import id.proto.greet.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {


    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        final String firstName  =request.getGreeting().getFirstName();

        try{
            for (int i = 0; i < 10; i++) {
                String result = "Hello "+ firstName+", response message number : "+i;
                GreetManyTimesResponse response =
                    GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000L);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            responseObserver.onCompleted();
        }
    }
}
