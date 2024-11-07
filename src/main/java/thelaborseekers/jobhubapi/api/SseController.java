package thelaborseekers.jobhubapi.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sse")
public class SseController {
    /*
    @GetMapping(value = "/feedbacks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamFeedbacks() {
        SseEmitter emitter = new SseEmitter();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 200; i++) {
                    emitter.send("Nuevo Feedback #" + i);
                    Thread.sleep(1000); // Simula un retardo de 5 segundos
                }
                emitter.complete(); // Completa la conexión cuando no hay más datos
            } catch (Exception ex) {
                emitter.completeWithError(ex); // Completa con error en caso de fallo
            }
        }).start();

        return emitter;
    }
    */
}