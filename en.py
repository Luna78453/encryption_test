#!/bin/python3

intrada = 'lorem ipsum pene polla'
outter = ''

for char in intrada:
    if isalpha(char):
        c = ord(char) + 3
        outter += chr(c)

print(outter)
