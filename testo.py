intrada = open("testo.txt", "rb")

for ln in intrada:
    for o in ln:
        print(o, chr(o))