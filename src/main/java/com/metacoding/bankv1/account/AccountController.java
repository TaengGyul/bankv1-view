package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;
    private final HttpSession session;

    // /account/1111?type=전체 (동적 쿼리)
    @GetMapping("/account/{number}")
    public String detail(@PathVariable("number") int number, @RequestParam(value = "type", required = false, defaultValue = "전체") String type,
                         HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        List<AccountResponse.DetailDTO> detailList = accountService.계좌상세보기(number, type, sessionUser.getId()); // getId()는 꼭 넣어야함 인증 아이디!
        request.setAttribute("models", detailList);
        return "account/detail";
    }

    @PostMapping("/account/transfer")
    public String transfer(AccountRequest.TransferDTO transferDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        accountService.계좌이체(transferDTO, sessionUser.getId());

        return "redirect:/"; // TODO
    }

    @GetMapping("/account/transfer-form")
    public String transferForm() {
        // 공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        return "/account/transfer-form";
    }

    @GetMapping("/account")
    public String list(HttpServletRequest request) {
        // 공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        // select * from account_tb where user_id = 1
        List<Account> accountList = accountService.나의계좌목록(sessionUser.getId());
        request.setAttribute("models", accountList);

        return "account/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/account/save-form")
    public String saveForm() {
        // 두가지 코드는 인증체크라고 함
        // 공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");
        return "account/save-form";
    }

    @PostMapping("/account/save")
    public String save(AccountRequest.SaveDTO saveDTO) {
        // 두가지 코드는 인증체크라고 함
        // 공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        accountService.계좌생성(saveDTO, sessionUser.getId());
        return "redirect:/account";
    }

}
