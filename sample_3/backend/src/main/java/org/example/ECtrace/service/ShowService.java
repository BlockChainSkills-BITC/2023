package org.example.ECtrace.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.example.ECtrace.model.Result;
import org.example.ECtrace.utils.WeBASEUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ShowService {

    @Autowired
    WeBASEUtils weBASEUtils;

    @Value("${system.contract.newEnergyAddress}")
    private String contractAddress;

    @Value("${userAddress}")
    String userAddress;

    public Result blockNumberS () {
        String result = weBASEUtils.blockNumber();
        return Result.success(result);
    }

    public Result transactionS(){
        Dict result = weBASEUtils.transaction();
        JSONObject resBody = JSONUtil.parseObj(result.get("result"));
        return Result.success(resBody);
    }
    public Result hashS(){
        Dict result = weBASEUtils.hash();
        JSONObject resBody = JSONUtil.parseObj(result.get("result"));
        return Result.success(resBody);
    }

    public Result blockTransCntS(){
        String result = weBASEUtils.blockTransCnt();
        return Result.success(result);
    }
}
