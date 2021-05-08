import hashlib

hash = '45D616FF7D5108BD93094FA15FE0E1D2'

result = hashlib.md5('1234'.encode()).hexdigest().upper()
print(result)

for i in range(10_000):
    text = 'HL{' + str(i) + '}'
    result = hashlib.md5(str(text).encode()).hexdigest().upper()
    print(str(text) + " => " + result)
    if (result == hash):
        print(f'FOUND!!!! i={text}')
        exit(1)

print('not found :(')
