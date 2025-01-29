package com.admin.bharatresult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.admin.bharatresult.entity.User;
import com.admin.bharatresult.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // method to get all users
    @GetMapping("/admin/users")
    public ModelAndView users(HttpSession session) {
        if (session.getAttribute("isMasterAdmin") != null) {
            if (!(boolean) session.getAttribute("isMasterAdmin")) {
                session.invalidate();
                return new ModelAndView("401");
            }
            List<User> users = this.userService.getUsers();
            ModelAndView mv = new ModelAndView("user-management");
            mv.addObject("users", users);
            return mv;
        }

        return new ModelAndView(new RedirectView("/admin/signin"));
    }

    // method to create new user
    @PostMapping("/admin/user")
    public ModelAndView createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (this.userService.existByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMsg", "Email already exist!");
            ModelAndView mv = new ModelAndView(new RedirectView("/admin/users"));
            return mv;
        }
        if (this.userService.existByMobile(user)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Mobile Number already exist!");
            ModelAndView mv = new ModelAndView(new RedirectView("/admin/users"));
            return mv;
        }
        this.userService.saveUser(user);
        redirectAttributes.addFlashAttribute("eventMsg", "Account created..");
        ModelAndView mv = new ModelAndView(new RedirectView("/admin/users"));
        return mv;
    }

    // method to get user profile by its session id
    @GetMapping("/admin/profile")
    public ModelAndView getUserProfile(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            ModelAndView mv = new ModelAndView("user-profile");
            mv.addObject("user", user);
            return mv;
        }
        return new ModelAndView("404");
    }

    // method to update user
    @PostMapping("/admin/users/update")
    public ModelAndView updateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userService.existById(user.getId())) {
            User dbUser = this.userService.userById(user.getId());
            if (dbUser.getEmail()==user.getEmail() || dbUser.getMobile()==user.getMobile()) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Duplicate Email Or Mobile Entry for User :" + user.getId());
                return new ModelAndView(new RedirectView("/admin/users"));
            }
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("eventMsg", "User Updated..");
            return new ModelAndView(new RedirectView("/admin/users"));
        }
        redirectAttributes.addFlashAttribute("errorMsg", "User does not Exist..");
        return new ModelAndView(new RedirectView("/admin/users"));

    }

    // method to delete user
    @GetMapping("/admin/user/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        User user=this.userService.userById(id);
        user.setDeleted(true);
        this.userService.saveUser(user);
        redirectAttributes.addFlashAttribute("eventMsg", "User deleted...");
        return new ModelAndView(new RedirectView("/admin/users"));
    }

}
