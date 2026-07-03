package food_ordering_system.Service;

import food_ordering_system.DTO.MenuDto;
import org.springframework.data.domain.Page;

public interface MenuService {

    MenuDto createMenu(MenuDto dto);

    Page<MenuDto> getAllMenus(Long categoryId, String search, int page, int size, String sort);

    MenuDto getMenuById(Long id);

    MenuDto updateMenu(Long id, MenuDto dto);

    void deleteMenu(Long id);
}
