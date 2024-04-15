package project.cart;

import java.util.ArrayList;
import project.performance.Performance;

public interface CartInterface {
	void printPerformanceList(); // 전체 공연 정보 목록 출력

	void insertCartPerformance(Performance p, int c, ArrayList<String> slist); // CartItem에 공연 정보를 등록

	void removeCartPerformance(int pnumId, int numId); // 장바구니 순번 numId의 항목을 삭제

	int payment(); // 결제금액 계산함수
}
