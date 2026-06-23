package food_ordering_system.Service;

import food_ordering_system.DTO.MenuDto;
import food_ordering_system.Exception.ResourceNotFoundException;
import food_ordering_system.Repository.CategoryRepository;
import food_ordering_system.Repository.MenuRepository;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public MenuServiceImpl(MenuRepository menuRepository,
                           CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuDto createMenu(MenuDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Menu menu = mapToEntity(dto, category);

        Menu saved = menuRepository.save(menu);

        return mapToDto(saved);
    }

    @Override
    public List<MenuDto> getAllMenus() {

        return menuRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDto getMenuById(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu not found"));

        return mapToDto(menu);
    }

    private MenuDto mapToDto(Menu menu) {

        MenuDto dto = new MenuDto();

        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        dto.setImageUrl(menu.getImageUrl());
        dto.setCategoryId(menu.getCategory().getId());
        dto.setCategoryName(menu.getCategory().getName());

        return dto;
    }

    private Menu mapToEntity(MenuDto dto, Category category) {

        Menu menu = new Menu();

        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());
        menu.setCategory(category);

        return menu;
    }
}