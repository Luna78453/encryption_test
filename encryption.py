import algorithms.caesar as caesar
import algorithms.vigenere as vigenere
import sys, os
from typing import IO

en_type = path = path_out = mode = key = ''

if len(sys.argv) == 6:
    en_type = str.lower(sys.argv[1])
    path = sys.argv[2]
    path_out = sys.argv[3]
    mode = str.lower(sys.argv[2])
    key = str.lower(sys.argv[5])

if len(sys.argv) == 5:
    en_type = str.lower(sys.argv[1])
    path = sys.argv[2]
    mode = str.lower(sys.argv[3])
    key = str.lower(sys.argv[4])

algorithm_dict = {}

def matchEncryptionType(entrata: IO, uscita: IO):
    match en_type:
        case 'caesar':
            if mode == 'decrypt' or mode == 'de' or mode == 'd':
                caesar.caesar_encrypt(entrata, int(key), True, uscita)
            elif mode == 'encrypt' or mode == 'en' or mode == 'e':
                caesar.caesar_encrypt(entrata, int(key), False, uscita)
            else:
                print('mode parse error: not found')
        case 'vigenere':
            if mode == 'decrypt' or mode == 'de' or mode == 'd':
                vigenere.vigenere_encrypt(entrata, key, True, uscita)
            elif mode == 'encrypt' or mode == 'en' or mode == 'e':
                vigenere.vigenere_encrypt(entrata, key, False, uscita)
            else:
                print('mode parse error: not found')

if path_out == '':
    with open(path, "rb") as entrata, open(os.path.splitext(entrata.name)[0] + "_en.txt", "wb") as uscita:
        matchEncryptionType(entrata, uscita)
else:
    with open(path, "rb") as entrata, open(path_out, "wb") as uscita:
        matchEncryptionType(entrata, uscita)

