

Title: File compression using Lempel-Ziv-Welch (LZW) algorithm

Programming Language Used: Java
Java Version: 8
JRE Version: 1.8

Modes of operation:
Compression
Decompression

Project design:
Data structure used for storing dictionary is HashMap.
 
Instructions to run the program:
Compression:

javac Compressor.java
java Compressor <filename> <number of bits>

Decompression:

javac Decompressor.java
java Decompressor <compressed filename> <number of bits>


Note:
1. The program works only on ASCII characters.
2. The program is efficient when the number of bits is between 9 and 12.
