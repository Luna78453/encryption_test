intrada = open("testo_en.txt")

for ln in intrada:
    for o in ln:
        print(o, ord(o))