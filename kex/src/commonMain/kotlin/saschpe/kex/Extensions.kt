/*
 * Copyright 2022 Sascha Peilicke <sascha@peilicke.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package saschpe.kex

val ByteArray.hexDecoded: String
    get() = Hex.toHexString(this)

val ByteArray.hexDecodedBytes: ByteArray
    get() = Hex.decode(this)

val ByteArray.hexEncodedBytes: ByteArray
    get() = Hex.encode(this)

fun ByteArray.toHexString(): String = Hex.toHexString(this)

val String.hexDecodedBytes: ByteArray
    get() = Hex.decode(this)