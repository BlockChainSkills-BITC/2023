package org.example.ECtrace.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WeBASEUtils {

    public Dict commonReq(String userAddress,
                   String funcName,
                   List funcParam,
                   String ABI,
                   String contractName,
                   String contractAddress) {
        JSONArray abiJSON = JSONUtil.parseArray(ABI);
        JSONObject data = JSONUtil.createObj();
        data.set("groupId", "1");
        data.set("user", userAddress);
        data.set("contractName", contractName);
        data.set("version", "");
        data.set("funcName", funcName);
        data.set("funcParam", funcParam);
        data.set("contractAddress", contractAddress);
        data.set("contractAbi", abiJSON);
        data.set("useAes", false);
        data.set("useCns", false);
        data.set("cnsName", "");
        String dataString = JSONUtil.toJsonStr(data);
        String responseBody = HttpRequest.post("http://192.168.1.85:5002/WeBASE-Front/trans/handle")
                .header(Header.CONTENT_TYPE, "application/json").body(dataString).execute().body();
        Dict retDict = new Dict();
        retDict.set("result", responseBody);
        return retDict;
    }

    public Dict listDeployedContract() {
        JSONObject data = JSONUtil.createObj();
        data.set("groupId","1");
        data.set("contractName","");
        data.set("contractStatus",2);
        data.set("contractAddress","");
        data.set("pageNumber",1);
        data.set("pageSize",10);

        String dataString = JSONUtil.toJsonStr(data);
        String responseBody = HttpRequest.post("http://192.168.145.153:5002/WeBASE-Front/contract/contractList")
                .header(Header.CONTENT_TYPE,"application/json")
                .body(dataString)
                .execute()
                .body();
        Dict retDict = new Dict();
        retDict.set("result", responseBody);
        return retDict;

    }
    public String blockNumber() {
        String responseBody = HttpRequest.get("http://192.168.145.153:5002/WeBASE-Front/1/web3/blockNumber")
                .header(Header.CONTENT_TYPE,"application/json").execute().body();
        return responseBody;
    }

    public Dict transaction(){
        String respanserBody = HttpRequest.get("http://192.168.145.153:5002/WeBASE-Front/1/web3/transaction-total")
                .header(Header.CONTENT_TYPE,"application/json").execute().body();
        Dict result = new Dict();
        result.set("result", respanserBody);
        return result;
    }

    public Dict hash(){
        HashMap<String, Object> paramMap = new HashMap<>();
        String block = blockNumber();
        paramMap.put("input", block);
        paramMap.put("type", 1);
        String respanserBody = HttpRequest.get("http://192.168.145.153:5002/WeBASE-Front/tool/hash")
                .header(Header.CONTENT_TYPE,"application/json").form(paramMap).execute().body();

        Dict result = new Dict();
        result.set("result", respanserBody);
        return result;
    }

    public String blockTransCnt(){
        String block = blockNumber();
        String respanserBody = HttpRequest.get("http://192.168.145.153:5002/WeBASE-Front/1/web3/blockTransCnt/")
                .header(Header.CONTENT_TYPE,"application/json").form(block).execute().body();

        return respanserBody;
    }

}
