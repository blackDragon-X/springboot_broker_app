package org.websocket.borker.chat.app.services;

import org.springframework.stereotype.Service;
import org.websocket.borker.chat.app.models.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    private final List<Message> messages = new ArrayList<>();

    @Override
    public List<Message> findAll() {
        return messages;
    }

    @Override
    public void save(Message message) {
        messages.add(message);
    }
}
