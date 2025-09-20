/*
 *     SheetMC Core: Outcome.kt
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

sealed class Outcome<out T> {
    data class Success<T>(val value: T) : Outcome<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Outcome<Nothing>()
}

class ErrorBuilder() {
    var message: String = "No message provided."
    var throwable: Throwable? = null

    fun build(): Outcome.Error = Outcome.Error(message, throwable)
}

fun okay(): Outcome.Success<Boolean> = Outcome.Success(true)
fun <T> okay(type: T): Outcome.Success<T> = Outcome.Success(type)
fun <T> okay(block: () -> T): Outcome.Success<T> {
    return Outcome.Success(block())
}

fun failed(message: String, throwable: Throwable? = null): Outcome.Error = Outcome.Error(message, throwable)
fun failed(block: ErrorBuilder.() -> Unit): Outcome.Error {
    val builder = ErrorBuilder()
    block(builder)
    return builder.build()
}