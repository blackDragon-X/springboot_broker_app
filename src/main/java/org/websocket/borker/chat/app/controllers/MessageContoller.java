package org.websocket.borker.chat.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.websocket.borker.chat.app.models.Message;
import org.websocket.borker.chat.app.services.MessageService;

import java.util.Date;
import java.util.Random;

@Controller
public class MessageContoller {
    private String[] colors = {"red", "blue", "green", "magenta", "orange", "purple", "yellow"};

    private final MessageService service;

    @Autowired
    private SimpMessagingTemplate webSocket;

    public MessageContoller(MessageService service) {
        this.service = service;
    }

    @MessageMapping("/message")
    @SendTo("/chat/message")
    public Message recieveMessage(Message mensaje){
        mensaje.setDate(new Date());
        mensaje.setText("Recibido por el broker: " + mensaje.getText());
        if (mensaje.getType().equals("NEW_USER")) {
            mensaje.setColor(this.colors[new Random().nextInt(colors.length)]);
            mensaje.setText("Nuevo usuario.");
        }else {
            service.save(mensaje);
        }
        return mensaje;
    }

    @MessageMapping("/writing")
    @SendTo("/chat/writing")
    public String isWriting(String username){
        System.out.println("username = " + username);
        return username.concat(" está escribiendo...");
    }

    @MessageMapping("/history")
    public void getHistoryMessages(String clientId){
        webSocket.convertAndSend("/chat/history/".concat(clientId), service.findAll());
    }

}
