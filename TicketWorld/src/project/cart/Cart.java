package project.cart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import project.main.Main;
import project.member.Admin;
import project.performance.Performance;
//프로젝트명 : Ticket World
//클레스 역할 : 고객의 장바구니와 관련된 정보를 관리하는 기능을 처리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public class Cart implements CartInterface, Serializable {
	// memberVariable
	public static final int ROW_NUM = 5; // 행
	public static final int COLUMN_NUM = 20; // 열
	public static Scanner sc = new Scanner(System.in);
	private ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

	// memberFunction
	public ArrayList<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(ArrayList<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	// 장바구니 전품목 출력
	public void printCart() {
		System.out.println();
		System.out.println("================================================================");
		System.out.println(" \t\t\t " + "내 장바구니");
		System.out.println("================================================================");
		System.out.println(" 공연ID | 공연명 | 공연일 | 예매좌석 | 총예매수 |  ");
		System.out.println("----------------------------------------------------------------");
		for (int i = 0; i < cartItemList.size(); i++) {
			if (i != cartItemList.size() - 2) {
				System.out.println("----------------------------------------------------------------");
				System.out.println(cartItemList.get(i));
			} else {
				System.out.println(cartItemList.get(i));
			}
		}
		System.out.println("================================================================");
		System.out.println();
	}

	// 좌석지정함수
	public void chooseSeats(int numId, int count) {
		int[][] seat = new int[ROW_NUM][COLUMN_NUM];
		ArrayList<String> seatNumList = new ArrayList<String>();
		seat = Main.performanceInfoList.get(numId).getSeatArr();
		// 좌석출력
		printSeats(seat);
		// 좌석선택
		String seatNumber = null; // 좌석번호
		int x = 0, y = 0; // 좌석 배열 인덱스
		for (int i = 0; i < count; i++) {
			// -----------------------------------------------------------------
			boolean seatNumberFlag = false;
			while (!seatNumberFlag) {
				System.out.print("원하는 좌석" + (i + 1) + "(예:A00)을 입력하세요. ");
				seatNumber = sc.nextLine().toUpperCase();
				if (seatNumber.length() < 1 || seatNumber.length() > 4) {
				} else {
					x = seatNumber.charAt(0) - 65;
					y = Integer.parseInt(seatNumber.substring(1).replaceAll("[^0-9]", "0")) - 1;
					if (x < 0 || x > ROW_NUM || y < 0 || y > COLUMN_NUM) {
					} else {
						seatNumberFlag = true;
					}
				}
			}
			// -----------------------------------------------------------------

			// 좌석 중복 금지
			if (seat[x][y] == 1) {
				System.out.println("선택할 수 없는 좌석입니다.");
				i--;
			} else {
				seat[x][y] = 1;
				printSeats(seat);
				seatNumList.add(new String(seatNumber));
				Main.performanceInfoList.get(numId).setSeatArr(seat);
				// 좌석 선택 시 독점
				Admin.savePerformanceList();
				Admin.lodePerformanceList();
			}
		}
		// 장바구니 추가하기
		System.out.println("좌석 " + count + "매 장바구니에 추가하셨습니다.");
		insertCartPerformance(Main.performanceInfoList.get(numId), count, seatNumList);
		Admin.savePerformanceList();
		Admin.lodePerformanceList();
	}

	// 좌석 출력함수
	public void printSeats(int[][] seat) {
		char ch = 'A';
		System.out.println("\n----------------------------------------------------------------");
		System.out.println("***************************** 무   대 ***************************");
		System.out.print(" " + "01 02 03 04 05 06 07 08 09 10    11 12 13 14 15 16 17 18 19 20");
		System.out.println("\n----------------------------------------------------------------");
		for (int i = 0; i < seat.length; i++) {
			System.out.print((char) (ch + i) + " ");
			for (int j = 0; j < seat[i].length; j++) {
				String seatsColor = (seat[i][j] == 0) ? ("□") : ("■");
				if (j == seat[i].length / 2 - 1) {
					System.out.print(seatsColor + "     ");
				} else {
					System.out.print(seatsColor + "  ");
				}
			}
			System.out.println("\n----------------------------------------------------------------");
		}
	}

	// 좌석선택해제함수
	public void cancelSeats(int pnumId, int numId) {
		int x = 0, y = 0;// 좌석 배열 인덱스
		ArrayList<String> list = new ArrayList<String>();
		int[][] seat = new int[ROW_NUM][COLUMN_NUM];
		list = cartItemList.get(numId).getSeatNum();
		seat = Main.performanceInfoList.get(pnumId).getSeatArr();

		for (String s : list) {
			x = s.charAt(0) - 65;
			y = Integer.parseInt(s.substring(1)) - 1;
			seat[x][y] = 0;
		}
		Main.performanceInfoList.get(pnumId).setSeatArr(seat);
		// 저장, 로딩
		Admin.savePerformanceList();
		Admin.lodePerformanceList();
	}

	// Overriding
	@Override
	public void printPerformanceList() {
		System.out.println("================================================================");
		System.out.println(" 공연ID | 공연명 | 장르 | 공연일 | 장소 | 제한연령 | 잔여좌석/총좌석 | 티켓가격 ");
		System.out.println("================================================================");
		Main.performanceInfoList.stream().forEach(System.out::println);
		System.out.println("================================================================");
	}

	@Override
	public void insertCartPerformance(Performance performace, int quantity, ArrayList<String> seatNumList) {
		CartItem cartItem = new CartItem(performace, quantity, seatNumList);
		cartItemList.add(cartItem);
	}

	@Override
	public void removeCartPerformance(int pnumId, int numId) {
		Performance p = Main.performanceInfoList.get(pnumId);
		p.setSoldSeats(
				p.getSoldSeats() - cartItemList.get(numId).getQuantity());
		cartItemList.remove(numId);
	}

	@Override
	public int payment() {
		int sum = 0;
		for (CartItem data : cartItemList) {
			sum += data.getTotalPrice();
		}
		return sum;
	}

}