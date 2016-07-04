package lol.corrado;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class S3 {

    public void run(S3Event event, Context context) {
        System.out.println(event.getRecords().get(0).getS3().getObject().getKey());
    }

}