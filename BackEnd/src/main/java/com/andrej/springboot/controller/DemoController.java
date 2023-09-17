package com.andrej.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.ContactDTO;
import com.andrej.springboot.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DemoController {

  @GetMapping("/admin")
  @PreAuthorize("hasAnyRole('backend_admin', 'admin')")
  public ResponseEntity<Map<String, String>> helloAdmin() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "Hello admin");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/manager")
  @PreAuthorize("hasAnyRole('backend_manager', 'manager')")
  public ResponseEntity<Map<String, String>> helloManager() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "Hello manager");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/demo")
  public String sayHi() {
    return "Hi";
  }
}
