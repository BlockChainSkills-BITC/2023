package org.example.ECtrace.controller;


import org.example.ECtrace.model.Result;
import org.example.ECtrace.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show")
public class ShowComtroller {
    @Autowired
    ShowService crudService;

    @RequestMapping(value = "/getblockNum", method = RequestMethod.GET)
    public Result<String> blockNumber() {
        return crudService.blockNumberS();
    }

    @RequestMapping(value = "/gettransaction", method = RequestMethod.GET)
    public Result<String> gettransaction() {
        return crudService.transactionS();
    }

    @RequestMapping(value = "/gethash", method = RequestMethod.GET)
    public Result<String> gethash() {
        return crudService.hashS();
    }

    @RequestMapping(value = "/getblockTransCnt", method = RequestMethod.GET)
    public Result<String> getblockTransCnt() {
        return crudService.blockTransCntS();
    }
}

