package org.example.utils;

/**
 * MurmurHash 是一种非加密哈希函数，以其快速和良好的分布特性而闻名。
 */
public class MurmurHash3 {


    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;
    private static final int R1 = 15;
    private static final int R2 = 13;
    private static final int M = 5;
    private static final int N = 0xe6546b64;

    public static int hash(String key) {
        int h1 = 0;
        int length = key.length();
        int offset = 0;

        while (length >= 4) {
            int k1 = getLittleEndianInt(key, offset);
            k1 *= C1;
            k1 = Integer.rotateLeft(k1, R1);
            k1 *= C2;
            h1 ^= k1;
            h1 = Integer.rotateLeft(h1, R2);
            h1 = h1 * M + N;
            length -= 4;
            offset += 4;
        }

        int k1 = 0;

        switch (length) {
            case 3:
                k1 ^= (key.charAt(offset + 2) & 0xff) << 16;
            case 2:
                k1 ^= (key.charAt(offset + 1) & 0xff) << 8;
            case 1:
                k1 ^= (key.charAt(offset) & 0xff);
                k1 *= C1;
                k1 = Integer.rotateLeft(k1, R1);
                k1 *= C2;
                h1 ^= k1;
        }

        h1 ^= key.length();

        h1 = fmix(h1);

        return h1;
    }

    private static int getLittleEndianInt(String key, int offset) {
        return (key.charAt(offset) & 0xff) |
                ((key.charAt(offset + 1) & 0xff) << 8) |
                ((key.charAt(offset + 2) & 0xff) << 16) |
                ((key.charAt(offset + 3) & 0xff) << 24);
    }

    private static int fmix(int h) {
        h ^= h >>> 16;
        h *= 0x85ebca6b;
        h ^= h >>> 13;
        h *= 0xc2b2ae35;
        h ^= h >>> 16;
        return h;
    }

}

