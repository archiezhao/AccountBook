package com.awesomeapp.finance.util;

import java.util.List;

import org.json.simple.*;

import com.awesomeapp.finance.database.AccountTransaction;

public class JsonUtil {
	@SuppressWarnings("unchecked")
	public static JSONObject getAccountTransactionJson(List<AccountTransaction> tranList) {
		JSONObject res = new JSONObject();
		if(tranList == null)
			return res;
		if(tranList.isEmpty())
			return res;
		
		JSONArray list = new JSONArray();
		
		for(AccountTransaction at : tranList) {
			JSONObject tmp = new JSONObject();
			tmp.put("TransactionId", at.getTransactionId().toString());
			tmp.put("Title", at.getTitle());
			tmp.put("Amount", at.getAmount());
			tmp.put("Note", at.getNote());
			tmp.put("User", at.getUser().getUsername());
			tmp.put("Category", at.getCategory().getCategoryname());
			
			list.add(tmp);
		}
		res.put("TransactionList", list);
		return res;
	}
}
