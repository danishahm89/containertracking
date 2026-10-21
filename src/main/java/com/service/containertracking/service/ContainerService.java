package com.service.containertracking.service;

import com.service.containertracking.entity.Container;
import com.service.containertracking.repository.ContainerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContainerService {

    @Autowired
    ContainerRepo containerRepo;

    public Container createContainer(Container container) {
        container.setStatus("NOT_STARTED");
        return containerRepo.save(container);
    }

    public Optional<Container> getContainerById(int containerId) {
        return containerRepo.findById(containerId);
    }

    public void updateContainerStatus(int containerId, String status) {
        Optional<Container> containerOpt = containerRepo.findById(containerId);
        if (containerOpt.isPresent()) {
            Container container = containerOpt.get();
            container.setStatus(status);
            containerRepo.save(container);
        }
    }

}
