/**
 * 太阳能板详情存储器，记录太阳能板的详细信息
 */
pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./Table.sol";
import "./LibString.sol";
import "./Ownable.sol";
import "./MapStorage.sol";

contract SolarPanelsStorage is Ownable {

    using LibString for string;

    MapStorage private mapStorage;

    event InsertResult(int256);
    event UpdateResult(int256);

    TableFactory tf;
    string constant TABLE_NAME = "sp_goods";

    string constant TABLE_NAME2 = "_interface";

    /**
    * 创建太能能电池板实体
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
    constructor() public {
        tf = TableFactory(0x1001);
        tf.createTable(TABLE_NAME, "num_id", "sp_name,sp_actual_Power,sp_rated_Power,sp_input_Time,sp_position,sp_price,Owner_ship");
        tf.createTable(TABLE_NAME2, "Owner_ship", "num_id,sp_name,sp_actual_Power,sp_rated_Power,sp_input_Time,sp_position,sp_price");
        mapStorage = new MapStorage();
    }
//TABLE_NAME(表1)
    /**
    * "85414020.00,太阳能电池,150W,205W,20h,China,5000RMB,test"
    * 插入数据
    */
    function insert(string memory _numid, string memory _goodsStr) public onlyOwner returns(int) {
        Goods memory _goods = convertGoods(_goodsStr);
        Table table = tf.openTable(TABLE_NAME);
        Entry entry = table.newEntry();
        convertEntry(_goods, entry);
        int256 count = table.insert(_numid, entry);
        emit InsertResult(count);
        return count;
    }

    /**
    *  "1,太阳能电池,150W,205W,20h,China,1000RMB,test"
    * 更新数据
    */
    function update(string memory _numid, string memory _goodsStr) public onlyOwner returns(int256) {
        Goods memory _goods = convertGoods(_goodsStr);
        Table table = tf.openTable(TABLE_NAME);
        require(_isnumidExist(table, _goods.numid), "update: current numid not exist");
        Entry entry = table.newEntry();
        convertEntry(_goods, entry);
        Condition condition = table.newCondition();
        int256 count = table.update(_numid, entry, condition);
        emit UpdateResult(count);
        return count;
    }

    /**
    *  85414020.00
    *  test3
    *  更改所属权
    */
    function transfer(string memory _numid, string memory Ownership) public returns(int256) {
        
        Table table = tf.openTable(TABLE_NAME);
        require(_isnumidExist(table, _numid), "update: current numid not exist");
        
        Entry entry = table.newEntry();
        entry.set("sp_price", "已出售");
        entry.set("Owner_ship", Ownership);
        
        Condition condition = table.newCondition();
        int256 count = table.update(_numid, entry, condition);
        emit UpdateResult(count);
        return count;
    }
    
    /**
    *  85414020.00
    *  test3
    *  更改单价
    */
    function saller(string memory _numid, string memory _price) public returns(int256) {
        int code = 0;
        
        Table table = tf.openTable(TABLE_NAME);
        require(_isnumidExist(table, _numid), "0");
        
        Entry entry = table.newEntry();
        entry.set("sp_price", _price);
        
        Condition condition = table.newCondition();
        int256 count = table.update(_numid, entry, condition);
        if(count != 1) {
            // 失败? 无权限或者其他错误?
            code = 0;
            return code;
        }
        emit UpdateResult(count);
        return count;
    }    
    
    /**
    * 通过numid查询数据
    */
    
    function getDetail(string memory _num_id) public view returns(string[] memory){
        Table table = tf.openTable(TABLE_NAME);
        Condition condition = table.newCondition();
        Entries entries = table.select(_num_id, condition);
        
        string[] memory value_list = new string[](uint256(entries.size()));
        
        for (int256 i = 0; i < entries.size(); ++i) {
            Entry entry = entries.get(i);
            value_list[uint256(i)] = _returnData(entry);
            
        }
        return value_list;
    }

    
    function convertGoods(string memory _str) private returns(Goods){
        string[] memory ss = _str.split(",");
        return Goods(ss[0],ss[1],ss[2],ss[3],ss[4],ss[5],ss[6],ss[7]);
    }

    function convertEntry(Goods memory _goods, Entry entry) private {
        entry.set("num_id",_goods.numid);
        entry.set("sp_name",_goods.name);
        entry.set("sp_actual_Power",_goods.actual_Power);
        entry.set("sp_rated_Power",_goods.rated_Power);
        entry.set("sp_input_Time",_goods.input_Time);
        entry.set("sp_position",_goods.position);
        entry.set("sp_price",_goods.price);
        entry.set("Owner_ship",_goods.Ownership);
        
    }

    function _isnumidExist(Table _table, string memory _id) internal view returns(bool) {
        Condition condition = _table.newCondition();
        return _table.select(_id, condition).size() != int(0);
    }

    //拼接成json数据
    function _returnData(Entry _entry) internal view returns(string){

        string memory _json = "{";

        _json = _json.concat("'numid':'");
        _json = _json.concat(_entry.getString("num_id"));
        _json = _json.concat("',");

        _json = _json.concat("'name':'");
        _json = _json.concat(_entry.getString("sp_name"));
        _json = _json.concat("',");

        _json = _json.concat("'actual_Power':'");
        _json = _json.concat(_entry.getString("sp_actual_Power"));
        _json = _json.concat("',");

        _json = _json.concat("'rated_Power':'");
        _json = _json.concat(_entry.getString("sp_rated_Power"));
        _json = _json.concat("',");

        _json = _json.concat("'input_Time':'");
        _json = _json.concat(_entry.getString("sp_input_Time"));
        _json = _json.concat("',");

        _json = _json.concat("'position':'");
        _json = _json.concat(_entry.getString("sp_position"));
        _json = _json.concat("',");

        _json = _json.concat("'price':'");
        _json = _json.concat(_entry.getString("sp_price"));
        _json = _json.concat("',");

        _json = _json.concat("'Ownership':'");
        _json = _json.concat(_entry.getString("Owner_ship"));
        _json = _json.concat("'");


        _json = _json.concat("}");

        return _json;
    }

    struct Goods {
        string numid; //编号
        string name; //太阳能板名称
        string actual_Power; //实际功率（单位：W)
        string rated_Power; //额定功率（单位：W）
        string input_Time; //生效时间（单位：h）
        string position; //投放位置
        string price; //单价 (单位：元)
        string Ownership; //所属权（address）
    }

    /**
    * 插入数据，已有数据不添加
    */
    function insert2(string memory Owner_ship, string memory _value) public onlyOwner returns(int) {
        Assert memory _assert = convertAssert2(_value);
        Table table = tf.openTable(TABLE_NAME);
        Entry entry = table.newEntry();
        convertEntry2(_assert, entry);
        int count = table.insert(Owner_ship, entry);
        return count;
    }
    
    /**
    * 通过key获取value，可以存在多个value
    */
    function select2(string memory _key) public view returns(string[] memory){
        Table table = tf.openTable(TABLE_NAME);
        Condition condition = table.newCondition();
        Entries entries = table.select(_key, condition);
        string[] memory value_list = new string[](uint256(entries.size()));
        for (int256 i = 0; i < entries.size(); ++i) {
            Entry entry = entries.get(i);
            value_list[uint256(i)] = _returnData2(entry);
            
        }
        return value_list;
    }

    function saller2(string memory Owner_ship, string memory _price) public returns(int256) {
        int code = 0;
        Table table = tf.openTable(TABLE_NAME2);
        Entry entry = table.newEntry();
        entry.set("sp_price", _price);
        Condition condition = table.newCondition();
        int256 count = table.update(Owner_ship, entry, condition);
        if(count != 1) {
            // 失败? 无权限或者其他错误?
            code = 0;
            return code;
        }
        emit UpdateResult(count);
        return count;
    }  
    
    
    function convertAssert2(string memory _str) private returns(Assert){
        string[] memory ss = _str.split(",");
        return Assert(ss[0],ss[1],ss[2],ss[3],ss[4],ss[5],ss[6],ss[7]);
    }

    function convertEntry2(Assert memory _assert, Entry entry) private {
        entry.set("num_id",_assert.numid);
        entry.set("sp_name",_assert.name);
        entry.set("sp_actual_Power",_assert.actual_Power);
        entry.set("sp_rated_Power",_assert.rated_Power);
        entry.set("sp_input_Time",_assert.input_Time);
        entry.set("sp_position",_assert.position);
        entry.set("sp_price",_assert.price);
        entry.set("Owner_ship",_assert.Ownership);

    }

    //拼接成json数据
    function _returnData2(Entry _entry) internal view returns(string){

        string memory _json = "{";
        
        _json = _json.concat("'numid':'");
        _json = _json.concat(_entry.getString("num_id"));
        _json = _json.concat("',");

        _json = _json.concat("'name':'");
        _json = _json.concat(_entry.getString("sp_name"));
        _json = _json.concat("',");

        _json = _json.concat("'actual_Power':'");
        _json = _json.concat(_entry.getString("sp_actual_Power"));
        _json = _json.concat("',");

        _json = _json.concat("'rated_Power':'");
        _json = _json.concat(_entry.getString("sp_rated_Power"));
        _json = _json.concat("',");

        _json = _json.concat("'input_Time':'");
        _json = _json.concat(_entry.getString("sp_input_Time"));
        _json = _json.concat("',");

        _json = _json.concat("'position':'");
        _json = _json.concat(_entry.getString("sp_position"));
        _json = _json.concat("',");

        _json = _json.concat("'price':'");
        _json = _json.concat(_entry.getString("sp_price"));
        _json = _json.concat("',");
        
        _json = _json.concat("'Ownership':'");
        _json = _json.concat(_entry.getString("Owner_ship"));
        _json = _json.concat("'");

        _json = _json.concat("}");

        return _json;
    }
    
    struct Assert {
        string numid; //编号
        string name; //太阳能板名称
        string actual_Power; //实际功率（单位：W)
        string rated_Power; //额定功率（单位：W）
        string input_Time; //生效时间（单位：h）
        string position; //投放位置
        string price; //单价 (单位：元)
        string Ownership; //所属权（address）
    }



    // function getDetail(string memory _num_id) public view returns(string memory _json){
    //     Entry entry = select(_num_id);
    //     _json = _returnData(entry);
    // }
    

    // /**
    // *  通过numid获取实体
    // */
    // function select(string memory _numid) private view returns(Entry _entry){
    //     Table table = tf.openTable(TABLE_NAME);
    //     require(_isnumidExist(table, _numid), "select: current numid not exist");

    //     Condition condition = table.newCondition();
    //     _entry = table.select(_numid, condition).get(int(0));
    // }
    
    // function select2( string memory id) public view returns(string[] memory){
    //     Table table =tf.openTable(TABLE_NAME);
    //     // require(_isnumidExist(table, _numid), "select: current numid not exist");
        
    //     Condition condition = table.newCondition();
    //     // condition.limit(2);
    //     // condition.EQ("Owner_ship", _Owner_ship);
        
    //     Entries entries = table.select(id, condition);
    //     string[] memory list = new string[](uint256(entries.size()));
        
    //     for(int i = 0; i < entries.size(); ++i){
    //       Entry entry = entries.get(i);
           
    //       list[uint256(i)] = entry.getString("Owner_ship");
    //     }        
    //     return list;
    //     // _entry = table.select(_numid, condition).get(int(7));
    // }
    
    // function SelectOwnership(string memory _Owner_ship) public view returns(string, string){
    //     Table table = tf.openTable(TABLE_NAME);
    //     Condition condition = table.newCondition();
    //     Entries entries = table.select(_Owner_ship, condition);
    //     for(int i = 0; i < entries.size(); ++i){
    //       return select2(i);
    //     }
        
    // }
    
    // function select3(string memory _numid) public view returns (string[] memory,string[] memory,string[] memory,
    // string[] memory,string[] memory,string[] memory,string[] memory,string[] memory)
    // {
    //     Table table = tf.openTable(TABLE_NAME);

    //     Condition condition = table.newCondition();

    //     Entries entries = table.select(_numid, condition);
    //     string[] memory a = new string[](uint256(entries.size()));
    //     string[] memory b = new string[](uint256(entries.size()));
    //     string[] memory c = new string[](uint256(entries.size()));
    //     string[] memory d = new string[](uint256(entries.size()));
    //     string[] memory e = new string[](uint256(entries.size()));
    //     string[] memory f = new string[](uint256(entries.size()));
    //     string[] memory g = new string[](uint256(entries.size()));
    //     string[] memory h = new string[](uint256(entries.size()));

    //     for (int256 i = 0; i < entries.size(); ++i) {
    //         Entry entry = entries.get(i);

    //         a[uint256(i)] = entry.getString("numid");
    //         b[uint256(i)] = entry.getString("name");
    //         c[uint256(i)] = entry.getString("actual_Power");
    //         d[uint256(i)] = entry.getString("rated_Power");
    //         e[uint256(i)] = entry.getString("input_Time");
    //         f[uint256(i)] = entry.getString("position");
    //         g[uint256(i)] = entry.getString("price");
    //         h[uint256(i)] = entry.getString("Ownership");
    //     }

    //     return (a, b, c, d, e, f, g, h);        
    // }
}
