package com.view;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.dto.AccountDTO;
import com.service.AccountService;
import com.service.AccountServiceImpl;

public class AccountView {
	static final int READ_ALL = 1;
	static final int TRANSFER = 2;
	static final int DEPOSIT = 3;
	static final int CREATE = 4;
	static final int DELETE = 5;

	private AccountView() {};

	public static void renderMenu() {
		System.out.println("**************************");
		System.out.println("       [계좌 관리 메뉴]       ");
		System.out.println("**************************");
		System.out.println("1. 전체 계좌 확인");
		System.out.println("2. 송금");
		System.out.println("3. 입금");
		System.out.println("4. 계좌 추가");
		System.out.println("5. 계좌 삭제");
		System.out.println("0. 종료");
		System.out.println("**************************");
		System.out.print("메뉴 입력 => ");
	}

	public static boolean run(Scanner scan) {
		int chooseMenu = scan.nextInt();

		switch (chooseMenu) {
		case READ_ALL: // 조회
			readAll();
			break;
		case TRANSFER: // 송금
			transfer(scan);
			break;
		case DEPOSIT: // 입금
			deposit(scan);
			break;
		case CREATE: // 생성
			createAccount(scan);
			break;
		case DELETE: // 삭제
			deleteAccount(scan);
			break;
		default:
			System.out.println("프로그램이 종료되었습니다.");
			return false;
		}

		return true;
	}

	public static void readAll() {
		AccountService service = new AccountServiceImpl();
		List<AccountDTO> list = service.readAll();
		renderDTO(list);
	}

	public static void transfer(Scanner scan) {
		AccountService service = new AccountServiceImpl();
		
		try {
			System.out.print("송신자의 계정 id를 입력해주세요. => ");
			int sendAcntId = scan.nextInt();
			System.out.print("보낼 금액을 입력해주세요. => ");
			int amount = scan.nextInt();	
			System.out.print("수신자의 계정 id를 입력해주세요. => ");
			int recvAcntId = scan.nextInt();

			// 송금 가능 잔액 확인 && 수신자 id 유무 확인 후 진행
			if (service.isPosibleSend(sendAcntId, amount) &&
				service.readOne(recvAcntId) != null) {
				
				service.transfer(sendAcntId, recvAcntId, amount);

				// 1,000 단위마다 , 출력을 위해 사용
				DecimalFormat df = new DecimalFormat("###,###");
				System.out.println(
						sendAcntId + " (id)님이 + " + recvAcntId + " (id)님에게 " + df.format(amount) + "원을 송금했습니다.");
			} else {
				System.out.println("송금을 실패했습니다.");
			}
		} catch (InputMismatchException e) {
			inputMiss(scan, "송금하지 않고 종료합니다. ");
		} 
	}

	public static void deposit(Scanner scan) {
		AccountService service = new AccountServiceImpl();
		
		try {
			System.out.print("입금할 계정의 id를 입력해주세요. => ");
			int recvAcntId = scan.nextInt();
			System.out.print("입금할 금액을 입력해주세요. => ");
			int amount = scan.nextInt();

			// 해당 id가 있는지 확인 후 진행
			if (service.readOne(recvAcntId) != null) {
				service.deposit(recvAcntId, amount);
				
				DecimalFormat df = new DecimalFormat("###,###");
				System.out.println(recvAcntId + "(id)님에게 " + df.format(amount) + "원을 입금했습니다.");
			} else {
				System.out.println("송금을 실패했습니다.");
			}
					
		} catch (InputMismatchException e) {
			inputMiss(scan, "송금하지 않고 종료합니다.");
		}
	}

	public static void createAccount(Scanner scan) {
		AccountService service = new AccountServiceImpl();
		
		System.out.print("계정을 생성하는분의 이름을 입력해주세요. => ");
		String name = scan.next();

		AccountDTO dto = new AccountDTO();
		dto.setName(name);
		service.createAccount(dto);
		System.out.println(name + " 계정이 생성되었습니다.");
	}

	public static void deleteAccount(Scanner scan) {
		AccountService service = new AccountServiceImpl();
		int id = -1;

		try {
			System.out.print("삭제할 계정의 id를 입력해주세요. => ");
			id = scan.nextInt();

			// 삭제할 id가 있는지 확인 후 진행
			if(service.readOne(id) != null) {
				System.out.print("정말로 삭제하시겠습니까? (y/n) => ");
				String yn = scan.next();
				
				switch (yn) {
				case "y":
				case "Y":
					service.deleteAccount(id);
					break;
				default:
					System.out.println("계정을 삭제하지 않고 종료합니다. ");
					return;
				}
				
				System.out.println(id + " (id)의 계정을 삭제했습니다. ");
			}
			else {
				System.out.println("해당 id의 계정이 존재하지 않습니다.");
			}
			
		} catch (InputMismatchException e) {
			inputMiss(scan, "계정을 삭제하지 않고 종료합니다. ");
		} 
	}

	public static void renderDTO(List<AccountDTO> list) {
		StringBuilder sb = new StringBuilder();

		sb.append('\n');
		sb.append(AccountDTO.getHeader()).append('\n');

		for (AccountDTO dto : list) {
			sb.append(dto.getDTO()).append('\n');
		}

		System.out.println(sb);
	}

	private static void inputMiss(Scanner scan, String message) {
		scan.nextLine(); // scan의 버퍼 비우기
		System.out.println("올바르지 않은 입력 입니다. " + message);
	}
}
