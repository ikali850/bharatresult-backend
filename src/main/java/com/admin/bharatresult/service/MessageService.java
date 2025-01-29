package com.admin.bharatresult.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bharatresult.entity.Messages;
import com.admin.bharatresult.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    public List<Messages> getAllMessages() {
        return this.messageRepo.findAll();
    }

    public boolean deleteMsg(Long id) {
        this.messageRepo.deleteById(id);
        return true;
    }

}
