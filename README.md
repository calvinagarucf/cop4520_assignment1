# Programming Assignment 1 - Multithreading primes

With my program, I believe the threads are for the most part working equivalently. With my implementation, there is a counter that determines the next number a given thread should check. This counter starts at 3 (the first number to check) and increments by 2 to avoid all even numbers. Each thread check whether the next integer is larger than the upper limit (10^8), and then checks that integer for primality. With this, each thread is checking approximately the same number of integers for primality. Additionally, the thread is always choosing the smallest number to check, so there isn't one thread only calculating very large numbers, they are done by the threads together. This is a large optimization to the naive approach of dividing the integer list into even ranges that each thread must check.

For optimizations with determining primality, I am only checking up to the square root of the number to check and I also skip all even numbers.

Compile and run:
javac MultiThreadingPrimes.java && java MultiThreadingPrimes
