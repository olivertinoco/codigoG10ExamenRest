package multi.modulo.sunat.application;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PostStartupExecutor {

    private final CreaTablaSunat creaTablaSunat;

    public PostStartupExecutor(CreaTablaSunat creaTablaSunat) {
        this.creaTablaSunat = creaTablaSunat;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        creaTablaSunat.crearTablaSunatAsync();
    }
}
