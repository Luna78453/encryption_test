import os

#!/bin/python3

#intrada = open("testo.txt")
#outter = open("testo_en.txt", "w")

def shift(_input, _output, shift_amnt):
    if _input.isalpha():
        c = chr(ord(_input) + shift_amnt)
        _output.write(c)

def encryption(_input, _output, shift_amnt):
    for line in _input:
        for char in line:
            shift(char, _output, shift_amnt)

def encrypt(decrypt):
    print("Enter file directory")
    dir = input(": ")

    print("Enter number of shifts")
    shifts = int(input(": "))

    if decrypt:
        shifts *= -1

    with open(dir) as intrada, open(os.path.splitext(intrada.name)[0] + "_en.txt", "w") as outter:
        encryption(intrada, outter, shifts)

    intrada.close()
    outter.close()

opt = ''

while True:
    while True:
        print("Select option: \nA) encrypt\nB) decrypt")
        opt = str.lower(input())

        if opt != 'a' and opt != 'b' and opt != 'q':
            print("Select one of the options presented.")

        break

    if opt == 'a':
        encrypt()
    elif opt == 'b':
        encrypt(True)
    elif opt == 'q':
        break