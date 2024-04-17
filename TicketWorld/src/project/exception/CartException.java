package project.exception;
//프로젝트명 : Ticket World
//클레스 역할 : 고객의 장바구니와 관련된 Exception을 처리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public class CartException extends Exception {
	public CartException(String str) {
		super(str);
	}
}
