package pl.wrona.iot.timetable.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.ShutdownApi;

@RestController
@RequiredArgsConstructor
public class ShutdownController implements ShutdownApi, ApplicationContextAware {

    private ApplicationContext context;


    @Override
    public ResponseEntity<Void> closeApp() {
        ((ConfigurableApplicationContext) context).close();
        return ResponseEntity.ok().build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
