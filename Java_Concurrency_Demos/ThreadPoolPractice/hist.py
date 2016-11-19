#!/usr/bin/python
import matplotlib.pyplot as plt

x = []
f = open("means.txt", "r")
for item in f:
    x.append(float(item.rstrip()))

plt.hist(x, bins=20)

plt.xlabel("values of sample means", fontsize=20)
plt.ylabel("number of instances of a mean", fontsize=20)
plt.title("histogram of sample means", fontsize=26)

plt.show()
