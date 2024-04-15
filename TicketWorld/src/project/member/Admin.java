package project.member;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import project.main.Main;
import project.performance.Performance;

public class Admin {
	// memberVariable
	public static Scanner sc = new Scanner(System.in);
	private static String[] adminArr = new String[2];
	private static String[] menuPManagement = new String[] { "ADDPERFORMANCE", "DELETEPERFORMANCE", "BACK" };

	// memberFunction
	public static String[] getAdminArr() {
		return adminArr;
	}

	// 관리자 로그인
	public static boolean administratorMode() {
		boolean adminFlag = false;
		System.out.print("관리자 아이디를 입력하세요 ");
		String id = sc.nextLine();
		System.out.print("관리자 비밀번호를 입력하세요 ");
		String pw = sc.nextLine();
		if (id.equals(adminArr[0]) && pw.equals(adminArr[1])) {
			adminFlag = true;
		} else {
			System.out.println("관리자 로그인 실패하셨습니다.");
		}
		return adminFlag;
	}

	// 관리자 공연항목관리
	public static void performanceItemManagement() {
		boolean flag = false;
		while (!flag) {
			printPerformanceItemManagementMenu();
			String menuSelect = sc.nextLine().replaceAll("[^1-3]", "0");
			if (menuSelect.length() == 0) {
				System.out.println("잘못 입력하셨습니다.");
			} else {
				int menuSelectNum = Integer.parseInt(menuSelect);
				if (menuSelectNum < 1 || menuSelectNum > 3) {
					System.out.println("1부터 3까지의 숫자를 입력해주세요");
				} else {
					switch (menuPManagement[menuSelectNum - 1]) {
					case "ADDPERFORMANCE":
						addPerformanceItems();
						// save
						break;
					case "DELETEPERFORMANCE":
						deletePerformanceItems();
						// save
						break;
					case "BACK":
						flag = true;
						break;
					}
				}
			}
		} // end of while
	}

	// 공연항목관리 메뉴 출력
	public static void printPerformanceItemManagementMenu() {
		System.out.println("****************************************************************");
		System.out.println(" 1. 공연 항목 추가        \t2. 공연 항목 삭제");
		System.out.println(" 3. 뒤로가기");
		System.out.println("****************************************************************");
	}

	// 공연 항목 추가
	public static void addPerformanceItems() {
		System.out.print("공연 정보를 추가하겠습니까? Y | N");
		String str = sc.nextLine();
		if (str.equalsIgnoreCase("Y")) {
			Performance performance = new Performance();
			Date date = new Date();
			SimpleDateFormat form = new SimpleDateFormat("yyMMddhhmmss");
			String dateStr = form.format(date);
			performance.setPerformanceID("p" + dateStr);
			System.out.println("공연ID : " + performance.getPerformanceID());
			System.out.print("공연명 : ");
			performance.setName(sc.nextLine());
			System.out.print("장르 : ");
			performance.setGenre(sc.nextLine());
			System.out.print("공연일(예:2023-01-01) : ");
			performance.setDayOfPerformance(sc.nextLine());
			System.out.print("장소 : ");
			performance.setVenue(sc.nextLine());
			// -----------------------------------------------------------------
			boolean ageFlag = false;
			while (!ageFlag) {
				System.out.print("관람제한연령 : ");
				String age = sc.nextLine().replaceAll("[^0-9]", "0");
				if (age.length() == 0) {
					System.out.println("다시 입력해주세요.");
				} else {
					int ageNum = Integer.parseInt(age);
					if (ageNum < 1) {
						System.out.println("다시 입력해주세요.");
					} else {
						performance.setLimitAge(ageNum);
						ageFlag = true;
					}
				}
			}
			// -----------------------------------------------------------------
			boolean totalSeatsFlag = false;
			while (!totalSeatsFlag) {
				System.out.print("총좌석수 : ");
				String totalSeats = sc.nextLine().replaceAll("[^0-9]", "0");
				if (totalSeats.length() == 0) {
					System.out.println("다시 입력해주세요.");
				} else {
					int totalSeatsNum = Integer.parseInt(totalSeats);
					if (totalSeatsNum < 1) {
						System.out.println("다시 입력해주세요.");
					} else {
						performance.setTotalSeats(totalSeatsNum);
						totalSeatsFlag = true;
					}
				}
			}
			// -----------------------------------------------------------------
			boolean priceFlag = false;
			while (!priceFlag) {
				System.out.print("티켓가격 : ");
				String price = sc.nextLine().replaceAll("[^0-9]", "0");
				if (price.length() == 0) {
					System.out.println("다시 입력해주세요.");
				} else {
					int priceNum = Integer.parseInt(price);
					if (priceNum < 1) {
						System.out.println("다시 입력해주세요.");
					} else {
						performance.setTicketPrice(priceNum);
						priceFlag = true;
					}
				}
			}
			// -----------------------------------------------------------------
			Main.performanceInfoList.add(performance);
			// 저장, 로딩
			savePerformanceList();
			lodePerformanceList();
		}
	}

	// 공연 항목 삭제
	public static void deletePerformanceItems() {
		boolean flag = false;
		if (Main.performanceInfoList.isEmpty()) {
			System.out.println("삭제할 공연정보가 없습니다.");
		} else {
			System.out.println("----------------------------------------------------------------");
			System.out.println(" 공연ID | 공연명 | 장르 | 공연일 | 장소 | 제한연령 | 잔여좌석/총좌석 | 티켓가격 ");
			System.out.println("----------------------------------------------------------------");
			Main.performanceInfoList.stream().forEach(System.out::println);
			System.out.println("----------------------------------------------------------------");
			System.out.print("삭제할 공연ID를 입력하세요(뒤로가기: -1)");
			String str = sc.nextLine();
			if (!str.equals("-1")) {
				for (int i = 0; i < Main.performanceInfoList.size(); i++) {
					if (str.equals(Main.performanceInfoList.get(i).getPerformanceID())) {
						System.out.print("공연 정보를 삭제하겠습니까? Y | N ");
						str = sc.nextLine();
						if (str.equalsIgnoreCase("Y")) {
							System.out.println(Main.performanceInfoList.get(i).getName() + " 공연 정보가 삭제되었습니다.");
							Main.performanceInfoList.remove(i);
							// 저장, 로딩
							savePerformanceList();
							lodePerformanceList();
						}
						flag = true;
						break;
					}
				}
				if (flag == false) {
					System.out.println("찾지 못했습니다.");
				}
			}
		}
	}

	// 회원 관리
	public static void manageMember() {
		if (Main.customerInfoList.isEmpty()) {
			System.out.println("삭제할 회원정보가 없습니다.");
		} else {
			System.out.println("----------------------------------------------------------------");
			System.out.println(" 등급 | 아이디 | 이름 | 나이 | 누적결제금액 | 마일리지 ");
			System.out.println("----------------------------------------------------------------");
			Main.customerInfoList.stream().forEach(System.out::println);
			System.out.println("----------------------------------------------------------------");

			System.out.print("삭제할 회원ID를 입력하세요(뒤로가기: -1)");
			String str = sc.nextLine();
			boolean flag = false;

			if (!str.equals("-1")) {
				for (int i = 0; i < Main.customerInfoList.size(); i++) {
					if (str.equals(Main.customerInfoList.get(i).getId())) {
						System.out.print("회원 정보를 삭제하겠습니까? Y | N ");
						str = sc.nextLine();
						if (str.equalsIgnoreCase("Y")) {
							int cusId = i;
							// 삭제 회원 장바구니 좌석지정 취소하기
							System.out.println(Main.customerInfoList.get(cusId).getId() + " 회원 정보가 삭제되었습니다.");
							customerCartDelete(cusId);
							Main.customerInfoList.remove(cusId);
							// 저장, 로딩
							saveCustomerList();
							lodeCustomerList();
						}
						flag = true;
						break;
					}
				}
				if (flag == false) {
					System.out.println("찾지 못했습니다.");
				}
			}
		}
	}

	// 삭제 회원 장바구니 좌석지정 취소하기
	public static void customerCartDelete(int cusId) {
		int cartCount = Main.customerInfoList.get(cusId).getCart().getCartItemList().size();
		if (cartCount == 0) {
		} else {
			int numId = -1, pnumId = -1;
			for (int i = 0; i < cartCount; i++) {
				for (int j = 0; j < Main.performanceInfoList.size(); j++) {
					if (Main.customerInfoList.get(cusId).getCart().getCartItemList().get(i).getPerformanceID()
							.equals(Main.performanceInfoList.get(j).getPerformanceID())) {
						numId = i;
						pnumId = j;
						break;
					}
				}
				Main.customerInfoList.get(cusId).getCart().cancelSeats(pnumId, numId);
				Main.customerInfoList.get(cusId).getCart().removeCartPerformance(pnumId, numId);
				// 공연 정보 저장, 로딩
				Admin.savePerformanceList();
				Admin.lodePerformanceList();
				// 고객 정보 저장, 로딩
				Admin.saveCustomerList();
				Admin.lodeCustomerList();
			}
		}
	}

	// performance 로딩
	public static void lodePerformanceList() {
		Main.performanceInfoList.clear();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("perfomances.txt"));
			Main.performanceInfoList.addAll((ArrayList<Performance>) ois.readObject());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("공연 정보 파일을 읽어올 수 없습니다.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// performance 저장
	public static void savePerformanceList() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("perfomances.txt"));
			oos.writeObject(Main.performanceInfoList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// customer 로딩
	public static void lodeCustomerList() {
		Main.customerInfoList.clear();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("customers.txt"));
			Main.customerInfoList.addAll((ArrayList<Customer>) ois.readObject());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("고객 정보 파일을 읽어올 수 없습니다.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// customer 저장
	public static void saveCustomerList() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("customers.txt"));
			oos.writeObject(Main.customerInfoList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// admin 로딩
	public static void lodeAdmin() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("admin.txt")));
			adminArr[0] = br.readLine();
			adminArr[1] = br.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("admin 파일을 읽어올 수 없습니다.");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
