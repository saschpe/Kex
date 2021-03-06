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

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteArrayTest {
    @Test
    fun hexDecoded() = assertEquals("61e9", byteArrayOf(97, -23).hexDecoded)

    @Test
    fun hexDecodedBytes() = assertContentEquals(byteArrayOf(97, -23), byteArrayOf(54, 49, 101, 57).hexDecodedBytes)

    @Test
    fun hexEncoded() = assertEquals("61e9", byteArrayOf(97, -23).hexEncoded)

    @Test
    fun hexEncodedBytes() = assertContentEquals(byteArrayOf(54, 49, 101, 57), byteArrayOf(97, -23).hexEncodedBytes)

    @Test
    fun toHexString() = assertEquals("61e9", byteArrayOf(97, -23).toHexString())
}
