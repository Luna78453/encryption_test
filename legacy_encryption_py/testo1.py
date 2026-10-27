def ror(n,rotations,width):
    # ror method Source - https://stackoverflow.com/a/61581334
    # Posted by fuzzydrawrings, modified by community. See post 'Timeline' for change history
    # Retrieved 2026-10-22, License - CC BY-SA 4.0
    return (2**width-1)&(n>>rotations|n<<(width-rotations))

def truncate32bits(entrata: int):
    uscita = entrata & 0xFFFFFFFF

    return uscita

gofuckyourself = 0b10000000000000000000000000000000
lol = 0

a = ror(gofuckyourself, 17, 32)
b = ror(gofuckyourself, 19, 32)
c = gofuckyourself>>10

sosigma = a ^ b ^ c

print('{}{}'.format('0'*(32 - gofuckyourself.bit_length()), bin(gofuckyourself)[2:]))
print('{}{}'.format('0'*(32 - a.bit_length()), bin(a)[2:]))
print('{}{}'.format('0'*(32 - b.bit_length()), bin(b)[2:]))
print('{}{}'.format('0'*(32 - c.bit_length()), bin(c)[2:]))
print('{}{}'.format('0'*(32 - sosigma.bit_length()), bin(sosigma)[2:]))
print('{}{}'.format('0'*(32 - (lol.bit_length() + lol == 0)), bin(lol)[2:]))
