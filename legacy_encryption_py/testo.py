iintrada = open("testo.txt", "rb")
intrada = iintrada.read()
decrypt = False

key_shifts = [int(c) - 97 for c in intrada]
key_shifts2 = [-(int(c) - 97) for c in intrada]

print(key_shifts)
print(key_shifts2)

iintrada.close()
