package id.ten.grpcunary.server;

import id.proto.reverse.Reverse;
import id.proto.reverse.ReverseRequest;
import id.proto.reverse.ReverseResponse;
import id.proto.reverse.ReverseServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ReverseServiceImpl extends ReverseServiceGrpc.ReverseServiceImplBase {

    @Override
    public void reverse(ReverseRequest request, StreamObserver<ReverseResponse> responseObserver) {
        Reverse reverse = request.getReverse();
        String originalText = reverse.getOriginalText();

        String reverseText = makeReverseText(originalText);

        ReverseResponse response = ReverseResponse.newBuilder()
                .setReverseText(reverseText)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    private String makeReverseText(String originalText) {
        if(originalText.isEmpty()) {
            originalText  = "TestCoba";
        }

        StringBuilder sb = new StringBuilder(originalText);

        return sb.reverse().toString();
    }
}
