package food_ordering_system.Controller;

import food_ordering_system.DTO.MenuDto;
import food_ordering_system.Service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuDto> create(
            @RequestBody @Valid MenuDto dto) {

        return ResponseEntity.ok(
                menuService.createMenu(dto));
    }

    @GetMapping
    public ResponseEntity<List<MenuDto>> all() {

        return ResponseEntity.ok(
                menuService.getAllMenus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> byId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                menuService.getMenuById(id));
    }
}