import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Int256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class MapStorage extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611001600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166356004b6a6040805190810160405280600681526020017f74785f6d617000000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016200014391906200028c565b602060405180830381600087803b1580156200015e57600080fd5b505af115801562000173573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250620001999190810190620001b6565b5062000336565b6000620001ae8251620002e5565b905092915050565b600060208284031215620001c957600080fd5b6000620001d984828501620001a0565b91505092915050565b6000620001ef82620002da565b80845262000205816020860160208601620002ef565b620002108162000325565b602085010191505092915050565b6000600382527f6b657900000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600582527f76616c75650000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006060820190508181036000830152620002a88184620001e2565b90508181036020830152620002bd816200021e565b90508181036040830152620002d28162000255565b905092915050565b600081519050919050565b6000819050919050565b60005b838110156200030f578082015181840152602081019050620002f2565b838111156200031f576000848401525b50505050565b6000601f19601f8301169050919050565b611b4680620003466000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063425080c81461007257806344590a7e146100af578063693ec85e146100ec5780638da5cb5b14610129578063f2fde38b14610154575b600080fd5b34801561007e57600080fd5b506100996004803603610094919081019061167d565b61017d565b6040516100a69190611873565b60405180910390f35b3480156100bb57600080fd5b506100d660048036036100d1919081019061167d565b61052b565b6040516100e39190611873565b60405180910390f35b3480156100f857600080fd5b50610113600480360361010e91908101906115fb565b610848565b6040516101209190611851565b60405180910390f35b34801561013557600080fd5b5061013e610d9e565b60405161014b9190611836565b60405180910390f35b34801561016057600080fd5b5061017b60048036036101769190810190611505565b610dc3565b005b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156101de57600080fd5b60009250600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600681526020017f74785f6d617000000000000000000000000000000000000000000000000000008152506040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161028e919061188e565b602060405180830381600087803b1580156102a857600080fd5b505af11580156102bc573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102e091908101906115a9565b91506102eb86610f18565b1580156102fe57506102fc85610f18565b155b80156103125750610310828787611168565b155b156104e8578173ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561037b57600080fd5b505af115801561038f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506103b39190810190611580565b90508073ffffffffffffffffffffffffffffffffffffffff1663e942b516866040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161040a9190611930565b600060405180830381600087803b15801561042457600080fd5b505af1158015610438573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff166331afac3687836040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016104939291906118e0565b602060405180830381600087803b1580156104ad57600080fd5b505af11580156104c1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506104e591908101906115d2565b92505b7f2c6df32d56be76537eba33389b71e9491282e916ec27a33d9d87ef3ca247241d836040516105179190611873565b60405180910390a182935050505092915050565b60008060008060009250600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600681526020017f74785f6d617000000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016105e1919061188e565b602060405180830381600087803b1580156105fb57600080fd5b505af115801561060f573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061063391908101906115a9565b91508173ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561069957600080fd5b505af11580156106ad573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106d1919081019061152e565b90508073ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1866040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016107289190611930565b600060405180830381600087803b15801561074257600080fd5b505af1158015610756573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff166328bb211787836040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016107b19291906118b0565b602060405180830381600087803b1580156107cb57600080fd5b505af11580156107df573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061080391908101906115d2565b92507f2c6df32d56be76537eba33389b71e9491282e916ec27a33d9d87ef3ca247241d836040516108349190611873565b60405180910390a182935050505092915050565b606060008060006060600080600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600681526020017f74785f6d617000000000000000000000000000000000000000000000000000008152506040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610900919061188e565b602060405180830381600087803b15801561091a57600080fd5b505af115801561092e573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061095291908101906115a9565b95508573ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156109b857600080fd5b505af11580156109cc573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506109f0919081019061152e565b94508573ffffffffffffffffffffffffffffffffffffffff1663e8434e3989876040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610a499291906118b0565b602060405180830381600087803b158015610a6357600080fd5b505af1158015610a77573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610a9b9190810190611557565b93508373ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610b0157600080fd5b505af1158015610b15573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610b3991908101906115d2565b604051908082528060200260200182016040528015610b6c57816020015b6060815260200190600190039081610b575790505b509250600091505b8373ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610bd857600080fd5b505af1158015610bec573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610c1091908101906115d2565b821215610d90578373ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610c6c9190611873565b602060405180830381600087803b158015610c8657600080fd5b505af1158015610c9a573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610cbe9190810190","611580565b90508073ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610d1390611910565b600060405180830381600087803b158015610d2d57600080fd5b505af1158015610d41573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250610d6a919081019061163c565b8383815181101515610d7857fe5b90602001906020020181905250816001019150610b74565b829650505050505050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610e1e57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515610e5a57600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60006060600080849250600083511415610f355760019350611160565b600091505b825182101561115b578282815181101515610f5157fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f010000000000000000000000000000000000000000000000000000000000000002905060207f010000000000000000000000000000000000000000000000000000000000000002817effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff19161415801561105a575060097f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614155b80156110cd5750600a7f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614155b80156111405750600d7f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614155b1561114e5760009350611160565b8180600101925050610f3a565b600193505b505050919050565b6000808473ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156111cf57600080fd5b505af11580156111e3573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250611207919081019061152e565b90508073ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1846040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161125e9190611930565b600060405180830381600087803b15801561127857600080fd5b505af115801561128c573d6000803e3d6000fd5b5050505060008573ffffffffffffffffffffffffffffffffffffffff1663e8434e3986846040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016112e99291906118b0565b602060405180830381600087803b15801561130357600080fd5b505af1158015611317573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061133b9190810190611557565b73ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561139e57600080fd5b505af11580156113b2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506113d691908101906115d2565b139150509392505050565b60006113ed8235611a23565b905092915050565b60006114018251611a43565b905092915050565b60006114158251611a55565b905092915050565b60006114298251611a67565b905092915050565b600061143d8251611a79565b905092915050565b60006114518251611a8b565b905092915050565b600082601f830112151561146c57600080fd5b813561147f61147a82611992565b611965565b9150808252602083016020830185838301111561149b57600080fd5b6114a6838284611ab9565b50505092915050565b600082601f83011215156114c257600080fd5b81516114d56114d082611992565b611965565b915080825260208301602083018583830111156114f157600080fd5b6114fc838284611ac8565b50505092915050565b60006020828403121561151757600080fd5b6000611525848285016113e1565b91505092915050565b60006020828403121561154057600080fd5b600061154e848285016113f5565b91505092915050565b60006020828403121561156957600080fd5b600061157784828501611409565b91505092915050565b60006020828403121561159257600080fd5b60006115a08482850161141d565b91505092915050565b6000602082840312156115bb57600080fd5b60006115c984828501611431565b91505092915050565b6000602082840312156115e457600080fd5b60006115f284828501611445565b91505092915050565b60006020828403121561160d57600080fd5b600082013567ffffffffffffffff81111561162757600080fd5b61163384828501611459565b91505092915050565b60006020828403121561164e57600080fd5b600082015167ffffffffffffffff81111561166857600080fd5b611674848285016114af565b91505092915050565b6000806040838503121561169057600080fd5b600083013567ffffffffffffffff8111156116aa57600080fd5b6116b685828601611459565b925050602083013567ffffffffffffffff8111156116d357600080fd5b6116df85828601611459565b9150509250929050565b6116f2816119f9565b82525050565b6000611703826119cb565b8084526020840193508360208202850161171c856119be565b60005b848110156117555783830388526117378383516117c9565b9250611742826119ec565b915060208801975060018101905061171f565b508196508694505050505092915050565b61176f81611a95565b82525050565b61177e81611aa7565b82525050565b61178d81611a19565b82525050565b600061179e826119e1565b8084526117b2816020860160208601611ac8565b6117bb81611afb565b602085010191505092915050565b60006117d4826119d6565b8084526117e8816020860160208601611ac8565b6117f181611afb565b602085010191505092915050565b6000600582527f76616c75650000000000000000000000000000000000000000000000000000006020830152604082019050919050565b600060208201905061184b60008301846116e9565b92915050565b6000602082019050818103600083015261186b81846116f8565b905092915050565b60006020820190506118886000830184611784565b92915050565b600060208201905081810360008301526118a881846117c9565b905092915050565b600060408201905081810360008301526118ca8185611793565b90506118d96020830184611766565b9392505050565b600060408201905081810360008301526118fa8185611793565b90506119096020830184611775565b9392505050565b60006020820190508181036000830152611929816117ff565b9050919050565b60006040820190508181036000830152611949816117ff565b9050818103602083015261195d8184611793565b905092915050565b6000604051905081810181811067ffffffffffffffff8211171561198857600080fd5b8060405250919050565b600067ffffffffffffffff8211156119a957600080fd5b601f19601f8301169050602081019050919050565b6000602082019050919050565b600081519050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611a4e826119f9565b9050919050565b6000611a60826119f9565b9050919050565b6000611a72826119f9565b9050919050565b6000611a84826119f9565b9050919050565b6000819050919050565b6000611aa0826119f9565b9050919050565b6000611ab2826119f9565b9050919050565b82818337600083830152505050565b60005b83811015611ae6578082015181840152602081019050611acb565b83811115611af5576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820a634a57b8dc99c10a2355857508b3f6c6f26e2bfb964982515e5828b52dcdd2f6c6578706572696d656e74616cf50037"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_key\",\"type\":\"string\"},{\"name\":\"_value\",\"type\":\"string\"}],\"name\":\"put\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_key\",\"type\":\"string\"},{\"name\":\"_value\",\"type\":\"string\"}],\"name\":\"remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_key\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"PutResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_PUT = "put";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_GET = "get";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event PUTRESULT_EVENT = new Event("PutResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected MapStorage(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt put(String _key, String _value) {
        final Function function = new Function(
                FUNC_PUT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void put(String _key, String _value, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PUT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForPut(String _key, String _value) {
        final Function function = new Function(
                FUNC_PUT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getPutInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getPutOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_PUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt remove(String _key, String _value) {
        final Function function = new Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void remove(String _key, String _value, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRemove(String _key, String _value) {
        final Function function = new Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getRemoveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getRemoveOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public List get(String _key) throws ContractException {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public String owner() throws ContractException {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void transferOwnership(String newOwner, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getTransferOwnershipInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public List<PutResultEventResponse> getPutResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUTRESULT_EVENT, transactionReceipt);
        ArrayList<PutResultEventResponse> responses = new ArrayList<PutResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PutResultEventResponse typedResponse = new PutResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribePutResultEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(PUTRESULT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribePutResultEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(PUTRESULT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeOwnershipTransferredEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeOwnershipTransferredEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static MapStorage load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new MapStorage(contractAddress, client, credential);
    }

    public static MapStorage deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(MapStorage.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class PutResultEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger count;
    }

    public static class OwnershipTransferredEventResponse {
        public TransactionReceipt.Logs log;

        public String previousOwner;

        public String newOwner;
    }
}
