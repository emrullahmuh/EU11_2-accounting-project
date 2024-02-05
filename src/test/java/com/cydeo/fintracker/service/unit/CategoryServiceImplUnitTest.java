package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.repository.CategoryRepository;
import com.cydeo.fintracker.service.impl.CategoryServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void should_throw_an_exception_when_category_doesnt_exist(){

        when(categoryRepository.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getById(1L));
    }

    @Test
    public void should_return_CategoryDto_when_category_exist() throws CategoryNotFoundException {

        Category category = new Category();
        category.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        when(categoryRepository.findByIdAndIsDeleted(category.getId(),false)).thenReturn(Optional.of(category));
        when(mapperUtil.convert(category, new CategoryDto())).thenReturn(categoryDto);

        CategoryDto result = categoryService.getById(category.getId());

        assertThat(result.getId()).isEqualTo(categoryDto.getId());
    }

    @Test
    @Disabled
    public void should_return_all_categories(){

        Category category1= new Category();
        category1.setId(1L);
        Category category2=new Category();
        category2.setId(2L);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryRepository.findAllByIsDeleted(false)).thenReturn(categoryList);
        when(mapperUtil.convert(any(Category.class),any(CategoryDto.class)))
                .thenAnswer(invocation -> {
                    Category category = invocation.getArgument(0);
                    CategoryDto categoryDto =new CategoryDto();
                    categoryDto.setId(category.getId());

                    return categoryDto;
                });
        List<CategoryDto> result = categoryService.listAllCategories();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @Disabled
    public void should_save_category_when_category_doesnt_exist(){

       CategoryDto categoryDto =new CategoryDto();

       when(mapperUtil.convert(Mockito.any(CategoryDto.class),Mockito.any(Category.class))).thenReturn(new Category());

       categoryService.save(categoryDto);

       Mockito.verify(mapperUtil).convert(Mockito.eq(categoryDto),Mockito.any(Category.class));

       Mockito.verify(categoryRepository).save(Mockito.any(Category.class));

    }

    @Test
    public void should_be_deleted_when_category_has_not_product() throws CategoryNotFoundException {

        Long categoryId = 1L;
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepository.findByIdAndIsDeleted(categoryId,false)).thenReturn(Optional.of(category));
        when(mapperUtil.convert(category,categoryDto)).thenReturn(categoryDto);

        categoryService.delete(categoryId);

        assertTrue(category.getIsDeleted());

        verify(categoryRepository,times(1)).save(category);

    }
}
