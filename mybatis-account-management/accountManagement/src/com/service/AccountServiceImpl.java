package com.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.config.MySqlSessionFactory;
import com.dao.AccountDAO;
import com.dto.AccountDTO;

public class AccountServiceImpl implements AccountService {

	@Override
	public AccountDTO readOne(int accountId) {
		SqlSession session = MySqlSessionFactory.getSession();
		AccountDTO dto = null;
		
		try {
			AccountDAO dao = new AccountDAO();
			dto = dao.readOne(session, accountId);
		} finally {
			session.close();
		}

		return dto;
	}
	
	@Override
	public List<AccountDTO> readAll() {
		SqlSession session = MySqlSessionFactory.getSession();
		List<AccountDTO> list = null;
		
		try {
			AccountDAO dao = new AccountDAO();
			list = dao.readAll(session);
		} finally {
			session.close();
		}

		return list;
	}
	
	@Override
	public int transfer(int sendAcntId, int recvAcntId, int amount) {
		SqlSession session = MySqlSessionFactory.getSession();
		int result = 0;
		
		try {
			AccountDAO dao = new AccountDAO();
			
			AccountDTO sendDTO = new AccountDTO();
			sendDTO.setAccountId(sendAcntId);
			sendDTO.setBalance(amount * -1);
			result += dao.updateBalance(session, sendDTO);
			
			AccountDTO recvDTO = new AccountDTO();
			recvDTO.setAccountId(recvAcntId);
			recvDTO.setBalance(amount);
			result += dao.updateBalance(session, recvDTO);
			
			if(result != 2) {
				session.rollback();
			}else {
				session.commit();
			}
		} catch(SQLException e) {
			if(session != null) session.rollback();
		}
		finally {
			session.close();
		}	
		
		return result;
	}
	
	@Override
	public int deposit(int sendAcntId, int amount) {
		SqlSession session = MySqlSessionFactory.getSession();
		int result = 0;
		
		try {
			AccountDAO dao = new AccountDAO();
			
			AccountDTO sendDTO = new AccountDTO();
			sendDTO.setAccountId(sendAcntId);
			sendDTO.setBalance(amount);
			result += dao.updateBalance(session, sendDTO);
			
			session.commit();
		} catch(SQLException e) {
			if(session != null) session.rollback();
		}
		finally {
			session.close();
		}	
		
		return result;
	}

	@Override
	public int createAccount(AccountDTO dto) {
		SqlSession session = MySqlSessionFactory.getSession();
		int result = 0;
		
		try {
			AccountDAO dao = new AccountDAO();
			result = dao.createAccount(session, dto);
			
			session.commit();
		} catch (SQLException e) {
			session.rollback();
		} finally {
			session.close();
		}	
		
		return result;
	}

	@Override
	public int deleteAccount(int accountId) {
		SqlSession session = MySqlSessionFactory.getSession();
		int result = 0;
		
		try {
			AccountDAO dao = new AccountDAO();
			result = dao.deleteAccount(session, accountId);
			
			session.commit();
		} catch (SQLException e) {
			session.rollback();
		} finally {
			session.close();
		}	
		
		return result;
	}
	
	@Override
	public boolean isPosibleSend(int accountId, int amount) {
		SqlSession session = MySqlSessionFactory.getSession();
		
		try {
			AccountDAO dao = new AccountDAO();
			AccountDTO dto = dao.readOne(session, accountId);
			
			if(dto == null)		// 해당 id 계정이 없다면 false
				return false;
			if(dto.getBalance() < amount)	// 돈이 충분치 않다면 false
				return false;

		}  finally {
			session.close();
		}	

		return true;
	}
	
}
