/**
* 角色存储器/控制器  
*
*  主合约
*/
pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./LibString.sol";
import "./Table.sol";
import "./MapStorage.sol";
import "./SolarPanelsStorage.sol";
import "./EnergyStorage.sol";

contract NewEnergy {

    using LibString for string;
    MapStorage private mapStorage;
    SolarPanelsStorage private solarPanelsStorage;
    EnergyStorage private energyStorage;

    event AddRoleResult(int count);
    event RemoveRoleResult(int count);

    TableFactory tf;
    string constant TABLE_NAME = "tx_role";

    string constant private U_Role = "用户";	// 用户
    string constant private EC_Role = "电力公司";	// 电力公司

    constructor() public {
        tf = TableFactory(0x1001);
        tf.createTable(TABLE_NAME, "Uaddress", "role,password,Assert");
        
        mapStorage = new MapStorage();
        solarPanelsStorage = new SolarPanelsStorage();
        energyStorage = new EnergyStorage();
    }
    

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
    function account_register(string memory _Uaddress, string memory _role, string memory _password) public returns(int) {
        int count = int(0);
        Table table = tf.openTable(TABLE_NAME);
        if(!_Uaddress.empty() && !_role.empty() && !_password.empty() && !_isExist(table, _Uaddress, _role, _password)){
            Entry entry = table.newEntry();
            entry.set("role", _role);
            entry.set("password", _password);
            count = table.insert(_Uaddress, entry);
        }
        emit AddRoleResult(count);
        return count;
    }

    /*
    **注销功能
    */
    function account_removeRole(string memory _Uaddress, string memory _role, string memory _password) public returns(int) {
        int count = int(0);
        Table table = tf.openTable(TABLE_NAME);
        if(!_Uaddress.empty() && !_role.empty() && !_password.empty() && !_isExist(table, _Uaddress, _role, _password)){
            Condition condition = table.newCondition();
            condition.EQ("role", _role);
            condition.EQ("password", _password);
            count = table.remove(_Uaddress, condition);
        }
        emit RemoveRoleResult(count);
        return count;
    }
    
    /*
    **登录功能
    */
    function account_login(string memory _Uaddress, string memory _role, string memory _password) public view returns(bool){
        Table table = tf.openTable(TABLE_NAME);
        return _isExist(table, _Uaddress, _role, _password);
    }

    function account_checkRole(string memory _Uaddress, string memory _role, string memory _password) public view{
        require(account_login(_Uaddress, _role, _password), "not have permission");
    }

    function _isExist(Table _table, string memory _Uaddress, string memory _role, string memory _password) 
    internal view returns(bool) 
    {
        Condition condition = _table.newCondition();
        condition.EQ("role", _role);
        condition.EQ("password", _password);
        return _table.select(_Uaddress, condition).size() > int(0);
    }

    function onlyURole(string memory Uaddress) private view{
        Table table = tf.openTable(TABLE_NAME);
        require(only(table, U_Role, Uaddress), "not has u permission");
    }

    function onlyECRole(string memory Uaddress) private view{
        Table table = tf.openTable(TABLE_NAME);
        require(only(table, EC_Role, Uaddress), "not has ec permission");
    }  

    function only(Table _table, string memory _Uaddress, string memory _role) 
    internal view returns(bool) 
    {
        Condition condition = _table.newCondition();
        condition.EQ("role", _role);
        return _table.select(_Uaddress, condition).size() > int(0);
    }     
    //查询
    // function select(string memory _Uaddress) public view returns(string[] memory, string[] memory, string[] memory){
    //     Table table = tf.openTable(TABLE_NAME);
    //     Condition condition = table.newCondition();
    //     Entries entries = table.select(_Uaddress, condition);
        
    //     string[] memory value_list1 = new string[](uint256(entries.size()));

    //     for (int256 i = 0; i < entries.size(); ++i) {
    //         Entry entry = entries.get(i);

    //         value_list2[uint256(i)] = entry.getString("role");
    //         value_list3[uint256(i)] = entry.getString("password");
    //     }
    //     return (value_list1, value_list2, value_list3);        
        
    // }
    
    /*
    描述 : 资产管理
    参数 :
            _Uaddress: 用户地址
            _Assert： 太阳能板编号
    返回值 :
          true 成功
          false 失败
    功能 ：
          Assert_put：添加资产
          Assert_get：获取资产列表
          Assert_remove：移除资产
    */    
    function Assert_put(string memory _Uaddress, string memory _Assert) public returns(int) {
        int count = mapStorage.put(_Uaddress, _Assert);
        return count;
    }
    
    function get_Address_Assert(string memory _Uaddress) public view returns(string[] memory){
        return mapStorage.get(_Uaddress);
    }
    
    function Assert_remove(string memory _Uaddress, string memory _Assert) public returns(int){
        int count = mapStorage.remove(_Uaddress, _Assert);
        return count;
    }

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
    */
    function SPU_insert(string _numid, string _goodsStr, string Uaddress) public returns(int256){
        onlyURole(Uaddress);
        int256 count = solarPanelsStorage.insert(_numid, _goodsStr);
        Assert_put(Uaddress, _numid);
        energyStorage.insert(_numid);
        solarPanelsStorage.insert2(Uaddress, _goodsStr);
        return count;
    }

    //更新太阳能板
    function SPU_update(string _numid, string _goodsStr, string Uaddress) public returns(int256){
        onlyURole(Uaddress);
        int256 count = solarPanelsStorage.update(_numid, _goodsStr);
        return count;
    }
    
    //太阳能板转让(购买)
    // Ownership: 所属权（用户地址）
    function SPU_transfer(string _numid, string Ownership, string Uaddress) public returns(int256){
        onlyURole(Uaddress);
        int256 count = solarPanelsStorage.transfer(_numid, Ownership);
        Assert_remove(Uaddress, _numid);
        Assert_put(Ownership, _numid);
        return count;
    }  

    /*
    ** 出售太阳能板
    ** Ownership: 所属权（用户地址）
    ** "GB/T 2296-2001, 600RMB , 0x489e233ff7740af3eed0c542f3b007b38258cb1c"
    */
    function SPU_saller(string _numid, string _price, string Uaddress) public returns(int256){
        onlyURole(Uaddress);
        int256 count = solarPanelsStorage.saller(_numid, _price);
        solarPanelsStorage.saller2(Uaddress, _price);
        return count;
    }       

    //获取单个型号(numid)所有太阳能板信息
    function get_numid_Spu(string _numid) public view returns(string[] memory){
        return solarPanelsStorage.getDetail(_numid);
    }  
    
    //获取账户(Uaddress)所有资产列表
    function get_Address_Spu(string _Uaddress) public view returns(string[] memory){
        return solarPanelsStorage.select2(_Uaddress);
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
    //
    function Energy_insert(string _numid) public returns(int){
        return energyStorage.insert(_numid);
    }
    
    function Energy_update(string _numid) public returns(int){
        return energyStorage.update(_numid);
    }

    function get_numid_Energy(string _numid) public view returns(int[] memory){
        return energyStorage.select(_numid);
    }

    function Energy_remove(string _numid, string _energy) public returns(int){
        return energyStorage.remove(_numid, _energy);
    }

    function Energy_transfer(string _from, string _to, uint _total, string _price) public returns(int) {
        // uint total = energyStorage.getEnergy(_from);
        energyStorage.update_sall(_from,_total);
        return energyStorage.insert2(_from,_from,_to,int(_total),_price);
    }

    function get_Energy_Order(string _numid) public view returns(string[] memory){
        return energyStorage.getOrder(_numid);
    }

    function get_Total_Energy(string _numid) public view returns(uint) {
        return energyStorage.getEnergy(_numid);
    }
    /**
    *  角色表
    * +----------------------+------------------------+-------------------------+
    * | Field                | Type                   | Desc                    |
    * +----------------------+------------------------+-------------------------+
    * | Uaddress             | string                 | 角色地址            
    * | role                 | string                 | 角色标识(用户或电力公司)
    * | password             | string                 | 用户密码                    
    * | Assert               | string[]
    * +----------------------+------------------------+-------------------------+
    */
    /**
    * 电池板表
    * +---------------------------+----------------+-----------------------------------------+
    * | Field                     |     Type       |      Desc                               |
    * +---------------------------+----------------+-----------------------------------------+
    * | num_id                    | string                 | 编号
    * | sp_name                   | string                 | 太阳能板名称
    * | sp_actual_Power           | string                 | 实际功率（单位：W)
    * | sp_rated_Power            | string                 | 额定功率（单位：W）
    * | sp_input_Time             | string                 | 生效时间（单位：h）
    * | sp_position               | string                 | 投放位置
    * | sp_price                  | string                 | 单价 (单位：元)
    * | Owner_ship                | string                 | 所属权（address）
    * +---------------------------+----------------+-----------------------------------------+
    */
}