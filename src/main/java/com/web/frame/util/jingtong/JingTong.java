package com.web.frame.util.jingtong;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.Transaction;
import com.blink.jtblc.client.Wallet;
import com.blink.jtblc.client.bean.Account;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AccountRelations;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.Ledger;
import com.blink.jtblc.client.bean.LedgerInfo;
import com.blink.jtblc.client.bean.Line;
import com.blink.jtblc.client.bean.Memo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.client.bean.TxJson;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.connection.Observer;
import com.web.frame.entity.response.ResponseTransactionJingTum;
import com.web.frame.entity.response.ResponseTxJson;
import com.web.frame.util.JsonFormat;

@Component
public class JingTong implements Observer{
	
	
	
	
	
	//测试
//	private static String server = "ws://101.200.235.69:5020";
//	public static String account = "jMyJpxudYfTc2P8hh95vEo7sai237bkx9N";
//	public static String secret = "shxUf7oXUSmLoHEeexuVjQkaEDLQ4";
//	public static String account2  =  "jnRosXhyNYAZMDfdVJBaozjsinj6UVgyEf";
//	public static String secret2 = "ssAqUc5Bkx5RbxLt7drfEeiMXLdgd";
	
	//正式
//	@Value("${chain.jintum.server}")
//	private String server = "wss://hc.jingtum.com:5020";
//	private Connection conn = ConnectionFactory.getCollection(server);
//	private Remote remote = new Remote(conn);
//	@Value("${chain.jintum.server2}")
//	private static String server2 = "wss://c02.jingtum.com:5020";
//	@Value("${chain.jintum.server3}")
//	private static String server3 = "wss://c04.jingtum.com:5020";
	
	private Remote remote;
	
	//[jMyJpxudYfTc2P8hh95vEo7sai237bkx9N, 02AE14A3A02BCACBAF1F438EF75C85A34D16F432FBAF915D4656353484DFF09158, shxUf7oXUSmLoHEeexuVjQkaEDLQ4]
	public static void main(String[] args) throws Exception{
		
		JingTong jingTong = new JingTong("wss://hc.jingtum.com:5020");
		ResponseTransactionJingTum transaction = jingTong.getTransaction("0348DF094805F8464BB67C84325DB503BCBE539F3D29A89CBE95B91C533709D4");
		System.out.println(JsonFormat.beanToJson(transaction));

//		List balance = jingTong.getBalance("jMyJpxudYfTc2P8hh95vEo7sai237bkx9N");
//		System.out.println(balance);
		
//		String transaction = jingTong.getTransaction("4ED79E1705B5514488B834C287D54E5EF996E4CC852ECADCD2CABE7105DC62E3");
//		System.out.println(transaction);
		
//		Map pay = jingTong.pay("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh", "snoPBjXtMeMyMHUVTgbuqAfg1SUTb",
//		"jnRosXhyNYAZMDfdVJBaozjsinj6UVgyEf","20","SWT",
//		"","啦啦啦啦","");
//		System.out.println(pay);
		
		
//		Map pay = jingTong.pay(account, secret,
//				account2,"0.000001","SWT",
//		"","交易测试","");
//		System.out.println(pay);
		
//		String transaction = jingTong.getTransaction("39DD26A76DAD685F528CD32D50B0D9172F30EA8153EFF473AF65C71CF4DAFA0C");
//		System.out.println(transaction);
		
//		List<String> createWallet = jingTong.createWallet();
//		System.out.println(createWallet);
		
//		List<String> importAccountBySecret = jingTong.importAccountBySecret("shLvZqnz3LX5jwJmDfEpddk5mcdrg");
//		System.out.println(importAccountBySecret);
		
//		List balance = jingTong.getBalance("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh");
//		System.out.println(balance);
		
//		String transaction = jingTong.getTransaction("1F261E165B514B5EB244C8F8E84114E2F1F81116C5CACB94D3964B27F8377B02");
//		System.out.println(transaction);
		
//		List<String> createWallet = jingTong.createWallet();
//		System.out.println(createWallet);
		
//		List balance = jingTong.getBalance("jKHidAREmGZsEL3yo5zJQqKPZtedkWUY7z");
//		System.out.println(balance);
		
//		jingTong.subscribeTransactions();
		
//		List balance = jingTong.getBalance("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh");
//		System.out.println(balance);
		
//		Map pay = jingTong.pay("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh", "snoPBjXtMeMyMHUVTgbuqAfg1SUTb",
//				"jGjx9ZPSiBKyERGCexwhb54ebjFkrSbJS9","0","SWT",
//				"","啦啦啦啦","");
//		System.out.println(pay);
		
		//信息:{"id":"1548727376760728","result":{"engine_result":"tesSUCCESS","engine_result_code":0,"engine_result_message":"The transaction was applied. Only final in a validated ledger.","tx_blob":"120000228000000024000772652F23E270C86140000000000F424068400000000000000A73210330E7FC9D56BB25D6893BA3F317AE5BCF33B3291BD63DB32654A313222F7FD0207446304402201B727C3A9D32AC30ABACB20D022F5306FE2F113C81198DAE9520868DC5CE27160220362EC9842065E3562D8FB25D70C92E5AB18551869F28BDC6E5FDCA2F8EB68E5C8114B5F762798A53D543A014CAF8B297CFF8F2F937E88314A4B7EBEF4E022D43BF1CD0C709AA6D0283208BE3","tx_json":{"Account":"jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh","Amount":"1000000","Destination":"jGjx9ZPSiBKyERGCexwhb54ebjFkrSbJS9","Fee":"10","Flags":2147483648,"Sequence":488037,"SigningPubKey":"0330E7FC9D56BB25D6893BA3F317AE5BCF33B3291BD63DB32654A313222F7FD020","Timestamp":602042568,"TransactionType":"Payment","TxnSignature":"304402201B727C3A9D32AC30ABACB20D022F5306FE2F113C81198DAE9520868DC5CE27160220362EC9842065E3562D8FB25D70C92E5AB18551869F28BDC6E5FDCA2F8EB68E5C","hash":"8B07B9EE2E70FBC1B7A814B571DDDDFCF3F20D39A046FF36C8A730A6BF2912C9"}},"status":"success","type":"response"}

//		Map pay = jingTong.pay("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh", "snoPBjXtMeMyMHUVTgbuqAfg1SUTb",
//				"jGjx9ZPSiBKyERGCexwhb54ebjFkrSbJS9","0.00000000000000001","CNY",
//				"jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS","啦啦啦啦","");
//		System.out.println(pay);
		
		//[{currency=SWT, value=599918634051.957606, issuer=}, {currency=CNY, value=0.08704374125139999, issuer=jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS}, {currency=VCC, value=0.011, issuer=jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS}, {currency=F14F1D4F75CB8EBC706AAB29E000F60B08BC4DAA, value=0.009998, issuer=jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS}, {currency=E2FC29A23254C92D5C0A9C06B1D4B6097793A5FE, value=0.002099, issuer=jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS}, {currency=YYL, value=0.00112, issuer=jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS}]

//		String transaction = jingTong.getTransaction("1F261E165B514B5EB244C8F8E84114E2F1F81116C5CACB94D3964B27F8377B02");
//		System.out.println(transaction);
		
//		jingTong.subscribeTransactions();
		
//		String transaction = jingTong.getTransaction("1C15FEA3E1D50F96B6598607FC773FF1F6E0125F30160144BE0C5CBC52F5151B");
//		System.out.println(transaction);
		
//		LedgerInfo requestLedgerInfo = JingTong.remote.requestLedgerInfo();
//		System.out.println("高度："+requestLedgerInfo.getLedgerIndex());
		
//		Ledger requestLedger = JingTong.remote.requestLedger("266955", true);
//		System.out.println(requestLedger.getLedgerHash());
//		System.out.println(requestLedger.getLedger().getTransactions());
//		String transaction = jingTong.getTransaction(requestLedger.getLedger().getTransactions().get(0));
//		System.out.println(transaction);
		
	}
	
	public JingTong(@Value("${chain.jintum.server}") String server) {
		
		Connection conn = ConnectionFactory.getCollection(server);
		remote = new Remote(conn);
	}
	
	private String formatLedgerTime(String timeHuman) throws Exception {
		
		timeHuman = timeHuman.toLowerCase();
		String [] months = {"january","february","march","april","may",
							"june","july","august","september","october",
							"november","december"};
		
		String [] monthsNum = {"01","02","03","04","05",
							   "06","07","08","09","10",
							   "11","12"};
		
		String month = timeHuman.split("-")[1];
		
		String monthNum = "";
		List<String> list = Arrays.asList(months);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(month)) {
				monthNum = monthsNum[i];
			}
		}
		timeHuman = timeHuman.replace(month, monthNum);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long theTime = df.parse(timeHuman).getTime()+8*60*60*1000;
		return df.format(new Date(theTime));
	}
	
	
	
	public int getBlockNumber() {
		
		return remote.requestLedgerInfo().getLedgerIndex();
	}
	
	/**
	 * 创建账户
	 * @return
	 */
	public List<String> createWallet() {
		Wallet generate = Wallet.generate();
		List<String> list = new ArrayList<String>();
		list.add(generate.getAddress());
		list.add(generate.getPublicKey());
		list.add(generate.getSecret());
		return list;
	}
	
	/**
	 * 通过私钥导入账户
	 * @param secret
	 * @return
	 */
	public List<String> importAccountBySecret(String secret){
		Wallet fromSecret = Wallet.fromSecret(secret);
		List<String> list = new ArrayList<String>();
		list.add(fromSecret.getAddress());
		list.add(fromSecret.getPublicKey());
		list.add(fromSecret.getSecret());
		return list;
	}
	
	/**
	 * 获取余额
	 * @param address
	 * @return
	 */
	public List getBalance(String address) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		AccountInfo accountInfo = remote.requestAccountInfo(address, "");
		Map<String, Object> swtMap = new HashMap<String, Object>();
		if(accountInfo == null) {
			swtMap.put("currency", "SWT");
			swtMap.put("value", "0");
			swtMap.put("issuer", "");
			list.add(swtMap);
			return list;
		}
		String swtBalance = new BigDecimal(accountInfo.getAccountData().getBalance()).divide(new BigDecimal("1000000")).toString();
		swtMap.put("currency", "SWT");
		swtMap.put("value", swtBalance);
		swtMap.put("issuer", "");
		list.add(swtMap);
		
		AccountRelations relations = remote.requestAccountRelations(address, "", "trust");
		List<Line> lines = relations.getLines();
		for (int i = 0; i < lines.size(); i++) {
			Map<String, Object> token = new HashMap<String, Object>();
			token.put("currency", lines.get(i).getCurrency());
			token.put("value", lines.get(i).getBalance());
			token.put("issuer", lines.get(i).getAccount());
			list.add(token);
		}
		return list;
	}
	
	/**
	 *  交易
	 * @param address
	 * @param secret
	 * @param destination
	 * @param value
	 * @param currency 为空则默认为"SWT"
	 * @param issuer 银关地址
	 * @param memos 备注
	 * @return
	 */
	public ResponseTxJson pay(String address,String secret,String destination,String value,String currency,
			String issuer,String memo){
		
		if(StringUtils.isEmpty(currency)) {
			currency = "SWT";
		}
		AmountInfo amount = new AmountInfo();
		amount.setCurrency(currency);
		amount.setValue(value);
		amount.setIssuer(issuer);
		Transaction tx = remote.buildPaymentTx(address, destination, amount);
		tx.addMemo(memo);
		tx.setSecret(secret);
		tx.setFee(20);
		TransactionInfo bean = tx.submit();
		TxJson txJson = bean.getTxJson();
		ResponseTxJson responseTxJson = new ResponseTxJson();
		BeanUtils.copyProperties(txJson, responseTxJson);
		
		return responseTxJson;
	}
	
	/**
	 * 获取交易信息
	 * @param txHash
	 * @return
	 */
	public ResponseTransactionJingTum getTransaction(String txHash){
		
		Account tx = remote.requestTx(txHash);
		List<Memo> memos = tx.getMemos();
		if(memos!=null) {
			for (int i = 0; i < memos.size(); i++) {
				memos.get(i).setMemoData(hexToByte(memos.get(i).getMemoData()));
			}
		}
		ResponseTransactionJingTum transaction = new ResponseTransactionJingTum();
		BeanUtils.copyProperties(tx, transaction);
		return transaction;
	}
	
	/**
	 * 订阅交易信息
	 */
	public void subscribeTransactions() {
		remote.transactions(this);
	}
	
	public String hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return new String(ret);
    }
	
	/**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

	@Override
	public void updateTransactions(String message) {
		
	}
}






