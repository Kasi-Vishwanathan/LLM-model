// File: buggy5.cpp

#include <zlib.h>
#include <iostream>
#include <vector>
#include <optional>
#include <cstring>

std::optional<std::vector<char>> gzcompress(const std::vector<char>& data, int level = Z_DEFAULT_COMPRESSION) {
    const uLong sourceLen = data.size();
    uLongf compressedDataSize = compressBound(sourceLen);
    std::vector<Bytef> compressedData(compressedDataSize);

    int ret = compress2(
        compressedData.data(), &compressedDataSize,
        reinterpret_cast<const Bytef*>(data.data()), sourceLen,
        level
    );

    if (ret != Z_OK) {
        return std::nullopt;
    }

    compressedData.resize(compressedDataSize);

    std::vector<char> result(sizeof(uLongf) + compressedDataSize);
    uLongf originalSourceLen = sourceLen;
    std::memcpy(result.data(), &originalSourceLen, sizeof(uLongf));
    std::memcpy(result.data() + sizeof(uLongf), compressedData.data(), compressedDataSize);

    return result;
}

std::optional<std::vector<char>> gzdecompress(const std::vector<char>& compressedDataWithHeader) {
    if (compressedDataWithHeader.size() < sizeof(uLongf)) {
        return std::nullopt;
    }

    uLongf originalSourceLen;
    std::memcpy(&originalSourceLen, compressedDataWithHeader.data(), sizeof(uLongf));

    const char* compressedData = compressedDataWithHeader.data() + sizeof(uLongf);
    const uLongf compressedSize = compressedDataWithHeader.size() - sizeof(uLongf);

    std::vector<char> decompressedData(originalSourceLen);
    uLongf decompressedSize = originalSourceLen;

    int ret = uncompress(
        reinterpret_cast<Bytef*>(decompressedData.data()), &decompressedSize,
        reinterpret_cast<const Bytef*>(compressedData), compressedSize
    );

    if (ret != Z_OK || decompressedSize != originalSourceLen) {
        return std::nullopt;
    }

    return decompressedData;
}

int main() {
    std::string originalStr = "Hello, world!";
    std::vector<char> source(originalStr.begin(), originalStr.end());
    source.push_back('\0');

    auto compressed = gzcompress(source);
    if (!compressed) {
        std::cerr << "Compression failed!" << std::endl;
        return 1;
    }

    auto decompressed = gzdecompress(*compressed);
    if (!decompressed) {
        std::cerr << "Decompression failed!" << std::endl;
        return 1;
    }

    if (*decompressed != source) {
        std::cerr << "Decompressed data mismatch!" << std::endl;
        return 1;
    }

    std::cout << "Success!" << std::endl;
    return 0;
}