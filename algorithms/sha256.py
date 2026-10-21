import os
from typing import List, Optional

def encryption(_input, _output):
    for line in _input:
        for char in line:
            _output.write(chr(char).encode(encoding="utf-8"))

def vigenere_encrypt(dir: str, key: str, decrypt: bool, dir_out: Optional[str]):
    if dir_out is None:
        with open(dir, "rb") as intrada, open(os.path.splitext(intrada.name)[0] + "_en.txt", "wb") as outter:
            encryption(intrada, outter)
    else:
        with open(dir, "rb") as intrada, open(dir_out, "wb") as outter:
            encryption(intrada, outter)

    intrada.close()
    outter.close()
