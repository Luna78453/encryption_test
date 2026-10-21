import os
from typing import List, IO

def shift(_input, _output, shift_amnt):
    if chr(_input).isalpha():   
        if chr(_input).islower():
            c = chr((_input - 97 + shift_amnt) % 26 + 97).encode(encoding="utf-8")
            #print('{} {} {} {}'.format(str(_input), ord(str(_input)) + shift_amnt, ord(str(_input)), c))
            _output.write(c)
        else: 
            c = chr((_input - 65 + shift_amnt) % 26 + 65).encode(encoding="utf-8")
            #print('{} {} {} {}'.format(str(_input), ord(str(_input)) + shift_amnt, ord(str(_input)), c))
            _output.write(c)
    else:
        _output.write(chr(_input).encode(encoding="utf-8"))

def encryption(_input, _output, shift_amnt):
    for line in _input:
        for char in line:
            shift(char, _output, shift_amnt)
 
 #dir: str, key: str, decrypt: bool, dir_out: Optional[str]
def caesar_encrypt(entrata: IO, shifts: int, decrypt: bool, uscita: IO):
    if decrypt:
        shifts *= -1

#   if str.lower(dir_out) == 'none':
#       with open(dir, "rb") as intrada, open(os.path.splitext(intrada.name)[0] + "_en.txt", "wb") as outter:
#           encryption(intrada, outter, shifts)    
#   else:
#       with open(dir, "rb") as intrada, open(dir_out, "wb") as outter:
#           encryption(intrada, outter, shifts)
    
    encryption(entrata, uscita, shifts)

    entrata.close()
    uscita.close()
