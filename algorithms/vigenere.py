import os
from typing import List, IO

def shift(_input: str, _output, shift_key: List[int], _iterator: int):
    c = chr((int(_input) - 97 + shift_key[_iterator]) % 26 + 97).lower().encode(encoding="utf-8")
    _output.write(c)

def encryption(_input, _output, shift_key: List[int]):
    cycling_iterator = 0
    for line in _input:
        for char in line:
            if cycling_iterator == len(shift_key):
                cycling_iterator = 0                          
            if chr(char).isalpha():
                print(chr(char), cycling_iterator)
                shift(char, _output, shift_key, cycling_iterator)
                cycling_iterator += 1
            else:
                _output.write(chr(char).encode(encoding="utf-8"))

#entrata: IO, shifts: int, decrypt: bool, uscita: IO
def vigenere_encrypt(entrata: IO, key: str, decrypt: bool, uscita: IO):
    if not decrypt:
        key_shifts = [ord(c) - 97 for c in key]  
    else:
        key_shifts = [-(ord(c) - 97) for c in key]  

#   if dir_out is None:
#       with open(dir, "rb") as intrada, open(os.path.splitext(intrada.name)[0] + "_en.txt", "wb") as outter:
#           encryption(intrada, outter, key_shifts)
#   else:
#       with open(dir, "rb") as intrada, open(dir_out, "wb") as outter:
#           encryption(intrada, outter, key_shifts)

    encryption(entrata, uscita, key_shifts)
    entrata.close()
    uscita.close()
