#!/bin/python3

intrada = open("testo.txt")
outter = ''
shift = 3

def transform(_input):
    _return = ''

    for char in _input:
        if char.isalpha():
            c = ord(char) + 3
            _return += chr(c)

    return _return

print(outter)
