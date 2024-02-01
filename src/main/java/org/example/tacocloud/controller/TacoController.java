package org.example.tacocloud.controller;

import org.example.tacocloud.data.OrderRepository;
import org.example.tacocloud.data.TacoRepository;
import org.example.tacocloud.entity.Taco;
import org.example.tacocloud.entity.TacoOrder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/tacos",
        produces = {"application/json", "text/xml"})
@CrossOrigin(origins="*")
public class TacoController {
    private TacoRepository tacoRepo;
    private OrderRepository orderRepo;
    public TacoController(TacoRepository tacoRepo, OrderRepository orderRepo) {
        this.tacoRepo = tacoRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }
    @GetMapping(params = "recentOrders")
    public Iterable<TacoOrder> recentOrders() {
        return orderRepo.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if(optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }
    @PutMapping(path="/{id}", consumes = "application/json")
    public Taco putTaco(
            @PathVariable("id") Long orderId,
            @RequestBody Taco taco) {
        taco.setId(orderId);
        return tacoRepo.save(taco);
    }
    @PatchMapping(path="/{id}", consumes = "application/json")
    public Taco patchTaco(
            @PathVariable("id") Long id,
            @RequestBody Taco patch) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if(!optTaco.isPresent()) return null;
        Taco taco = optTaco.get();
        if (patch.getName() != null) {
            taco.setName(patch.getName());
        }
        if(patch.getIngredients() != null) {
            taco.setIngredients(patch.getIngredients());
        }
        return tacoRepo.save(taco);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable("id") Long id) {
        try {
            tacoRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }
}
