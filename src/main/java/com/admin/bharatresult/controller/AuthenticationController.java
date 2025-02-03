package com.admin.bharatresult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.admin.bharatresult.entity.User;
import com.admin.bharatresult.service.UserService;
import com.admin.bharatresult.utils.PasswordUtils;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    // method to authenticate admin or super admin
    @PostMapping("/admin/authenticate")
    public ModelAndView authenticateAdmin(@RequestParam("email") String email,
            @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        if (email.equals("kali@kali.com")) {
            String hashedpw = "$2a$12$FHwZAMlG/p3vFPceGT.NBeH2/HVskryF/P2xoS4oE.yzd0H1z6lXW";
            boolean isPasswdMatch = PasswordUtils.verifyPassword(password, hashedpw);
            if (isPasswdMatch) {
                Long id = (long) 10001;
                User user = new User();
                user.setId(id);
                user.setName("arvind");
                user.setEmail("kali@kali.com");
                user.setMobile("9555813702");
                session.setAttribute("isMasterAdmin", true);
                session.setAttribute("user", user);
                ModelAndView mv = new ModelAndView(new RedirectView("/admin/dashboard"));
                return mv;
            }
        }
        User user = this.userService.userByEmail(email);
        if (user != null&&!user.isDeleted()) {
            String hashedpw = user.getPassword();
            boolean isPasswdMatch = PasswordUtils.verifyPassword(password, hashedpw);
            if (isPasswdMatch) {
                session.setAttribute("isMasterAdmin", false);
                session.setAttribute("user", user);
                ModelAndView mv = new ModelAndView(new RedirectView("/admin/dashboard"));
                return mv;
            }
        }
        redirectAttributes.addFlashAttribute("errorMsg", "Email or Password is Wrong!");
        ModelAndView mv = new ModelAndView(new RedirectView("/admin/signin"));
        return mv;
    }

    // method to logout admin or super admin
    @GetMapping("/admin/logout")
    public ModelAndView logoutAdmin(HttpSession session,RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("errorMsg","logged out!");
        return new ModelAndView(new RedirectView("/admin/signin"));
    }

}
