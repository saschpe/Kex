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
import kotlin.test.assertEquals

class HexTest {
    @Test
    fun toHexString() {
        assertEquals("0001020304050607", Hex.toHexString(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7)))
        assertEquals("08090a0b0c0d0e0f", Hex.toHexString(byteArrayOf(8, 9, 10, 11, 12, 13, 14, 15)))
        assertEquals("61e9", Hex.toHexString(byteArrayOf(97, -23)))
    }

    @Test
    fun byteArray_toHexString() = assertEquals("61e9", byteArrayOf(97, -23).toHexString())
}
