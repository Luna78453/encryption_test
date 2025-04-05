#!/bin/python3

intrada = 'lorem ipsum pene polla'
outter = ''

shift = 3

for char in intrada:
    if char.isalpha():
        c = ord(char) + shift
        outter += chr(c)

print(outter)
