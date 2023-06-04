package com.stackroute.login.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(name = "API-KANBAN",url = "localhost:8888")
public interface UserProxy {
@PostMapping("/kanbandetails/registerUserDetails")
    public abstract ResponseEntity<?> sendUserDtoToKanbanDetailsApp(@RequestBody UserDto userDto);
}
