package jpabook.jpashop.domain;

public class ValueMain {

    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        System.out.println("a == b: " + (a == b)); // true (기본 타입이므로 값 자체인 10을 비교)

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        // a와 b의 Address는 서로 다른 객체. 즉, 메모리 주소가 다름.
        System.out.println("address1 == address2: " + (address1 == address2)); // false (참조 타입이므로 서로 다른 객체의 메모리 주소를 비교함)
        System.out.println("address1 equals address2: " + address1.equals(address2)); // false (오버라이딩 하지 않은 equals()메서드는 기본적으로 == 비교를 실행한다.)
    }

}
