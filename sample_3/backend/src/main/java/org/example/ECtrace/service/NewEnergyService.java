package org.example.ECtrace.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.example.ECtrace.model.Result;
import org.example.ECtrace.model.bo.*;
import org.example.ECtrace.model.bo.SPU_insterBO;
import org.example.ECtrace.model.vo.ResultVO;
import org.example.ECtrace.model.vo.SpuVO;
import org.example.ECtrace.model.vo.num_spu_lsitVO;
import org.example.ECtrace.utils.WeBASEUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class NewEnergyService {
  public static final String ABI = org.example.ECtrace.utils.IOUtil.readResourceAsString("abi/NewEnergy.abi");

  @Autowired
  WeBASEUtils weBASEUtils;

  @Value("${system.contract.newEnergyAddress}")
  private String contractAddress;

  @Value("${userAddress}")
  String userAddress;

   /*
    描述 : 交易市场
    参数 :
            _goodsStr: 太阳能板详情信息
            _numid： 太阳能板编号
    返回值 :
          true 成功
          false 失败
    功能 ：
          insert ： 添加太阳能板
          transfer ： 太阳能板转让
          saller ： 出售太阳能板
          get_numid_Spu ： 获取单个型号(numid)所有太阳能板信息
          get_Address_Spu: 获取账户(Uaddress)所有资产列表
          get_Address_Assert: 获取地址名下的所有资产
    */
  /** 创建太阳能板
   *  Uaddress：  用户地址
   *  _goodsStr： 太阳能板信息
   *  _numid：   太阳能板编号
   */
    /*添加太阳能板（创建）
    GB/T 2296-2001

    * "GB/T 2296-2001,太阳能光伏板,150W/m^2,260W/m^2,20h,China,700RMB,0x489e233ff7740af3eed0c542f3b007b38258cb1c"
    * 插入数据
    用户1： 0xb55d15fd16910c67e00b88cb38f0e24b8f3f5ce0
    用户2： 0x6afa01e66ff8e8dad90836ede88188f90357d685
    电力公司: 0x69363ed57bafd2b0d8e3cb720941c2160255404c
    */

  public  Result<String> SPU_insertService(SPU_insterBO spu_insterBO) {
//    List funcParam = new ArrayList();
    JSONArray funcParam = new JSONArray();
    funcParam.add(spu_insterBO.get_numid());
    funcParam.add(spu_insterBO.getName());
    funcParam.add(spu_insterBO.getActual_Power());
    funcParam.add(spu_insterBO.getRated_Power());
    funcParam.add(spu_insterBO.getInput_Time());
    funcParam.add(spu_insterBO.getPosition());
    funcParam.add(spu_insterBO.getPrice());
    funcParam.add(spu_insterBO.getAddress());

    Dict result = weBASEUtils.commonReq(userAddress,
            "SPU_insert", funcParam, ABI, "NewEnergy", contractAddress);
    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    if (data.equals("Success")) {
      return Result.success("ok");
    }else {
      return Result.success("failed");
    }
  }


  public  Result<String> SPU_sallerService(SPU_sallerBO spu_sallerBO) {
    List funcParam = new ArrayList();
    funcParam.add(选手填写部分);
    funcParam.add(选手填写部分);
    funcParam.add(选手填写部分);
    Dict result = weBASEUtils.commonReq(userAddress,
            选手填写部分, funcParam, ABI, 选手填写部分, contractAddress);

    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    if (data.equals("Success")) {
      return Result.success("ok");
    }else {
      return Result.success("failed");
    }
  }

  public  Result<String> SPU_transferService(SPU_transferBO spu_transferBO) {
    List funcParam = new ArrayList();
    funcParam.add(选手填写部分);
    funcParam.add(选手填写部分);
    funcParam.add(选手填写部分);
    Dict result = weBASEUtils.commonReq(userAddress,
            选手填写部分, funcParam, ABI, 选手填写部分, contractAddress);
    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    if (data.equals("Success")) {
      return Result.success("ok");
    }else {
      return Result.success("failed");
    }
  }

  public Result get_numid_SpuService(String _numid) {
    List funcParam = new ArrayList();
    funcParam.add(选手填写部分);
    Dict result = weBASEUtils.commonReq(userAddress,
            选手填写部分, funcParam, ABI, 选手填写部分, contractAddress);
    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
    JSONArray res =  JSONUtil.parseArray(resBody.get(0));
    JSONArray array = new JSONArray();
    for (int i = 0; i < res.toArray().length; i++){
      JSONObject jsonObject = JSONUtil.parseObj(res.get(i));
      array.add(jsonObject);
    }

    return Result.success(array);
  }

  public Result get_Address_SpuService(String _Address) {
    List funcParam = new ArrayList();
    funcParam.add(_Address);
    Dict result = weBASEUtils.commonReq(userAddress,
            "get_Address_Spu", funcParam, ABI, "NewEnergy", contractAddress);

    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
    JSONArray res =  JSONUtil.parseArray(resBody.get(0));
    JSONArray array = new JSONArray();
    for (int i = 0; i < res.toArray().length; i++){
      JSONObject jsonObject = JSONUtil.parseObj(res.get(i));
      array.add(jsonObject);
    }

    return Result.success(array);
  }

  public Result get_AssertS(String _Address) {
      List funcParam = new ArrayList();
      funcParam.add(_Address);
      Dict result = weBASEUtils.commonReq(userAddress,
              "get_Address_Assert", funcParam, ABI, "NewEnergy", contractAddress);
      JSONArray resBody = JSONUtil.parseArray(result.get("result"));
      JSONArray res =  JSONUtil.parseArray(resBody.get(0));
//    JSONArray array = new JSONArray();
//    for (int i = 0; i < res.toArray().length; i++){
//      JSONObject jsonObject = JSONUtil.parseObj(res.get(i));
//      array.add(jsonObject);
//    }

      return Result.success(res);
    }

  public Result get_Address_AssertService(String _Address) {
    List funcParam = new ArrayList();
    funcParam.add(_Address);
    Dict result = weBASEUtils.commonReq(userAddress,
            "get_Address_Assert", funcParam, ABI, "NewEnergy", contractAddress);
    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
    JSONArray res =  JSONUtil.parseArray(resBody.get(0));
    JSONArray data = new JSONArray();
    for (Object item : res){
      String _numid = StrUtil.toString(item);
      JSONArray obj = (JSONArray) get_numid_SpuService(_numid).getData();
      data.add(obj.get(0));
    }

    return Result.success(data);
  }

      /*
    描述 : 能源市场
    参数 :
            _goodsStr: 太阳能板详情信息
            _numid： 太阳能板编号
    返回值 :
          true 成功
          false 失败
    功能 ：
          Energy_insert: 初始化
          Energy_update ： 刷新
          get_numid_Energy： 查看能量列表（每日能量增长量）
          Energy_remove： 清除
          Energy_transfer： 出售能量
          get_Energy_Order： 获取订单列表
          get_Total_Energy： 获取总能量
    */
  public  Result<String> Energy_insertService(Energy_insertBO energy_insertBO) {
    List funcParam = new ArrayList();
    funcParam.add(energy_insertBO.get_numid());
    Dict result = weBASEUtils.commonReq(userAddress,
            "Energy_insert", funcParam, ABI, "NewEnergy", contractAddress);
    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    if (data.equals("Success")) {
      return Result.success("ok");
    }else {
      return Result.success("failed");
    }
  }
  public  Result Energy_updateService(String _Address) {
    List funcParam = new ArrayList();
    funcParam.add(_Address);
    Dict result = weBASEUtils.commonReq(userAddress,
            "Energy_update", funcParam, ABI, "NewEnergy", contractAddress);
    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    return Result.success(data);
  }


  public  Result<String> Energy_transferService(Energy_transferBO energy_transferBO) {
    List funcParam = new ArrayList();
    funcParam.add(energy_transferBO.get_from());
    funcParam.add(energy_transferBO.get_to());
    funcParam.add(energy_transferBO.get_total());
    funcParam.add(energy_transferBO.get_price());

    Dict result = weBASEUtils.commonReq(userAddress,
            "Energy_transfer", funcParam, ABI, "NewEnergy", contractAddress);
    JSONObject  resBody = JSONUtil.parseObj(result.get("result"));
    String data = (String) resBody.get("message");

    if (data.equals("Success")) {
      return Result.success("ok");
    }else {
      return Result.success("failed");
    }
  }

  public Result get_Total_EnergyService(String _numid) {
    List funcParam = new ArrayList();
    funcParam.add(_numid);
    Dict result = weBASEUtils.commonReq(userAddress,
            "get_Total_Energy", funcParam, ABI, "NewEnergy", contractAddress);
    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
//    JSONArray res =  JSONUtil.parseArray(resBody.get(0));


    return Result.success(resBody.get(0));
  }

  public Result get_numid_EnergyService(String _numid) {
    List funcParam = new ArrayList();
    funcParam.add(_numid);
    Dict result = weBASEUtils.commonReq(userAddress,
            "get_numid_Energy", funcParam, ABI, "NewEnergy", contractAddress);
    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
    JSONArray res =  JSONUtil.parseArray(resBody.get(0));
//    JSONArray array = new JSONArray();
//    for (int i = 0; i < res.toArray().length; i++){
//      JSONObject jsonObject = JSONUtil.parseObj(res.get(i));
//      array.add(jsonObject);
//    }

    return Result.success(res);
  }

  public Result get_Energy_OrderService(String _numid) {
    List funcParam = new ArrayList();
    funcParam.add(_numid);
    Dict result = weBASEUtils.commonReq(userAddress,
            "get_Energy_Order", funcParam, ABI, "NewEnergy", contractAddress);
    JSONArray resBody = JSONUtil.parseArray(result.get("result"));
    JSONArray res =  JSONUtil.parseArray(resBody.get(0));
    JSONArray array = new JSONArray();
    for (int i = 0; i < res.toArray().length; i++){
      JSONObject jsonObject = JSONUtil.parseObj(res.get(i));
      array.add(jsonObject);
    }

    return Result.success(array);
  }
  /*
  *  区块链浏览器
  **/
  public Dict listContract() {
    Dict result =weBASEUtils.listDeployedContract();
    JSONObject bodyJSON = JSONUtil.parseObj(result.get("result"));
    JSONArray constractArray = JSONUtil.parseArray(bodyJSON.get("data"));
    List<Object> retArr = constractArray.stream().map(obj ->{
      JSONObject rawObj = (JSONObject) obj;
      JSONObject json = new JSONObject();
      json.set("合约名称", rawObj.get("contractName"));
      json.set("合约地址", rawObj.get("contractAddress"));
      json.set("部署时间", rawObj.get("deployTime"));
      json.set("创建时间", rawObj.get("createTime"));
      json.set("修改时间", rawObj.get("modifyTime"));
      json.set("abi", rawObj.get("contractAbi"));
      return json;
    }).collect(Collectors.toList());

    Dict retDict = new Dict();
    retDict.set("result",retArr);
    return retDict;
  }
}
