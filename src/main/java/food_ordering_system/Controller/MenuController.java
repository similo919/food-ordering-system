package food_ordering_system.Controller;

import food_ordering_system.DTO.MenuDto;
import food_ordering_system.Service.MenuService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<MenuDto>> all(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(
                menuService.getAllMenus(categoryId, search, page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> byId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                menuService.getMenuById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDto> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuDto dto) {

        return ResponseEntity.ok(
                menuService.updateMenu(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        menuService.deleteMenu(id);

        return ResponseEntity.noContent().build();
    }
}
