#!/bin/python3

intrada = open("testo.txt")
outter = ''

def transform(_input):
    _return = ''

    for char in _input:
        if isalpha(char):
            c = ord(char) + 3
            _return += chr(c)

    return _return

print(outter)
