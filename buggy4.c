/* a32.c */
#include <stdint.h>
#include <stddef.h>

uint32_t adler32(uint32_t adler, const uint8_t *buf, size_t len) {
    uint32_t s1 = adler & 0xffff;
    uint32_t s2 = (adler >> 16) & 0xffff;
    size_t n;

    if (buf == NULL) {
        /* Return current adler if no data is processed */
        if (len == 0)
            return adler;
        /* Historical behavior for invalid input (buf NULL with len > 0) */
        return 1;
    }

    for (n = 0; n < len; n++) {
        s1 = (s1 + buf[n]) % 65521;
        s2 = (s2 + s1) % 65521;
    }

    return (s2 << 16) | s1;
}