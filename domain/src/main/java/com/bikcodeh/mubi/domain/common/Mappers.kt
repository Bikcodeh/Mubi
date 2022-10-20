package com.bikcodeh.mubi.domain.common

interface Mapper<I, O> {
    fun map(input: I): O
}

// Non-nullable to Non-nullable
interface ListMapper<I, O>: Mapper<List<I>, List<O>>