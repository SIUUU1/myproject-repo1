package project.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import project.cart.Bill;
import project.cart.CartInterface;
import project.exception.CartException;
import project.member.Admin;
import project.member.Customer;
import project.member.VIPCustomer;
import project.performance.Performance;

//데이터베이스를 사용하지 않고 파일로 데이터를 관리하였습니다.
//프로젝트명 : Ticket World
//클레스 역할 : 고객, 공연과 관련된 정보를 관리하는 기능을 처리하는 클래스 
//제작자 : 안시우, 제작일 : 24년 4월 16일
public class Main {
	public static final int ROW_NUM = 5; // 행
	public static final int COLUMN_NUM = 20; // 열
	public static final int VIP_ACCUMULPAYMENT = 100_000; // VIP등급최소누적금액
	public static ArrayList<Performance> performanceInfoList = new ArrayList<Performance>();
	public static ArrayList<Customer> customerInfoList = new ArrayList<Customer>();
	public static String[] menuLoginPage = new String[] { "LOGIN", "REGISTER", "ADMINLOGIN", "FORGOTIDPW", "QUIT" }; // 로그인페이지메뉴
	public static String[] menuUserMode = new String[] { "CUSTOMERINFO", "TICKETING", "CART", "BILL", "OPENTALK",
			"LOGOUT" }; // 유저모드메뉴
	public static String[] menuAdminMode = new String[] { "MANAGEMEMBER", "MANAGEPERFORMANCE", "QUIT" }; // 관리자모드메뉴
	public static String[] menuSearchP = new String[] { "SEARCHNAME", "SEARCHGENRE", "RESERVATION", "BACK" }; // 공연검색메뉴
	public static String[] menuCart = new String[] { "PAYMENT", "CARTREMOVEITEM", "CARTCLEAR", "BACK" }; // 장바구니 메뉴
	public static String[] menuSearchGenre = new String[] { "뮤지컬", "콘서트", "연극", "클래식", "무용", "기타" }; // 공연장르 검색메뉴
	public static String[] menuForgotIdPw = new String[] { "SEARCHID", "RESETPW", "BACK" }; // 아이디 비밀번호 찾기메뉴
	public static Scanner sc = new Scanner(System.in);
	static Customer customer = new Customer(); // 현재 고객
	static int cusNumId = -1; // 현재 고객의 고객리스트 index

	public static void main(String[] args) {
		boolean flag = false; // 로그인 페이지 무한 반복
		boolean loginFlag = false;
		boolean mainExitFlag = false; // 유저 main 페이지 무한 반복
		boolean adminFlag = false;
		boolean exitFlag = false; // 관리자 main 페이지 무한 반복

		// 파일 로딩
		try {
			Admin.lodeAdmin();
			Admin.lodeCustomerList();
			Admin.lodePerformanceList();
		} catch (Exception e) {
			System.out.println("파일을 읽어올 수 없습니다.");
		}
		// 로그인 페이지
		while (!flag) {
			loginPage();
			String menuSelect = sc.nextLine().replaceAll("[^1-4]", "0");
			if (menuSelect.length() == 0) { // menuSelect가 null일때 대비
				System.out.println("다시 입력하세요.");
			} else {
				int menuSelectNum = Integer.parseInt(menuSelect);
				if (menuSelectNum < 1 || menuSelectNum > 4) {
					System.out.println("1부터 4까지의 숫자를 입력해주세요");
				} else {
					switch (menuLoginPage[menuSelectNum - 1]) {
					case "LOGIN":
						// 고객 로그인
						if (signIn(customerInfoList)) {
							loginFlag = true;
							flag = true;
						}
						break;
					case "REGISTER":
						// 회원가입
						register();
						break;
					case "ADMINLOGIN":
						// 관리자 로그인
						if (Admin.administratorMode()) {
							adminFlag = true;
							flag = true;
						}
						break;
					case "FORGOTIDPW":
						// 아이디 비밀번호 찾기
						forgotIdPw();
						break;
					case "QUIT":
						System.out.println("종료합니다.");
						flag = true;
						break;
					}
				}
			}
		} // end of while

		// UserMode 페이지
		if (loginFlag) {
			System.out.println(customer.getName() + "님 환영합니다.");
			while (!mainExitFlag) {
				menuIntroduction();
				String menuSelect = sc.nextLine().replaceAll("[^1-6]", "0");
				if (menuSelect.length() == 0) {
					System.out.println("다시 입력해주세요.");
				} else {
					int menuSelectNum = Integer.parseInt(menuSelect);
					if (menuSelectNum < 1 || menuSelectNum > 6) {
						System.out.println("1부터 6까지의 숫자를 입력해주세요");
					} else {
						switch (menuUserMode[menuSelectNum - 1]) {
						case "CUSTOMERINFO":
							// 본인 정보 확인하기
							menuCustomerInfo();
							break;
						case "TICKETING":
							// 예매하기
							menuTicketing();
							break;
						case "CART":
							// 장바구니
							menuShoppingCart();
							break;
						case "BILL":
							// 결제 내역 출력
							menuPaymentBill();
							break;
						case "OPENTALK":
							// 공연별 오픈톡
							System.out.println("오픈톡이 실행됩니다.");
							menuOpenTalk();
							break;
						case "LOGOUT":
							System.out.println("로그아웃");
							mainExitFlag = true;
							break;
						}
					}
				}
			} // end of while
		}

		// 관리자 Mode 페이지
		if (adminFlag) {
			while (!exitFlag) {
				printAdminModeMenu();
				String menuSelect = sc.nextLine().replaceAll("[^1-3]", "0");
				if (menuSelect.length() == 0) {
					System.out.println("다시 입력해주세요.");
				} else {
					int menuSelectNum = Integer.parseInt(menuSelect);
					if (menuSelectNum < 1 || menuSelectNum > 3) {
						System.out.println("1부터 3까지의 숫자를 입력해주세요");
					} else {
						switch (menuAdminMode[menuSelectNum - 1]) {
						case "MANAGEMEMBER":
							// 회원 관리
							Admin.manageMember();
							break;
						case "MANAGEPERFORMANCE":
							// 공연항목관리
							Admin.performanceItemManagement();
							break;
						case "QUIT":
							System.out.println("관리자 모드 종료합니다.");
							exitFlag = true;
							break;
						}
					}
				}
			} // end of while
		}
	}// end of main

	// 공연별 오픈톡
	public static void menuOpenTalk() {
		int pnumId = -1;
		boolean flag = false;
		// 전체공연 출력
		CartInterface.printPerformanceList();
		while (!flag) {
			System.out.print("오픈톡방에 참여할 공연ID를 입력하세요. ");
			String pId = sc.nextLine();
			for (int i = 0; i < performanceInfoList.size(); i++) {
				if (pId.equals(performanceInfoList.get(i).getPerformanceID())) {
					pnumId = i;
					flag = true;
					break;
				}
			}
		} // end of while
		if(flag) {
			
		}
	}

	public static void loginPage() {
		System.out.println("****************************************************************");
		System.out.println("\t\t" + "   Welcome to Ticket World");
		System.out.println("****************************************************************");
		System.out.println(" 1. 회원 로그인        \t2. 회원가입");
		System.out.println(" 3. 관리자 로그인       \t4. 아이디/비밀번호 찾기");
		System.out.println(" 5. 종료");
		System.out.println("****************************************************************");

	}

	public static void menuIntroduction() {
		System.out.println("****************************************************************");
		System.out.println("\t\t\t" + "Ticket World Menu");
		System.out.println("****************************************************************");
		System.out.println(" 1. 본인 정보 확인하기     \t2. 예매하기");
		System.out.println(" 3. 장바구니             \t4. 결제내역보기");
		System.out.println(" 5. 공연별 오픈톡         \t6. 로그아웃");
		System.out.println("****************************************************************");
	}

	// 관리자 모드 메뉴 출력
	public static void printAdminModeMenu() {
		System.out.println("****************************************************************");
		System.out.println(" 1. 회원 관리             \t2. 공연항목관리");
		System.out.println(" 3. 종료");
		System.out.println("****************************************************************");
	}

	// 고객 로그인
	public static boolean signIn(ArrayList<Customer> customerList) {
		boolean loginFlag = false;
		System.out.print("아이디를 입력하세요 ");
		String id = sc.nextLine();
		System.out.print("비밀번호를 입력하세요 ");
		String pw = sc.nextLine();
		for (int i = 0; i < customerList.size(); i++) {
			if (id.equals(customerList.get(i).getId()) && pw.equals(customerList.get(i).getPw())) {
				customer = customerList.get(i);
				cusNumId = i;
				loginFlag = true;
				break;
			}
		}
		if (loginFlag == false) {
			System.out.println("존재하지 않는 아이디거나 잘못된 비밀번호를 입력하셨습니다.");
		}
		return loginFlag;
	}

	// 회원가입
	public static void register() {
		boolean exitFlag = false;
		System.out.print("회원가입 하시겠습니까 Y | N ");
		String str = sc.nextLine();
		if (str.equalsIgnoreCase("Y")) {
			while (!exitFlag) {
				System.out.print("아이디 : "); // 중복 피하기
				String id = sc.nextLine();
				boolean flag = false;

				for (Customer c : customerInfoList) {
					if (id.equals(c.getId())) {
						System.out.println("이미 존재하는 아이디 입니다.");
						flag = true;
						break;
					}
				}
				if (flag == false) {
					Customer customer = new Customer();
					customer.setId(id);
					System.out.print("비밀번호 : ");
					customer.setPw(sc.nextLine());
					System.out.print("이름 : ");
					customer.setName(sc.nextLine());
					System.out.print("연락처(예:010-0000-0000) : ");
					customer.setPhone(sc.nextLine());
					System.out.print("주소 : ");
					customer.setAddress(sc.nextLine());
					// -----------------------------------------------------------------
					boolean ageFlag = false;
					while (!ageFlag) {
						System.out.print("나이 : ");
						String age = sc.nextLine().replaceAll("[^0-9]", "0");
						int ageNum = Integer.parseInt(age);
						if (age.length() == 0 || ageNum < 1 || ageNum > 130) {
							System.out.println("다시 입력해주세요.");
						} else {
							customer.setAge(ageNum);
							ageFlag = true;
						}
					}
					// -----------------------------------------------------------------
					System.out.println("회원가입 성공하셨습니다.");
					customerInfoList.add(customer);
					// 새로운 고객 저장
					Admin.saveCustomerList();
					// 회원 정보 로딩
					Admin.lodeCustomerList();
					exitFlag = true;
				}
			} // end of while
		}
	}

	// 아이디 또는 비밀번호 찾기
	public static void forgotIdPw() {
		boolean flag = false;
		Customer cus = null;
		while (!flag) {
			System.out.println("****************************************************************");
			System.out.println(" 1. 아이디 찾기           \t2. 비밀번호 재설정");
			System.out.println(" 3. 뒤로가기");
			System.out.println("****************************************************************");
			String menuSelect = sc.nextLine().replaceAll("[^1-2]", "0");
			if (menuSelect.length() == 0) {
				System.out.println("다시 입력하세요.");
			} else {
				int menuSelectNum = Integer.parseInt(menuSelect);
				if (menuSelectNum < 1 || menuSelectNum > 2) {
					System.out.println("1부터 3까지의 숫자를 입력해주세요.");
				} else {
					switch (menuForgotIdPw[menuSelectNum - 1]) {
					case "SEARCHID":
						// 아이디 찾기
						// 본인 확인
						cus = identification();
						if (cus != null) {
							System.out.println("고객님의 아이디는 " + cus.getId() + "입니다.");
						}
						break;
					case "RESETPW":
						// 비밀번호 재설정
						cus = identification();
						if (cus != null) {
							System.out.print("재설정할 비밀번호를 입력하세요.");
							String pw = sc.nextLine();
							cus.setPw(pw);
							System.out.println("비밀번호가 성공적으로 재설정되었습니다.");
						}
						flag = true;
						break;
					case "BACK":
						// 뒤로가기
						flag = true;
						break;

					}
				}
			}
		} // end of while
	}

	// 본인 확인하기
	public static Customer identification() {
		Customer cus = null;
		boolean flag = false;
		System.out.print("이름을 입력하세요. ");
		String name = sc.nextLine();
		System.out.print("전화번호를 입력하세요. ");
		String phone = sc.nextLine();
		for (int i = 0; i < customerInfoList.size(); i++) {
			if (name.equals(customerInfoList.get(i).getName()) && phone.equals(customerInfoList.get(i).getPhone())) {
				cus = customerInfoList.get(i);
				flag = true;
				break;
			}
		}
		if (flag == false) {
			System.out.println("존재하지 않는 회원입니다.");
		}
		return cus;
	}

	// 본인 정보 확인하기
	public static void menuCustomerInfo() {
		System.out.println("================================================================");
		System.out.println("\t\t\t  " + customer.getName() + "님의 정보");
		System.out.println("================================================================");
		System.out.println(" 등급 | 아이디 | 이름 | 나이 | 누적결제금액 | 마일리지 ");
		System.out.println("----------------------------------------------------------------");
		System.out.println(customer.toString());
		System.out.println("================================================================");
	}

	// 예매하기
	public static void menuTicketing() {
		boolean exitFlag = false;
		// 전체 공연 정보 목록 출력
		CartInterface.printPerformanceList();
		// 공연 검색
		exitFlag = searchPerformance();
		// 장바구니 추가하기
		while (!exitFlag) {
			System.out.print("예매할 공연 ID를 입력하세요(뒤로가기: -1)");
			String strId = sc.nextLine();
			int numId = -1;
			if (strId.equals("-1")) {
				exitFlag = true;
				continue;
			}
			for (int i = 0; i < performanceInfoList.size(); i++) {
				if (strId.equals(performanceInfoList.get(i).getPerformanceID())) {
					numId = i;
				}
			}
			// 일치하면 추가하기
			if (numId != -1) {
				if (performanceInfoList.get(numId).calcRemainingSeat() == 0) {
					System.out.println("해당 공연이 매진되어 예매할 수 없습니다.");
					exitFlag = true;
				} else {
					System.out.print("예매할 좌석수를 입력하세요.");
					String countNum = sc.nextLine().replaceAll("[^0-9]", "0");
					if (countNum.length() == 0) {
						System.out.println("다시 입력해주세요");
					} else {
						int count = Integer.parseInt(countNum);
						if (count < 1) {
							System.out.println("다시 입력해주세요");
						} else {
							if (performanceInfoList.get(numId).calcRemainingSeat() >= count) {
								// 공연 좌석 정하기
								customer.getCart().chooseSeats(numId, count);
								// 고객 정보 저장, 로딩
								customerInfoList.remove(cusNumId);
								customerInfoList.add(cusNumId, customer);
								Admin.saveCustomerList();
								Admin.lodeCustomerList();
							} else {
								System.out.println("예매할 공연의 잔여좌석이 부족합니다.");
							}
							exitFlag = true;
						}
					}
				}
			} else {
				System.out.println("다시 입력해주세요.");
			}
		} // end of while
	}

	// 장바구니
	public static void menuShoppingCart() {
		boolean flag = false;
		int cartCount = customer.getCart().getCartItemList().size();
		// 장바구니출력
		customer.getCart().printCart();
		if (cartCount != 0) {
			while (!flag) {
				printCartMenu();
				try {
					String menuSelect = sc.nextLine().replaceAll("[^1-4]", "0");
					if (menuSelect.length() == 0) {
						System.out.println("다시 입력해주세요");
					} else {
						int menuSelectNum = Integer.parseInt(menuSelect);
						if (menuSelectNum < 1 || menuSelectNum > 4) {
							System.out.println("1부터 4까지의 숫자를 입력해주세요");
						} else {
							switch (menuCart[menuSelectNum - 1]) {
							case "PAYMENT":
								// 결제하기
								menuOrderCartItem();
								break;
							case "CARTREMOVEITEM":
								// 장바구니 항목 삭제하기
								menuCartRemoveItem();
								break;
							case "CARTCLEAR":
								// 장바구니 비우기
								menuCartClear();
								break;
							case "BACK":
								flag = true;
								break;
							}
						}
					}
				} catch (CartException e) {
					System.out.println(e);
				} catch (Exception e) {
					System.out.println(e);
				}
			} // end of while
		}
	}

	// 장바구니 메뉴 출력
	public static void printCartMenu() {
		System.out.println("****************************************************************");
		System.out.println(" 1. 결제하기             \t2. 장바구니 항목 삭제하기");
		System.out.println(" 3. 장바구니 비우기        \t4. 뒤로가기");
		System.out.println("****************************************************************");
	}

	// 결제내역 출력
	public static void menuPaymentBill() {
		if (!customer.getBillList().isEmpty()) {
			ArrayList<Bill> billList = new ArrayList<Bill>();
			billList.addAll(customer.getBillList());
			// 최근 결제일부터 출력
			Collections.reverse(billList);
			billList.forEach(Bill::printBill);
			// 결제취소
		} else {
			System.out.println("결제내역이 없습니다.");
			System.out.println();
		}
	}

	// 결제하기
	public static void menuOrderCartItem() {
		int cartCount = customer.getCart().getCartItemList().size();
		if (cartCount == 0) {
			customer.getCart().printCart();
		} else {
			System.out.print("장바구니에 있는 모든 항목을 결제하시겠습니까? Y | N ");
			String str = sc.nextLine();
			if (str.equalsIgnoreCase("Y")) {
				System.out.println("결제창으로 이동합니다.");
				System.out.println();
				System.out.print(customer.getName() + "님의 기본정보를 가져오시겠습니까? Y | N ");
				str = sc.nextLine();
				if (str.equalsIgnoreCase("Y")) {
					// 결제금액 계산 및 적립
					int totalPaymentAmount = customer.calcPrice(customer.getCart().payment());
					// 영수증 출력 메서드 호출
					Bill bill = new Bill(customer.getName(), customer.getPhone(), customer.getAddress(),
							totalPaymentAmount, customer.getCart().getCartItemList());
					bill.printBill();
					customer.addBillList(bill);
				} else {
					System.out.print("배송받을 고객명을 입력하세요. ");
					String name = sc.nextLine();
					System.out.print("배송받을 고객의 연락처를 입력하세요. ");
					String phone = sc.nextLine();
					System.out.print("배송받을 고객의 배송지를 입력하세요. ");
					String address = sc.nextLine();
					System.out.println();
					// 결제금액 계산 및 적립
					int totalPaymentAmount = customer.calcPrice(customer.getCart().payment());
					// 영수증 출력 메서드 호출
					Bill bill = new Bill(name, phone, address, totalPaymentAmount,
							customer.getCart().getCartItemList());
					bill.printBill();
					customer.getBillList().add(bill);
				}
				// 장바구니 비우기
				customer.getCart().getCartItemList().clear();
				// 고객등급 업데이트
				updateCustomerGrade();
				// 고객 정보 저장, 로딩
				customerInfoList.remove(cusNumId);
				customerInfoList.add(cusNumId, customer);
				Admin.saveCustomerList();
				Admin.lodeCustomerList();
			}
		}
	}

	// 장바구니 항목 삭제하기
	public static void menuCartRemoveItem() throws CartException {
		boolean exitFlag = false;
		int cartCount = customer.getCart().getCartItemList().size();
		if (cartCount == 0) {
			throw new CartException("장바구니에 항목이 없습니다.");
		} else {
			// 장바구니 출력
			customer.getCart().printCart();
			while (!exitFlag) {
				System.out.print("삭제할 공연 ID를 입력하세요(뒤로가기: -1)>>");
				String strId = sc.nextLine();
				int numId = -1;

				if (strId.equals("-1")) {
					exitFlag = true;
					continue;
				}
				boolean flag = false;
				for (int i = 0; i < cartCount; i++) {
					if (strId.equals(customer.getCart().getCartItemList().get(i).getPerformanceID())) {
						numId = i;
						flag = true;
						break;
					}
				}
				if (flag) {
					System.out.print("장바구니 항목을 삭제하시겠습니까? Y | N ");
					String str = sc.nextLine();
					if (str.equalsIgnoreCase("Y")) {
						int pnumId = -1;
						// 공연좌석 원래대로
						for (int i = 0; i < performanceInfoList.size(); i++) {
							if (strId.equals(performanceInfoList.get(i).getPerformanceID())) {
								pnumId = i;
							}
						}
						customer.getCart().cancelSeats(pnumId, numId);
						System.out.println(
								customer.getCart().getCartItemList().get(numId).getItem().getName() + "공연이 삭제되었습니다.");
						customer.getCart().removeCartPerformance(pnumId, numId);
						// 공연 정보 저장, 로딩
						Admin.savePerformanceList();
						Admin.lodePerformanceList();
						// 고객 정보 저장, 로딩
						customerInfoList.remove(cusNumId);
						customerInfoList.add(cusNumId, customer);
						Admin.saveCustomerList();
						Admin.lodeCustomerList();
					}
					exitFlag = true;
				} else {
					System.out.println("다시 입력해주세요.");
				}
			} // end of while
		}
	}

	// 장바구니 비우기
	public static void menuCartClear() throws CartException {
		int cartCount = customer.getCart().getCartItemList().size();
		if (cartCount == 0) {
			throw new CartException("장바구니에 항목이 없습니다.");
		} else {
			System.out.print("장바구니의 모든 항목을 삭제하시겠습니까? Y | N ");
			String str = sc.nextLine();
			if (str.equalsIgnoreCase("Y")) {
				int numId = -1, pnumId = -1;
				for (int i = 0; i < cartCount; i++) {
					for (int j = 0; j < performanceInfoList.size(); j++) {
						if (customer.getCart().getCartItemList().get(i).getPerformanceID()
								.equals(performanceInfoList.get(j).getPerformanceID())) {
							numId = i;
							pnumId = j;
							break;
						}
					}
					// 좌석지정 취소
					customer.getCart().cancelSeats(pnumId, numId);
				}
				// 장바구니 비우기
				customer.getCart().removeAllCart();
				// 공연 정보 저장, 로딩
				Admin.savePerformanceList();
				Admin.lodePerformanceList();
				// 고객 정보 저장, 로딩
				customerInfoList.remove(cusNumId);
				customerInfoList.add(cusNumId, customer);
				Admin.saveCustomerList();
				Admin.lodeCustomerList();
			}
		}
		System.out.println("장바구니의 모든 항목을 삭제했습니다.");
	}

	// 고객등급 업데이트
	public static void updateCustomerGrade() {
		if (customer.getAccumulatedPayment() > VIP_ACCUMULPAYMENT) {
			VIPCustomer vipCustomer = new VIPCustomer(customer.getName(), customer.getPhone(), customer.getId(),
					customer.getPw(), customer.getAddress(), customer.getAge(), customer.getCart(),
					customer.getBillList(), customer.getAccumulatedPayment(), customer.getMileage());
			customerInfoList.remove(cusNumId);
			customerInfoList.add(cusNumId, vipCustomer);
			customer = vipCustomer;
			// 고객 정보 저장, 로딩
			Admin.saveCustomerList();
			Admin.lodeCustomerList();
		}
	}

	// 공연 검색하기
	public static boolean searchPerformance() {
		boolean quit = false;
		boolean flag = false; // 뒤로가기 기능
		ArrayList<Performance> perfomanceList = new ArrayList<Performance>();

		while (!quit) {
			System.out.println("****************************************************************");
			System.out.println(" 1. 공연명 검색        \t2. 장르 검색");
			System.out.println(" 3. 즉시예매           \t4. 뒤로가기");
			System.out.println("****************************************************************");

			String menuSelect = sc.nextLine().replaceAll("[^1-4]", "0");
			if (menuSelect.length() == 0) {
				System.out.println("다시 입력해주세요.");
			} else {
				int menuSelectNum = Integer.parseInt(menuSelect);
				if (menuSelectNum < 1 || menuSelectNum > 4) {
					System.out.println("1부터 4까지의 숫자를 입력해주세요");
				} else {
					switch (menuSearchP[menuSelectNum - 1]) {
					case "SEARCHNAME":
						System.out.print("검색할 공연명을 입력하세요. ");
						String name = sc.nextLine();
						perfomanceList = (ArrayList<Performance>) performanceInfoList.stream()
								.filter(e -> name.equals(e.getName())).sorted().collect(Collectors.toList());
						quit = printSearchList(perfomanceList);
						break;
					case "SEARCHGENRE":
						String str = searchGenre();
						perfomanceList = (ArrayList<Performance>) performanceInfoList.stream()
								.filter(e -> str.equals(e.getGenre())).sorted().collect(Collectors.toList());
						quit = printSearchList(perfomanceList);
						break;
					case "RESERVATION":
						quit = true;
						break;
					case "BACK":
						flag = true;
						quit = true;
						break;
					}
				}
			}
		} // end of while
		return flag;
	}

	// 공연 장르 검색
	public static String searchGenre() {
		String str = null;
		boolean quit = false;

		while (!quit) {
			System.out.println("****************************************************************");
			System.out.println(" 1. 뮤지컬            \t2. 콘서트");
			System.out.println(" 3. 연극              \t4. 클래식");
			System.out.println(" 5. 무용              \t6. 기타");
			System.out.println("****************************************************************");
			System.out.print("검색할 장르를 고르세요. ");
			String menuSelect = sc.nextLine().replaceAll("[^1-6]", "0");
			if (menuSelect.length() == 0) {
				System.out.println("다시 입력해주세요.");
			} else {
				int menuSelectNum = Integer.parseInt(menuSelect);
				if (menuSelectNum < 1 || menuSelectNum > 6) {
					System.out.println("1부터 6까지의 숫자를 입력해주세요");
				} else {
					str = menuSearchGenre[menuSelectNum - 1];
					if (str.equals("기타")) {
						System.out.print("검색어를 입력하세요. ");
						str = sc.nextLine();
					}
					quit = true;
				}
			}
		}
		return (str != null) ? (str) : ("");

	}

	// 검색 출력하기
	public static boolean printSearchList(ArrayList<Performance> perfomanceList) {
		boolean flag = false;
		if (!perfomanceList.isEmpty()) {
			System.out.println("================================================================");
			System.out.println(" 공연ID | 공연명 | 장르 | 공연일 | 장소 | 제한연령 | 잔여좌석/총좌석 | 티켓가격 ");
			System.out.println("================================================================");
			perfomanceList.forEach(System.out::println);
			System.out.println("================================================================");
			flag = true;
		} else {
			System.out.println("찾으시는 정보가 없습니다.");
		}
		return flag;
	}
}
