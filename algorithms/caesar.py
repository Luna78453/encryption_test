import os

def shift(_input, _output, shift_amnt):
    c = chr(_input + shift_amnt).encode(encoding="utf-8")
    #print('{} {} {} {}'.format(str(_input), ord(str(_input)) + shift_amnt, ord(str(_input)), c))
    _output.write(c)

def encryption(_input, _output, shift_amnt):
    for line in _input:
        for char in line:
            shift(char, _output, shift_amnt)
 
def caesar_encrypt(dir, shifts, decrypt, dir_out):
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
