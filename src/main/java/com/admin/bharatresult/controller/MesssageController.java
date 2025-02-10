package com.admin.bharatresult.controller;

import java.util.List;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.admin.bharatresult.entity.Messages;
import com.admin.bharatresult.service.MessageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MesssageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/admin/messages")
    public ModelAndView getMessages() {
        List<Messages> allMessages = this.messageService.getAllMessages();
        ModelAndView mv = new ModelAndView("messages");
        mv.addObject("messages", allMessages);
        return mv;
    }

    @GetMapping("/admin/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        this.messageService.deleteMsg(id);
        redirectAttributes.addFlashAttribute("eventMsg", "Deleted...");
        return new ModelAndView(new RedirectView("/admin/messages"));
    }

}
