package com.blo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class __BinaryBets_sol_BinaryBets extends Contract {
    private static final String BINARY = "6060604052610e10600055341561001557600080fd5b60018054600160a060020a03191633600160a060020a03161790556108d08061003f6000396000f300606060405263ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663245bef0381146100685780635a5404be1461008d5780639a9c29f6146100bb578063bc27d2b4146100d6578063f90b2bfe146100e157600080fd5b341561007357600080fd5b61007b6100f7565b60405190815260200160405180910390f35b341561009857600080fd5b61007b600160a060020a03600435811690602435166044356064356084356100fd565b34156100c657600080fd5b6100d460043560243561028a565b005b6100d4600435610558565b34156100ec57600080fd5b6100d4600435610759565b60005481565b6000600280548060010182816101139190610817565b9160005260206000209060070201600060a06040519081016040528060408051908101604052808c600160a060020a031681526020016000815250815260200160408051908101604052808b600160a060020a03168152602001600081525081526020018881526020018764e8d4a510000281526020018681525090919091506000820151818151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03919091161781556020820151816001015550506020820151600282018151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03919091161781556020820151816001015550506040820151816004015560608201518160050155608082015160069091015550506002547fb054aec47640e55df4b0fd46685ec11693ac36f7c77a67fdf86b6b9678a7395c906000190133604051918252600160a060020a031660208201526040908101905180910390a1506002546000190195945050505050565b6002548290811061029a57600080fd5b60015433600160a060020a039081169116146102b557600080fd5b60028054849081106102c357fe5b906000526020600020906007020160040154421080610324575060028054849081106102eb57fe5b90600052602060002090600702016005015460028481548110151561030c57fe5b90600052602060002090600702016000016001015414155b806103715750600280548490811061033857fe5b90600052602060002090600702016005015460028481548110151561035957fe5b90600052602060002090600702016002016001015414155b1561037b57600080fd5b600280548490811061038957fe5b9060005260206000209060070201600601548211156104155760028054849081106103b057fe5b600091825260209091206007909102015460028054600160a060020a03909216916108fc9190869081106103e057fe5b9060005260206000209060070201600501546002029081150290604051600060405180830381858888f1935050505050610553565b600280548490811061042357fe5b90600052602060002090600702016006015482101561047b57600280548490811061044a57fe5b6000918252602090912060026007909202018101548154600160a060020a03909116916108fc91869081106103e057fe5b600280548490811061048957fe5b600091825260209091206007909102015460028054600160a060020a03909216916108fc9190869081106104b957fe5b9060005260206000209060070201600501549081150290604051600060405180830381858888f19350505050506002838154811015156104f557fe5b6000918252602090912060026007909202018101548154600160a060020a03909116916108fc918690811061052657fe5b9060005260206000209060070201600501549081150290604051600060405180830381858888f150505050505b505050565b6002548190811061056857600080fd5b8160028181548110151561057857fe5b9060005260206000209060070201600501543414151561059757600080fd5b826002818154811015156105a757fe5b600091825260209091206007909102015433600160a060020a03908116911614801590610602575060028054829081106105dd57fe5b600091825260209091206002600790920201015433600160a060020a03908116911614155b1561060c57600080fd5b600280548590811061061a57fe5b600091825260209091206007909102015433600160a060020a03908116911614156106cb57600060028581548110151561065057fe5b906000526020600020906007020160000160010154111561069c577fe143a0341c9a473d4fa85c038fc4e4ab299201723d98e94ed08afbec71b6e5bf60405160405180910390a1600080fd5b346002858154811015156106ac57fe5b6000918252602090912060016007909202010180549091019055610753565b60006002858154811015156106dc57fe5b9060005260206000209060070201600201600101541115610728577fe143a0341c9a473d4fa85c038fc4e4ab299201723d98e94ed08afbec71b6e5bf60405160405180910390a1600080fd5b3460028581548110151561073857fe5b60009182526020909120600360079092020101805490910190555b50505050565b6002548190811061076957600080fd5b8160028181548110151561077957fe5b600091825260209091206007909102015433600160a060020a039081169116148015906107d4575060028054829081106107af57fe5b600091825260209091206002600790920201015433600160a060020a03908116911614155b156107de57600080fd5b60005460028054859081106107ef57fe5b9060005260206000209060070201600401540142111561055357600280548490811061048957fe5b81548183558181151161055357600083815260209020610553916108a19160079182028101918502015b8082111561089d57805473ffffffffffffffffffffffffffffffffffffffff1990811682556000600183018190556002830180549092169091556003820181905560048201819055600582018190556006820155600701610841565b5090565b905600a165627a7a72305820d33a26edd21edea8f353c2b996585ba118e37a434b48850d5f58e39bea93be170029";

    private __BinaryBets_sol_BinaryBets(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private __BinaryBets_sol_BinaryBets(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<BetCreatedEventResponse> getBetCreatedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("BetCreated", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<BetCreatedEventResponse> responses = new ArrayList<BetCreatedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            BetCreatedEventResponse typedResponse = new BetCreatedEventResponse();
            typedResponse.id = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BetCreatedEventResponse> betCreatedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("BetCreated", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, BetCreatedEventResponse>() {
            @Override
            public BetCreatedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                BetCreatedEventResponse typedResponse = new BetCreatedEventResponse();
                typedResponse.id = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<BadEventResponse> getBadEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Bad", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<BadEventResponse> responses = new ArrayList<BadEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            BadEventResponse typedResponse = new BadEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BadEventResponse> badEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Bad", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, BadEventResponse>() {
            @Override
            public BadEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                BadEventResponse typedResponse = new BadEventResponse();
                return typedResponse;
            }
        });
    }

    public List<ChangeEventResponse> getChangeEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Change", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ChangeEventResponse> responses = new ArrayList<ChangeEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ChangeEventResponse typedResponse = new ChangeEventResponse();
            typedResponse.yessss = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeEventResponse> changeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Change", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeEventResponse>() {
            @Override
            public ChangeEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChangeEventResponse typedResponse = new ChangeEventResponse();
                typedResponse.yessss = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<Uint256> settleOffsetTime() {
        Function function = new Function("settleOffsetTime", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> createBet(Address _highBettor, Address _lowBettor, Uint256 _expiry, Uint256 _szabo, Uint256 _initialPrice) {
        Function function = new Function("createBet", Arrays.<Type>asList(_highBettor, _lowBettor, _expiry, _szabo, _initialPrice), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> settle(Uint256 id, Uint256 finalPrice) {
        Function function = new Function("settle", Arrays.<Type>asList(id, finalPrice), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> sendBet(Uint256 id, BigInteger weiValue) {
        Function function = new Function("sendBet", Arrays.<Type>asList(id), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function, weiValue);
    }

    public Future<TransactionReceipt> dissolve(Uint256 id) {
        Function function = new Function("dissolve", Arrays.<Type>asList(id), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<__BinaryBets_sol_BinaryBets> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(__BinaryBets_sol_BinaryBets.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<__BinaryBets_sol_BinaryBets> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(__BinaryBets_sol_BinaryBets.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static __BinaryBets_sol_BinaryBets load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new __BinaryBets_sol_BinaryBets(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static __BinaryBets_sol_BinaryBets load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new __BinaryBets_sol_BinaryBets(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class BetCreatedEventResponse {
        public Uint256 id;

        public Address sender;
    }

    public static class BadEventResponse {
    }

    public static class ChangeEventResponse {
        public Uint256 yessss;
    }
}
