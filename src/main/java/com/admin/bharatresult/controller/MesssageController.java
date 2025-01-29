package com.admin.bharatresult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println(allMessages);
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
