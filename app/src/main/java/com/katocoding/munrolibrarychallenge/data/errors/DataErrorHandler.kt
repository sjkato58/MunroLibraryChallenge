package com.katocoding.munrolibrarychallenge.data.errors

import com.katocoding.munrolibrarychallenge.R

class DataErrorHandler {

    fun sortCSVError(
        errorType: ErrorType
    ): Int = when (errorType) {
        ErrorType.MaxSmallerThanMin -> R.string.err_max_smaller_min
        ErrorType.CSVHeaderMissing -> R.string.err_csv_header_missing
        ErrorType.CSVNameMissing -> R.string.err_csv_name_missing
        ErrorType.CSVHeightMMissing -> R.string.err_csv_height_missing
        ErrorType.CSVGridRefMissing -> R.string.err_csv_gridref_missing
        ErrorType.CSVHillCategoryMissing -> R.string.err_csv_hillcategory_missing
        else -> R.string.err_csv_unknown
    }
}