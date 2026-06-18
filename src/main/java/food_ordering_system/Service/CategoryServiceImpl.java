package food_ordering_system.Service;

import food_ordering_system.DTO.CategoryDTO;
import food_ordering_system.entity.Category;
import food_ordering_system.Exception.CategoryNotFoundException;
import food_ordering_system.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    private Category mapToEntity(CategoryDTO dto) {
        Category c = new Category();
        c.setName(dto.getName());
        return c;
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        return mapToDTO(repository.save(mapToEntity(dto)));
    }

    @Override
    public List<CategoryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category c = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        return mapToDTO(c);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category c = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        c.setName(dto.getName());

        return mapToDTO(repository.save(c));
    }

    @Override
    public void delete(Long id) {
        Category c = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        repository.delete(c);
    }
}