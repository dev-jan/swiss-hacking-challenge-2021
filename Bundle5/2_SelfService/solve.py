# pip3 install pyopenssl
from OpenSSL import crypto

def to_pem(cert: crypto.X509):
    return crypto.dump_certificate(crypto.FILETYPE_PEM, cert).decode("utf-8")

def from_pem(cert: str):
    return crypto.load_certificate(crypto.FILETYPE_PEM, bytes(cert, "utf-8"))

def generate_key():
    key = crypto.PKey()
    key.generate_key(crypto.TYPE_RSA, 4096)
    return key

dummy_ca_key = generate_key()

# read and alter real CA
file = open('ca.pem', 'r')
ca = from_pem(file.read())
ca.gmtime_adj_notAfter(30 * 24 * 60 * 60)
# sign the altered CA with a dummy CA, the signature doens't get checked
ca.sign(dummy_ca_key, "sha512")
with open('alteredca.pem', 'w') as file:
    file.write(to_pem(ca))
