package thelaborseekers.jobhubapi.api;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;


@Controller
public class WebsocketController {

    @MessageMapping("/feedback/{id}")
    @SendTo("/topic/{id}")
    public FeedbackDetailDTO sendfeedback(@DestinationVariable String id, FeedbackDetailDTO feedback) {
        return feedback;
    }

}
