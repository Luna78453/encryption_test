# Source - https://stackoverflow.com/a/61581334
# Posted by fuzzydrawrings, modified by community. See post 'Timeline' for change history
# Retrieved 2026-10-22, License - CC BY-SA 4.0

def ror(n,rotations,width):
    return (2**width-1)&(n>>rotations|n<<(width-rotations))

def rol(n,rotations,width):
    return (2**width-1)&(n<<rotations|n>>(width-rotations))

num = 16
rots = 4

print(bin(num))
print(bin(ror(num, rots, 8)))
print(bin(rol(num, rots, 8)))
