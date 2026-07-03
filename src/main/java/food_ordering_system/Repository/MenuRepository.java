package food_ordering_system.Repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(
            value = """
            SELECT menu FROM Menu menu
            JOIN FETCH menu.category
            WHERE (:categoryId IS NULL OR menu.category.id = :categoryId)
            AND (:search IS NULL OR LOWER(menu.name) LIKE LOWER(CONCAT('%', :search, '%')))
            """,
            countQuery = """
            SELECT COUNT(menu) FROM Menu menu
            WHERE (:categoryId IS NULL OR menu.category.id = :categoryId)
            AND (:search IS NULL OR LOWER(menu.name) LIKE LOWER(CONCAT('%', :search, '%')))
            """
    )
    Page<Menu> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("search") String search,
            Pageable pageable
    );

    boolean existsByCategoryId(Long categoryId);
}
