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

import io.ktor.utils.io.core.*
import kotlin.experimental.or
import kotlin.jvm.JvmStatic
import kotlin.math.min

/**
 * Convert hex-encoded data to bytes and back again.
 */
object Hex {
    private val encodingTable = byteArrayOf(
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte()
    )
    private val decodingTable = ByteArray(128)

    init {
        decodingTable.indices.forEach { decodingTable[it] = 0xff.toByte() }
        encodingTable.indices.forEach { decodingTable[encodingTable[it].toInt()] = it.toByte() }
        decodingTable['A'.code] = decodingTable['a'.code]
        decodingTable['B'.code] = decodingTable['b'.code]
        decodingTable['C'.code] = decodingTable['c'.code]
        decodingTable['D'.code] = decodingTable['d'.code]
        decodingTable['E'.code] = decodingTable['e'.code]
        decodingTable['F'.code] = decodingTable['f'.code]
    }

    /**
     * Encode the input data producing a hex-encoded ByteArray.
     *
     * @return A String containing the hex-encoded data.
     */
    @JvmStatic
    fun toHexString(data: ByteArray): String = encode(data).decodeToString()

    /**
     * Encode the input data producing a hex-encoded ByteArray.
     *
     * @return A ByteArray containing the hex-encoded data.
     */
    @JvmStatic
    fun encode(data: ByteArray): ByteArray = buildPacket { encodeInternal(data, this) }.readBytes()

    /**
     * Decode the hex-encoded input data. It is assumed the input data is valid.
     *
     * @return A ByteArray representing the decoded data.
     */
    @JvmStatic
    fun decode(data: ByteArray): ByteArray = buildPacket { decodeInternal(data, this) }.readBytes()

    /**
     * Decode the hex-encoded String data - whitespaces will be ignored.
     *
     * @return A ByteArray representing the decoded data.
     */
    @JvmStatic
    fun decode(data: String): ByteArray = buildPacket { decodeInternal(data, this) }.readBytes()

    private fun encodeInternal(inBuf: ByteArray, inOff: Int, inLen: Int, outBuf: ByteArray, outOff: Int): Int {
        var inPos = inOff
        val inEnd = inOff + inLen
        var outPos = outOff
        while (inPos < inEnd) {
            val b: Int = inBuf[inPos++].toInt() and 0xFF
            outBuf[outPos++] = encodingTable[b ushr 4]
            outBuf[outPos++] = encodingTable[b and 0xF]
        }
        return outPos - outOff
    }

    private fun encodeInternal(buf: ByteArray, output: Output): Int {
        var offM = 0
        var lenM = buf.size
        val tmp = ByteArray(72)
        while (lenM > 0) {
            val inLen = min(36, lenM)
            val outLen = encodeInternal(buf, offM, inLen, tmp, 0)
            output.writeFully(tmp, 0, outLen)
            offM += inLen
            lenM -= inLen
        }
        return lenM * 2
    }

    private fun decodeInternal(data: ByteArray, output: Output): Int {
        val off = 0
        val length = data.size
        var b1: Byte
        var b2: Byte
        var outLen = 0
        val buf = ByteArray(36)
        var bufOff = 0
        var end = off + length
        while (end > off) {
            if (!ignore(Char(data[end - 1].toUShort()))) {
                break
            }
            end--
        }
        var i = off
        while (i < end) {
            while (i < end && ignore(Char(data[i].toUShort()))) {
                i++
            }
            b1 = decodingTable[data[i++].toInt()]
            while (i < end && ignore(Char(data[i].toUShort()))) {
                i++
            }
            b2 = decodingTable[data[i++].toInt()]
            if (b1 or b2 < 0) {
                throw Exception("invalid characters encountered in Hex data")
            }
            buf[bufOff++] = (b1.toInt() shl 4 or b2.toInt()).toByte()
            if (bufOff == buf.size) {
                output.writeFully(buf, 0, buf.size)
                bufOff = 0
            }
            outLen++
        }
        if (bufOff > 0) {
            output.writeFully(buf, 0, bufOff)
        }
        return outLen
    }

    private fun decodeInternal(data: String, output: Output): Int {
        var b1: Byte
        var b2: Byte
        var length = 0
        val buf = ByteArray(36)
        var bufOff = 0
        var end = data.length
        while (end > 0) {
            if (!ignore(data[end - 1])) {
                break
            }
            end--
        }
        var i = 0
        while (i < end) {
            while (i < end && ignore(data[i])) {
                i++
            }
            b1 = decodingTable[data[i++].code]
            while (i < end && ignore(data[i])) {
                i++
            }
            b2 = decodingTable[data[i++].code]
            if (b1 or b2 < 0) {
                throw Exception("invalid characters encountered in Hex string")
            }
            buf[bufOff++] = (b1.toInt() shl 4 or b2.toInt()).toByte()
            if (bufOff == buf.size) {
                output.writeFully(buf, 0, buf.size)
                bufOff = 0
            }
            length++
        }
        if (bufOff > 0) {
            output.writeFully(buf, 0, bufOff)
        }
        return length
    }

    private fun ignore(c: Char) = c == '\n' || c == '\r' || c == '\t' || c == ' '
}
