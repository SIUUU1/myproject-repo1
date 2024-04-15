package project.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import project.cart.Bill;
import project.cart.Cart;

public class Customer implements Comparable<Customer>, Serializable {
	// memberVariable
	public static Scanner sc = new Scanner(System.in);
	private String name; // 이름
	private String phone; // 폰번호
	private String id; // 아이디
	private String pw; // 비밀번호
	private String address; // 주소
	private int age; // 연령
	private Cart cart; // 장바구니
	private ArrayList<Bill> billList; // 결제내역
	private String grade; // 등급
	private int accumulatedPayment; // 누적결제금액(등급 평가시 사용할 것)
	private double mileageRatio; // 적립비율
	private int mileage; // 마일리지

	// constructor
	public Customer() {
		super();
		cart = new Cart();
		billList = new ArrayList<Bill>();
		this.grade = "Basic";
		this.mileageRatio = 0.03;
	}

	public Customer(String name, String phone, String id, String pw, String address, int age) {
		super();
		cart = new Cart();
		billList = new ArrayList<Bill>();
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.pw = pw;
		this.address = address;
		this.age = age;
		this.grade = "Basic";
		this.mileageRatio = 0.03;
	}

	public Customer(String name, String phone, String id, String pw, String address, int age, Cart cart,
			ArrayList<Bill> billList, int accumulatedPayment, int mileage) {
		super();
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.pw = pw;
		this.address = address;
		this.age = age;
		this.cart = cart;
		this.billList = billList;
		this.accumulatedPayment = accumulatedPayment;
		this.mileage = mileage;
		this.grade = "Basic";
		this.mileageRatio = 0.03;
	}

	// memberFunction
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public ArrayList<Bill> getBillList() {
		return billList;
	}

	public void setBillList(ArrayList<Bill> billList) {
		this.billList = billList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getAccumulatedPayment() {
		return accumulatedPayment;
	}

	public void setAccumulatedPayment(int accumulatedPayment) {
		this.accumulatedPayment = accumulatedPayment;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public double getMileageRatio() {
		return mileageRatio;
	}

	public void setMileageRatio(double mileageRatio) {
		this.mileageRatio = mileageRatio;
	}

	// Bill 추가함수
	public void addBillList(Bill bill) {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		billList = getBillList();
		billList.add(bill);
		this.billList = billList;
	}

	// 결제 계산 함수
	public int calcPrice(int price) {
		boolean flag = false;
		int usingPoints = 0; // 사용할 포인트

		while (!flag) {
			System.out.println(name + "고객님의 현재 적립금은 " + mileage + "원 입니다.");
			System.out.print("사용할 적립금을 입력해주세요(예: 0) ");
			usingPoints = sc.nextInt();
			sc.nextLine();
			if (mileage >= usingPoints) {
				flag = true;
			} else {
				System.out.println("사용할 적립금이 부족합니다.");
			}
		}
		System.out.println("적립금 " + usingPoints + "원 사용되었습니다.");
		price -= usingPoints;
		this.mileage -= usingPoints;
		this.accumulatedPayment += price;
		this.mileage += (int) (price * mileageRatio);
		System.out.println("적립금 " + (int) (price * mileageRatio) + "원 적립되어, 총 적립금은 " + this.mileage + "원 입니다.");
		return price;
	}

	// Overriding
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		Customer customer = null;
		if (obj instanceof Customer) {
			customer = (Customer) obj;
			return id.equals(customer.id);
		}
		return false;
	}

	@Override
	public int compareTo(Customer o) {
		return name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return " " + grade + " | " + id + " | " + name + " | " + age + " | " + accumulatedPayment + " | " + mileage
				+ "";
	}

}
