package com.hfc;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * Created by elizabeth on 5/4/16.
 */
@Controller
public class ReplyController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting receiveGreeting(HelloMessage message) throws Exception {
        System.out.println("Received greeting : " + message.getName());
        Random r = new Random();
        int rint = r.nextInt(10);
        return new Greeting(""+(rint+1)+";"+(rint+2)+";"+(rint+3)+";"+rint+"");
    }


}

