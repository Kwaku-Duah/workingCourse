package com.groupthree.lms.controller.courseController;

import com.groupthree.lms.dto.courseDto.ModuleDTO;
import com.groupthree.lms.services.courseService.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/module")
public class ModuleController {

    private final ModuleService moduleService;


    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<ModuleDTO> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        ModuleDTO moduleDTO = moduleService.getModuleById(id);
        return ResponseEntity.ok(moduleDTO);
    }

    // @PostMapping
    // public ModuleDTO createModule(@RequestBody ModuleDTO moduleDTO) {
    //     return moduleService.saveModule(moduleDTO);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
//     @PutMapping("/{id}")
//     public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, @RequestBody ModuleDTO moduleDTO) {
//         ModuleDTO updatedModule = moduleService.updateModule(id, moduleDTO);
//         return ResponseEntity.ok(updatedModule);
//     }
}