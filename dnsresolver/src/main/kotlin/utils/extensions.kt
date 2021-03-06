package utils

import resolver.DnsPacket
import resolver.RRData

fun Int.toBooleanArray(takeLastBits: Int): BooleanArray {
    val converted = Integer.toBinaryString(this)
        .takeLast(takeLastBits)
        .map { it == '1' }
        .toBooleanArray()

    if (converted.size < takeLastBits) {
        val augmented = BooleanArray(takeLastBits)
        for(i in converted.indices) {
            augmented[augmented.size - (converted.size - i)] = converted[i]
        }

        return augmented
    }
    return converted
}

fun Byte.toBooleanArray(): BooleanArray = this.toInt().toBooleanArray(8)

fun BooleanArray.toByte(): Byte {
    return this.joinToString("") { if (it) "1" else "0" }.toInt(radix = 2).toByte()
}

fun BooleanArray.toInt(): Int {
    return this.joinToString("") { if (it) "1" else "0" }.toInt(radix = 2)
}

fun Int.toTwoBytes(): List<Byte> {
    return listOf(
        (this shr 8).toByte(),
        (this and 0b11111111).toByte()
    )
}

fun Int.toFourBytes(): List<Byte> {
    return listOf(
        (this shr 32).toByte(),
        (this shr 16 and 0xFF).toByte(),
        (this shr 8 and 0xFF).toByte(),
        (this and 0xFF).toByte()
    )
}

inline fun <reified T: RRData> Collection<DnsPacket.ResourceRecord>.ofType(): Collection<Pair<DnsPacket.ResourceRecord, T>> {
    return this
        .filter { it.data is T }
        .map { it to it.data as T }
}