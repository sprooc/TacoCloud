package org.example.tacocloud.data;

import org.example.tacocloud.entity.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    public Page<Taco> findAll(Pageable pageable);
}
