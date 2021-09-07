package com.jas.web;

import com.jas.domain.Account;
import com.jas.exception.AccountNotFoundExecption;
import com.jas.service.AccountService;
import com.mysql.cj.log.Log;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 舊列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/accountlist")
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                       Model model) {
//        List<Account> accountList = accountService.findAll();
//        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Page<Account> page1 = accountService.findAllByPage(pageable);
        model.addAttribute("page", page1);
        return "accounts";
    }

    /**
     * ID查詢資料
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/account/{id}")
    public String detail(@PathVariable long id, Model model) {
        Account account = accountService.findOne(id);
        if (account == null)
//            account = new Account();
            throw new AccountNotFoundExecption("您查詢的帳號不存在");
        model.addAttribute("account", account);
        return "account";
    }

    /**
     * 新增資料 (無傳送ID)
     * @return
     */
    @GetMapping("/account/input")
    public String inputPage() {

        return "input";
    }

    /**
     * 更新資料 (傳送ID)
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/account/{id}/update")
    public String editPage(@PathVariable long id, Model model) {
        Account account = accountService.findOne(id);
        model.addAttribute("account", account);
        return "update";
    }

//    @GetMapping("/account/status/{status}")
//    public String listByStatus(@PathVariable String status, Model model) {
//        List<Account> accountList = accountService.findByStatus(status);
//        model.addAttribute("account", status);
//
//        return "accounts";
//    }

    /**
     * 使用狀態查詢資料
     * 配合頁面及上下頁按鈕
     * @param pageable
     * @param status
     * @param model
     * @return
     */
    @GetMapping("/accountlist/{status}")
    public String findStatus(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                             @PathVariable String status,
                             Model model){
        Page<Account> page1;
        if (status.equals("全部"))
            page1 = accountService.findAllByPage(pageable);
        else
            page1 = accountService.findStatusByPage(status, pageable);

        model.addAttribute("page", page1);
        model.addAttribute("status", status);
        return "accounts";
    }

    /**
     * 接收更新資料並轉址回列表
     * @param account
     * @param attributes
     * @return
     */
    @PostMapping("/account/input")
    public String post(Account account, final RedirectAttributes attributes) {
        accountService.save(account);
        attributes.addFlashAttribute("message", "<" + account.getName()+ "> 提交成功");
        return "redirect:/accountlist/全部";
    }

    /**
     * 刪除資料並返回列表
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/account/{id}/delete")
    public String delete(@PathVariable long id, final RedirectAttributes attributes) {
        accountService.delete(id);
        attributes.addFlashAttribute("message", "刪除成功");
        return "redirect:/accountlist/全部";
    }

    /**
     * 異常處理頁面
     * @return
     */
    @GetMapping("/exception")
    public String testException() {
        throw new RuntimeException("測試異常處理");
    }

}
