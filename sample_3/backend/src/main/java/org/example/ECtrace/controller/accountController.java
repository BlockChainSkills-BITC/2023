package org.example.ECtrace.controller;

import cn.hutool.core.lang.Dict;
import org.example.ECtrace.model.bo.LoginBO;
import org.example.ECtrace.model.bo.RegisterBO;
import org.example.ECtrace.model.Result;
import org.example.ECtrace.model.vo.ResultVO;
import org.example.ECtrace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class accountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(选手填写部分, method = RequestMethod.POST)
    public Result<String> register(@RequestBody RegisterBO registerBO) {
        return 选手填写部分;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody LoginBO loginBO) {
        选手填写部分;
    }

}
