package com.katocoding.munrolibrarychallenge.data.errors

enum class ErrorType {
    UNKNOWN,
    CSVHeaderMissing, CSVNameMissing, CSVHeightMMissing, CSVGridRefMissing, CSVHillCategoryMissing,
    MaxSmallerThanMin,
    NONE
}