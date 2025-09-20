/*
 *     SheetMC Core: Result.kt
 *     Copyright (C) 2025 SheetMC.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.sheetmc.core

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Result<Nothing>()
}

class ErrorBuilder() {
    var message: String = "No message provided."
    var throwable: Throwable? = null

    fun build(): Result.Error = Result.Error(message, throwable)
}

fun Okay(): Result.Success<Boolean> = Result.Success(true)
fun <T> Okay(type: T): Result.Success<T> = Result.Success(type)
fun <T> Okay(block: () -> T): Result.Success<T> {
    return Result.Success(block())
}

fun Error(message: String, throwable: Throwable? = null): Result.Error = Result.Error(message, throwable)
fun Error(block: ErrorBuilder.() -> Unit): Result.Error {
    val builder = ErrorBuilder()
    block(builder)
    return builder.build()
}