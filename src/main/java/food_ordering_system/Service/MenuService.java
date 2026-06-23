package food_ordering_system.Service;

import food_ordering_system.DTO.MenuDto;

import java.util.List;

public interface MenuService {

    MenuDto createMenu(MenuDto dto);

    List<MenuDto> getAllMenus();

    MenuDto getMenuById(Long id);
}