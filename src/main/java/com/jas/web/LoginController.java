package com.jas.web;

import com.jas.domain.User;
import com.jas.form.UserForm;
import com.jas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登入頁面跳轉
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登入帳號密碼驗證
     * @param name
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String loginPost (@RequestParam String name, @RequestParam String password, HttpSession session) {
        User user = userService.findByNameAndPassword(name, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }

    /**
     * 註冊頁面
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    /**
     * Post接收註冊帳號表單處理
     * 因為有二次確認密碼的關係
     * 所以先做UserForm驗證再轉換為真實UserModel
     * 再匯入資料庫中
     * @param userForm
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String registerPost(@Valid UserForm userForm, BindingResult result, Model model) {

        if (!userForm.comfirmPassword()) {
            result.rejectValue("comfirmPassword", "comfirmError", "兩次密碼不一致");
        }
        if (result.hasErrors()) {
            return "register";
        }

        User user = userForm.convertToUser();       // 將註冊頁面表單轉換為真實Model
        userService.save(user);
        return "redirect:/login";
    }



}
