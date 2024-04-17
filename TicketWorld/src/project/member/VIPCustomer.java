package project.member;
import java.util.ArrayList;
import project.cart.Bill;
import project.cart.Cart;
//프로젝트명 : Ticket World
//클레스 역할 : VIP고객과 관련된 정보를 관리하는 기능을 처리하는 클래스
//제작자 : 안시우, 제작일 : 24년 4월 16일
public class VIPCustomer extends Customer {
	// memberVariable
	private double saleRatio; // 구매할인율

	// constructor
	public VIPCustomer(String name, String phone, String id, String pw, String address, int age, Cart cart,
			ArrayList<Bill> billList, int accumulatedPayment, int mileage) {
		super(name, phone, id, pw, address, age, cart, billList, accumulatedPayment, mileage);
		this.setGrade("VIP");
		this.setMileageRatio(0.05);
		this.saleRatio = 0.1;
	}

	// memberFunction
	public double getSaleRatio() {
		return saleRatio;
	}

	public void setSaleRatio(double saleRatio) {
		this.saleRatio = saleRatio;
	}

	// Overriding
	@Override
	public int calcPrice(int price) {
		boolean flag = false;
		int usingPoints = 0;

		while (!flag) {
			System.out.println(this.getName() + "고객님의 현재 적립금은 " + this.getMileage() + "원 입니다.");
			System.out.print("사용할 적립금을 입력해주세요(예: 0) ");
			usingPoints = sc.nextInt();
			sc.nextLine();
			if (this.getMileage() >= usingPoints) {
				flag = true;
			} else {
				System.out.println("사용할 적립금이 부족합니다.");
			}
		}
		System.out.print("적립금" + usingPoints + "원 사용되었습니다.");
		price -= ((int) (price * saleRatio) + usingPoints);
		this.setMileage(getMileage() - usingPoints + (int) (price * this.getMileageRatio()));
		this.setAccumulatedPayment(getAccumulatedPayment() + price);
		System.out.println((int) (price * saleRatio) + "원 할인 받으셨고, " + (int) (price * this.getMileageRatio())
				+ "원 적립되어, 총 적립금은 " + this.getMileage() + "원 입니다.\"");
		return price;
	}
}
