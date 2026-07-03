package food_ordering_system.Service;

import food_ordering_system.DTO.MenuDto;
import food_ordering_system.Exception.ResourceNotFoundException;
import food_ordering_system.Repository.CategoryRepository;
import food_ordering_system.Repository.MenuRepository;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<MenuDto> getAllMenus(Long categoryId, String search, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));

        return menuRepository.findByFilters(categoryId, normalizeSearch(search), pageable)
                .map(this::mapToDto);
    }

    @Override
    public MenuDto getMenuById(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu not found"));

        return mapToDto(menu);
    }

    @Override
    public MenuDto updateMenu(Long id, MenuDto dto) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());
        menu.setCategory(category);

        return mapToDto(menuRepository.save(menu));
    }

    @Override
    public void deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu not found"));

        menuRepository.delete(menu);
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

    private Sort buildSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.unsorted();
        }

        String[] parts = sort.split(",", 2);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            return Sort.unsorted();
        }

        return Sort.by(Sort.Direction.fromString(parts[1].trim()), parts[0].trim());
    }

    private String normalizeSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }

        return search.trim();
    }
}
