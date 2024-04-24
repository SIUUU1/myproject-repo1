package project.cart;

import java.util.ArrayList;

import project.main.Main;
import project.performance.Performance;

//프로젝트명 : Ticket World
//클레스 역할 : 고객의 장바구니와 관련된 정보를 관리하는 기능을 포함한 인터페이스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public interface CartInterface {
	// CartItem에 공연 정보를 등록
	void insertCartPerformance(Performance p, int c, ArrayList<String> slist);

	// 장바구니 순번 numId의 항목을 삭제
	void removeCartPerformance(int pnumId, int numId);

	// 장바구니 비우기
	void removeAllCart();

	// 결제금액 계산함수
	int payment();

	// 전체 공연 출력 함수
	public static void printPerformanceList() {
		System.out.println("================================================================");
		System.out.println(" 공연ID | 공연명 | 장르 | 공연일 | 장소 | 제한연령 | 잔여좌석/총좌석 | 티켓가격 ");
		System.out.println("================================================================");
		Main.performanceInfoList.stream().forEach(System.out::println);
		System.out.println("================================================================");
	}
}
