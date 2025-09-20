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
    data class Failure(val message: String, val throwable: Throwable? = null) : Outcome<Nothing>()
}

class FailureBuilder() {
    var message: String = "No message provided."
    var throwable: Throwable? = null

    fun build(): Outcome.Failure = Outcome.Failure(message, throwable)
}

fun success(): Outcome.Success<Boolean> = Outcome.Success(true)
fun <T> success(value: T): Outcome.Success<T> = Outcome.Success(value)
fun <T> success(block: () -> T): Outcome.Success<T> {
    return Outcome.Success(block())
}

fun failure(message: String, throwable: Throwable? = null): Outcome.Failure = Outcome.Failure(message, throwable)
fun failure(block: FailureBuilder.() -> Unit): Outcome.Failure {
    val builder = FailureBuilder()
    block(builder)
    return builder.build()
}