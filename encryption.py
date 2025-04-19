#!/bin/python3

import sys


mode = str.lower(sys.argv[1])
_type = str.lower(sys.argv[2])
path = sys.argv[3]
key = sys.argv[4]
path_out = sys.argv[5]

if mode == 'decrypt':
    caesar_encrypt(path, int(key), True, path_out)
elif mode == 'encrypt':
    caesar_encrypt(path, int(key), False, path_out)
else:
    print('error writing first argument: mode')
