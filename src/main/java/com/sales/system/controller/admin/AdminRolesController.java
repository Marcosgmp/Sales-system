package com.sales.system.controller.admin;

import com.sales.system.entity.Roles;
import com.sales.system.service.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class AdminRolesController {

    private final RolesService rolesService;

    public AdminRolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostMapping
    public ResponseEntity<Roles> create(@RequestParam String name) {
        return ResponseEntity.ok(rolesService.create(name));
    }

    @GetMapping
    public ResponseEntity<List<Roles>> listAll() {
        return ResponseEntity.ok(rolesService.listAll());
    }
}
