package org.websocket.borker.chat.app.services;

import org.websocket.borker.chat.app.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    void save(Message message);
}
