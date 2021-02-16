package com.abbasi.domain.datasources.remote

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import com.abbasi.domain.FakeDataUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRemoteDataSource : CategoryRemoteDataSource {

    private var behavior = Behavior.SHOULD_RETURN_VALID_DATA
    private var validData = FakeDataUtil.getAllFakeCategories()

    override fun getAll(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        emit(
            when (behavior) {
                Behavior.SHOULD_RETURN_VALID_DATA -> Resource.Valid(validData)
                Behavior.SHOULD_RETURN_INVALID -> Resource.Invalid("No data")
                Behavior.SHOULD_RETURN_EMPTY_DATA -> Resource.Valid(emptyList())
            }
        )
    }

    fun fake_setBehavior(behavior: Behavior) {
        this.behavior = behavior
    }

    fun fake_setValidData(data: List<Category>) {
        this.validData = data
    }

    enum class Behavior {
        SHOULD_RETURN_VALID_DATA,
        SHOULD_RETURN_INVALID,
        SHOULD_RETURN_EMPTY_DATA,
    }
}