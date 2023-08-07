import java.util.Scanner;

import com.view.AccountView;

public class AccountMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean loop = true;
		
		while(loop) {
			AccountView.renderMenu();		// 메뉴 출력
			loop = AccountView.run(scan);	// 메뉴 실행
		} 
		
		scan.close();
	} // end main
}
