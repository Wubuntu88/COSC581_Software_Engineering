#!/usr/bin/python
import matplotlib.pyplot as plt

x = []
f = open("means.txt", "r")
for item in f:
    x.append(float(item.rstrip()))

plt.hist(x, bins=20)

plt.xlabel("values of sample means", fontsize=30)
plt.ylabel("number of instances of a mean", fontsize=30)
plt.title("histogram of sample means", fontsize=36)

plt.show()
