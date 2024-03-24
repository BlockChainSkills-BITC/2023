package org.example.ECtrace.controller;

import cn.hutool.core.lang.Dict;
import org.example.ECtrace.model.Result;
import org.example.ECtrace.model.bo.*;
import org.example.ECtrace.model.bo.SPU_sallerBO;
import org.example.ECtrace.model.vo.ChangeVO;
import org.example.ECtrace.model.vo.num_spu_lsitVO;
import org.example.ECtrace.service.NewEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/")
public class NewEnergyController {
    @Autowired
    NewEnergyService newEnergyService;

    /**
     * 电力交易接口
     */
    @RequestMapping(value = "/SPU_insert", method = RequestMethod.POST)
    public Result<String> SPU_insert(@RequestBody SPU_insterBO spu_insterBO) {
        return newEnergyService.SPU_insertService(spu_insterBO);
    }
    @RequestMapping(选手填写部分, method = RequestMethod.POST)
    public Result<String> SPU_saller(@RequestBody SPU_sallerBO spu_sallerBO) {
        return 选手填写部分;
    }

    @RequestMapping(选手填写部分, method = RequestMethod.POST)
    public Result<String> SSPU_transfer(@RequestBody SPU_transferBO spu_transferBO) {
        return 选手填写部分;
    }

    @RequestMapping(选手填写部分, method = RequestMethod.GET)
    public Result get_numid_Spu(@RequestParam("_numid") String _numid) {
        return 选手填写部分;
    }

    @RequestMapping(value = "/get_Address_Spu", method = RequestMethod.GET)
    public Result get_Address_Spu(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_Address_SpuService(_Address);
    }

    @RequestMapping(value = "/get_Assert", method = RequestMethod.GET)
    public Result get_Assert(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_AssertS(_Address);
    }

    @RequestMapping(value = "/get_Address_Assert", method = RequestMethod.GET)
    public Result get_Address_Assert(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_Address_AssertService(_Address);
    }

    /**
     * 能源交易接口
     */
    @RequestMapping(value = "/Energy_insert", method = RequestMethod.POST)
    public Result<String> Energy_insert(@RequestBody Energy_insertBO energy_insertBO) {
        return newEnergyService.Energy_insertService(energy_insertBO);
    }

    @RequestMapping(value = "/Energy_update", method = RequestMethod.GET)
    public Result<String> Energy_update(@RequestParam("_Address") String _Address) {
        return newEnergyService.Energy_updateService(_Address);
    }

    @RequestMapping(value = "/Energy_transfer", method = RequestMethod.POST)
    public Result<String> Energy_transfer(@RequestBody Energy_transferBO energy_transferBO) {
        return newEnergyService.Energy_transferService(energy_transferBO);
    }

    @RequestMapping(value = "/get_Total_Energy", method = RequestMethod.GET)
    public Result get_Total_Energy(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_Total_EnergyService(_Address);
    }

    @RequestMapping(value = "/get_numid_Energy", method = RequestMethod.GET)
    public Result get_numid_Energy(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_numid_EnergyService(_Address);
    }

    @RequestMapping(value = "/get_Energy_Order", method = RequestMethod.GET)
    public Result get_Energy_Order(@RequestParam("_Address") String _Address) {
        return newEnergyService.get_Energy_OrderService(_Address);
    }
    /**
     * 区块链浏览器列表
     */
    @RequestMapping(value= "list", method = RequestMethod.GET)
    public Dict listDeployedContract() {
        return  newEnergyService.listContract();
    }


}
