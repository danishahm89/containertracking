package com.service.containertracking.controller;

import com.service.containertracking.entity.Container;
import com.service.containertracking.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/containers")
public class ContainerController {

    @Autowired
    ContainerService containerService;

    @PostMapping
    public ResponseEntity<Container> createContainer(@RequestBody Container container) {
        Container result = containerService.createContainer(container);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{containerId}")
    public ResponseEntity<Container> getContainerStatus(@PathVariable int containerId) {
        Optional<Container> container = containerService.getContainerById(containerId);
        return container.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{containerId}/status/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable int containerId, @PathVariable String status) {
        containerService.updateContainerStatus(containerId, status);
        return ResponseEntity.ok().build();
    }

}
