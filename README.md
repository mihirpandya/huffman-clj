# Web App on Huffman Coding


## About

Huffman coding is an algorithm that is used for lossless data compression that relies on the frequencies of characters in a string to determine a compressed bitstring that it can be represented by. This is done by first generating a Huffman tree where the leaves of the tree are all the characters in the string. Each character's corresponding bitstring is determined by traversing down the tree to that character where each left traversal represents a 0 and each right traversal represents a 1.

I wrote this app in Clojure and chose the Noir web framework to turn it into a web app. Amazon's Elastic Beanstalk is used to deploy this web app. This was done by making a WAR file from the Clojure app and then pushing that file to the S3.

## Why Clojure

I thought of writing this algorithm in Clojure since Clojure provides some useful collections and significant room for a functional design for this algorithm. Particular places where I found Clojure useful are listed below:

-- A priority queue could easily be implemented with a sequence and have O(1) insertions and O(log n) deletions.

-- The frequencies of characters could be determined quickly by making use of filter which operates in a parallel manner on a sequence.

-- Making your own datatype to represent a Huffman tree node, which allowed easy accessibility to a node's value and allowed the flexibility of building a priority queue with respect to any key the client wants.

I had a lot of fun writing this in Clojure and look forward to using Clojure to write cooler stuff!