package com.abbasi.data.local.models

import com.abbasi.data.FakeDataUtil
import com.abbasi.domain.models.Category
import com.google.common.truth.Truth
import org.junit.Test

class CategoryEntityTest {


    @Test
    fun fromDomain() {
        val inputCategory = FakeDataUtil.Domain.category1

        val output = CategoryEntity.fromDomain(inputCategory)

        assertCategoryIsSame(output, inputCategory)
    }

    @Test
    fun ListfromDomain() {
        val inputCategory = FakeDataUtil.Domain.getAllFakeCategories()

        val output = inputCategory.fromDomain()

        output.forEachIndexed { index, entity ->
            assertCategoryIsSame(entity, inputCategory[index])
        }
    }

    @Test
    fun toDomainModel() {
        val inputCategory = FakeDataUtil.Local.category1
        val inputProducts = FakeDataUtil.Domain.getAllFakeProducts()

        val output = inputCategory.toDomainModel(inputProducts)

        Truth.assertThat(output.id).isEqualTo(inputCategory.id)
        Truth.assertThat(output.name).isEqualTo(inputCategory.name)
        Truth.assertThat(output.description).isEqualTo(inputCategory.description)
        Truth.assertThat(output.products.size).isEqualTo(inputProducts.size)
    }

    private fun assertCategoryIsSame(
        output: CategoryEntity,
        inputCategory: Category
    ) {
        Truth.assertThat(output.id).isEqualTo(inputCategory.id)
        Truth.assertThat(output.name).isEqualTo(inputCategory.name)
        Truth.assertThat(output.description).isEqualTo(inputCategory.description)
    }
}