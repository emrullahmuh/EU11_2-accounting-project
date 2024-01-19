package com.cydeo.my_accounting_project.service.unit;

import com.cydeo.dto.CategoryDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.exception.CategoryNotFoundException;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.impl.CategoryServiceImpl;
import com.cydeo.util.MapperUtil;
import net.bytebuddy.matcher.ElementMatcher;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
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

        when(categoryRepository.findByIdAndIsDeleted(1L,false)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(()->categoryService.getById(1L));

        assertThat(throwable).isInstanceOf(CategoryNotFoundException.class);

        assertThat(throwable).hasMessage("Category Not Found");
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
    public void should_return_all_categories(){

        Category category1= new Category();
        category1.setId(1L);
        Category category2=new Category();
        category2.setId(2L);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryRepository.findAllByIsDeleted(false)).thenReturn(categoryList);
        when(mapperUtil.convert(Mockito.any(Category.class),Mockito.any(CategoryDto.class)))
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
