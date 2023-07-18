package com.mysite.prac.user;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.prac.answer.AnswerService;
import com.mysite.prac.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        
        try {
        userService.create(userCreateForm.getUsername(), 
                userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
        	e.printStackTrace();
        	bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
        	return "signup_form";
        }catch(Exception e) {
        	e.printStackTrace();
        	bindingResult.reject("signupFailed", e.getMessage());
        	return "signup_form";
        }
        
        return "redirect:/";
    }
    
    
    @GetMapping("/login")
    public String login() {
    	return "login_form";
    }
    


    
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value= "/mypage")
    public String mypage(Model model, Principal principal) {
    	String username = principal.getName();
    	model.addAttribute("username",username);
    	model.addAttribute("questionList",
    	questionService.getCurrentListByUser(username, 5));
    	 model.addAttribute("answerList",
    	 answerService.getCurrentListByUser(username, 5));
    	return "my_page";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myInfo")
    public String modifyPassword(PasswordModifyForm passwordModifyForm, Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username",username);
    	return "myInfo";
    }
    
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/myInfo")
    public String modifyPassword(@Valid PasswordModifyForm passwordModifyForm, BindingResult bindingResult, Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            return "myInfo";
        }

        if (!this.userService.isSamePassword(user, passwordModifyForm.getBeforePassword())) {
            bindingResult.rejectValue("beforePassword", "notBeforePassword", "이전 비밀번호와 일치하지 않습니다. ");
            return "myInfo";
        }

        if (!passwordModifyForm.getPassword1().equals(passwordModifyForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "myInfo";
        }

        try {
            userService.modifyPassword(user, passwordModifyForm.getPassword1());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyPasswordFailed", e.getMessage());
            return "myInfo";
        }

       
        return "my_page";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin")
    public String admin() {
    	return "admin_page";
    }
    
    
   
   
   
}
