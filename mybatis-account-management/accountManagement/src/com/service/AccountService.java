package com.service;

import java.util.List;

import com.dto.AccountDTO;

public interface AccountService {
	public AccountDTO readOne(int accountId);
	public List<AccountDTO> readAll();
	public int transfer(int sendAcntId, int recvAcntId, int amount);
	public int deposit(int sendAcntId, int amount);
	public int createAccount(AccountDTO dto);
	public int deleteAccount(int accountId);
	public boolean isPosibleSend(int accountId, int amount);
}
