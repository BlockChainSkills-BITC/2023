package org.example.ECtrace.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.example.ECtrace.model.Result;
import org.example.ECtrace.model.bo.LoginBO;
import org.example.ECtrace.model.bo.RegisterBO;
import org.example.ECtrace.model.vo.ResultVO;
import org.example.ECtrace.utils.WeBASEUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    public static final String ABI = org.example.ECtrace.utils.IOUtil.readResourceAsString("abi/NewEnergy.abi");

    @Autowired
    WeBASEUtils weBASEUtils;

    @Value("${system.contract.newEnergyAddress}")
    private String contractAddress;

    @Value("${userAddress}")
    String userAddress;


        /*
    描述 : 账户管理功能
    参数 :
            _Uaddress: 用户地址
            _role: 用户角色
            _password: 密码
    返回值 :
            return  > 0 注册成功
    功能   :
            account_register：注册账号
            account_removeRole：删除注销
            account_login：登录账号
    */
    /*
    **注册功能

    0x489e233ff7740af3eed0c542f3b007b38258cb1c

    0xb55d15fd16910c67e00b88cb38f0e24b8f3f5ce0
    */

    public  Result<String> RegisterService(RegisterBO registerBO) {
        List funcParam = new ArrayList();
        funcParam.add(选手填写部分);
        funcParam.add(选手填写部分);
        funcParam.add(选手填写部分);
        Dict result = weBASEUtils.commonReq(userAddress,
                选手填写部分, funcParam, ABI, 选手填写部分, contractAddress);
        JSONObject respBody = JSONUtil.parseObj(result.get("result"));
        String data = (String)respBody.get("message");

        if (data.equals("Success")) {
            return Result.success("ok");
        }else {
            return Result.error(ResultVO.PARAM_EMPTY);
        }
    }


    public  Result LoginService(LoginBO loginBO) {
        List funcParam = new ArrayList();
        funcParam.add(选手填写部分);
        funcParam.add(选手填写部分);
        Dict result = weBASEUtils.commonReq(userAddress,
                选手填写部分, funcParam, ABI, 选手填写部分, contractAddress);
        JSONArray respBody = JSONUtil.parseArray(result.get("result"));
        String data = (String)respBody.get(0);
        if (data.equals("true")){
            return Result.success(loginBO.getAddress());
        }else {
            return Result.error(ResultVO.PARAM_EMPTY);
        }
    }
}
