import algorithms.caesar as caesar
import algorithms.vigenere as vigenere
import sys


mode = str.lower(sys.argv[1])
_type = str.lower(sys.argv[2])
path = sys.argv[3]
key = str.lower(sys.argv[4])
path_out = sys.argv[5]

algorithm_dict = {}

match _type:
    case 'caesar':
        if mode == 'decrypt':
            caesar.caesar_encrypt(path, int(key), True, path_out)
        elif mode == 'encrypt':
            caesar.caesar_encrypt(path, int(key), False, path_out)
        else:
            print('error writing first argument: mode')
    case 'vigenere':
        if mode == 'decrypt':
            vigenere.vigenere_encrypt(path, key, True, path_out)
        elif mode == 'encrypt':
            vigenere.vigenere_encrypt(path, key, False, path_out)
        else:
            print('error writing first argument: mode')
