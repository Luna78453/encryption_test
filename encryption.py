#!/bin/python3
import os
import sys

mode = str.lower(sys.argv[1])
_type = str.lower(sys.argv[2])
path = sys.argv[3]
key = sys.argv[4]
path_out = sys.argv[5]

def shift(_input, _output, shift_amnt):
    c = chr(_input + shift_amnt).encode(encoding="utf-8")
    #print('{} {} {} {}'.format(str(_input), ord(str(_input)) + shift_amnt, ord(str(_input)), c))
    _output.write(c)

def encryption(_input, _output, shift_amnt):
    for line in _input:
        for char in line:
            shift(char, _output, shift_amnt)
 
def encrypt(dir, shifts, decrypt, dir_out):
    if decrypt:
        shifts *= -1

    if str.lower(dir_out) == 'none':
        with open(dir, "rb") as intrada, open(os.path.splitext(intrada.name)[0] + "_en.txt", "wb") as outter:
            encryption(intrada, outter, shifts)    
    else:
        with open(dir, "rb") as intrada, open(dir_out, "wb") as outter:
            encryption(intrada, outter, shifts)

    intrada.close()
    outter.close()

if mode == 'decrypt':
    encrypt(path, int(key), True, path_out)
elif mode == 'encrypt':
    encrypt(path, int(key), False, path_out)
else:
    print('error writing first argument: mode')
