/**
 * 电力能源详情存储器，记录电力能源详细信息
 */
pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./Table.sol";
import "./LibString.sol";
import "./Ownable.sol";


contract EnergyStorage is Ownable {

    using LibString for string;

    event Result(int count);

    TableFactory tf;
    string constant TABLE_NAME = "E_energy";
    string constant TABLE_NAME2 = "_order";

    constructor() public {
        tf = TableFactory(0x1001);
        tf.createTable(TABLE_NAME, "num_id", "energy");

        tf.createTable(TABLE_NAME2, "id", "num,from,to,total,price");
    }


    /**
    * ""
    * 插入数据
    */
    function insert(string memory _numid) public returns(int) {
        int count = int(0);
        Table table = tf.openTable(TABLE_NAME);
        if(!_numid.empty()){
            Entry entry = table.newEntry();
            entry.set("energy", int(0));
            count = table.insert(_numid, entry);
        }
        emit Result(count);
        return count;
    }
    
  
    function update(string memory _numid) public returns(int) {
        int count = int(0);
        int produce_random = rand();
        Table table = tf.openTable(TABLE_NAME);
        if(!_numid.empty()){
            Entry entry = table.newEntry();
            entry.set("energy", produce_random);
            count = table.insert(_numid, entry);
        }
        emit Result(count);
        return count;
    }
    
    /**
    * 通过key获取value，可以存在多个value
    */
    function select(string memory _numid) public view returns(int[] memory){
        Table table = tf.openTable(TABLE_NAME);
        Condition condition = table.newCondition();
        Entries entries = table.select(_numid, condition);
        
        int[] memory value_list = new int[](uint256(entries.size()));
        
        for (int256 i = 0; i < entries.size(); ++i) {
            Entry entry = entries.get(i);
            value_list[uint256(i)] = entry.getInt("energy");
        }
        
        return value_list;

    }
    
    struct Total {
        uint Utotal;
        uint Usalltotal;
    }
    mapping(string => Total) totalmap;
    //能量总和
    function getEnergy(string memory _numid) public view returns(uint){
        // Total memory _total = totalmap(_numid);
        int[] memory value_list = select(_numid);
        uint total = 0;
        for (uint256 i = 0; i < value_list.length; i++) {
            // Arr[i] = uint(value_list[uint256(i)]);
            total += uint(value_list[uint256(i)]);
        }
        return total;

    }
    
    function update_sall(string memory _numid, uint sallE) public returns(bool){
        Total _total = totalmap[_numid];
        totalmap[_numid].Utotal = getEnergy(_numid);
        totalmap[_numid].Usalltotal = sallE;
        totalmap[_numid].Utotal -= sallE;
        return true;
    }       
    
    
    function remove(string memory _numid, string memory _energy) public returns(int) {
        int count = int(0);
        Table table = tf.openTable(TABLE_NAME);
        Condition condition = table.newCondition();
        condition.EQ("energy",_energy);
        count = table.remove(_numid,condition);  
        emit Result(count);
        return count;        
    }    

    //产生随机数
    function rand() private view returns(int) {
        int rand = int(keccak256(abi.encodePacked(block.difficulty, now))) % 100;
        if(rand < 70 && rand > 50){rand += 60;}
        if(rand < 50 && rand > 40){rand += 70;}
        if(rand < 40 && rand > 30){rand += 80;}
        if(rand < 30 && rand > 20){rand += 90;}
        if(rand < 20){rand += 100;}
        if(rand <= 0){rand += 110;}
        return rand;
    }    
    
    //记录该地址的交易订单
    function insert2(string memory _id, string memory _from, string memory _to, int _total, string memory _price) public returns(int) 
    {
        Table table = tf.openTable(TABLE_NAME2);
        Entry entry = table.newEntry();
        entry.set("from",_from);
        entry.set("to",_to);
        entry.set("total",int(_total));
        entry.set("price",_price);
        int count = table.insert(_id, entry);
        emit Result(count);
        return count;
    }
    //查询地址的交易订单
    function getOrder(string memory _id) public view returns(string[] memory){
        Table table = tf.openTable(TABLE_NAME2);
        Condition condition = table.newCondition();
        Entries entries = table.select(_id, condition);
        
        string[] memory value_list = new string[](uint256(entries.size()));
        
        for (int256 i = 0; i < entries.size(); ++i) {
            Entry entry = entries.get(i);
            value_list[uint256(i)] = _returnData(entry);
            
        }
        return value_list;
    }    
    

    // function convertGoods(string memory _str) private returns(Order){
    //     string[] memory ss = _str.split(",");
    //     return Order(ss[0],ss[1],ss[2],ss[3],ss[4],ss[5]);
    // }

    // function convertEntry(Order memory _order, Entry entry) private {
    //     entry.set("id",_order._UAddress);
    //     entry.set("num",_order._id);
    //     entry.set("from",_order._from);
    //     entry.set("to",_order._to);
    //     entry.set("total",_order._total);
    //     entry.set("price",_order._price);
        
    // }   
    
    //拼接成json数据
    function _returnData(Entry _entry) internal view returns(string){

        string memory _json = "{";
        
        // _json = _json.concat("'UAddress':'");
        // _json = _json.concat(_entry.getString("UAddress"));
        // _json = _json.concat("',");

        // _json = _json.concat("'id':'");
        // _json = _json.concat(_entry.getString("id"));
        // _json = _json.concat("',");

        _json = _json.concat("'from':'");
        _json = _json.concat(_entry.getString("from"));
        _json = _json.concat("',");

        _json = _json.concat("'to':'");
        _json = _json.concat(_entry.getString("to"));
        _json = _json.concat("',");
        
        _json = _json.concat("'total':'");
        _json = _json.concat(_entry.getString("total"));
        _json = _json.concat("',");
        
        _json = _json.concat("'price':'");
        _json = _json.concat(_entry.getString("price"));
        _json = _json.concat("'");


        _json = _json.concat("}");

        return _json;
    }    
    
    // struct Order{
    //     string _UAddress;
    //     string _id;
    //     string _from;
    //     string _to;
    //     string _total;
    //     string _price;
    // }

    // struct Energy {
    //     string Ownership;
    //     string energy_loss; //能量损耗
    //     string energy_produce; //剩余能量
    //     string today_produce; //今日发电
    //     string tody_loss;//今日消耗
    //     string yesterday_produce;//昨日发电
    //     string yesterday_loss;//昨日消耗
    //     string week_produce;//本周发电
    //     string week_loss;//本周消耗
    //     string month_produce;//本月发电
    //     string month_loss;//本月消耗       

    // } 
    /**
    * 创建质押物品表
    * +---------------------------+----------------+-----------------------------------------+
    * | Field                     |     Type       |      Desc                               |
    * +---------------------------+----------------+-----------------------------------------+
    * | num_id                          string
    * | energy_loss;                    string          
      | energy_produce;                 string              
      | today_produce;                  string              
      | tody_loss;                      string
      | yesterday_produce;              string
      | yesterday_loss;                 string
      | week_produce;                   string
      | week_loss;                      string
      | month_produce;                  string
      | month_loss;                     string
    * +---------------------------+----------------+-----------------------------------------+
    */
}
