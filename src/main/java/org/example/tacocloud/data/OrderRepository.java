package org.example.tacocloud.data;

import org.example.tacocloud.entity.TacoOrder;
import org.example.tacocloud.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    public List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
