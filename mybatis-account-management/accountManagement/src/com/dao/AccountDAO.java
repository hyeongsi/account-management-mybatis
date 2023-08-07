package com.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.dto.AccountDTO;

public class AccountDAO {
	
	public AccountDTO readOne(SqlSession session, int accountId) {
		AccountDTO dto = session.selectOne("AccountMapper.readOne", accountId);
		return dto;
	}
	
	public List<AccountDTO> readAll(SqlSession session){
		List<AccountDTO> list = session.selectList("AccountMapper.readAll");
		return list;
	}
	
	public int updateBalance(SqlSession session, AccountDTO dto) throws SQLException {
		int result = session.update("AccountMapper.updateBalance", dto);
		return result;
	}
	
	public int createAccount(SqlSession session, AccountDTO dto) throws SQLException {
		int result = session.insert("AccountMapper.createAccount", dto);
		return result;
	}
	
	public int deleteAccount(SqlSession session, int accountId) throws SQLException {
		int result = session.delete("AccountMapper.deleteAccount", accountId);
		return result;
	}
}
