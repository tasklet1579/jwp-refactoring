package kitchenpos.domain.validator;

import kitchenpos.dao.OrderRepository;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTableValidator {
    private final OrderRepository orderRepository;

    public OrderTableValidator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void hasOrderStatusInCookingOrMeal(OrderTable orderTable) {
        List<Order> orders = orderRepository.findByOrderTableId(orderTable.getId());
        if (orders.stream().anyMatch(Order::hasOrderStatusInCookingOrMeal)) {
            throw new IllegalArgumentException("주문 상태가 조리 또는 식사인 테이블은 주문 등록 가능 상태를 변경할 수 없습니다.");
        }
    }

    public void hasOrderStatusInCookingOrMeal(List<OrderTable> orderTables) {
        orderTables.forEach(this::hasOrderStatusInCookingOrMeal);
    }
}
