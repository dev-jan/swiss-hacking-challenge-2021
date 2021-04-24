from OpenSSL import crypto
from flask import Flask, Response, request, render_template

app = Flask(__name__)

def generate_key():
    key = crypto.PKey()
    key.generate_key(crypto.TYPE_RSA, 4096)
    return key

def generate_expired_ca():
    ca_key = generate_key()
    ca_cert = crypto.X509()
    ca_cert.get_subject().C = "CH"
    ca_cert.get_subject().ST = "Zurich"
    ca_cert.get_subject().L = "Zurich"
    ca_cert.get_subject().O = "SelfService Company Ltd"
    ca_cert.get_subject().OU = "SelfService IT Department"
    ca_cert.get_subject().CN = "SelfService Legacy Root CA"
    ca_cert.set_serial_number(420)
    ca_cert.gmtime_adj_notBefore(-(30 * 24 * 60 * 60))
    ca_cert.gmtime_adj_notAfter(-(1 * 24 * 60 * 60))
    ca_cert.set_issuer(ca_cert.get_subject())
    ca_cert.set_pubkey(ca_key)
    ca_cert.add_extensions(
        [
            crypto.X509Extension(b"basicConstraints", True, b"CA:TRUE,pathlen:0"),
            crypto.X509Extension(b"keyUsage", True, b"keyCertSign,cRLSign"),
            crypto.X509Extension(b"subjectKeyIdentifier", False, b"hash", subject=ca_cert),
        ]
    )
    ca_cert.add_extensions(
        [
            crypto.X509Extension(
                b"authorityKeyIdentifier", False, b"keyid:always", issuer=ca_cert
            ),
        ]
    )
    ca_cert.sign(ca_key, "sha512")
    return (ca_cert, ca_key)

def generate_client(ca_cert, ca_key):
    client_key = generate_key()
    client_cert = crypto.X509()
    client_cert.get_subject().C = "CH"
    client_cert.get_subject().ST = "Zurich"
    client_cert.get_subject().L = "Zurich"
    client_cert.get_subject().O = "SelfService Company Ltd"
    client_cert.get_subject().OU = "SelfService IT Department"
    client_cert.get_subject().CN = "seppli@self-service.local"
    client_cert.set_serial_number(1337)
    client_cert.gmtime_adj_notBefore(-(30 * 24 * 60 * 60))
    client_cert.gmtime_adj_notAfter(30 * 24 * 60 * 60)
    client_cert.set_issuer(ca_cert.get_subject())
    client_cert.set_pubkey(client_key)
    client_cert.add_extensions(
        [
            crypto.X509Extension(b"basicConstraints", True, b"CA:FALSE"),
            crypto.X509Extension(b"subjectKeyIdentifier", False, b"hash", subject=client_cert),
        ]
    )
    client_cert.add_extensions(
        [
            crypto.X509Extension(
                b"authorityKeyIdentifier", False, b"keyid:always", issuer=ca_cert
            ),
        ]
    )
    client_cert.sign(ca_key, "sha512")
    return client_cert

def to_pem(cert: crypto.X509):
    return crypto.dump_certificate(crypto.FILETYPE_PEM, cert).decode("utf-8")

def from_pem(cert: str):
    return crypto.load_certificate(crypto.FILETYPE_PEM, bytes(cert, "utf-8"))

gen_ca_cert, gen_ca_key = generate_expired_ca()
gen_client_cert = generate_client(gen_ca_cert, gen_ca_key)

def check_cert_valid(ca_cert: crypto.X509, client_cert: crypto.X509) -> bool:
    store = crypto.X509Store()
    store.add_cert(ca_cert)
    ctx = crypto.X509StoreContext(store, client_cert)
    ctx.verify_certificate()

@app.route("/")
def page_index():
    return render_template("index.html")

@app.route("/cert", methods=["POST"])
def page_cert():
    try:
        ca_cert = from_pem(request.files["ca"].read().decode("utf-8"))
        client_cert = from_pem(request.files["client"].read().decode("utf-8"))
        check_cert_valid(ca_cert, client_cert)

        expected_ca_key = crypto.dump_publickey(crypto.FILETYPE_PEM, gen_ca_cert.get_pubkey())
        actual_ca_key = crypto.dump_publickey(crypto.FILETYPE_PEM, ca_cert.get_pubkey())
        if ca_cert.get_subject().CN == "SelfService Legacy Root CA" and client_cert.get_subject().CN == "seppli@self-service.local" and expected_ca_key == actual_ca_key:
            return render_template("response.html", maybe_the_flag=open("/well-known/flag.txt", "r").read())
        else:
            return render_template("response.html", maybe_the_flag="Almost")
    except Exception as e:
        return render_template("response.html", maybe_the_flag="Got an error: " + str(e))

@app.route("/client.pem", methods=["GET"])
def download_client():
    return Response(to_pem(gen_client_cert),
        mimetype="application/x-pem-file",
        headers={"Content-disposition": "attachment; filename=client.pem"})

@app.route("/ca.pem", methods=["GET"])
def download_ca():
    return Response(to_pem(gen_ca_cert),
        mimetype="application/x-pem-file",
        headers={"Content-disposition": "attachment; filename=ca.pem"})

@app.route("/party")
def page_party():
    return render_template("party.html")

if __name__ == "__main__":
    app.run()
