package com.hfc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by elizabeth on 5/4/16.
 */
@Controller
public class ReplyController {

    private SimpMessagingTemplate template;

    @Autowired
    public ReplyController(SimpMessagingTemplate template) {
        this.template = template;
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::greet, 0,3, TimeUnit.SECONDS);
    }

    @RequestMapping(path="/topic/greetings", method= RequestMethod.POST)
    public void greet() {
        Random r = new Random();
        int rint = r.nextInt(10);
        Greeting greeting = new Greeting(""+(rint+1)+";"+(rint+2)+";"+(rint+3)+";"+rint+"");
        System.out.println("Publishing greeting");
        this.template.convertAndSend("/topic/greetings", greeting);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting receiveGreeting(HelloMessage message) throws Exception {
        System.out.println("Received greeting : " + message.getName());
        Random r = new Random();
        int rint = r.nextInt(10);
        return new Greeting(""+(rint+1)+";"+(rint+2)+";"+(rint+3)+";"+rint+"");
    }

}

