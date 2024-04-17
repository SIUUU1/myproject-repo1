package project.cart;
import java.util.ArrayList;
import project.performance.Performance;
//프로젝트명 : Ticket World
//클레스 역할 : 고객의 장바구니와 관련된 정보를 관리하는 기능을 포함한 인터페이스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public interface CartInterface {
	void printPerformanceList(); // 전체 공연 정보 목록 출력

	void insertCartPerformance(Performance p, int c, ArrayList<String> slist); // CartItem에 공연 정보를 등록

	void removeCartPerformance(int pnumId, int numId); // 장바구니 순번 numId의 항목을 삭제

	int payment(); // 결제금액 계산함수
}
