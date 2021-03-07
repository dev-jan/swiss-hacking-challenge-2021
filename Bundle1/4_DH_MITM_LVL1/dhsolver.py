import base64
import json
from Crypto.Protocol.KDF import scrypt
from Crypto.Cipher import AES

AES_KEY_LEN = 16

def intToBase64String(input: int) -> str:
    return base64.b64encode(input.to_bytes((input.bit_length() + 7) // 8, "big")).decode('ascii')

# input given by alice
aliceMessageString = input("Enter the message json from alice: ")
aliceMessage = json.loads(aliceMessageString)
g = int.from_bytes(base64.b64decode(aliceMessage["g"]), "big")
p = int.from_bytes(base64.b64decode(aliceMessage["p"]), "big")
phi = int.from_bytes(base64.b64decode(aliceMessage["phi"]), "big")
pubA = int.from_bytes(base64.b64decode(aliceMessage["pubA"]), "big")
salt = base64.b64decode(aliceMessage["salt"])

# generate variables for fake Bob (=ourself)
b = 1 % phi
pubB = (g ** b) % p

# generate a new key to communicate as "Alice" with Bob
fakeA = b
fakePubA = pubB

dh_key = (pubA ** b) % p
key_bytes = dh_key.to_bytes((p.bit_length() + 7) // 8, "big")
aes_enc_key = scrypt(key_bytes, salt, AES_KEY_LEN, N=2**14, r=8, p=1)
answerToAlice = {
    "pubB": intToBase64String(pubB)
}
answerToBob = {
    "g": aliceMessage["g"],
    "p": aliceMessage["p"],
    "phi": aliceMessage["phi"],
    "pubA": intToBase64String(fakePubA),
    "salt": aliceMessage["salt"]
}
print("#########")
print("Drop the message from Alice and send the following message to Bob instead: \n" + json.dumps(answerToBob))

print("#########")
print("Drop the message from Bob and send the following message to alice instead: \n" + json.dumps(answerToAlice))

# establish session with bob
bobMessageString = input("Enter the answer from Bob: ")
bobMessage = json.loads(bobMessageString)
pubBfromBob = int.from_bytes(base64.b64decode(bobMessage["pubB"]), "big")
dh_key_bob = (pubBfromBob ** fakeA) % p
key_bytes_bob = dh_key_bob.to_bytes((p.bit_length() + 7) // 8, "big")
aes_enc_key_bob = scrypt(key_bytes_bob, salt, AES_KEY_LEN, N=2**14, r=8, p=1)


# hacky way to decrypt the communication
while True:
    # get the encrypted message from alice
    encryptedMessageString = input("Enter the encrypted message json from alice: ")
    encryptedMessage = json.loads(encryptedMessageString)
    nonce = base64.b64decode(encryptedMessage["nonce"])
    ctxt = base64.b64decode(encryptedMessage["ctxt"])
    tag = base64.b64decode(encryptedMessage["tag"])
    cipher = AES.new(aes_enc_key, AES.MODE_GCM, nonce)
    data = cipher.decrypt_and_verify(ctxt, tag)
    print("decrypted message: " + str(data))

    bobCipher = AES.new(aes_enc_key_bob, AES.MODE_GCM, nonce)
    bobCtxt, bobTag = bobCipher.encrypt_and_digest(data)
    messageToBob = {
        "nonce": encryptedMessage["nonce"],
        "ctxt": base64.b64encode(bobCtxt).decode('ascii'),
        "tag": base64.b64encode(bobTag).decode('ascii')
    }
    print("reencrypted message for bob: " + json.dumps(messageToBob))

    # get the encrypted message from bob
    encryptedAnswerFromBobString = input("Enter the encrypted message json from bob: ")
    encryptedAnswerFromBob = json.loads(encryptedAnswerFromBobString)
    nonceBob = base64.b64decode(encryptedAnswerFromBob["nonce"])
    ctxtBob = base64.b64decode(encryptedAnswerFromBob["ctxt"])
    tagBob = base64.b64decode(encryptedAnswerFromBob["tag"])
    bobAnswerCipher = AES.new(aes_enc_key_bob, AES.MODE_GCM, nonceBob)
    answerData = bobAnswerCipher.decrypt_and_verify(ctxtBob, tagBob)
    print("decrypted message: " + str(answerData))

    aliceCipher = AES.new(aes_enc_key, AES.MODE_GCM, nonceBob)
    ctxtForAlice, tagForAlice = aliceCipher.encrypt_and_digest(answerData)
    messageToAlice = {
        "nonce": encryptedAnswerFromBob["nonce"],
        "ctxt": base64.b64encode(ctxtForAlice).decode('ascii'),
        "tag": base64.b64encode(tagForAlice).decode('ascii')
    }
    print("reencrypted message for alice: " + json.dumps(messageToAlice))
