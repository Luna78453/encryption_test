from typing import IO

def ror(n: int,rotations: int,width: int):
    # ror method Source - https://stackoverflow.com/a/61581334
    # Posted by fuzzydrawrings, modified by community. See post 'Timeline' for change history
    # Retrieved 2026-10-22, License - CC BY-SA 4.0
    return (2**width-1)&(n>>rotations|n<<(width-rotations))

def truncate32bits(entrata: int):
    uscita = entrata & 0xFFFFFFFF

    return uscita

def encryption(entrata: IO, uscita: IO):
    h0 = 0x6a09e667
    h1 = 0xbb67ae85
    h2 = 0x3c6ef372
    h3 = 0xa54ff53a
    h4 = 0x510e527f
    h5 = 0x9b05688c
    h6 = 0x1f83d9ab
    h7 = 0x5be0cd19

    k = [
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
        ]

    work_a = h0
    work_b = h1
    work_c = h2
    work_d = h3
    work_e = h4
    work_f = h5
    work_g = h6
    work_h = h7

    message_block = bytearray()

    for line in entrata:
        for char in line:
            message_block += char.to_bytes()

    original_len = (len(message_block)*8).to_bytes(8, 'big')
    
    #print(message_block)
    #print(len(message_block))

    message_block += 0b10000000.to_bytes()

    num_of_blocks = len(message_block)//64 + 1
    num_of_bytes = 64*num_of_blocks

    message_block += bytearray(num_of_bytes - 8 - len(message_block))
    message_block += original_len

    #print(message_block)
    #print(len(message_block))

    bit512_chunks = [list(message_block[i:i + 64]) for i in range(0, len(list(message_block)), 64)]

    #print([bin(bit512_chunks[0][i]) for i in range(64)])

    for b in range(num_of_blocks):
        message_schedule = [0] * 64

        #print([bin(message_schedule[i]) for i in range(64)])

        for i in range(16):
            message_schedule[i] = ((bit512_chunks[b][i*4]<<24) + 
                                   (bit512_chunks[b][i*4 + 1]<<16) + 
                                   (bit512_chunks[b][i*4 + 2]<<8) + 
                                   bit512_chunks[b][i*4 + 3])
            #print(message_schedule[i].bit_length())

        #print([bin(message_schedule[i]) for i in range(64)])

        for i in range(16, 64):
            sigma0 = (ror(message_schedule[i - 15], 7, 32))^(ror(message_schedule[i - 15], 18, 32))^(message_schedule[i - 15]>>3)
            sigma1 = (ror(message_schedule[i - 2], 17, 32))^(ror(message_schedule[i - 2], 19, 32))^(message_schedule[i - 2]>>10)
            #print('{:02d}. sigma0: {}{}'.format(i, '0'*(32 - sigma0.bit_length()), bin(sigma0)[2:]))
            #print('{:02d}. sigma0: {}{}'.format(i, '0'*(32 - sigma1.bit_length()), bin(sigma1)[2:]))
            message_schedule[i] = truncate32bits(message_schedule[i - 16] + sigma0 + message_schedule[i - 7] + sigma1)

        #print([bin(message_schedule[i]) for i in range(64)])

        #for i in range(64):
        #    print('w{:02d}: {}{}'.format(i, '0'*(32 - message_schedule[i].bit_length()), bin(message_schedule[i])[2:]))

        for i in range(64):
            captsigma0 = ror(work_a, 2, 32)^ror(work_a, 13, 32)^ror(work_a, 22, 32)
            captsigma1 = ror(work_e, 6, 32)^ror(work_e, 11, 32)^ror(work_e, 25, 32)

            choice = (work_e&work_f) ^ ((~work_e)&work_g)
            majority = (work_a&work_b) ^ (work_a&work_c) ^ (work_b&work_c)

            temp1 = truncate32bits(work_h + captsigma1 + choice + k[i] + message_schedule[i])

            temp2 = truncate32bits(captsigma0 + majority)

            work_h = work_g
            work_g = work_f
            work_f = work_e
            work_e = truncate32bits(work_d + temp1)
            work_d = work_c
            work_c = work_b
            work_b = work_a
            work_a = truncate32bits(temp1 + temp2)

        h0 = truncate32bits(h0 + work_a)
        h1 = truncate32bits(h1 + work_b)
        h2 = truncate32bits(h2 + work_c)
        h3 = truncate32bits(h3 + work_d)
        h4 = truncate32bits(h4 + work_e)
        h5 = truncate32bits(h5 + work_f)
        h6 = truncate32bits(h6 + work_g)
        h7 = truncate32bits(h7 + work_h)

    o0 = h0.to_bytes(4, byteorder='big')
    o1 = h1.to_bytes(4, byteorder='big')
    o2 = h2.to_bytes(4, byteorder='big')
    o3 = h3.to_bytes(4, byteorder='big')
    o4 = h4.to_bytes(4, byteorder='big')
    o5 = h5.to_bytes(4, byteorder='big')
    o6 = h6.to_bytes(4, byteorder='big')
    o7 = h7.to_bytes(4, byteorder='big')

    o = bytes(o0 + o1 + o2 + o3 + o4 + o5 + o6 + o7)

    #print(o.hex())
    #print(len(o.hex()))
    
    #c = chr((_input - 97 + shift_key[_iterator]) % 26 + 97).encode(encoding="utf-8")
    #print('{} {} {} {}'.format(str(_input), ord(str(_input)) + shift_key[_iterator], ord(str(_input)), c))
    uscita.write(o.hex().encode(encoding="utf-8"))

def sha256_encrypt(entrata: IO, uscita: IO):
    encryption(entrata, uscita)
    entrata.close()
    uscita.close()
