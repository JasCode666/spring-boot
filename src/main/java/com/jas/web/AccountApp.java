package com.jas.web;

import com.jas.domain.Account;
import com.jas.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class AccountApp {

    @Autowired
    private AccountService accountService;

//    @GetMapping("/accounts")
//    public List<Account> getAll() {
//
//        return accountService.findAll();
//    }

    @GetMapping("/accounts")
    public Page<Account> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
//        return accountService.findAll();
        return accountService.findAllByPage(PageRequest.of(page, size, sort));
    }

    @PostMapping("/accounts")
    public Account post(@RequestParam String name,
                        @RequestParam String status,
                        @RequestParam String user,
                        @RequestParam String level,
                        @RequestParam String job,
                        @RequestParam String beanAccount,
                        @RequestParam String beanPassword) {

        Account account = new Account();
        account.setName(name);
        account.setStatus(status);
        account.setUser(user);
        account.setLevel(level);
        account.setJob(job);
        account.setBeanAccount(beanAccount);
        account.setBeanPassword(beanPassword);
        return accountService.save(account);
    }

    @GetMapping("/accounts/{id}")
    public Account getOne(@PathVariable long id) {

        return accountService.findOne(id);
    }

    @PutMapping("/accounts")
    public Account update(@RequestParam long id,
                          @RequestParam String name,
                          @RequestParam String status,
                          @RequestParam String user,
                          @RequestParam String level,
                          @RequestParam String job,
                          @RequestParam String beanAccount,
                          @RequestParam String beanPassword) {

        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setStatus(status);
        account.setUser(user);
        account.setLevel(level);
        account.setJob(job);
        account.setBeanAccount(beanAccount);
        account.setBeanPassword(beanPassword);
        return account;
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteOne(@PathVariable long id) {

        accountService.delete(id);
    }

//    @PostMapping("/accounts/by")
//    public List<Account> findByStatus(@RequestParam String status, @RequestParam long id) {
//        return accountService.findByStatus(status);
////        return accountService.findByNoticeContains(notice);  // like 查詢 '%notice%'
////        return accountService.updateByJPQL(status, id);
//    }

    @PostMapping("/accounts/update")
    public int updateBy(@RequestParam String status, @RequestParam long id) {

        return accountService.updateByJPQL(status, id);
    }

}
